package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.AuthenticationBO;
import lk.ijse.culinaryacademy.dao.DAOFactory;
import lk.ijse.culinaryacademy.dao.custom.UserDAO;
import lk.ijse.culinaryacademy.dto.UserDTO;
import lk.ijse.culinaryacademy.entity.User;
import lk.ijse.culinaryacademy.exception.InUseException;
import lk.ijse.culinaryacademy.exception.InvalidCredentialsException;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationBOImpl implements AuthenticationBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDAO(DAOFactory.DAOType.USER);
    @Override
    public UserDTO getUser(String userName) throws InvalidCredentialsException {
        try {
            User user=userDAO.getUser(userName);
            return new UserDTO(user.getUserId(),user.getUserName(),user.getPassword(),user.getRole());
        } catch (Exception e) {
            throw new InvalidCredentialsException(e.getMessage());
        }
    }

    @Override
    public void signUp(UserDTO userDTO) throws UserAlreadyExistsException {
      User user=new User(userDTO.getUserName(),userDTO.getPassword(),userDTO.getRole());
      userDAO.save(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOS=new ArrayList<>();
        List<User> allUsers = userDAO.getAll();

        for (User user : allUsers) {
            userDTOS.add(new UserDTO(user.getUserId(),user.getUserName(),user.getPassword(),user.getRole()));
        }
        return userDTOS;
    }

    @Override
    public void deleteUser(UserDTO userDTO) throws InUseException {
        User user=new User(userDTO.getUserId(),userDTO.getUserName(),userDTO.getPassword(),userDTO.getRole());
        userDAO.delete(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        User user=new User(userDTO.getUserId(),userDTO.getUserName(),userDTO.getPassword(),userDTO.getRole());
        userDAO.update(user);
    }
}
