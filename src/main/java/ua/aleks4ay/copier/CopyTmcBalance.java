package ua.aleks4ay.copier;

import ua.aleks4ay.domain.dao.*;
import ua.aleks4ay.domain.javadbf.TmcBalanceReader;
import ua.aleks4ay.domain.model.TmcBalance;

import java.sql.Connection;
import java.util.*;

public class CopyTmcBalance extends AbstractCopy<TmcBalance>{

    public static void main(String[] args) {
        new CopyTmcBalance().doCopy();
    }

    public CopyTmcBalance() {
        super("techno_item");
    }

    public void doCopy() {
        Connection connection = Utils.getConnection();
        AbstractDao<TmcBalance> dao = new TmcTechnoBalanceDao(connection);
        List<TmcBalance> tmcBalancesFrom1C = new TmcBalanceReader().getAll();
        Set<TmcBalance> tmcSet = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        for (TmcBalance t : tmcBalancesFrom1C) {
            tmcSet.add(t);
        }
        List<TmcBalance> tmcBalancesAfterFilter = new ArrayList<>();
        tmcBalancesAfterFilter.addAll(tmcSet);
        copyNewRecord(dao, tmcBalancesAfterFilter);
        Utils.closeConnection(connection);
    }
}
