package jm.task.core.jdbc.dao;

import com.mysql.cj.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String cut = "CREATE TABLE IF NOT EXISTS users" +
                "                (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "                name VARCHAR(50)," +
                "                 lastname VARCHAR(50)," +
                "                 age TINYINT(3));";
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.createSQLQuery(cut)
                    .executeUpdate();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String dut = "DROP TABLE IF EXISTS users;";
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.createSQLQuery(dut)
                    .executeUpdate();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try{
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.save(new User(name, lastName, age));

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.delete(session.get(User.class, id));

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            users = session.createQuery("FROM User ")
                            .getResultList();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = Util.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            session.createQuery("DELETE User")
                            .executeUpdate();

            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
