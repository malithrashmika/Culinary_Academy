package lk.ijse.culinaryacademy.dao.custom;

import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.dao.SuperDAO;
import lk.ijse.culinaryacademy.entity.Student;

public interface StudentDAO extends CrudDAO<Student> {
    Student getStudent(String studentId);
    Long getStudentCount();
}
