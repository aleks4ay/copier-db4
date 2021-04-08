package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFRow;
import ua.aleks4ay.domain.model.InvoiceDescription;

public class InvoiceDescriptionReader extends AbstractReader<InvoiceDescription> {

    public InvoiceDescriptionReader() {
        super("invoice_descr", "DT3592.DBF");
    }

    @Override
    public InvoiceDescription readEntity(DBFRow row) {
        String idInvoice = row.getString("IDDOC");
        String idTmc = row.getString("SP3579");//Must not be: 'Go designer to size measurement', 'Shipment', 'Fixing', 'DELETED'
        if (idTmc.equalsIgnoreCase("   CBN") || idTmc.equalsIgnoreCase("   7LH") ||
                idTmc.equalsIgnoreCase("   9VQ") || idTmc.equalsIgnoreCase("     0")) {
            return null;
        }
        int position = row.getInt("LINENO");
        int quantity = row.getInt("SP3581");
        double newPayment = row.getDouble("SP3589");

        return new InvoiceDescription(idInvoice, idTmc, position, quantity, newPayment);
    }
}
