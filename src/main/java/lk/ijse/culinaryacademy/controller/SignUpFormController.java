package lk.ijse.culinaryacademy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.AuthenticationBO;
import lk.ijse.culinaryacademy.dto.UserDTO;
import lk.ijse.culinaryacademy.exception.ExceptionHandler;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;
import lk.ijse.culinaryacademy.util.PasswordStorage;

import java.io.IOException;

public class SignUpFormController {

    @FXML
    private RadioButton adminCheckBox;

    @FXML
    private RadioButton admissionCheckBox;

    @FXML
    private PasswordField inputPassword;

    @FXML
    private TextField inputUserName;

    @FXML
    private AnchorPane signUpForm;

    AuthenticationBO authenticationBO= (AuthenticationBO) BOFactory.getBO(BOFactory.BOType.AUTH);

    @FXML
    void adminCheckBoxOnAction(ActionEvent event) {
        adminCheckBox.setSelected(true);
        admissionCheckBox.setSelected(false);
    }

    @FXML
    void admissionCheckBoxOnAction(ActionEvent event) {
        admissionCheckBox.setSelected(true);
        adminCheckBox.setSelected(false);
    }

    @FXML
    void backToLoginOnAction(MouseEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/loginForm.fxml")));
            Stage stage = (Stage) signUpForm.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void inputUserNameOnAction(ActionEvent event) {
        inputPassword.requestFocus();
    }

    @FXML
    void signUpBtnOnAction(ActionEvent event) throws UserAlreadyExistsException {
        if (isValied()){
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(inputUserName.getText().trim());
            userDTO.setPassword(PasswordStorage.hashPassword(inputPassword.getText().trim()));

            if (adminCheckBox.isSelected()) {
                userDTO.setRole("Admin");
            } else {
                userDTO.setRole("Admissions Coordinator");
            }

            try {
                authenticationBO.signUp(userDTO);
            } catch (UserAlreadyExistsException e) {
                ExceptionHandler.handleException(e);
            }

            inputUserName.clear();
            inputPassword.clear();
            adminCheckBox.setSelected(false);
            admissionCheckBox.setSelected(false);
        } else {
            new Alert(Alert.AlertType.WARNING,"Please Enter All Fields !!").show();
        }
    }
    public boolean isValied() {
        return !inputUserName.getText().isEmpty() && !inputPassword.getText().isEmpty() && (adminCheckBox.isSelected() || admissionCheckBox.isSelected());
    }
}
