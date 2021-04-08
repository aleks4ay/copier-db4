package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.*;
import ua.aleks4ay.domain.javadbf.InvoiceReader;
import ua.aleks4ay.domain.model.Invoice;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CopyInvoice extends AbstractCopy<Invoice> {

    public CopyInvoice() {
        super("invoice");
    }

    public static void main(String[] args) {
        new CopyInvoice().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Invoice> dao = new InvoiceDao(connection);
        List<Invoice> invoiceFrom1C = new InvoiceReader().getAll();
        copyNewRecord(dao, invoiceFrom1C);
        Utils.closeConnection(connection);
    }

    public List<String> doCopyWithIdOrders(List<String> idOrders) {
        Connection connection = Utils.getConnection();
        InvoiceDao dao = new InvoiceDao(connection);
        List<Invoice> invoiceFrom1C = new InvoiceReader().getAll();
        List<Invoice> invoiceAfterFilter = new ArrayList<>();
        for (Invoice inv : invoiceFrom1C) {
            if (idOrders.contains(inv.getIdOrder())) {
                invoiceAfterFilter.add(inv);
            }
        }
        copyNewRecord(dao, invoiceFrom1C);
        Utils.closeConnection(connection);
        return dao.getAllIdDoc(invoiceAfterFilter);
    }
}