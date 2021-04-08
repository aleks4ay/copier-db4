package ua.aleks4ay.domain.dao;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.aleks4ay.domain.model.Invoice;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InvoiseDaoTest {

    static Connection conn;
    static InvoiceDao dao;
    private static Invoice invoice1, invoice2, invoice3;
    private static List<Invoice> invoices = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new InvoiceDao(conn);
        invoice1 = new Invoice("1", "idOrdr_1", 11.1);
        invoice2 = new Invoice("2", "idOrdr_2", 22.2);
        invoice3 = new Invoice("3", "idOrdr_3", 33.3);
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS invoice CASCADE;");
        statement.execute("CREATE TABLE invoice (" +
                "  iddoc CHARACTER VARYING(9) PRIMARY KEY NOT NULL," +
                "  id_order CHARACTER VARYING(9)," +
                "  price NUMERIC(14,3)" +
                ");");
        statement.execute("INSERT INTO invoice(iddoc, id_order, price) VALUES " +
                "('1', 'idOrdr_1', 11.1), " +
                "('2', 'idOrdr_2', 22.2), " +
                "('3', 'idOrdr_3', 33.3);");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Invoice expected = invoice2;
        Invoice actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        Invoice invoice4 = new Invoice("4", "idOrdr_4", 44.4);
        Invoice invoice5 = new Invoice("5", "idOrdr_5", 55.5);
        List<Invoice> newInvoice = new ArrayList<>();
        newInvoice.add(invoice4);
        newInvoice.add(invoice5);
        dao.saveAll(newInvoice);
        int expected = 5;
        List<Invoice> invoicesFromDB = dao.getAll();
        assertEquals(expected, invoicesFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Invoice expectedInvoice2 = new Invoice("2", "id_2_up", 122.2);
        Invoice expectedInvoice3 = new Invoice("3", "id_3_up", 133.3);
        List<Invoice> newInvoices = new ArrayList<>();
        newInvoices.add(expectedInvoice3);
        newInvoices.add(expectedInvoice2);
        dao.updateAll(newInvoices);
        Invoice actualInvoice2 = dao.getOneById("2");
        Invoice actualInvoice3 = dao.getOneById("3");
        assertEquals(expectedInvoice2, actualInvoice2);
        assertEquals(expectedInvoice3, actualInvoice3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Invoice> deletedInvoices = new ArrayList<>();
        deletedInvoices.add(invoice1);
        deletedInvoices.add(invoice3);
        dao.deleteAll(deletedInvoices);
        int expected = 1;
        List<Invoice> invoicesFromDB = dao.getAll();
        assertEquals(expected, invoicesFromDB.size());
    }
}