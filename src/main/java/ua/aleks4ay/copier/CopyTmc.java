package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.AbstractDao;
import ua.aleks4ay.domain.dao.Utils;
import ua.aleks4ay.domain.dao.TmcDao;
import ua.aleks4ay.domain.javadbf.TmcReader;
import ua.aleks4ay.domain.model.Tmc;

import java.sql.Connection;
import java.util.List;

public class CopyTmc extends AbstractCopy<Tmc> {

    public CopyTmc() {
        super("tmc");
    }

    public static void main(String[] args) {
        new CopyTmc().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Tmc> dao = new TmcDao(connection);
        List<Tmc> tmcFrom1C = new TmcReader().getAll();
        copyNewRecord(dao, tmcFrom1C);
        Utils.closeConnection(connection);
    }
}