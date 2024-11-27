package lk.ijse.culinaryacademy.dao.custom;

import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.dao.SuperDAO;
import lk.ijse.culinaryacademy.entity.Enrollment;

public interface EnrollmentDAO extends SuperDAO {
    void save(Enrollment enrollment);
    void update(Enrollment enrollment);
    Enrollment getEnrollment(String studentId,String programName);
}
