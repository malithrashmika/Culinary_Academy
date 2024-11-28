package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.exception.InUseException;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;

import java.util.List;

public interface AcademicBO extends SuperBO {

    void saveProgram(ProgramsDTO programsDTO) throws UserAlreadyExistsException;
    void deleteProgram(ProgramsDTO programsDTO) throws InUseException;
    void updateProgram(ProgramsDTO programsDTO);
    List<ProgramsDTO> getAllProgram();
    ProgramsDTO getProgram(String programId);

    // ADD PROGRAM TO STUDENT
    void registerStudentToProgram(String studentId,String programName,double installment);
    Long getProgramCount();
}
