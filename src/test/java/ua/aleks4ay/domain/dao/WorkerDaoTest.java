package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Worker;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class WorkerDaoTest {

    static Connection conn;
    static WorkerDao dao;
    private static Worker worker1, worker2, worker3;
    private static List<Worker> workers = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new WorkerDao(conn);
        worker1 = new Worker("1", "worker1");
        worker2 = new Worker("2", "worker2");
        worker3 = new Worker("3", "worker3");
        workers.add(worker1);
        workers.add(worker2);
        workers.add(worker3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS worker;");
        statement.execute("CREATE TABLE worker (" +
                "  id VARCHAR(9) PRIMARY KEY NOT NULL," +
                "  name VARCHAR" +
                ");");
        statement.execute("INSERT INTO worker(id, name) VALUES ('1', 'worker1'), ('2', 'worker2'), ('3', 'worker3');");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Worker expected = worker2;
        Worker actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        Worker worker4 = new Worker("4", "worker4");
        Worker worker5 = new Worker("5", "worker5");
        List<Worker> newWorker = new ArrayList<>();
        newWorker.add(worker4);
        newWorker.add(worker5);
        dao.saveAll(newWorker);
        int expected = 5;
        List<Worker> workersFromDB = dao.getAll();
        assertEquals(expected, workersFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Worker expectedWorker2 = new Worker("2", "worker2_update");
        Worker expectedWorker3 = new Worker("3", "worker3_update");
        List<Worker> newWorkers = new ArrayList<>();
        newWorkers.add(expectedWorker3);
        newWorkers.add(expectedWorker2);
        dao.updateAll(newWorkers);
        Worker actualWorker2 = dao.getOneById("2");
        Worker actualWorker3 = dao.getOneById("3");
        assertEquals(expectedWorker2, actualWorker2);
        assertEquals(expectedWorker3, actualWorker3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Worker> deletedWorkers = new ArrayList<>();
        deletedWorkers.add(worker1);
        deletedWorkers.add(worker3);
        dao.deleteAll(deletedWorkers);
        int expected = 1;
        List<Worker> workersFromDB = dao.getAll();
        assertEquals(expected, workersFromDB.size());
    }
}