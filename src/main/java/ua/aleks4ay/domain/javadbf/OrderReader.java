package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.*;
import ua.aleks4ay.domain.model.Order;

import java.sql.*;
import java.util.Date;

public class OrderReader extends AbstractReader<Order>  {

    public OrderReader() {
        super("orders", "DH1898.DBF");
    }

    @Override
    public Order readEntity(DBFRow row) {
        int keyOrderToFactory = row.getInt("SP14694");
        if (keyOrderToFactory != 1) {
            return null;
        }
        String idDoc = row.getString("IDDOC");
        String idClient = row.getString("SP1899");
        Date date = row.getDate("SP14836");
        Timestamp dateToFactory;
        if (date == null) {
            dateToFactory = null;
        }
        else if (date.getTime() < 1560805200000L) {
            return null;
        }
        else {
            dateToFactory = new Timestamp(date.getTime());
        }
        int durationTime = row.getInt("SP14695");
        String idManager = row.getString("SP14680");
        double price = row.getDouble("SP14684");
        return new Order(idDoc, idClient, idManager, durationTime, dateToFactory, price);
    }
}
