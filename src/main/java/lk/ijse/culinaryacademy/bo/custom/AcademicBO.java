package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;

import java.util.List;

public interface AcademicBO extends SuperBO {

    void saveProgram(ProgramsDTO culinaryProgramDTO);
    void deleteProgram(ProgramsDTO culinaryProgramDTO);
    void updateProgram(ProgramsDTO culinaryProgramDTO);
    List<ProgramsDTO> getAllProgram();
    ProgramsDTO getProgram(String programId);

    // ADD PROGRAM TO STUDENT
    void registerStudentToProgram(String studentId,String programName,double installment);
    Long getCulinaryProgramCount();

    List<ProgramsDTO> getAllCulinaryProgram();
