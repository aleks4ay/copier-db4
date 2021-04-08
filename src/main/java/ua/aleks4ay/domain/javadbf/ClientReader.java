package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFRow;
import ua.aleks4ay.domain.model.Client;

import java.io.UnsupportedEncodingException;

public class ClientReader extends AbstractReader<Client> {

    public ClientReader() {
        super("client", "SC172.DBF");
    }

    @Override
    public Client readEntity(DBFRow row) {
        String id = row.getString("ID");
        String name = null;
        try {
            name = new String(row.getString("DESCR").getBytes("ISO-8859-1"), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (name == null || name.equals("")) {
            name = "-";
        }
        return new Client(id, name);
    }
}