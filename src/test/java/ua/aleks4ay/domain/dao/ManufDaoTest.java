package ua.aleks4ay.domain.dao;

import org.junit.*;
import ua.aleks4ay.domain.model.Manufacture;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManufDaoTest {

    static Connection conn;
    static ManufDao dao;
    private static Manufacture manufacture1, manufacture2, manufacture3;
    private static List<Manufacture> manufactures = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new ManufDao(conn);
        manufacture1 = new Manufacture("1", "idOrd_1", 1, 5, "idTmc", "descr", 800, 600, 850, "emb");
        manufacture2 = new Manufacture("2", "idOrd_2", 2, 8, "idTmc", "descr", 900, 600, 850, "emb");
        manufacture3 = new Manufacture("3", "idOrd_3", 3, 9, "idTmc", "descr", 1000, 600, 850, "emb");
        manufactures.add(manufacture1);
        manufactures.add(manufacture2);
        manufactures.add(manufacture3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS manufacture CASCADE;");
        statement.execute("CREATE TABLE manufacture (" +
                "  iddoc CHARACTER VARYING(9)," +
                "  id_order CHARACTER VARYING(9)," +
                "  position INTEGER," +
                "  quantity INTEGER," +
                "  id_tmc CHARACTER VARYING(9)," +
                "  descr_second CHARACTER VARYING," +
                "  size_a INTEGER," +
                "  size_b INTEGER," +
                "  size_c INTEGER," +
                "  embodiment CHARACTER VARYING(9)" +
                ");");
        statement.execute("INSERT INTO manufacture(iddoc, position, id_order, quantity, id_tmc, descr_second, " +
                "size_a, size_b, size_c, embodiment) VALUES " +
                "('1', 1, 'idOrd_1', 5, 'idTmc', 'descr', 800, 600, 850, 'emb'), " +
                "('2', 2, 'idOrd_2', 8, 'idTmc', 'descr', 900, 600, 850, 'emb'), " +
                "('3', 3, 'idOrd_3', 9, 'idTmc', 'descr', 1000, 600, 850, 'emb');");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Manufacture expected = manufacture2;
        Manufacture actual = dao.getOneById("2-2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        Manufacture man4 = new Manufacture("4", "idOrd_4", 1, 5, "idTmc", "descr", 800, 600, 850, "emb");
        Manufacture man5 = new Manufacture("5", "idOrd_5", 1, 5, "idTmc", "descr", 800, 600, 850, "emb");
        List<Manufacture> newManufacture = new ArrayList<>();
        newManufacture.add(man4);
        newManufacture.add(man5);
        dao.saveAll(newManufacture);
        int expected = 5;
        List<Manufacture> manufacturesFromDB = dao.getAll();
        assertEquals(expected, manufacturesFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Manufacture expectedManufacture2 =
                new Manufacture("2", "ord_2up", 2, 8, "idTmc", "descr", 900, 600, 850, "emb");
        Manufacture expectedManufacture3 =
                new Manufacture("3", "ord_3up", 3, 9, "idTmc", "descr", 1000, 600, 850, "emb");
        List<Manufacture> newManufactures = new ArrayList<>();
        newManufactures.add(expectedManufacture3);
        newManufactures.add(expectedManufacture2);
        dao.updateAll(newManufactures);
        Manufacture actualManufacture2 = dao.getOneById("2-2");
        Manufacture actualManufacture3 = dao.getOneById("3-3");
        assertEquals(expectedManufacture2, actualManufacture2);
        assertEquals(expectedManufacture3, actualManufacture3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Manufacture> deletedManufactures = new ArrayList<>();
        deletedManufactures.add(manufacture1);
        deletedManufactures.add(manufacture3);
        dao.deleteAll(deletedManufactures);
        int expected = 1;
        List<Manufacture> manufacturesFromDB = dao.getAll();
        assertEquals(expected, manufacturesFromDB.size());
    }
}