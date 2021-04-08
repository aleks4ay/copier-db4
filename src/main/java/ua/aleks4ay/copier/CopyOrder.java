package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.AbstractDao;
import ua.aleks4ay.domain.dao.Utils;
import ua.aleks4ay.domain.javadbf.OrderReader;
import ua.aleks4ay.domain.dao.OrderDao;
import ua.aleks4ay.domain.model.Order;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CopyOrder extends AbstractCopy<Order>{

    public CopyOrder() {
        super("orders");
    }

    public static void main(String[] args) {
        new CopyOrder().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Order> dao = new OrderDao(connection);
        List<Order> ordersFrom1C = new OrderReader().getAll();
        copyNewRecord(dao, ordersFrom1C);
        Utils.closeConnection(connection);
    }

    public List<String> doCopyWithIdJournal(List<String> journalId) {
        Connection connection = Utils.getConnection();
        OrderDao dao = new OrderDao(connection);
        List<Order> ordersFrom1C = new OrderReader().getAll();
        List<Order> ordersAfterFilter = new ArrayList<>();
        for (Order ord : ordersFrom1C) {
            if (journalId.contains(ord.getIdDoc())) {
                ordersAfterFilter.add(ord);
            }
        }
        copyNewRecord(dao, ordersAfterFilter);
        Utils.closeConnection(connection);
        return dao.getAllIdDoc(ordersAfterFilter);
    }
}

