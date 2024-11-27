package lk.ijse.culinaryacademy.dao.custom;

import lk.ijse.culinaryacademy.dao.CrudDAO;
import lk.ijse.culinaryacademy.entity.User;

public interface UserDAO extends CrudDAO<User> {
    User getUser(String userName);
}
