package lk.ijse.culinaryacademy.dao;

import lk.ijse.culinaryacademy.exception.InUseException;

public interface CrudDAO<T> extends SuperDAO {
    void save(T t);
    void update(T t);
    void delete(T t) throws InUseException;
    T getAll(T t);
    T findById(int id);
    long count();
}
