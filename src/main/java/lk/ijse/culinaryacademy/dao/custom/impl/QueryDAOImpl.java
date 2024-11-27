package lk.ijse.culinaryacademy.dao.custom.impl;

import lk.ijse.culinaryacademy.dao.custom.QueryDAO;
import lk.ijse.culinaryacademy.entity.Student;

import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<Student> getAllProgramsStudent() {
        return List.of();
    }

    @Override
    public List<Object[]> getAllEqualByProgramName(String programName) {
        return List.of();
    }
}
