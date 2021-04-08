package ua.aleks4ay.domain.dao;

import org.junit.*;
import ua.aleks4ay.domain.model.Description;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DescriptionDaoTest {

    static Connection conn;
    static DescriptionDao dao;
    private static Description description1, description2, description3;
    private static List<Description> descriptions = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new DescriptionDao(conn);
        description1 = new Description("1", 1, "idTmc", 5, "descrSecond", 800, 600, 150, "emb", 11.1);
        description2 = new Description("2", 2, "idTmc", 5, "descrSecond", 800, 600, 250, "emb", 22.2);
        description3 = new Description("3", 3, "idTmc", 5, "descrSecond", 800, 600, 350, "emb", 33.3);
        descriptions.add(description1);
        descriptions.add(description2);
        descriptions.add(description3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS descriptions;");
        statement.execute("CREATE TABLE descriptions (" +
                "  iddoc CHARACTER VARYING(9)," +
                "  position INTEGER," +
                "  id_tmc CHARACTER VARYING(9)," +
                "  quantity INTEGER," +
                "  descr_second CHARACTER VARYING(300)," +
                "  size_a INTEGER," +
                "  size_b INTEGER," +
                "  size_c INTEGER," +
                "  embodiment CHARACTER VARYING," +
                "  price NUMERIC(14,3)" +
                ");");
        statement.execute("INSERT INTO descriptions (iddoc, position, id_tmc, quantity, descr_second, " +
                "size_a, size_b, size_c, embodiment, price) VALUES " +
                "('1', 1, 'idTmc', 5, 'descrSecond', 800, 600, 150, 'emb', 11.1)," +
                "('2', 2, 'idTmc', 5, 'descrSecond', 800, 600, 250, 'emb', 22.2)," +
                "('3', 3, 'idTmc', 5, 'descrSecond', 800, 600, 350, 'emb', 33.3);");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Description expected = description2;
        Description actual = dao.getOneById("2-2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        Description description4 = new Description("4", 4, "idTmc", 5, "descrSecond", 800, 600, 150, "emb", 11.1);
        Description description5 = new Description("5", 5, "idTmc", 5, "descrSecond", 800, 600, 150, "emb", 11.1);
        List<Description> newDescription = new ArrayList<>();
        newDescription.add(description4);
        newDescription.add(description5);
        dao.saveAll(newDescription);
        int expected = 5;
        List<Description> descriptionsFromDB = dao.getAll();
        assertEquals(expected, descriptionsFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Description expectedDescription2 = new Description("2", 2, "idTmc", 5, "update", 800, 600, 950, "emb", 91.1);
        Description expectedDescription3 = new Description("3", 3, "idTmc", 5, "update", 800, 600, 950, "emb", 91.1);
        List<Description> newDescriptions = new ArrayList<>();
        newDescriptions.add(expectedDescription3);
        newDescriptions.add(expectedDescription2);
        dao.updateAll(newDescriptions);
        Description actualDescription2 = dao.getOneById("2-2");
        Description actualDescription3 = dao.getOneById("3-3");
        assertEquals(expectedDescription2, actualDescription2);
        assertEquals(expectedDescription3, actualDescription3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Description> deletedDescriptions = new ArrayList<>();
        deletedDescriptions.add(description1);
        deletedDescriptions.add(description3);
        dao.deleteAll(deletedDescriptions);
        int expected = 1;
        List<Description> descriptionsFromDB = dao.getAll();
        assertEquals(expected, descriptionsFromDB.size());
    }
}