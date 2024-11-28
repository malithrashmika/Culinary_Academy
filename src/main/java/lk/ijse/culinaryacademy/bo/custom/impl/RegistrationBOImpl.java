package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.RegistrationBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.EnrollmentDAO;
import lk.ijse.culinaryacademy.dao.custom.ProgramsDAO;
import lk.ijse.culinaryacademy.dao.custom.QueryDAO;
import lk.ijse.culinaryacademy.dao.custom.StudentDAO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.entity.Enrollment;
import lk.ijse.culinaryacademy.entity.Programs;
import lk.ijse.culinaryacademy.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {
    ProgramsDAO programsDAO= (ProgramsDAO) DAOFactory.getDAO(DAOFactory.DAOType.STUDENT);
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAO(DAOFactory.DAOType.STUDENT);
    EnrollmentDAO enrollmentDAO = (EnrollmentDAO) DAOFactory.getDAO(DAOFactory.DAOType.ENROLLMENT);
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAO(DAOFactory.DAOType.QUERY);

    @Override
    public void updateEnrollment(String studentId, String programName, double payment) {
        Student student = studentDAO.getStudent(studentId);
        Enrollment enrollment= enrollmentDAO.getEnrollment(studentId, programName);
        Programs programs=programsDAO.getProgramsCheckName(programName);
        enrollmentDAO.update(new Enrollment(enrollment.getEnrollId(),enrollment.getFirstInstallment(),enrollment.getBalance()-payment,student,programs));
    }

    @Override
    public List<Object[]> getAllEqualByProgramName(String programName) {
        return queryDAO.getAllEqualByProgramName(programName);
    }
    @Override
    public List<StudentDTO> getAllProgramStudents(){
        List<StudentDTO> studentDTOS = new ArrayList<>();
        List<Student> allProgramsStudent = queryDAO.getAllProgramsStudent();

        for (Student student : allProgramsStudent) {
            studentDTOS.add(new StudentDTO(student.getStudentId(),student.getName(),student.getAddress(),student.getTel(),student.getRegistrationDate(),student.getEnrollments()));
        }
        return studentDTOS;
    }
}
