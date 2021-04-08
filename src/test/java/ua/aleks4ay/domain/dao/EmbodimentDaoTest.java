package ua.aleks4ay.domain.dao;

import static org.junit.Assert.assertEquals;

import org.junit.*;
import ua.aleks4ay.domain.model.Embodiment;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmbodimentDaoTest {
    static Connection conn;
    static EmbodimentDao dao;
    private static Embodiment embodiment1, embodiment2, embodiment3;
    private static List<Embodiment> workers = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new EmbodimentDao(conn);
        embodiment1 = new Embodiment("1", "embodiment1");
        embodiment2 = new Embodiment("2", "embodiment2");
        embodiment3 = new Embodiment("3", "embodiment3");
        workers.add(embodiment1);
        workers.add(embodiment2);
        workers.add(embodiment3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS embodiment;");
        statement.execute("CREATE TABLE embodiment (" +
                "  id VARCHAR(9) PRIMARY KEY NOT NULL," +
                "  description VARCHAR" +
                ");");
        statement.execute("INSERT INTO embodiment(id, description) VALUES " +
                "('1', 'embodiment1'), ('2', 'embodiment2'), ('3', 'embodiment3');");
    }

    @Test
    public void getOneById() throws Exception {
        Embodiment expected = embodiment2;
        Embodiment actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAll() throws Exception {
        Embodiment embodiment4 = new Embodiment("4", "embodiment4");
        Embodiment embodiment5 = new Embodiment("5", "embodiment5");
        List<Embodiment> newEmbodiments = new ArrayList<>();
        newEmbodiments.add(embodiment4);
        newEmbodiments.add(embodiment5);
        dao.saveAll(newEmbodiments);
        int expected = 5;
        List<Embodiment> embodimentsFromDB = dao.getAll();
        assertEquals(expected, embodimentsFromDB.size());
    }

    @Test
    public void updateAll() throws Exception {
        Embodiment expectedEmbodiment2 = new Embodiment("2", "embodiment2_update");
        Embodiment expectedEmbodiment3 = new Embodiment("3", "embodiment3_update");
        List<Embodiment> newEmbodiments = new ArrayList<>();
        newEmbodiments.add(expectedEmbodiment3);
        newEmbodiments.add(expectedEmbodiment2);
        dao.updateAll(newEmbodiments);
        Embodiment actualEmbodiment2 = dao.getOneById("2");
        Embodiment actualEmbodiment3 = dao.getOneById("3");
        assertEquals(expectedEmbodiment2, actualEmbodiment2);
        assertEquals(expectedEmbodiment3, actualEmbodiment3);
    }

    @Test
    public void deleteAll() throws Exception {
        List<Embodiment> deletedEmbodiments = new ArrayList<>();
        deletedEmbodiments.add(embodiment1);
        deletedEmbodiments.add(embodiment3);
        dao.deleteAll(deletedEmbodiments);
        int expected = 1;
        List<Embodiment> embodimentFromDB = dao.getAll();
        assertEquals(expected, embodimentFromDB.size());
    }

}