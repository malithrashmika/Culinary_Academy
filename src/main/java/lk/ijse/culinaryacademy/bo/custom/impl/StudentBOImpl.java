package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.ProgramsDAO;
import lk.ijse.culinaryacademy.dao.custom.StudentDAO;
import lk.ijse.culinaryacademy.dao.custom.impl.StudentDAOImpl;
import lk.ijse.culinaryacademy.db.FactoryConfiguration;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.entity.Enrollment;
import lk.ijse.culinaryacademy.entity.Programs;
import lk.ijse.culinaryacademy.entity.Student;
import lk.ijse.culinaryacademy.exception.InUseException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class   StudentBOImpl implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAO(DAOFactory.DAOType.STUDENT);
    ProgramsDAO programsDAO = (ProgramsDAO) DAOFactory.getDAO(DAOFactory.DAOType.PROGRAM);
    @Override
    public void deleteStudent(StudentDTO studentDTO) throws InUseException {
        Student student=new Student(studentDTO.getStudentId(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getTel(),studentDTO.getRegistrationDate(),studentDTO.getEnrollments());
        studentDAO.delete(student);


//        StudentDAOImpl studentDAO = new StudentDAOImpl();
//        try {
//            studentDAO.delete(new Student());
//        }catch (InUseException e){
//            alert("This student have enrollment");
//        } catch (Exception e){
//            alert("Somthing went wrong");
//        }
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {
        Student student = new Student(studentDTO.getStudentId(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getTel(),studentDTO.getRegistrationDate(),studentDTO.getEnrollments());
        studentDAO.update(student);
    }

    @Override
    public List<StudentDTO> getAllStudent() {
        List<Student> allStudent = studentDAO.getAll();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (Student student : allStudent) {
            studentDTOList.add(new StudentDTO(student.getStudentId(),student.getName(),student.getAddress(),student.getTel(),student.getRegistrationDate(),student.getEnrollments()));
        }
        return studentDTOList;
    }

    @Override
    public List<ProgramsDTO> getAllProgram() {
        List<Programs> allprograms=programsDAO.getAll();
        List<ProgramsDTO> programsDTOList = new ArrayList<>();
        for (Programs programs: allprograms) {
            programsDTOList.add(new ProgramsDTO(programs.getProgramId(),programs.getProgramName(),programs.getDuration(),programs.getFee(),programs.getEnrollments()));
        }
        return programsDTOList;
    }

    @Override
    public void saveStudentWithProgram(StudentDTO studentDTO, String programName, double installment) {
Student student=new Student(studentDTO.getStudentId(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getTel(),studentDTO.getRegistrationDate(),studentDTO.getEnrollments());
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();
        try {
            session.save(student);
            Programs programCheckName= programsDAO.getProgramsCheckName(programName.trim());
            Enrollment enrollment= new Enrollment(installment,programCheckName.getFee()-installment,student,programCheckName);
            session.save(enrollment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public StudentDTO getStudent(String studentId) {
       Student student= studentDAO.getStudent(studentId);
       return new StudentDTO(student.getStudentId(),student.getName(),student.getAddress(),student.getTel(),student.getRegistrationDate(),student.getEnrollments());
    }

    @Override
    public Long getStudentCount() {
        return studentDAO.getStudentCount();
    }

    @Override
    public String getGeneratedStudentId() {
        return studentDAO.generateStudentId();
    }
}
