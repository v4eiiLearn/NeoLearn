package ru.neoflex.managedBean;


import ru.neoflex.entity.CreditProduct;
import ru.neoflex.entity.User;
import ru.neoflex.enums.UserRole;
import ru.neoflex.payments.schema.CreditType;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.*;

public class MyServletListener implements ServletContextListener {

    @PersistenceContext(unitName = "main")
    private EntityManager em;

    @Resource
    private UserTransaction userTransaction;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Query nativeQuery = em.createNativeQuery("SELECT * FROM accounts", User.class);
        if (nativeQuery.getResultList().size() == 0)
            initDBValues();
    }

    public void initDBValues() {
        User u = new User();
        User u1 = new User();
        u1.setUserRole(UserRole.CREDIT_MANAGER);
        u1.setUserPsw("nts");
        u1.setUserName("Katar");
        u1.setUserLogin("nts");
        u.setUserLogin("admin");
        u.setUserName("Admin");
        u.setUserPsw("admin");
        u.setUserRole(UserRole.SUPERVISOR);
        CreditProduct c = new CreditProduct("Продукт1", 80, 120, 600000L, 1500000L, 9.4f, CreditType.DIFF);
        CreditProduct c1 = new CreditProduct("Продукт2", 120, 200, 1500000L, 3000000L, 11.6f, CreditType.ANNUITY);
        try {
            userTransaction.begin();
            em.persist(u);
            em.persist(u1);
            em.persist(c);
            em.persist(c1);
            // does not work   ?
//            em.createNativeQuery("INSERT INTO accounts (login, password, user_name, role) " +
//                    "VALUES ('nts', 'nts', 'Katar', 'CREDIT_MANAGER'), ('admin', 'admin', 'Admin', 'SUPERVISOR')");
//            em.createNativeQuery("INSERT INTO credit_product (product_name, min_term, max_term, min_price, max_price, percent, type)" +
//                    " VALUES ('Продукт1', 80, 120, 600000, 1500000, 9.4, 'DIFF'), ('Продукт2', 120, 200, 1500000, 3000000, 11.6, 'ANNUITY')");
            userTransaction.commit();
        }
        catch (NotSupportedException | SystemException | HeuristicMixedException | HeuristicRollbackException | RollbackException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
