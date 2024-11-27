package lk.ijse.culinaryacademy.dao.custom.impl;

import lk.ijse.culinaryacademy.dao.custom.UserDAO;
import lk.ijse.culinaryacademy.db.FactoryConfiguration;
import lk.ijse.culinaryacademy.entity.User;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    @Override
    public void save(User user) throws UserAlreadyExistsException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.save(user);
        } catch (Exception e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }
        transaction.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(user);

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAll(User user) {
        List<User> users = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        users = session.createQuery("from User", User.class).list();

        transaction.commit();
        session.close();

        return users;
    }

    @Override
    public User getUser(String userName) {
        User user = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "from User u where u.userName = :userName";
        user = session.createQuery(hql, User.class).setParameter("userName", userName).uniqueResult();
        transaction.commit();
        session.close();
        return user;
    }
}
