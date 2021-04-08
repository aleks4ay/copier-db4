package ua.aleks4ay.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.domain.model.AbstractEntity;

import java.sql.*;
import java.util.*;
import java.util.stream.IntStream;

import static ua.aleks4ay.log.ClassNameUtil.*;

public abstract class AbstractDao<T> {

    public abstract T getOneById(String id);
    public abstract boolean saveAll(List<T> tt);
    public abstract boolean updateAll(List<T> tt);
    public abstract boolean deleteAll(Collection<T> tt);

    protected abstract void fillEntityStatement(PreparedStatement statement, T t) throws SQLException;
    public abstract void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException;
    public abstract T readEntity(ResultSet rs) throws SQLException;

    public Connection connection = null;
    private String tableName = null;

    private static final Logger log = LoggerFactory.getLogger(AbstractDao.class);
    private static final String SQL_GET_ALL = "SELECT * FROM ";
    private static final String SQL_CLEAR = "DELETE FROM ";

    protected AbstractDao(Connection connection, String tableName) {
        this.connection = connection;
        this.tableName = tableName;
        log.debug("Get connection to DataBase from {} from {}.", getPreviousClassName(), getPreviousPreviousClassName());
    }

    public boolean saveAllChanges(String sql, Collection<T> tt, String target) {
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            int result = 0;
            for (T t: tt) {
                ps = connection.prepareStatement(sql);
                if (target.equalsIgnoreCase("delete")) {
                    fillOnlyIdStatement(ps, ((AbstractEntity<T>)t).getId());
                } else {
                    fillEntityStatement(ps, t);
                }
                ps.addBatch();
                int[] numberOfUpdates = ps.executeBatch();
                result += IntStream.of(numberOfUpdates).sum();
            }
            if (result == tt.size()) {
                connection.commit();
                log.debug("Commit - OK. {} '{}' {}d.", result, tableName, target);
                return true;
            }
            else {
                log.warn("{}d {}, but need {} {} '{}'. Not equals!", target, result, target, tt.size(), tableName);
                connection.rollback();
            }
        } catch (SQLException e) {
            log.warn("Exception during {} {} new '{}'. SQL = {}.", target, tt.size(), tableName, sql, e);
        } finally {
            try {
                Utils.closeStatement(ps);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connection.prepareStatement(SQL_GET_ALL + tableName + ";");
            rs = prepStatement.executeQuery();
            log.debug("Get all rows from '{}'.", tableName);
            while (rs.next()) {
                result.add(readEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Utils.closeResultSet(rs);
            Utils.closeStatement(prepStatement);
        }
        return result;
    }

    public T getOneAbstract(String sql, String id) {
        T result = null;
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            ps = connection.prepareStatement(sql);
            fillOnlyIdStatement(ps, id);
            rs = ps.executeQuery();
            log.debug("Get {} with id={}.", tableName, id);
            if (rs.next()) {
                result = readEntity(rs);
            }
        } catch (SQLException e) {
            log.warn("Exception during get new '{}'. SQL = {}.", tableName, sql, e);
        } finally {
            Utils.closeStatement(ps);
        }
        return result;
    }

    public Map<String, T> getAllAsMap() {
        List<T> entityList = getAll();
        Map<String, T> result = new HashMap<>();
        for (T t : entityList) {
            result.put(((AbstractEntity)t).getId(), t);
        }
        return result;
    }

    public boolean clearAll() {
        try {
            Statement statement = connection.createStatement();
            statement.execute(SQL_CLEAR + tableName + ";");
            log.debug("Table '{}' cleared.", tableName);
            return true;
        } catch (SQLException e) {
            log.warn("Exception during empty table '{}'. SQL = {}.", tableName, SQL_CLEAR + tableName, e);
        }
        return false;
    }
}
