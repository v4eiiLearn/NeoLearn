package ru.neoflex.repository;

import ru.neoflex.dao.Dao;
import ru.neoflex.entity.PaymentSchedule;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PaymentScheduleRepository implements Dao<PaymentSchedule> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(PaymentSchedule paymentScheduleRepository) {
        em.persist(paymentScheduleRepository);
    }

    @Override
    public void update(PaymentSchedule paymentScheduleRepository) {
        em.merge(paymentScheduleRepository);
    }

    @Override
    public void destroy(PaymentSchedule paymentScheduleRepository) {
        em.remove(paymentScheduleRepository);
    }

    @Override
    public PaymentSchedule findById(Integer id) {
        return em.find(PaymentSchedule.class, id);
    }

    @Override
    public List<PaymentSchedule> findAll() {
        Query q = em.createNamedQuery("findAllPaymentSchedule");
        return q.getResultList();
    }
}
