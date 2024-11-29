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

        // Initialize session and transaction
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Step 1: Get the total count of programs
            String hqlProgramCount = "SELECT COUNT(p.programId) FROM Programs p";
            Query<Long> countQuery = session.createQuery(hqlProgramCount, Long.class);
            Long totalPrograms = countQuery.uniqueResult();

            // Step 2: Fetch students enrolled in all programs
            String hql = "SELECT s FROM Student s " +
                    "JOIN s.enrollments e " +
                    "GROUP BY s.studentId " +
                    "HAVING COUNT(DISTINCT e.programs.programId) = :totalPrograms";

            Query<Student> query = session.createQuery(hql, Student.class);
            query.setParameter("totalPrograms", totalPrograms);

            // Execute the query and get the results
            students = query.getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
