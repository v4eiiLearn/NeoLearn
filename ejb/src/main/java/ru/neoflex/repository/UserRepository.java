package ru.neoflex.repository;

import ru.neoflex.dao.Dao;
import ru.neoflex.entity.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateful
@LocalBean
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

    public User findByLoginAndPassword(String login, String psw) {
        Query q = em.createNamedQuery("findByLoginAndPassword", User.class);
        q.setParameter("login", login);
        q.setParameter("psw", psw);
        Object singleResult;
        try {
            singleResult = q.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
        return (User) singleResult;
    }

    public User findByLogin(String login) {
        Query q = em.createNamedQuery("findByLogin", User.class);
        q.setParameter("login", login);
        return (User) q.getSingleResult();
    }

}
