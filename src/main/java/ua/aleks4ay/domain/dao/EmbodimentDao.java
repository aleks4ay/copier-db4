package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Embodiment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class EmbodimentDao extends AbstractDao<Embodiment> {

    private static final String SQL_SAVE = "INSERT INTO embodiment (description, id) VALUES (?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM embodiment WHERE id = ?;";
    private static final String SQL_UPDATE = "UPDATE embodiment SET description=? WHERE id=?;";
    private static final String SQL_DELETE = "DELETE FROM embodiment WHERE id=?;";

    public EmbodimentDao(Connection connection) {
        super(connection, "embodiment");
    }

    @Override
    public Embodiment getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<Embodiment> embodimentList) {
        return saveAllChanges(SQL_SAVE, embodimentList, "save");
    }

    @Override
    public boolean updateAll(List<Embodiment> embodimentList) {
        return saveAllChanges(SQL_UPDATE, embodimentList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Embodiment> embodimentList) {
        return saveAllChanges(SQL_DELETE, embodimentList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, Embodiment embodiment) throws SQLException {
        statement.setString(1, embodiment.getDescription());
        statement.setString(2, embodiment.getId());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public Embodiment readEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String description = rs.getString("description");
        return new Embodiment(id, description);
    }
}