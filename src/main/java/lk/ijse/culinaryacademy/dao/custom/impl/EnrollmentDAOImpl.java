package lk.ijse.culinaryacademy.dao.custom.impl;

import lk.ijse.culinaryacademy.dao.custom.EnrollmentDAO;
import lk.ijse.culinaryacademy.db.FactoryConfiguration;
import lk.ijse.culinaryacademy.entity.Enrollment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EnrollmentDAOImpl implements EnrollmentDAO {
    @Override
    public void save(Enrollment enrollment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(enrollment);

        transaction.commit();
        session.close();
    }

    @Override
    public void update(Enrollment enrollment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(enrollment);

        transaction.commit();
        session.close();
    }

    @Override
    public Enrollment getEnrollment(String studentId, String programName) {
       Enrollment enrollment =null;
       Session session = FactoryConfiguration.getInstance().getSession();
       Transaction transaction = session.beginTransaction();

       String hql = "SELECT Enrollment FROM Enrollment e JOIN e.student s JOIN e.programs p WHERE p.studentId = :studentId AND p.programName = :programName";
       enrollment=session.createQuery(hql,Enrollment.class).setParameter("studentId",studentId).setParameter("programName",programName).uniqueResult();
       transaction.commit();
       session.close();
       return enrollment;
    }
}
