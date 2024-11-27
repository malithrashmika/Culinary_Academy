package lk.ijse.culinaryacademy.dao;

import lk.ijse.culinaryacademy.dao.custom.impl.*;

public class DAOFactory {

    public enum DAOType{
        PROGRAM,STUDENT,QUERY,ENROLLMENT,USER
    }
    public static SuperDAO getDAO(DAOType daoType){
        return switch (daoType){
            case PROGRAM -> new ProgramsDAOImpl();
            case STUDENT -> new StudentDAOImpl();
            case QUERY -> new QueryDAOImpl();
            case ENROLLMENT -> new EnrollmentDAOImpl();
            case USER -> new UserDAOImpl();
            default -> null;
        };
    }
}
