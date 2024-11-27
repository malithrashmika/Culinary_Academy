package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.RegistrationBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;

import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {
    @Override
    public void updateEnrollment(String studentId, String programName, double payment) {

    }

    @Override
    public List<ProgramsDTO> getAllCulinaryProgram() {
        return List.of();
    }

    @Override
    public List<Object[]> getAllEqualByProgramName(String programName) {
        return List.of();
    }
}
