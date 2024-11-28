package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;

import java.util.List;

public interface RegistrationBO extends SuperBO {
    void updateEnrollment(String studentId,String programName,double payment);
    List<Object[]> getAllEqualByProgramName(String programName);
}
