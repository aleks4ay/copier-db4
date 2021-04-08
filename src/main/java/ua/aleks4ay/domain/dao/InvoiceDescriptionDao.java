package ua.aleks4ay.domain.dao;

import ua.aleks4ay.domain.model.InvoiceDescription;

import java.sql.*;
import java.util.Collection;
import java.util.List;

public class InvoiceDescriptionDao extends AbstractDao<InvoiceDescription>{

    private static final String SQL_SAVE = "INSERT INTO invoice_descr (id_tmc, quantity, payment, id_invoice, position) " +
            " VALUES (?, ?, ?, ?, ?);";
    private static final String SQL_GET_ONE = "SELECT * FROM invoice_descr WHERE id_invoice=? and position=?;";
    private static final String SQL_UPDATE = "UPDATE invoice_descr SET id_tmc=?, quantity=?, payment=?" +
            " WHERE id_invoice=? and position=?;";
    private static final String SQL_DELETE = "DELETE FROM invoice_descr WHERE id_invoice=? and position=?;";

    public InvoiceDescriptionDao(Connection connection) {
        super(connection, "invoice_descr");
    }

    @Override
    public InvoiceDescription getOneById(String id) {
        return getOneAbstract(SQL_GET_ONE, id);
    }

    @Override
    public boolean saveAll(List<InvoiceDescription> invoiceDescriptionList) {
        return saveAllChanges(SQL_SAVE, invoiceDescriptionList, "save");
    }

    @Override
    public boolean updateAll(List<InvoiceDescription> invoiceDescriptionList) {
        return saveAllChanges(SQL_UPDATE, invoiceDescriptionList, "update");
    }

    @Override
    public boolean deleteAll(Collection<InvoiceDescription> invoiceDescriptionList) {
        return saveAllChanges(SQL_DELETE, invoiceDescriptionList, "delete");
    }

    @Override
    public void fillEntityStatement(PreparedStatement ps, InvoiceDescription invoiceDescription) throws SQLException {
        ps.setString(4, invoiceDescription.getIdInvoice());
        ps.setInt(5, invoiceDescription.getPosition());
        ps.setString(1, invoiceDescription.getIdTmc());
        ps.setInt(2, invoiceDescription.getQuantity());
        ps.setDouble(3, invoiceDescription.getPayment());
    }

    @Override
    public void fillOnlyIdStatement(PreparedStatement statement, String complexId) throws SQLException {
        String[] key = complexId.split("-");
        statement.setString(1, key[0]);
        statement.setInt(2, Integer.valueOf(key[1]));
    }

    @Override
    public InvoiceDescription readEntity(ResultSet rs) throws SQLException {
        String idInvoice = rs.getString("id_invoice");
        String idTmc = rs.getString("id_tmc");
        int position = rs.getInt("position");
        int quantity = rs.getInt("quantity");
        double payment = rs.getDouble("payment");
        return new InvoiceDescription(idInvoice, idTmc, position, quantity, payment);
    }
}
