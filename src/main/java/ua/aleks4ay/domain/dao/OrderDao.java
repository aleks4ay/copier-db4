package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Order;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class OrderDao extends AbstractDao<Order> {

    private static final String SQL_SAVE = "INSERT INTO orders(id_client, id_manager, duration, " +
            "t_factory, price, iddoc) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM orders WHERE iddoc = ?;";
    private static final String SQL_UPDATE = "UPDATE orders SET id_client=?, id_manager=?, duration=?, " +
            "t_factory=?, price=? WHERE iddoc=?;";
    private static final String SQL_DELETE = "DELETE FROM orders WHERE iddoc=?;";

    public OrderDao(Connection connection) {
        super(connection, "orders");
    }

    @Override
    public Order getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<Order> orderList) {
        return saveAllChanges(SQL_SAVE, orderList, "save");
    }

    @Override
    public boolean updateAll(List<Order> orderList) {
        return saveAllChanges(SQL_UPDATE, orderList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Order> orderList) {
        return saveAllChanges(SQL_DELETE, orderList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement ps, Order order) throws SQLException {
        ps.setString(1, order.getIdClient());
        ps.setString(2, order.getIdManager());
        ps.setInt(3, order.getDurationTime());
        ps.setTimestamp(4, order.getDateToFactory());
        ps.setDouble(5, order.getPrice());
        ps.setString(6, order.getIdDoc());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public Order readEntity(ResultSet rs) throws SQLException {
        String idDoc = rs.getString("iddoc");
        String idClient = rs.getString("id_client");
        String idManager = rs.getString("id_manager");
        int durationTime = rs.getInt("duration");
        Timestamp dateToFactory = rs.getTimestamp("t_factory");
        double price = rs.getDouble("price");
        return new Order(idDoc, idClient, idManager, durationTime, dateToFactory, price);
    }

    public List<String> getAllIdDoc(List<Order> orders) {
        return orders
                .stream()
                .map(Order::getIdDoc)
                .collect(Collectors.toList());
    }
}
