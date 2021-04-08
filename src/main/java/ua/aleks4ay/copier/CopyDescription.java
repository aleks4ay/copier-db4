package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.*;
import ua.aleks4ay.domain.javadbf.DescriptionReader;
import ua.aleks4ay.domain.model.Description;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CopyDescription extends AbstractCopy<Description>{

    public CopyDescription() {
        super("descriptions");
    }

    public static void main(String[] args) {
        new CopyDescription().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Description> dao = new DescriptionDao(connection);
        List<Description> descriptionsFrom1C = new DescriptionReader().getAll();
        copyNewRecord(dao, descriptionsFrom1C);
        Utils.closeConnection(connection);
    }

    public void doCopyWithIdOrders(List<String> idOrders) {
        Connection connection = Utils.getConnection();
        AbstractDao<Description> dao = new DescriptionDao(connection);
        List<Description> descriptionsFrom1C = new DescriptionReader().getAll();
        List<Description> descriptionsAfterFilter = new ArrayList<>();
        for (Description desc : descriptionsFrom1C) {
            if (idOrders.contains(desc.getIdDoc())) {
                descriptionsAfterFilter.add(desc);
            }
        }
        copyNewRecord(dao, descriptionsAfterFilter);
        Utils.closeConnection(connection);
    }
}
