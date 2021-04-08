package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Manufacture;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class ManufDao extends AbstractDao<Manufacture>{

    private static final String SQL_GET_ONE = "SELECT * FROM manufacture WHERE iddoc = ? and position=?;";
    private static final String SQL_DELETE = "DELETE FROM manufacture WHERE iddoc = ? and position=?;";
    private static final String SQL_SAVE = "INSERT INTO manufacture (id_order, quantity, id_tmc, descr_second, " +
            "size_a, size_b, size_c, embodiment, iddoc, position) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE manufacture SET id_order=?, quantity=?, " +
            "id_tmc=?, descr_second=?, size_a=?, size_b=?, size_c=?, embodiment=? WHERE iddoc = ? and position=?;";

    public ManufDao(Connection conn) {
        super(conn, "manufacture");
    }


    @Override
    public Manufacture getOneById(String complexId) {
        return getOneAbstract(SQL_GET_ONE, complexId);
    }

    @Override
    public boolean saveAll(List<Manufacture> manufactureList) {
        return saveAllChanges(SQL_SAVE, manufactureList, "save");
    }

    @Override
    public boolean updateAll(List<Manufacture> manufactureList) {
        return saveAllChanges(SQL_UPDATE, manufactureList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Manufacture> manufactureList) {
        return saveAllChanges(SQL_DELETE, manufactureList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement ps, Manufacture manufacture) throws SQLException {
        ps.setString(9, manufacture.getIdDoc());
        ps.setInt(10, manufacture.getPosition());
        ps.setString(1, manufacture.getIdOrder());
        ps.setInt(2, manufacture.getQuantity());
        ps.setString(3, manufacture.getIdTmc());
        ps.setString(4, manufacture.getDescrSecond());
        ps.setInt(5, manufacture.getSizeA());
        ps.setInt(6, manufacture.getSizeB());
        ps.setInt(7, manufacture.getSizeC());
        ps.setString(8, manufacture.getEmbodiment());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String complexId) throws SQLException {
        String[] key = complexId.split("-");
        statement.setString(1, key[0]);
        statement.setInt(2, Integer.valueOf(key[1]));
    }

    @Override
    public Manufacture readEntity(ResultSet rs) throws SQLException {
        String idDoc = rs.getString("iddoc");
        String idOrder = rs.getString("id_order");
        int pos = rs.getInt("position");
        int quantity = rs.getInt("quantity");
        String idTmc = rs.getString("id_tmc");
        String descrSecond = rs.getString("descr_second");
        int sizeA = rs.getInt("size_a");
        int sizeB = rs.getInt("size_b");
        int sizeC = rs.getInt("size_c");
        String embodiment = rs.getString("embodiment");
        return new Manufacture(idDoc, idOrder, pos, quantity, idTmc, descrSecond, sizeA, sizeB, sizeC, embodiment);
    }
}
