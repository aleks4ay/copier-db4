package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.*;
import ua.aleks4ay.domain.javadbf.ClientReader;
import ua.aleks4ay.domain.model.Client;

import java.sql.Connection;
import java.util.List;

public class CopyClient extends AbstractCopy<Client>{

    public CopyClient() {
        super("client");
    }

    public static void main(String[] args) {
        new CopyClient().doCopy();
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<Client> dao = new ClientDao(connection);
        List<Client> clientsFrom1C = new ClientReader().getAll();
        copyNewRecord(dao, clientsFrom1C);
        Utils.closeConnection(connection);
    }
}

