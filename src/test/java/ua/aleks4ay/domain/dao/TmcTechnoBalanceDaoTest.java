package ua.aleks4ay.domain.dao;

import org.junit.*;
import ua.aleks4ay.domain.model.TmcBalance;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TmcTechnoBalanceDaoTest {

    static Connection conn;
    static TmcTechnoBalanceDao dao;
    private static TmcBalance tmc1, tmc2, tmc3;
    private static List<TmcBalance> tmcs = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new TmcTechnoBalanceDao(conn);
        tmc1 = new TmcBalance("1", 11, Timestamp.valueOf("2021-3-15 14:15:00"));
        tmc2 = new TmcBalance("2", 22, Timestamp.valueOf("2021-3-15 14:15:00"));
        tmc3 = new TmcBalance("3", 33, Timestamp.valueOf("2021-3-15 14:15:00"));
        tmcs.add(tmc1);
        tmcs.add(tmc2);
        tmcs.add(tmc3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS techno_item;");
        statement.execute("CREATE TABLE techno_item (" +
                "  id SERIAL PRIMARY KEY NOT NULL," +
                "  idtmc CHARACTER VARYING(9)," +
                "  store_c INTEGER DEFAULT 0," +
                "  period TIMESTAMP WITHOUT TIME ZONE" +
                ");");
        statement.execute("INSERT INTO techno_item(idtmc, store_c, period) VALUES " +
                "('1', 11, '2021-3-15 14:15:00'), " +
                "('2', 22, '2021-3-15 14:15:00'), " +
                "('3', 33, '2021-3-15 14:15:00');");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        TmcBalance expected = tmc2;
        TmcBalance actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        TmcBalance tmc4 = new TmcBalance("4", 44, Timestamp.valueOf("2021-3-15 14:15:00"));
        TmcBalance tmc5 = new TmcBalance("5", 55, Timestamp.valueOf("2021-3-15 14:15:00"));
        List<TmcBalance> newTmc = new ArrayList<>();
        newTmc.add(tmc4);
        newTmc.add(tmc5);
        dao.saveAll(newTmc);
        int expected = 5;
        List<TmcBalance> tmcsFromDB = dao.getAll();
        assertEquals(expected, tmcsFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        TmcBalance expectedTmc2 = new TmcBalance("2", 202, Timestamp.valueOf("2021-3-15 14:15:00"));
        TmcBalance expectedTmc3 = new TmcBalance("3", 303, Timestamp.valueOf("2021-3-15 14:15:00"));
        List<TmcBalance> newTmcs = new ArrayList<>();
        newTmcs.add(expectedTmc3);
        newTmcs.add(expectedTmc2);
        dao.updateAll(newTmcs);
        TmcBalance actualTmc2 = dao.getOneById("2");
        TmcBalance actualTmc3 = dao.getOneById("3");
        assertEquals(expectedTmc2, actualTmc2);
        assertEquals(expectedTmc3, actualTmc3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<TmcBalance> deletedTmcs = new ArrayList<>();
        deletedTmcs.add(tmc1);
        deletedTmcs.add(tmc3);
        dao.deleteAll(deletedTmcs);
        int expected = 1;
        List<TmcBalance> tmcsFromDB = dao.getAll();
        assertEquals(expected, tmcsFromDB.size());
    }
}