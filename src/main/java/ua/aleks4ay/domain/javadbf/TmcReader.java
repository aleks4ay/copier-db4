package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFRow;
import ua.aleks4ay.domain.model.Tmc;

import java.io.UnsupportedEncodingException;

public class TmcReader extends AbstractReader<Tmc>{

    public TmcReader() {
        super("tmc", "SC302.DBF");
    }

    @Override
    public Tmc readEntity(DBFRow row) {
        String id = row.getString("ID");
        String parentId = row.getString("PARENTID");
        String code = row.getString("CODE");
        String descr = null;
        String descrAll = null;
        String art = null;
        try {
            descr = new String(row.getString("DESCR").getBytes("ISO-8859-1"), "Windows-1251");
            descrAll = new String(row.getString("SP276").getBytes("ISO-8859-1"), "Windows-1251");
            art = new String(row.getString("SP278").getBytes("ISO-8859-1"), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int isFolder = row.getInt("ISFOLDER");
        String type = row.getString("SP277");
        int sizeA = row.getInt("SP14690");
        int sizeB = row.getInt("SP14691");
        int sizeC = row.getInt("SP14692");
        return new Tmc(id, parentId, art, code, descr, isFolder, descrAll, type, sizeA, sizeB, sizeC);
    }
}
