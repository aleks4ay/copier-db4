package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFRow;
import ua.aleks4ay.domain.model.Embodiment;

import java.io.UnsupportedEncodingException;

public class EmbodimentReader extends AbstractReader<Embodiment> {

    public EmbodimentReader() {
        super("embodiment", "SC14716.DBF");
    }

    @Override
    public Embodiment readEntity(DBFRow row) {
        String id = row.getString("ID");
        String descr = null;
        try {
            descr = new String(row.getString("DESCR").getBytes("ISO-8859-15"), "Windows-1251");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new Embodiment(id, descr);
    }
}
