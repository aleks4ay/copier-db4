package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import ua.aleks4ay.domain.model.Manufacture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

import static ua.aleks4ay.log.ClassNameUtil.getCurrentClassName;

public class ManufReader extends AbstractReader<Manufacture> {
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    public ManufReader() {
        super("manuf", "DT2728.DBF");
    }

    @Override
    public Manufacture readEntity(DBFRow row) {
        String idDoc = row.getString("IDDOC");
        String idOrder = row.getString("SP2722");//idOrder "     0" mean it's technoProduct
        int pos = row.getInt("LINENO");
        int quantity = row.getInt("SP2725");
        String idTmc = row.getString("SP2721");
        String descrSecond = null;
        try {
            descrSecond = new String(row.getString("SP14726").getBytes("ISO-8859-15"), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int sizeA = row.getInt("SP14722");
        int sizeB = row.getInt("SP14723");
        int sizeC = row.getInt("SP14724");
        String embodiment = row.getString("SP14725");
        return new Manufacture(idDoc, idOrder, pos, quantity, idTmc, descrSecond, sizeA, sizeB, sizeC, embodiment);
    }
}
