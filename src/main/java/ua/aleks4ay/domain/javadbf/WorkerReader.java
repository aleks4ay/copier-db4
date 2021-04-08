package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFRow;
import ua.aleks4ay.domain.model.Worker;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class WorkerReader extends AbstractReader<Worker>{

    public static void main(String[] args) {
        List<Worker> all = new WorkerReader().getAll();
        System.out.println(all.size());
    }

    public WorkerReader() {
        super("worker", "SC1670.DBF");
    }

    @Override
    public Worker readEntity(DBFRow row) {
        String id = row.getString("ID");
        String name = null;
        try {
            name = new String(row.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new Worker(id, name);
    }
}
