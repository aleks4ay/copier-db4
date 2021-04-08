package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFRow;
import ua.aleks4ay.domain.model.Invoice;

public class InvoiceReader extends AbstractReader<Invoice>{

    public InvoiceReader() {
        super("invoice", "DH3592.DBF");
    }

    @Override
    public Invoice readEntity(DBFRow row) {
        String key = row.getString("SP3561");
        if (key.equals("   0     0")) {
            return null;
        }
        String idDoc = row.getString("IDDOC");
        String idOrder = row.getString("SP3561").substring(4);
        double newPayment = row.getDouble("SP3589");
        return new Invoice(idDoc, idOrder, newPayment);
    }
}
