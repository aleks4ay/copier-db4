package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.*;
import ua.aleks4ay.domain.javadbf.InvoiceDescriptionReader;
import ua.aleks4ay.domain.model.InvoiceDescription;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CopyInvoiceDescription extends AbstractCopy<InvoiceDescription> {

    public CopyInvoiceDescription() {
        super("invoice_descr");
    }

    public static void main(String[] args) {
        new CopyInvoiceDescription().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<InvoiceDescription> dao = new InvoiceDescriptionDao(connection);
        List<InvoiceDescription> invoiceDescriptionFrom1C = new InvoiceDescriptionReader().getAll();
        copyNewRecord(dao, invoiceDescriptionFrom1C);
        Utils.closeConnection(connection);
    }

    public void doCopyWithIdInvoice(List<String> idInvoice) {
        Connection connection = Utils.getConnection();
        AbstractDao<InvoiceDescription> dao = new InvoiceDescriptionDao(connection);
        List<InvoiceDescription> invoiceDescriptionFrom1C = new InvoiceDescriptionReader().getAll();
        List<InvoiceDescription> invoiceDescriptionAfterFilter = new ArrayList<>();
        for (InvoiceDescription desc : invoiceDescriptionFrom1C) {
            if (idInvoice.contains(desc.getIdInvoice())) {
                invoiceDescriptionAfterFilter.add(desc);
            }
        }
        copyNewRecord(dao, invoiceDescriptionAfterFilter);
        Utils.closeConnection(connection);
    }
}

