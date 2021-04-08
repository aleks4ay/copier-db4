package ua.aleks4ay.domain.dao;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.aleks4ay.domain.model.InvoiceDescription;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InvoiceDescriptionDaoTest {

    static Connection conn;
    static InvoiceDescriptionDao dao;
    private static InvoiceDescription invoiceDescription1, invoiceDescription2, invoiceDescription3;

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new InvoiceDescriptionDao(conn);
        invoiceDescription1 = new InvoiceDescription("1", "idTmc", 1, 11, 11.1);
        invoiceDescription2 = new InvoiceDescription("2", "idTmc", 2, 22, 22.2);
        invoiceDescription3 = new InvoiceDescription("3", "idTmc", 3, 33, 33.3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS invoice_descr;");
        statement.execute("CREATE TABLE invoice_descr (" +
                "  id_invoice CHARACTER VARYING(9)," +
                "  id_tmc CHARACTER VARYING(9)," +
                "  position INTEGER," +
                "  quantity INTEGER," +
                "  payment NUMERIC(14,3)" +
                ");");
        statement.execute("INSERT INTO invoice_descr (id_invoice, id_tmc, position, quantity, payment) VALUES " +
                "('1', 'idTmc', 1, 11, 11.1)," +
                "('2', 'idTmc', 2, 22, 22.2)," +
                "('3', 'idTmc', 3, 33, 33.3);");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        InvoiceDescription expected = invoiceDescription2;
        InvoiceDescription actual = dao.getOneById("2-2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        InvoiceDescription invoiceDescription4 = new InvoiceDescription("4", "idTmc", 4, 11, 411.1);
        InvoiceDescription invoiceDescription5 = new InvoiceDescription("5", "idTmc", 5, 11, 511.1);
        List<InvoiceDescription> newInvoiceDescription = new ArrayList<>();
        newInvoiceDescription.add(invoiceDescription4);
        newInvoiceDescription.add(invoiceDescription5);
        dao.saveAll(newInvoiceDescription);
        int expected = 5;
        List<InvoiceDescription> invoiceDescriptionsFromDB = dao.getAll();
        assertEquals(expected, invoiceDescriptionsFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        InvoiceDescription expectedInvoiceDescription2 = new InvoiceDescription("2", "idTmc_up", 2, 11, 91.1);
        InvoiceDescription expectedInvoiceDescription3 = new InvoiceDescription("3", "idTmc_up", 3, 22, 91.1);
        List<InvoiceDescription> newInvoiceDescriptions = new ArrayList<>();
        newInvoiceDescriptions.add(expectedInvoiceDescription3);
        newInvoiceDescriptions.add(expectedInvoiceDescription2);
        dao.updateAll(newInvoiceDescriptions);
        InvoiceDescription actualInvoiceDescription2 = dao.getOneById("2-2");
        InvoiceDescription actualInvoiceDescription3 = dao.getOneById("3-3");
        assertEquals(expectedInvoiceDescription2, actualInvoiceDescription2);
        assertEquals(expectedInvoiceDescription3, actualInvoiceDescription3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<InvoiceDescription> deletedInvoiceDescriptions = new ArrayList<>();
        deletedInvoiceDescriptions.add(invoiceDescription1);
        deletedInvoiceDescriptions.add(invoiceDescription3);
        dao.deleteAll(deletedInvoiceDescriptions);
        int expected = 1;
        List<InvoiceDescription> invoiceDescriptionsFromDB = dao.getAll();
        assertEquals(expected, invoiceDescriptionsFromDB.size());
    }
}