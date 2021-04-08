package ua.aleks4ay.domain.dao;

import org.junit.*;
import ua.aleks4ay.domain.model.Journal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JournalDaoTest {
    private static Connection conn;
    private static JournalDao dao;
    private static Journal journal1, journal2, journal3;
    private static List<Journal> journals = new ArrayList<>();

    @BeforeClass
    public static void createJournals() {
        conn = Utils.getConnectionTest();
        dao = new JournalDao(conn);
        journal1 = new Journal("1", "J-0001", Timestamp.valueOf("2021-3-15 14:15:00"));
        journal2 = new Journal("2", "J-0002", Timestamp.valueOf("2021-3-15 14:15:00"));
        journal3 = new Journal("3", "J-0003", Timestamp.valueOf("2021-3-15 14:15:00"));
        journals.add(journal1);
        journals.add(journal2);
        journals.add(journal3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS journal;");
        statement.execute("CREATE TABLE journal (" +
                "  iddoc CHARACTER VARYING(9) PRIMARY KEY NOT NULL," +
                "  docno CHARACTER VARYING(10)," +
                "  datecreate TIMESTAMP WITHOUT TIME ZONE" +
                ");");
        statement.execute("INSERT INTO journal (iddoc, docno, datecreate) VALUES " +
                "('1', 'J-0001', '2021-3-15 14:15:00'), " +
                "('2', 'J-0002', '2021-3-15 14:15:00'), " +
                "('3', 'J-0003', '2021-3-15 14:15:00');");
    }

    @Test
    public void saveAllTest() throws Exception {
        Journal journal4 = new Journal("4", "J-0004", Timestamp.valueOf("2021-3-15 14:15:00"));
        Journal journal5 = new Journal("5", "J-0005", Timestamp.valueOf("2021-3-15 14:15:00"));
        List<Journal> newJournals = new ArrayList<>();
        newJournals.add(journal4);
        newJournals.add(journal5);
        dao.saveAll(newJournals);
        int expected = 5;
        List<Journal> journalsFromDB = dao.getAll();
        assertEquals(expected, journalsFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Journal expectedJournal2 = new Journal("2", "J-2-updat", Timestamp.valueOf("2021-3-15 14:15:00"));
        Journal expectedJournal3 = new Journal("3", "J-3-updat", Timestamp.valueOf("2021-3-15 14:15:00"));
        List<Journal> newJournals = new ArrayList<>();
        newJournals.add(expectedJournal3);
        newJournals.add(expectedJournal2);
        dao.updateAll(newJournals);
        Journal actualJournal2 = dao.getOneById("2");
        Journal actualJournal3 = dao.getOneById("3");
        assertEquals(expectedJournal2, actualJournal2);
        assertEquals(expectedJournal3, actualJournal3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Journal> deletedJournals = new ArrayList<>();
        deletedJournals.add(journal1);
        deletedJournals.add(journal3);
        dao.deleteAll(deletedJournals);
        int expected = 1;
        List<Journal> journalsFromDB = dao.getAll();
        assertEquals(expected, journalsFromDB.size());
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Journal expected = journal2;
        Journal actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }
}