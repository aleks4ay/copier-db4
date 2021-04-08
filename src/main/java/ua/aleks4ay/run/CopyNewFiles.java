package ua.aleks4ay.run;

import java.io.*;
import java.sql.Connection;
import java.util.*;
import java.nio.file.Files;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.copier.*;
import ua.aleks4ay.domain.dao.InvoiceDao;
import ua.aleks4ay.domain.dao.JournalDao;
import ua.aleks4ay.domain.dao.OrderDao;
import ua.aleks4ay.domain.dao.Utils;
import ua.aleks4ay.domain.model.Invoice;
import ua.aleks4ay.domain.model.Journal;
import ua.aleks4ay.domain.model.Order;
import ua.aleks4ay.domain.tools.DataControl;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CopyNewFiles {

    private static String serverPath = null;
    private static String dbfPath = null;
    private static final Logger log = LoggerFactory.getLogger(CopyNewFiles.class);
    private long id = 0L;

    static {
        try (InputStream in = CopyNewFiles.class.getClassLoader().getResourceAsStream("database.properties")){
            Properties properties = new Properties();
            properties.load(in);
            serverPath = properties.getProperty("server1C.path");
            dbfPath = properties.getProperty("dbReader.path");
            log.debug("Loaded properties as Stream: server1C.path = {}, dbReader.path = {}.", serverPath, dbfPath);
        } catch (IOException e) {
            log.warn("Exception during Loaded properties from file {}.", new File("/database.properties").getPath(), e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        new CopyNewFiles().update();
    }

    public void update() /*throws SQLException, IOException, ClassNotFoundException*/ {
//        boolean needWrite;
        while (true) {
            log.info("Start copy filas from 1C.");
            long oldId = id;
            long t1 = System.currentTimeMillis();
//            needWrite = false;
            boolean isNewClient = copyFiles("SC172");
            boolean isNewWorker = copyFiles("SC1670");
            boolean isNewEmbodiment = copyFiles("SC14716");
            boolean isNewTmc = copyFiles("SC302");
            boolean isNewBalance = copyFiles("RG1253");
            boolean isNewJournal = copyFiles("1SJOURN");
            boolean isNewOrder = copyFiles("DH1898");
            boolean isNewDescription = copyFiles("DT1898");
            boolean isNewManuf = copyFiles("DT2728");
            boolean isNewInvoice = copyFiles("DH3592");
            boolean isNewInvoiceDescription = copyFiles("DT3592");


            if (oldId != id) {
                System.out.println("id = " + id);
                if (isNewEmbodiment) {
                    new CopyEmbodiment().doCopy();
                }
                if (isNewClient) {
                    new CopyClient().doCopy();
                }
                if (isNewWorker) {
                    new CopyWorker().doCopy();
                }
                if (isNewTmc) {
                    new CopyTmc().doCopy();
                }
                if (isNewBalance) {
                    new CopyTmcBalance().doCopy();
                }
                List<String> idJournals = new ArrayList<>();
                if (isNewJournal) {
                    idJournals = new CopyJournal().doCopyWithIdJournal();
                } else if (isNewOrder) {
                    Connection connection = Utils.getConnection();
                    JournalDao journalDao = new JournalDao(connection);
                    List<Journal> journals = journalDao.getAll();
                    idJournals = journalDao.getAllIdDoc(journals);
                    Utils.closeConnection(connection);
                }

                List<String> idOrders = new ArrayList<>();
                if (isNewOrder) {
                    idOrders = new CopyOrder().doCopyWithIdJournal(idJournals);
                }
                if (! isNewOrder & (isNewDescription | isNewManuf | isNewInvoice)) {
                    Connection connection = Utils.getConnection();
                    OrderDao orderDao = new OrderDao(connection);
                    List<Order> orders = orderDao.getAll();
                    idOrders = orderDao.getAllIdDoc(orders);
                    Utils.closeConnection(connection);
                }
                List<String> idInvoice = new ArrayList<>();
                if (isNewInvoice) {
                    idInvoice = new CopyInvoice().doCopyWithIdOrders(idOrders);
                }
                if (isNewInvoiceDescription){
                    if (! isNewInvoice) {
                        Connection connection = Utils.getConnection();
                        InvoiceDao invoiceDao = new InvoiceDao(connection);
                        List<Invoice> invoices = invoiceDao.getAll();
                        idInvoice = invoiceDao.getAllIdDoc(invoices);
                        Utils.closeConnection(connection);
                    }
                    new CopyInvoiceDescription().doCopyWithIdInvoice(idInvoice);
                }
                if (isNewDescription) {
                    new CopyDescription().doCopyWithIdOrders(idOrders);
                }
                if (isNewManuf) {
                    new CopyManuf().doCopyWithIdOrders(idOrders);
                }
                DataControl.writeTimeChange();
                DataControl.writeTimeChangeFrom1C();
            }
            long t2 = System.currentTimeMillis();
            log.info("Total time for copy = {} c.", (double) ((t2 - t1) / 1000));

            try {
                Thread.sleep(15 * 60 * 1000); // sleep 15 min
            } catch (InterruptedException e) {
                log.warn("Exception during sleep 15 min.", e);
            }

        }
    }

    private boolean copyFiles(String filePath) {
        File fromFile1 = new File(serverPath + "\\" + filePath + ".DBF");
        File toFile1 = new File(dbfPath + "\\" + filePath + ".DBF");
        File fromFile2 = new File(serverPath + "\\" + filePath + ".CDX");
        File toFile2 = new File(dbfPath + "\\" + filePath + ".CDX");
        return copyOneFile(fromFile1, toFile1) | copyOneFile(fromFile2, toFile2);
    }

    private boolean copyOneFile(File fromFile, File toFile) {
        if (fromFile.lastModified() != toFile.lastModified()) {
            try {
                log.debug("new file '{}'.", toFile.getName());
                id ++;
                Files.copy(fromFile.toPath(), toFile.toPath(), REPLACE_EXISTING);
                return true;
            } catch (IOException e) {
                log.warn("Exception during copy '{}'.", toFile.getName(), e);
            }
        }
        return false;
    }
}
