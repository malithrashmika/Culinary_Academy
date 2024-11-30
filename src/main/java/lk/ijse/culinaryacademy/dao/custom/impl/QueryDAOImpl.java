package lk.ijse.culinaryacademy.dao.custom.impl;

import lk.ijse.culinaryacademy.dao.custom.QueryDAO;
import lk.ijse.culinaryacademy.db.FactoryConfiguration;
import lk.ijse.culinaryacademy.entity.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<Student> getAllProgramsStudent() {
        List<Student> students = new ArrayList<>();
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Fetch total number of programs
            String hqlProgramCount = "SELECT COUNT(p.programId) FROM Programs p";
            Long totalPrograms = session.createQuery(hqlProgramCount, Long.class).uniqueResult();

            if (totalPrograms == null || totalPrograms == 0) {
                System.out.println("No programs found in the database.");
                return students; // Return empty list
            }

            // Fetch students enrolled in all programs
            String hql = "SELECT s FROM Student s " +
                    "JOIN s.enrollments e " +
                    "GROUP BY s.studentId " +
                    "HAVING COUNT(DISTINCT e.programs.programId) = :totalPrograms";

            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("totalPrograms", totalPrograms);

            students = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return students;
    }



    @Override
    public List<Object[]> getAllEqualByProgramName(String programName) {
        List<Object[]> results=null;

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql= "SELECT s,e,p FROM Student s JOIN s.enrollments e JOIN e.programs p WHERE p.programName = :programName";
        results =session.createQuery(hql).setParameter("programName",programName).getResultList();
        transaction.commit();
        return results;
    }
}
