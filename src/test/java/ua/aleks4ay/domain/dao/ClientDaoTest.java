package ua.aleks4ay.domain.dao;

import org.junit.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import ua.aleks4ay.domain.model.Client;

public class ClientDaoTest {
    private static Connection conn;
    private static ClientDao dao;
    private static Client client1, client2, client3;
    private static List<Client> clients = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new ClientDao(conn);
        client1 = new Client("1", "client1");
        client2 = new Client("2", "client2");
        client3 = new Client("3", "client3");
        clients.add(client1);
        clients.add(client2);
        clients.add(client3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS client;");
        statement.execute("CREATE TABLE client (" +
                "  id VARCHAR(9) PRIMARY KEY NOT NULL," +
                "  name VARCHAR" +
                ");");
        statement.execute("INSERT INTO client(id, name) VALUES ('1', 'client1'), ('2', 'client2'), ('3', 'client3');");
    }

    @Test
    public void saveAllTest() throws Exception {
        Client client4 = new Client("4", "client4");
        Client client5 = new Client("5", "client5");
        List<Client> newClients = new ArrayList<>();
        newClients.add(client4);
        newClients.add(client5);
        dao.saveAll(newClients);
        int expected = 5;
        List<Client> clientsFromDB = dao.getAll();
        assertEquals(expected, clientsFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Client expectedClient2 = new Client("2", "client2_update");
        Client expectedClient3 = new Client("3", "client3_update");
        List<Client> newClients = new ArrayList<>();
        newClients.add(expectedClient3);
        newClients.add(expectedClient2);
        dao.updateAll(newClients);
        Client actualClient2 = dao.getOneById("2");
        Client actualClient3 = dao.getOneById("3");
        assertEquals(expectedClient2, actualClient2);
        assertEquals(expectedClient3, actualClient3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Client> deletedClients = new ArrayList<>();
        deletedClients.add(client1);
        deletedClients.add(client3);
        dao.deleteAll(deletedClients);
        int expected = 1;
        List<Client> clientsFromDB = dao.getAll();
        assertEquals(expected, clientsFromDB.size());
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Client expected = client2;
        Client actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }

    @Test
    public void fillEntityStatementTest() throws Exception {
        String sqlSave = "INSERT INTO client (name, id) VALUES (?, ?);";
        Client actualClient = new Client("6", "client_for_6");
        PreparedStatement ps = conn.prepareStatement(sqlSave);
        dao.fillEntityStatement(ps, actualClient);
        ps.execute();
        Client filledAndSavedClient = dao.getOneById(actualClient.getId());
        assertEquals(filledAndSavedClient, actualClient);
    }

    @Test
    public void fillOnlyIdStatementTest() throws Exception {
        Client expected = client3;
        Client actual = null;
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM client WHERE id=?;");
        dao.fillOnlyIdStatement(ps, "3");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            actual = new Client(rs.getString("id"), rs.getString("name"));
        }
        assertEquals(expected, actual);
    }

    @Test
    public void readEntityTest() throws Exception {
        Client expected = client2;
        Client actual = null;
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM client WHERE id = '2';");
        if (rs.next()) {
            actual = dao.readEntity(rs);
        }
        assertEquals(expected, actual);
    }
}