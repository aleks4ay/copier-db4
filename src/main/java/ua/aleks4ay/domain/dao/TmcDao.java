package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Tmc;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class TmcDao extends AbstractDao<Tmc> {

    private static final String SQL_SAVE = "INSERT INTO tmc (id_parent, code, art, descr, is_folder, size_a, size_b, " +
            "size_c, descr_all, type, id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM tmc WHERE id = ?;";
    private static final String SQL_UPDATE = "UPDATE tmc SET id_parent=?, code=?, art=?, descr=?, is_folder=?, " +
            "size_a=?, size_b=?, size_c=?, descr_all=?, type=? WHERE id=?;";
    private static final String SQL_DELETE = "DELETE FROM tmc WHERE id=?;";

    public TmcDao(Connection connection) {
        super(connection, "tmc");
    }

    @Override
    public Tmc getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<Tmc> tmcList) {
        return saveAllChanges(SQL_SAVE, tmcList, "save");
    }

    @Override
    public boolean updateAll(List<Tmc> tmcList) {
        return saveAllChanges(SQL_UPDATE, tmcList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Tmc> tmcList) {
        return saveAllChanges(SQL_DELETE, tmcList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, Tmc tmc) throws SQLException {
        statement.setString(1, tmc.getIdParent());
        statement.setString(2, tmc.getCode());
        statement.setString(3, tmc.getArt());
        statement.setString(4, tmc.getDescr());
        statement.setInt(5, tmc.getIsFolder());
        statement.setInt(6, tmc.getSizeA());
        statement.setInt(7, tmc.getSizeB());
        statement.setInt(8, tmc.getSizeC());
        statement.setString(9, tmc.getDescrAll());
        statement.setString(10, tmc.getType());
        statement.setString(11, tmc.getId());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public Tmc readEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String idParent = rs.getString("id_parent");
        String code = rs.getString("code");
        String descr = rs.getString("descr");
        int isFolder = rs.getInt("is_folder");
        String descrAll = rs.getString("descr_all");
        String type = rs.getString("type");
        String art = rs.getString("art");
        int sizeA = rs.getInt("size_a");
        int sizeB = rs.getInt("size_b");
        int sizeC = rs.getInt("size_c");
        return new Tmc(id, idParent, art, code, descr, isFolder, descrAll, type, sizeA, sizeB, sizeC);
    }
}