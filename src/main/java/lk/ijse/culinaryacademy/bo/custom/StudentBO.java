package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.exception.InUseException;

import java.util.List;

public interface StudentBO extends SuperBO {
    void deleteStudent(StudentDTO studentDTO) throws InUseException
            ;
    void updateStudent(StudentDTO studentDTO);
    List<StudentDTO> getAllStudent();
    List<ProgramsDTO> getAllCulinaryProgram();
    void saveStudentWithProgram(StudentDTO object, String value, double v);
    StudentDTO getStudent(String studentId);
}
