package ua.aleks4ay.domain.dao;

import org.junit.*;
import ua.aleks4ay.domain.model.Order;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderDaoTest {

    static Connection conn;
    static OrderDao dao;
    private static Order order1, order2, order3;
    private static List<Order> orders = new ArrayList<>();

    @BeforeClass
    public static void createClients() {
        conn = Utils.getConnectionTest();
        dao = new OrderDao(conn);
        order1 = new Order("1", "idCl_1", "idMng_1", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 11.1);
        order2 = new Order("2", "idCl_2", "idMng_2", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 22.2);
        order3 = new Order("3", "idCl_3", "idMng_3", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 33.3);
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @AfterClass
    public static void endTest() {
        Utils.closeConnection(conn);
    }

    @Before
    public void setUp() throws Exception {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE if EXISTS orders CASCADE;");
        statement.execute("CREATE TABLE orders (" +
                "  iddoc CHARACTER VARYING(9) PRIMARY KEY NOT NULL," +
                "  id_client CHARACTER VARYING(9)," +
                "  id_manager CHARACTER VARYING(9)," +
                "  duration INTEGER," +
                "  t_factory TIMESTAMP WITHOUT TIME ZONE," +
                "  price NUMERIC(14,3));");
        statement.execute("INSERT INTO orders(iddoc, id_client, id_manager, duration, t_factory, price) VALUES " +
                "('1', 'idCl_1', 'idMng_1', 14, '2021-3-15 14:15:00', 11.1), " +
                "('2', 'idCl_2', 'idMng_2', 14, '2021-3-15 14:15:00', 22.2), " +
                "('3', 'idCl_3', 'idMng_3', 14, '2021-3-15 14:15:00', 33.3);");
    }

    @Test
    public void getOneByIdTest() throws Exception {
        Order expected = order2;
        Order actual = dao.getOneById("2");
        assertEquals(expected, actual);
    }

    @Test
    public void saveAllTest() throws Exception {
        Order order4 = new Order("4", "idCl_4", "idMng_4", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 44.4);
        Order order5 = new Order("5", "idCl_5", "idMng_5", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 55.5);
        List<Order> newOrder = new ArrayList<>();
        newOrder.add(order4);
        newOrder.add(order5);
        dao.saveAll(newOrder);
        int expected = 5;
        List<Order> ordersFromDB = dao.getAll();
        assertEquals(expected, ordersFromDB.size());
    }

    @Test
    public void updateAllTest() throws Exception {
        Order expectedOrder2 = new Order("2", "idCl_up", "idMng_2", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 122.2);
        Order expectedOrder3 = new Order("3", "idCl_up", "idMng_3", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 133.3);
        List<Order> newOrders = new ArrayList<>();
        newOrders.add(expectedOrder3);
        newOrders.add(expectedOrder2);
        dao.updateAll(newOrders);
        Order actualOrder2 = dao.getOneById("2");
        Order actualOrder3 = dao.getOneById("3");
        assertEquals(expectedOrder2, actualOrder2);
        assertEquals(expectedOrder3, actualOrder3);
    }

    @Test
    public void deleteAllTest() throws Exception {
        List<Order> deletedOrders = new ArrayList<>();
        deletedOrders.add(order1);
        deletedOrders.add(order3);
        dao.deleteAll(deletedOrders);
        int expected = 1;
        List<Order> ordersFromDB = dao.getAll();
        assertEquals(expected, ordersFromDB.size());
    }
}