package lk.ijse.culinaryacademy.dao.custom;

import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.dao.SuperDAO;
import lk.ijse.culinaryacademy.entity.Student;

import java.util.List;

public interface QueryDAO extends SuperDAO {

    List<Student> getAllProgramsStudent();
    List<Object[]> getAllEqualByProgramName(String programName);
}
