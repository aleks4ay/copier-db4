package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.domain.dao.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ua.aleks4ay.log.ClassNameUtil.getCurrentClassName;

public abstract class AbstractReader<T> {

    public abstract T readEntity(DBFRow row);

    private final String tableName;
    private final String pathName;
    private final String dbfFolderPath;

    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());


    protected AbstractReader(String tableName, String pathName) {
        this.tableName = tableName;
        this.pathName = pathName;
        this.dbfFolderPath = Utils.getDbfReaderPath();
    }

    public List<T> getAll() {
        List<T> listResult = new ArrayList<>();

        DBFReader reader = null;
        try {
            reader = new DBFReader(new FileInputStream(dbfFolderPath + pathName));
            log.debug("Get connection to file {}.", pathName);
            DBFRow row;

            while ((row = reader.nextRow()) != null) {
                T t = readEntity(row);
                if (t != null) {
                    listResult.add(t);
                }
            }

            log.debug("Was read {} {} from 1S.", listResult.size(), tableName);
            return listResult;
        } catch (DBFException | IOException e) {
            log.warn("Exception during reading file '{}'." ,tableName, e);
        } catch (Exception e) {
            log.warn("Exception during writing all '{}'.", tableName, e);
        }
        finally {
            DBFUtils.close(reader);
        }

        log.debug("{} not found.", tableName);
        return null;
    }
}
