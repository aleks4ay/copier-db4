package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.*;
import ua.aleks4ay.domain.javadbf.ManufReader;
import ua.aleks4ay.domain.model.Manufacture;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CopyManuf extends AbstractCopy<Manufacture> {

    public CopyManuf() {
        super("manufacture");
    }

    public static void main(String[] args) {
        new CopyManuf().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Manufacture> dao = new ManufDao(connection);
        List<Manufacture> manufactureFrom1C = new ManufReader().getAll();
        copyNewRecord(dao, manufactureFrom1C);
        Utils.closeConnection(connection);
    }

    public void doCopyWithIdOrders(List<String> idOrders) {
        Connection connection = Utils.getConnection();
        AbstractDao<Manufacture> dao = new ManufDao(connection);
        List<Manufacture> manufactureFrom1C = new ManufReader().getAll();
        List<Manufacture> manufactureAfterFilter = new ArrayList<>();
        for (Manufacture desc : manufactureFrom1C) {
            if (idOrders.contains(desc.getIdOrder())) {
                manufactureAfterFilter.add(desc);
            }
        }
        copyNewRecord(dao, manufactureAfterFilter);
        Utils.closeConnection(connection);
    }
}