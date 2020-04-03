package ru.neoflex.repository;

import ru.neoflex.dao.Dao;
import ru.neoflex.entity.CreditProduct;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateful
@LocalBean
public class CreditProductRepository implements Dao<CreditProduct> {

    @PersistenceContext(unitName = "main")
    private EntityManager em;

    public void create(CreditProduct creditProduct) {
        em.persist(creditProduct);
    }

    public void update(CreditProduct creditProduct) {
        em.merge(creditProduct);
    }

    public void destroy(CreditProduct creditProduct) {
        em.remove(creditProduct);
    }

    public CreditProduct findById(Integer id) {
        return em.find(CreditProduct.class, id);
    }

    public List<CreditProduct> findAll() {
        Query q = em.createNamedQuery("findAllCreditProduct", CreditProduct.class);
        return q.getResultList();
    }

    public List<CreditProduct> findBetweenPriceAndTerm(Long price, Integer term) {
        Query q = em.createNamedQuery("findProductBetweenPriceAndTerm", CreditProduct.class);
        q.setParameter("price", price);
        q.setParameter("term", term);
        return q.getResultList();
    }

    public CreditProduct findByName(String productName) {
        Query q = em.createNamedQuery("findByProductName", CreditProduct.class);
        q.setParameter("productName", productName);
        return (CreditProduct) q.getSingleResult();
    }

}
