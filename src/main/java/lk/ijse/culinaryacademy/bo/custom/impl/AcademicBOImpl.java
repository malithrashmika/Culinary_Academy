package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.AcademicBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.ProgramsDAO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.entity.Programs;
import lk.ijse.culinaryacademy.exception.InUseException;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public class AcademicBOImpl  implements AcademicBO {

    ProgramsDAO programsDAO= (ProgramsDAO) DAOFactory.getDAO(DAOFactory.DAOType.PROGRAM);

    @Override
    public void saveProgram(ProgramsDTO programsDTO) throws UserAlreadyExistsException {
        Programs programs = new Programs(programsDTO.getProgramId(), programsDTO.getProgramName(), programsDTO.getDuration(), programsDTO.getFee(), programsDTO.getEnrollments());
        programsDAO.save(programs);
    }

    @Override
    public void deleteProgram(ProgramsDTO programsDTO) throws InUseException {
        Programs programs=new Programs(programsDTO.getProgramId(), programsDTO.getProgramName(), programsDTO.getDuration(), programsDTO.getFee(), programsDTO.getEnrollments());
        programsDAO.delete(programs);
    }

    @Override
    public void updateProgram(ProgramsDTO programsDTO) {
        Programs programs=new Programs(programsDTO.getProgramId(), programsDTO.getProgramName(), programsDTO.getDuration(), programsDTO.getFee(), programsDTO.getEnrollments());
        programsDAO.update(programs);
    }

    @Override
    public List<ProgramsDTO> getAllProgram() {
       List<Programs> allprograms=programsDAO.getAll();
       List<ProgramsDTO> programsDTOs=new ArrayList<ProgramsDTO>();
       for (Programs program : allprograms) {
           programsDTOs.add(new ProgramsDTO(program.getProgramId(), program.getProgramName(), program.getDuration(), program.getFee(), program.getEnrollments()));
       }
       return programsDTOs;
    }

    @Override
    public ProgramsDTO getProgram(String programId) {
        Programs programs=programsDAO.getCulinaryProgram(programId);
        if(programs!=null){
            return new ProgramsDTO(programs.getProgramId(), programs.getProgramName(), programs.getDuration(), programs.getFee(), programs.getEnrollments());
        }
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
