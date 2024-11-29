package lk.ijse.culinaryacademy.dao.custom.impl;

import lk.ijse.culinaryacademy.dao.custom.ProgramsDAO;
import lk.ijse.culinaryacademy.db.FactoryConfiguration;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.entity.Programs;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProgramsDAOImpl implements ProgramsDAO {
    @Override
    public void save(Programs programs) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(programs);

        transaction.commit();
        session.close();

    }

    @Override
    public void update(Programs programs) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(programs);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Programs programs) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(programs);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Programs> getAll() {
        List<Programs> programsList;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        programsList = session.createQuery("from Programs", Programs.class).list();
        transaction.commit();
        session.close();
        return programsList;
    }

    @Override
    public Programs getProgramsCheckName(String programName) {
        Programs program = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String hql = "FROM Programs p WHERE p.programName = :programName";
        Query<Programs> query = session.createQuery(hql, Programs.class);
        query.setParameter("programName", programName);
        program = query.uniqueResult();
        transaction.commit();
        session.close();
        return program;
    }

    @Override
    public Programs getCulinaryProgram(String programId) {
        Programs program = null;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        program = session.get(Programs.class, programId);

        transaction.commit();
        session.close();

        return program;
    }

    @Override
    public Long getProgramCount() {
        Long count = 0L;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT COUNT(c) FROM Programs c";
        Query<Long> query = session.createQuery(hql, Long.class);

        count = query.uniqueResult();

        transaction.commit();
        session.close();

        return count;
    }

    @Override
    public String generateProgramId() {
        String lastId = ""; // Get the last ID from the database
        String newId;

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery("SELECT p.programId FROM Programs p ORDER BY p.programId DESC", String.class);
            query.setMaxResults(1); // Get only the last programId
            lastId = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (lastId != null && !lastId.isEmpty()) {
            // Extract the numeric part from the last ID
            String numericPart = lastId.replaceAll("[^\\d]", ""); // Remove all non-numeric characters
            int nextId = Integer.parseInt(numericPart) + 1; // Increment the numeric part
            newId = String.format("P%03d", nextId); // Format as P001, P002, etc.
        } else {
            // If no IDs exist, start with P001
            newId = "P001";
        }

        return newId;
    }

}

