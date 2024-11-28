package lk.ijse.culinaryacademy.bo.custom;

import lk.ijse.culinaryacademy.bo.SuperBO;
import lk.ijse.culinaryacademy.dto.UserDTO;
import lk.ijse.culinaryacademy.exception.InUseException;
import lk.ijse.culinaryacademy.exception.InvalidCredentialsException;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;

import java.util.List;

public interface AuthenticationBO extends SuperBO {
    UserDTO getUser(String userName) throws InvalidCredentialsException;

    void signUp(UserDTO userDTO) throws UserAlreadyExistsException, UserAlreadyExistsException;

    List<UserDTO> getAllUsers();
    void deleteUser(UserDTO userDTO) throws InUseException;
    void updateUser(UserDTO userDTO);
}
