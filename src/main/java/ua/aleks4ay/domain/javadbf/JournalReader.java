package ua.aleks4ay.domain.javadbf;

import java.io.*;
import com.linuxense.javadbf.*;
import ua.aleks4ay.domain.model.Journal;
import ua.aleks4ay.domain.tools.TimeConverter;

import java.sql.Timestamp;
import java.util.*;

public class JournalReader extends AbstractReader<Journal> {

    public JournalReader() {
        super("journal", "1SJOURN.DBF");
    }

    @Override
    public Journal readEntity(DBFRow row) {
        Date keyOrderYear = row.getDate("DATE");
        int keyOrderIsEnable = row.getInt("CLOSED");
        if (keyOrderYear.getTime() < 1577836800000L || keyOrderIsEnable ==4 ) {
            return null;
        }
        String idDoc = row.getString("IDDOC");
        String docNumber = null;
        try {
            docNumber = new String(row.getString("DOCNO").getBytes("ISO-8859-15"), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        long dateCreate = row.getDate("DATE").getTime();
        long timeCreate = TimeConverter.convertStrToTimeMillisecond(row.getString("TIME"));

        return new Journal(idDoc, docNumber, new Timestamp(dateCreate + timeCreate));
    }
}
