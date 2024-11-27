package lk.ijse.culinaryacademy.bo;

import lk.ijse.culinaryacademy.bo.custom.impl.AcademicBOImpl;
import lk.ijse.culinaryacademy.bo.custom.impl.AuthenticationBOImpl;
import lk.ijse.culinaryacademy.bo.custom.impl.RegistrationBOImpl;
import lk.ijse.culinaryacademy.bo.custom.impl.StudentBOImpl;

public class BOFactory {
    public enum BOType{
        ACADEMIC,STUDENT,REGISTRATION,AUTH
    }

        public static SuperBO getBO(BOType boType){
        return switch (boType){
            case AUTH -> new AuthenticationBOImpl();
            case REGISTRATION -> new RegistrationBOImpl();
            case STUDENT -> new StudentBOImpl();
            case ACADEMIC -> new AcademicBOImpl();
            default -> null;
        };
        }
}
