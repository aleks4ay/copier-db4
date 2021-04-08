package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Client;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class ClientDao extends AbstractDao<Client> {

    protected static final String SQL_SAVE = "INSERT INTO client (name, id) VALUES (?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM client WHERE id = ?;";
    private static final String SQL_UPDATE = "UPDATE client SET name=? WHERE id=?;";
    private static final String SQL_DELETE = "DELETE FROM client WHERE id=?;";

    public ClientDao(Connection connection) {
        super(connection, "client");
    }

    @Override
    public Client getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<Client> clientList) {
        return saveAllChanges(SQL_SAVE, clientList, "save");
    }

    @Override
    public boolean updateAll(List<Client> clientList) {
        return saveAllChanges(SQL_UPDATE, clientList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Client> clientList) {
        return saveAllChanges(SQL_DELETE, clientList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.getName());
        statement.setString(2, client.getId());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public Client readEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        return new Client(id, name);
    }
}



