package ir.mctab.hw12.instagram.repositories;

import ir.mctab.hw12.instagram.config.HibernateUtil;
import ir.mctab.hw12.instagram.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class UserRepository extends CrudDAO<User, Long> {

    private static UserRepository userRepository;

    private UserRepository() {
    }

    public static UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository();
            return userRepository;
        }
        return userRepository;
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    public User loadByUsername(String username) {
        User user;
        Session session = HibernateUtil.getSession();
        Query<User> query = session.createQuery("from User where username ='" + username + "'");
        user = query.uniqueResult();
        return user;
    }

    public User loadByUsernamePassword(String username, String password) {
        User user;
        Session session = HibernateUtil.getSession();
        Query<User> query = session.createQuery("from User where username ='" + username + "' and password ='" + password + "'");
        user = query.uniqueResult();
        return user;
    }

}
