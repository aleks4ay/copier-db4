package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.*;
import ua.aleks4ay.domain.javadbf.WorkerReader;
import ua.aleks4ay.domain.model.Worker;

import java.sql.Connection;
import java.util.List;

public class CopyWorker extends AbstractCopy<Worker>{

    public CopyWorker() {
        super("worker");
    }

    public static void main(String[] args) {
        new CopyWorker().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Worker> dao = new WorkerDao(connection);
        List<Worker> workersFrom1C = new WorkerReader().getAll();
        copyNewRecord(dao, workersFrom1C);
        Utils.closeConnection(connection);
    }
}