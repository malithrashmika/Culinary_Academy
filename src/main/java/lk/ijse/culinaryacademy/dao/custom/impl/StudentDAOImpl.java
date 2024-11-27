package lk.ijse.culinaryacademy.dao.custom.impl;

import lk.ijse.culinaryacademy.dao.custom.StudentDAO;
import lk.ijse.culinaryacademy.entity.Student;
import lk.ijse.culinaryacademy.exception.InUseException;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public void save(Student student) {

    }

    @Override
    public void update(Student student) {

    }

    @Override
    public void delete(Student student) throws InUseException {

//        studentDTO.getStudentId() ;
//        if (student have entrollment){
//                 throw new InUseException()
//        }



//        try {
//            studentdao.deleteStudent(studentDTO.getStudentId());
//        }catch (Exception e){
//            throw new InUseException("This student have enrollment.");
//        }
    }

    @Override
    public Student getAll(Student student) {
        return null;
    }

    @Override
    public Student findById(int id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
