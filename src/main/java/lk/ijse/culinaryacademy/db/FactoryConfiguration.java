package lk.ijse.culinaryacademy.db;

import lk.ijse.culinaryacademy.entity.Enrollment;
import lk.ijse.culinaryacademy.entity.Programs;
import lk.ijse.culinaryacademy.entity.Student;
import lk.ijse.culinaryacademy.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private static SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Programs.class)
                .addAnnotatedClass(Enrollment.class)
                .addAnnotatedClass(Student.class);

        sessionFactory = configuration.buildSessionFactory();
    }


    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration =
                new FactoryConfiguration() : factoryConfiguration;

    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

}
