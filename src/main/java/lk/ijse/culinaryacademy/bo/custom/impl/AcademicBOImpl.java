package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.AcademicBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;

import java.util.List;

public class AcademicBOImpl  implements AcademicBO {

    @Override
    public void saveProgram(ProgramsDTO culinaryProgramDTO) {

    }

    @Override
    public void deleteProgram(ProgramsDTO culinaryProgramDTO) {

    }

    @Override
    public void updateProgram(ProgramsDTO culinaryProgramDTO) {

    }

    @Override
    public List<ProgramsDTO> getAllProgram() {
        return List.of();
    }

    @Override
    public ProgramsDTO getProgram(String programId) {
        return null;
    }

    @Override
    public void registerStudentToProgram(String studentId, String programName, double installment) {

    }

    @Override
    public Long getCulinaryProgramCount() {
        return 0;
    }

    @Override
    public List<ProgramsDTO> getAllCulinaryProgram() {
        return List.of();
    }
}
