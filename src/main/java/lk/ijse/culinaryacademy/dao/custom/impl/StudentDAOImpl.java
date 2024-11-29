package lk.ijse.culinaryacademy.dao.custom.impl;

import lk.ijse.culinaryacademy.dao.custom.StudentDAO;
import lk.ijse.culinaryacademy.db.FactoryConfiguration;
import lk.ijse.culinaryacademy.entity.Student;
import lk.ijse.culinaryacademy.exception.InUseException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public void save(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(student);

        transaction.commit();
        session.close();
    }

    @Override
    public void update(Student student) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(student);

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Student student) throws InUseException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(student);

        transaction.commit();
        session.close();

//        studentDTO.getStudentId() ;
//        if (student have entrollment){
//                 throw new InUseException()
//        }



//        try {
//            studentdao.deleteStudent(studentDTO.getStudentId());
//        }catch (Exception e){
//            throw new InUseException("This student have enrollment.");
//        }
    }

    @Override
    public List<Student> getAll() {
        List<Student> students;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        students = session.createQuery("from Student", Student.class).list();

        transaction.commit();
        session.close();

        return students;
    }

    @Override
    public Student getStudent(String studentId) {
        Student student = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        student = session.get(Student.class, studentId);

        transaction.commit();
        session.close();

        return student;
    }

    @Override
    public Long getStudentCount() {
        Long count = 0L;

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(s) FROM Student s";
        Query<Long> query = session.createQuery(hql, Long.class);

        count = query.uniqueResult();

        transaction.commit();
        session.close();

        return count;
    }

    @Override
    public String generateStudentId() {
        String lastId = ""; // Holds the last student ID from the database
        String newId;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery("SELECT s.studentId FROM Student s ORDER BY s.studentId DESC", String.class);
            query.setMaxResults(1); // Fetch only the latest studentId
            lastId = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lastId != null && !lastId.isEmpty()) {
            // Extract the numeric part from the last ID
            String numericPart = lastId.replaceAll("[^\\d]", ""); // Remove non-numeric characters
            int nextId = Integer.parseInt(numericPart) + 1; // Increment the numeric part
            newId = String.format("S%03d", nextId); // Format as S001, S002, etc.
        } else {
            // If no IDs exist, start with S001
            newId = "S001";
        }

        return newId;
    }


}
