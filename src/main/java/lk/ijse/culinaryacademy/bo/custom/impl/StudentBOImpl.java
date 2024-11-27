package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dao.custom.impl.StudentDAOImpl;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.entity.Student;
import lk.ijse.culinaryacademy.exception.InUseException;

import java.util.List;

public class StudentBOImpl implements StudentBO {
    @Override
    public void deleteStudent(StudentDTO studentDTO) {
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        try {
            studentDAO.delete(new Student());
        }catch (InUseException e){
            alert("This student have enrollment");
        } catch (Exception e){
            alert("Somthing went wrong");
        }
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {

    }

    @Override
    public List<StudentDTO> getAllStudent() {
        return List.of();
    }

    @Override
    public List<ProgramsDTO> getAllCulinaryProgram() {
        return List.of();
    }

    @Override
    public void saveStudentWithProgram(StudentDTO object, String value, double v) {

    }

    @Override
    public StudentDTO getStudent(String studentId) {
        return null;
    }
}
