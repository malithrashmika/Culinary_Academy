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
        List<Student> students=new ArrayList<>();

//        Session session = FactoryConfiguration.getInstance().getSession();
//        Transaction transaction = session.beginTransaction();
//
//        String hqlProgramCount = "select count(*) from Programs";
//        Query<Long> counthql=session.createQuery(hqlProgramCount, Long.class);
//        Long total=counthql.uniqueResult();
//
//        String hql="SELECT Student FROM Student s JOIN S.enrollments e GROUP BY s.studentId HAVING COUNT (DISTINCT e.programs.programId) =:totalPrograms";
//
//        Query <Student> query= session.createQuery(hql, Student.class);
//        query.setParameter("totalPrograms", total);
//
//        students=query.getResultList();
//        transaction.commit();
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
