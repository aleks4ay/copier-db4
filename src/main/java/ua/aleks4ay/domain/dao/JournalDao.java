package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Journal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JournalDao extends AbstractDao<Journal> {

    private static final String SQL_SAVE = "INSERT INTO journal (docno, datecreate, iddoc) VALUES (?, ?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM journal WHERE iddoc = ?;";
    private static final String SQL_UPDATE = "UPDATE journal SET docno=?, datecreate=? WHERE iddoc=?;";
    private static final String SQL_DELETE = "DELETE FROM journal WHERE iddoc=?;";

    public JournalDao(Connection connection) {
        super(connection, "journal");
    }

    @Override
    public Journal getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<Journal> journalList) {
        return saveAllChanges(SQL_SAVE, journalList, "save");
    }

    @Override
    public boolean updateAll(List<Journal> journalList) {
        return saveAllChanges(SQL_UPDATE, journalList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Journal> journalList) {
        return saveAllChanges(SQL_DELETE, journalList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement statement, Journal journal) throws SQLException {
        statement.setString(3, journal.getIdDoc());
        statement.setString(1, journal.getDocNumber());
        statement.setTimestamp(2, journal.getDateCreate());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public Journal readEntity(ResultSet rs) throws SQLException {
        String idDoc = rs.getString("iddoc");
        String docNumber = rs.getString("docno");
        Timestamp dateCreate = rs.getTimestamp("datecreate");
        return new Journal(idDoc, docNumber, dateCreate);
    }

    public List<String> getAllIdDoc(List<Journal> journals) {
        return journals
                .stream()
                .map(Journal::getIdDoc)
                .collect(Collectors.toList());
    }
}
