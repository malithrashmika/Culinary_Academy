package lk.ijse.culinaryacademy.dao;

import lk.ijse.culinaryacademy.exception.InUseException;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;

import java.util.List;

public interface CrudDAO<T> extends SuperDAO {
    void save(T t) throws UserAlreadyExistsException;
    void update(T t);
    void delete(T t) throws InUseException;
    List<T>getAll();

}
