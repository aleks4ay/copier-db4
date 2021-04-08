package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Worker;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class WorkerDao extends AbstractDao<Worker> {

    protected static final String SQL_SAVE = "INSERT INTO worker (name, id) VALUES (?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM worker WHERE id = ?;";
    private static final String SQL_UPDATE = "UPDATE worker SET name=? WHERE id=?;";
    private static final String SQL_DELETE = "DELETE FROM worker WHERE id=?;";

    public WorkerDao(Connection connection) {
        super(connection, "worker");
    }

    @Override
    public Worker getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<Worker> workerList) {
        return saveAllChanges(SQL_SAVE, workerList, "save");
    }

    @Override
    public boolean updateAll(List<Worker> workerList) {
        return saveAllChanges(SQL_UPDATE, workerList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Worker> workerList) {
        return saveAllChanges(SQL_DELETE, workerList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, Worker worker) throws SQLException {
        statement.setString(1, worker.getName());
        statement.setString(2, worker.getId());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public Worker readEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        return new Worker(id, name);
    }
}