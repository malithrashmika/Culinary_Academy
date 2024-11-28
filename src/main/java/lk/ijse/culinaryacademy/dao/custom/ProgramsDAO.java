package lk.ijse.culinaryacademy.dao.custom;

import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.entity.Programs;

public interface ProgramsDAO extends CrudDAO<Programs> {
    Programs getProgramsCheckName(String programName);
    Programs getCulinaryProgram(String programId);
    Long getProgramCount();
}
