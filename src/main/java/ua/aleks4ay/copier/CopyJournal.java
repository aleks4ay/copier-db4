package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.AbstractDao;
import ua.aleks4ay.domain.dao.JournalDao;
import ua.aleks4ay.domain.dao.Utils;
import ua.aleks4ay.domain.javadbf.JournalReader;
import ua.aleks4ay.domain.model.Journal;

import java.sql.Connection;
import java.util.List;

public class CopyJournal extends AbstractCopy<Journal>{

    public CopyJournal() {
        super("journal");
    }

    public static void main(String[] args) {
        new CopyJournal().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Journal> dao = new JournalDao(connection);
        List<Journal> ordersFrom1C = new JournalReader().getAll();
        copyNewRecord(dao, ordersFrom1C);
        Utils.closeConnection(connection);
    }

    public List<String> doCopyWithIdJournal() {
        Connection connection = Utils.getConnection();
        JournalDao dao = new JournalDao(connection);
        List<Journal> ordersFrom1C = new JournalReader().getAll();
        copyNewRecord(dao, ordersFrom1C);
        Utils.closeConnection(connection);
        return dao.getAllIdDoc(ordersFrom1C);
    }
}
