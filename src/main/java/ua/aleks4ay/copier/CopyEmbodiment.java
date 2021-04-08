package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.AbstractDao;
import ua.aleks4ay.domain.dao.EmbodimentDao;
import ua.aleks4ay.domain.dao.Utils;
import ua.aleks4ay.domain.javadbf.EmbodimentReader;
import ua.aleks4ay.domain.model.Embodiment;

import java.sql.Connection;
import java.util.List;

public class CopyEmbodiment extends AbstractCopy<Embodiment> {

    public CopyEmbodiment() {
        super("embodiment");
    }

    public static void main(String[] args) {
        new CopyEmbodiment().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Embodiment> dao = new EmbodimentDao(connection);
        List<Embodiment> embodimentsFrom1C = new EmbodimentReader().getAll();
        copyNewRecord(dao, embodimentsFrom1C);
        Utils.closeConnection(connection);
    }
}