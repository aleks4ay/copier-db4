package ua.aleks4ay.copier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleks4ay.domain.dao.AbstractDao;
import ua.aleks4ay.domain.model.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ua.aleks4ay.log.ClassNameUtil.getCurrentClassName;

public abstract class AbstractCopy<T> {
    private final long start = System.currentTimeMillis();
    private final String entityName;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());

    protected AbstractCopy (String name) {
        this.entityName = name;
        log.info("Start writing '{}'.", entityName);
    }

    public void copyNewRecord(AbstractDao<T> dao, List<T> tt) {
        List<T> listNewEntity = new ArrayList<>();
        List<T> listUpdatingEntity = new ArrayList<>();
        Map<String, T> oldEntity = dao.getAllAsMap();
        for (T t : tt) {
            String id = ((AbstractEntity)t).getId();
            if (!oldEntity.containsKey(id)) {
                listNewEntity.add(t);
            } else if (!oldEntity.get(id).equals(t)) {
                listUpdatingEntity.add(t);
                log.info("Changes: {}", ((AbstractEntity<T>)t).getDifferences(oldEntity.get(id)));
                oldEntity.remove(id);
            }
            else {
                oldEntity.remove(id);
            }
        }
        if (listNewEntity.size() > 0) {
            boolean isSaved = dao.saveAll(listNewEntity);
            if (isSaved) {
                log.info("Added {} new {} to DataBase.", listNewEntity.size(), entityName);
            }
        }
        if (listUpdatingEntity.size() > 0) {
            boolean isUpdated = dao.updateAll(listUpdatingEntity);
            if (isUpdated) {
                log.info("Updated {} new {} to DataBase.", listUpdatingEntity.size(), entityName);
            }
        }
        if (oldEntity.size() > 0) {
            boolean isDeleted = dao.deleteAll(oldEntity.values());
            if (isDeleted) {
                log.info("Delete {} new {} from DataBase.", oldEntity.size(), entityName);
            }
        }
        long end = System.currentTimeMillis();
        log.info("End writing '{}'. Time = {} c.", entityName, (double)(end-start)/1000);
    }
}

