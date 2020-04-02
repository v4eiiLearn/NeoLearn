package ru.neoflex.repository;

import ru.neoflex.dao.Dao;
import ru.neoflex.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class UserRepository implements Dao<User> {

    @PersistenceContext(unitName = "main")
    private EntityManager em;

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void destroy(User user) {
        em.remove(user);
    }

    @Override
    public User findById(Integer id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        Query q = em.createNamedQuery("findAllUsers", User.class);
        return q.getResultList();
    }
}
