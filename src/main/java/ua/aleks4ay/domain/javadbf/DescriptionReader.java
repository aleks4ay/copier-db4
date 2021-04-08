package ua.aleks4ay.domain.javadbf;

import java.io.*;
import com.linuxense.javadbf.*;
import ua.aleks4ay.domain.model.Description;

public class DescriptionReader extends AbstractReader<Description> {

    public DescriptionReader() {
        super("descriptions", "DT1898.DBF");
    }

    @Override
    public Description readEntity(DBFRow row) {
        String idDoc = row.getString("IDDOC");
        String idTmc = row.getString("SP1902");//Must not be: 'Go designer to size measurement', 'Shipment', 'Fixing', 'DELETED'
        if (idTmc.equalsIgnoreCase("   CBN") || idTmc.equalsIgnoreCase("   7LH") ||
                idTmc.equalsIgnoreCase("   9VQ") || idTmc.equalsIgnoreCase("     0")) {
            return null;
        }
        int position = row.getInt("LINENO");
        int quantity = row.getInt("SP1905");
        String descrSecond = null;
        try {
            descrSecond = new String(row.getString("SP14676").getBytes("ISO-8859-15"), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int sizeA = row.getInt("SP14686");
        int sizeB = row.getInt("SP14687");
        int sizeC = row.getInt("SP14688");
        String idEmbodiment = row.getString("SP14717");
        double price = row.getDouble("SP14681");
        return new Description(idDoc, position, idTmc, quantity, descrSecond, sizeA, sizeB, sizeC, idEmbodiment, price);
    }
}
