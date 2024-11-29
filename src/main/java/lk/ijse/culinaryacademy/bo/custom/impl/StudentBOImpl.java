package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.ProgramsDAO;
import lk.ijse.culinaryacademy.dao.custom.StudentDAO;
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

public class StudentBOImpl implements StudentBO {
   StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAO(DAOFactory.DAOType.STUDENT);
   ProgramsDAO programsDAO = (ProgramsDAO) DAOFactory.getDAO(DAOFactory.DAOType.PROGRAM);

    @Override
    public void deleteStudent(StudentDTO studentDTO) throws InUseException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Fetch student from the database
            Student studentToDelete = session.get(Student.class, studentDTO.getStudentId());

            if (studentToDelete != null) {
                // Check if the student has active enrollments
                if (!studentToDelete.getEnrollments().isEmpty()) {
                    throw new InUseException("This student has active enrollments and cannot be deleted.");
                }

                // Then delete the student
                session.delete(studentToDelete);
                transaction.commit();
            } else {
                throw new RuntimeException("Student not found!");
            }
        } catch (InUseException e) {
            transaction.rollback();
            throw e;
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void updateStudent(StudentDTO studentDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Fetch existing student from the database
            Student existingStudent = session.get(Student.class, studentDTO.getStudentId());

            if (existingStudent != null) {
                // Update the student's details
                existingStudent.setName(studentDTO.getName());
                existingStudent.setAddress(studentDTO.getAddress());
                existingStudent.setTel(studentDTO.getTel());
                existingStudent.setRegistrationDate(studentDTO.getRegistrationDate());

                // Update enrollments if needed
                for (Enrollment enrollment : existingStudent.getEnrollments()) {
                    // Update specific details for enrollments if needed
                    // For example, update installment or program if required
                    session.update(enrollment);
                }

                // Save the updated student
                session.update(existingStudent);
                transaction.commit();
            } else {
                throw new RuntimeException("Student not found!");
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<StudentDTO> getAllStudent() {
        List<Student> allStudents = studentDAO.getAll();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (Student student : allStudents) {
            studentDTOList.add(new StudentDTO(student.getStudentId(), student.getName(), student.getAddress(),
                    student.getTel(), student.getRegistrationDate(), student.getEnrollments()));
        }
        return studentDTOList;
    }

    @Override
    public List<ProgramsDTO> getAllProgram() {
        List<Programs> allPrograms = programsDAO.getAll();
        List<ProgramsDTO> programsDTOList = new ArrayList<>();
        for (Programs program : allPrograms) {
            programsDTOList.add(new ProgramsDTO(program.getProgramId(), program.getProgramName(),
                    program.getDuration(), program.getFee(), program.getEnrollments()));
        }
        return programsDTOList;
    }

    @Override
    public void saveStudentWithProgram(StudentDTO studentDTO, String programName, double installment) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            // Save the student
            Student student = new Student(studentDTO.getStudentId(), studentDTO.getName(), studentDTO.getAddress(),
                    studentDTO.getTel(), studentDTO.getRegistrationDate(), studentDTO.getEnrollments());
            session.save(student);

            // Get the program details
            Programs program = programsDAO.getProgramsCheckName(programName.trim());

            // Create the enrollment and associate it with the student and program
            Enrollment enrollment = new Enrollment(installment, program.getFee() - installment, student, program);
            session.save(enrollment);

            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public StudentDTO getStudent(String studentId) {
        Student student = studentDAO.getStudent(studentId);
        return new StudentDTO(student.getStudentId(), student.getName(), student.getAddress(),
                student.getTel(), student.getRegistrationDate(), student.getEnrollments());
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
