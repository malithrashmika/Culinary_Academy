package lk.ijse.culinaryacademy.bo.custom.impl;

import lk.ijse.culinaryacademy.bo.custom.AuthenticationBO;
import lk.ijse.culinaryacademy.dto.UserDTO;
import lk.ijse.culinaryacademy.exception.InvalidCredentialsException;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;

import java.util.List;

public class AuthenticationBOImpl implements AuthenticationBO {
    @Override
    public UserDTO getUser(String userName) throws InvalidCredentialsException {
        return null;
    }

    @Override
    public void signUp(UserDTO userDTO) throws UserAlreadyExistsException {

    }

    @Override
    public List<UserDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(UserDTO userDTO) {

    }

    @Override
    public void updateUser(UserDTO userDTO) {

    }
}
