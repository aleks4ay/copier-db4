package ua.aleks4ay.domain.model;

import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class OrderTest {

    private static Order order1, order2, order3, order_Equal_3, order_NotEqual_3;

    @BeforeClass
    public static void createClients() {
        order1 = new Order("1", "idCl_1", "idMng_1", 14, null, 11.1);
        order2 = new Order("1", "idCl_1", "idMng_1", 14, null, 11.1);
        order3 = new Order("1", "idCl_1", "idMng_1", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 11.1);
        order_Equal_3 = new Order("1", "idCl_1", "idMng_1", 14, Timestamp.valueOf("2021-3-15 14:15:00"), 11.1);
        order_NotEqual_3 = new Order("1", "idCl_1", "idMng_1", 14, Timestamp.valueOf("2021-4-15 16:15:00"), 11.1);
    }

    @Test
    public void equalNullToNull() throws Exception {
        assertEquals(order1, order2);
    }

    @Test
    public void equalTimeToNull() throws Exception {
        assertNotEquals(order3, order1);
    }

    @Test
    public void equalNullToTime() throws Exception {
        assertNotEquals(order1, order3);
    }

    @Test
    public void equalTestTimeToTimeTheSame() throws Exception {
        assertEquals(order3, order_Equal_3);
    }

    @Test
    public void equalTestTimeToTimeNotTheSame() throws Exception {
        assertNotEquals(order3, order_NotEqual_3);
    }

}