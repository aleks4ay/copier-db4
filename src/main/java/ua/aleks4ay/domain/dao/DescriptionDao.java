package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Description;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class DescriptionDao extends AbstractDao<Description> {

    private static final String SQL_SAVE = "INSERT INTO descriptions (id_tmc, quantity, descr_second, " +
            "size_a, size_b, size_c, embodiment, price, iddoc, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE descriptions SET id_tmc=?, quantity=?, descr_second=?, " +
            "size_a=?, size_b=?, size_c=?, embodiment=?, price=? WHERE iddoc = ? and position=?;";
    private static final String SQL_GET_ONE = "SELECT * FROM descriptions WHERE iddoc = ? and position=?;";
    private static final String SQL_DELETE = "DELETE FROM descriptions WHERE iddoc = ? and position=?;";

    public DescriptionDao(Connection connection) {
        super(connection, "descriptions");
    }

    @Override
    public Description getOneById(String complexId) {
        return getOneAbstract(SQL_GET_ONE, complexId);
    }

    @Override
    public boolean saveAll(List<Description> descriptionList) {
        return saveAllChanges(SQL_SAVE, descriptionList, "save");
    }

    @Override
    public boolean updateAll(List<Description> descriptionList) {
        return saveAllChanges(SQL_UPDATE, descriptionList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Description> descriptionList) {
        return saveAllChanges(SQL_DELETE, descriptionList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, Description description) throws SQLException {
        statement.setString(9, description.getIdDoc());
        statement.setInt(10, description.getPosition());
        statement.setString(1, description.getIdTmc());
        statement.setInt(2, description.getQuantity());
        statement.setString(3, description.getDescrSecond());
        statement.setInt(4, description.getSizeA());
        statement.setInt(5, description.getSizeB());
        statement.setInt(6, description.getSizeC());
        statement.setString(7, description.getEmbodiment());
        statement.setDouble(8, description.getPrice());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String complexId) throws SQLException {
        String[] key = complexId.split("-");
        statement.setString(1, key[0]);
        statement.setInt(2, Integer.valueOf(key[1]));
    }

    @Override
    public Description readEntity(ResultSet rs) throws SQLException {
        String idDoc = rs.getString("iddoc");
        int position = rs.getInt("position");
        String idTmc = rs.getString("id_tmc");
        int quantity = rs.getInt("quantity");
        String descrSecond = rs.getString("descr_second");
        int sizeA = rs.getInt("size_a");
        int sizeB = rs.getInt("size_b");
        int sizeC = rs.getInt("size_c");
        String embodiment = rs.getString("embodiment");
        double price = rs.getDouble("price");
        return new Description(idDoc, position, idTmc, quantity, descrSecond, sizeA, sizeB, sizeC, embodiment, price);
    }
}