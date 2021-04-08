package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.TmcBalance;

import java.sql.*;
import java.util.*;

public class TmcTechnoBalanceDao extends AbstractDao<TmcBalance>{

    private static final String SQL_SAVE ="INSERT INTO techno_item (store_c, idtmc) VALUES (?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM techno_item WHERE idtmc = ?;";
    private static final String SQL_UPDATE = "UPDATE techno_item SET store_c=? WHERE idtmc = ?;";
    private static final String SQL_DELETE = "DELETE FROM techno_item WHERE idtmc=?;";

    public TmcTechnoBalanceDao(Connection connection) {
        super(connection, "techno_item");
    }

    @Override
    public TmcBalance getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<TmcBalance> tmcList) {
        return saveAllChanges(SQL_SAVE, tmcList, "save");
    }

    @Override
    public boolean updateAll(List<TmcBalance> tmcList) {
        return saveAllChanges(SQL_UPDATE, tmcList, "update");
    }

    @Override
    public boolean deleteAll(Collection<TmcBalance> tmcList) {
        return saveAllChanges(SQL_DELETE, tmcList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, TmcBalance tmc) throws SQLException {
        statement.setInt(1, tmc.getQuantityOnStore());
        statement.setString(2, tmc.getId());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public TmcBalance readEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("idtmc");
        int quantity = rs.getInt("store_c");
        Timestamp period = rs.getTimestamp("period");
        return new TmcBalance(id, quantity, period);
    }
}



