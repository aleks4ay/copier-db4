package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.Invoice;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class InvoiceDao extends AbstractDao<Invoice> {

    private static final String SQL_SAVE = "INSERT INTO invoice (id_order, price, iddoc) VALUES (?, ?, ?);";
    private static final String SQL_UPDATE = "UPDATE invoice SET id_order=?, price=? WHERE iddoc=?;";
    private static final String SQL_GET_ONE = "SELECT * FROM invoice WHERE iddoc = ?;";
    private static final String SQL_DELETE = "DELETE FROM invoice WHERE iddoc=?;";

    public InvoiceDao(Connection connection) {
        super(connection, "invoice");
    }

    @Override
    public Invoice getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<Invoice> invoiceList) {
        return saveAllChanges(SQL_SAVE, invoiceList, "save");
    }

    @Override
    public boolean updateAll(List<Invoice> invoiceList) {
        return saveAllChanges(SQL_UPDATE, invoiceList, "update");
    }

    @Override
    public boolean deleteAll(Collection<Invoice> invoiceList) {
        return saveAllChanges(SQL_DELETE, invoiceList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement ps, Invoice invoice) throws SQLException {
        ps.setString(3, invoice.getIdDoc());
        ps.setString(1, invoice.getIdOrder());
        ps.setDouble(2, invoice.getPrice());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String id) throws SQLException {
        statement.setString(1, id);
    }

    @Override
    public Invoice readEntity(ResultSet rs) throws SQLException {
        String idDoc = rs.getString("iddoc");
        String idOrder = rs.getString("id_order");
        double price = rs.getDouble("price");
        return new Invoice(idDoc, idOrder, price);
    }

    public List<String> getAllIdDoc(List<Invoice> invoices) {
        return invoices
                .stream()
                .map(Invoice::getIdDoc)
                .collect(Collectors.toList());
    }
}