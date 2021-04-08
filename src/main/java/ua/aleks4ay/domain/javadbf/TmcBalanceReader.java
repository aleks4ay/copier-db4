package ua.aleks4ay.domain.javadbf;

import com.linuxense.javadbf.DBFRow;
import ua.aleks4ay.domain.model.AbstractEntity;
import ua.aleks4ay.domain.model.TmcBalance;

import java.sql.Timestamp;
import java.util.*;

public class TmcBalanceReader extends AbstractReader<TmcBalance>{

    public static void main(String[] args) {
        TmcBalanceReader tmcBalanceReader = new TmcBalanceReader();
        List<TmcBalance> tmcBalances = tmcBalanceReader.getAll();
        Set<TmcBalance> tmcSet = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        for (TmcBalance t : tmcBalances) {
            tmcSet.add(t);
        }
        tmcSet.forEach(System.out::println);
        System.out.println(tmcBalances.size());
        System.out.println("size of Tmc = " + tmcSet.size());
    }

    public TmcBalanceReader() {
        super("tmc_balance", "RG1253.DBF");
    }

    @Override
    public TmcBalance readEntity(DBFRow row) {
        if (! row.getString("SP4643").equals("     C")) {
            return null;
        }
        Date period = row.getDate("PERIOD");
        String idTmc = row.getString("SP1249");
        Integer count = row.getInt("SP1251");
        return new TmcBalance(idTmc, count, new Timestamp(period.getTime()));
    }
}
