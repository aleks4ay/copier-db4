package ua.aleks4ay.domain.dao;

import org.junit.*;
import ua.aleks4ay.domain.model.Tmc;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TmcDaoTest {

    static Connection conn;
    static TmcDao dao;
    private static Tmc tmc1, tmc2, tmc3;
    private static List<Tmc> tmcs = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new TmcDao(conn);
        tmc1 = new Tmc("1", "p_1", "art", "code1", "descr1", 1, "descrAll_1", "type_1", 800, 600, 850);
        tmc2 = new Tmc("2", "p_2", "art", "code2", "descr2", 2, "descrAll_2", "type_2", 800, 600, 850);
        tmc3 = new Tmc("3", "p_3", "art", "code3", "descr3", 3, "descrAll_3", "type_3", 800, 600, 850);
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
        statement.execute("DROP TABLE if EXISTS tmc;");
        statement.execute("CREATE TABLE tmc (" +
                "  id CHARACTER VARYING(9) PRIMARY KEY NOT NULL," +
                "  id_parent CHARACTER VARYING(9)," +
                "  art CHARACTER VARYING(24)," +
                "  code CHARACTER VARYING(5)," +
                "  descr CHARACTER VARYING(50)," +
                "  is_folder INTEGER," +
                "  size_a INTEGER," +
                "  size_b INTEGER," +
                "  size_c INTEGER," +
                "  descr_all CHARACTER VARYING(100)," +
                "  type CHARACTER VARYING(9)" +
                ");");
        statement.execute("INSERT INTO tmc(id, id_parent, code, art, descr, is_folder, descr_all, type, " +
                "size_a, size_b, size_c) VALUES " +
                "('1', 'p_1', 'code1', 'art', 'descr1', 1, 'descrAll_1', 'type_1', 800, 600, 850), " +
                "('2', 'p_2', 'code2', 'art', 'descr2', 2, 'descrAll_2', 'type_2', 800, 600, 850), " +
                "('3', 'p_3', 'code3', 'art', 'descr3', 3, 'descrAll_3', 'type_3', 800, 600, 850);");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Tmc expected = tmc2;
        Tmc actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        Tmc tmc4 = new Tmc("4", "p_4", "code4", "art", "descr4", 4, "descrAll_4", "type_4", 800, 600, 850);
        Tmc tmc5 = new Tmc("5", "p_5", "code5", "art", "descr5", 5, "descrAll_5", "type_5", 800, 600, 850);
        List<Tmc> newTmc = new ArrayList<>();
        newTmc.add(tmc4);
        newTmc.add(tmc5);
        dao.saveAll(newTmc);
        int expected = 5;
        List<Tmc> tmcsFromDB = dao.getAll();
        assertEquals(expected, tmcsFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Tmc expectedTmc2 = new Tmc("2", "p_2", "code2", "art", "descr2_update", 2, "descrAll_2", "type_2", 800, 600, 850);
        Tmc expectedTmc3 = new Tmc("3", "p_3", "code3", "art", "descr3_update", 3, "descrAll_3", "type_3", 800, 600, 850);
        List<Tmc> newTmcs = new ArrayList<>();
        newTmcs.add(expectedTmc3);
        newTmcs.add(expectedTmc2);
        dao.updateAll(newTmcs);
        Tmc actualTmc2 = dao.getOneById("2");
        Tmc actualTmc3 = dao.getOneById("3");
        assertEquals(expectedTmc2, actualTmc2);
        assertEquals(expectedTmc3, actualTmc3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Tmc> deletedTmcs = new ArrayList<>();
        deletedTmcs.add(tmc1);
        deletedTmcs.add(tmc3);
        dao.deleteAll(deletedTmcs);
        int expected = 1;
        List<Tmc> tmcsFromDB = dao.getAll();
        assertEquals(expected, tmcsFromDB.size());
    }
}