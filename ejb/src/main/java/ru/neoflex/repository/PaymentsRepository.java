package ru.neoflex.repository;

import ru.neoflex.dao.Dao;
import ru.neoflex.entity.Payments;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateful
@RequestScoped
@LocalBean
public class PaymentsRepository implements Dao<Payments> {

    @PersistenceContext(unitName = "main")
    private EntityManager em;

    @Override
    public void create(Payments payments) {
        em.persist(payments);
    }

    @Override
    public void update(Payments payments) {
        em.merge(payments);
    }

    @Override
    public void destroy(Payments payments) {
        em.remove(payments);
    }

    @Override
    public Payments findById(Integer id) {
        return em.find(Payments.class, id);
    }

    @Override
    public List<Payments> findAll() {
        Query q = em.createNamedQuery("findAllPayments", Payments.class);
        return q.getResultList();
    }
}
