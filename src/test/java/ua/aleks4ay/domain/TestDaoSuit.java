package ua.aleks4ay.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.aleks4ay.domain.dao.*;


@Suite.SuiteClasses(
    {
        ClientDaoTest.class,
        DescriptionDaoTest.class,
        EmbodimentDaoTest.class,
        InvoiceDescriptionDaoTest.class,
        InvoiseDaoTest.class,
        JournalDaoTest.class,
        ManufDaoTest.class,
        OrderDaoTest.class,
        TmcDaoTest.class,
        TmcTechnoBalanceDaoTest.class,
        WorkerDaoTest.class
    })
@RunWith(Suite.class)
public class TestDaoSuit {
}

