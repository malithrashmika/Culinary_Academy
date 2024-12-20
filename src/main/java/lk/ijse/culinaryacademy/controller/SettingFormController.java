package lk.ijse.culinaryacademy.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.AuthenticationBO;
import lk.ijse.culinaryacademy.dto.UserDTO;
import lk.ijse.culinaryacademy.tdm.UserTm;
import lk.ijse.culinaryacademy.util.PasswordStorage;

import java.util.List;
import java.util.Optional;

public class SettingFormController {

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colUserName;

    @FXML
    private TableColumn<?, ?> colUserRole;

    @FXML
    private AnchorPane settingForm;

    @FXML
    private TableView<UserTm> tblUser;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Pane visiblePane;

    AuthenticationBO authenticationBO = (AuthenticationBO) BOFactory.getBO(BOFactory.BOType.AUTH);

    List<UserDTO> allUsers;

    public void initialize() {
        txtNewPassword.setVisible(false);
        txtConfirmPassword.setVisible(false);
        txtUserName.setText(LoginFormController.userDTO.getUserName());

        if (!LoginFormController.userDTO.getRole().equals("Admin")) {
            visiblePane.setVisible(false);
        }

        setCellValueFactory();
        loadAllUsers();
    }

    private void loadAllUsers() {
        tblUser.getItems().clear();
        ObservableList<UserTm> userTms = tblUser.getItems();
        allUsers = authenticationBO.getAllUsers();

        for (UserDTO userDTO : allUsers) {
            userTms.add(new UserTm(userDTO.getUserName(), userDTO.getRole(), createButton(userDTO)));
        }
        tblUser.setItems(userTms);
    }

    private void setCellValueFactory() {
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("delete"));
    }

    // Create the delete button for each user row
    private Button createButton(UserDTO userDTO) {
        Button button = new Button("Delete");
        button.setStyle("-fx-background-color: red;-fx-text-fill: white;");

        button.setOnAction((e) -> {
            deleteUser(userDTO);  // Call deleteUser when the button is clicked
        });

        return button;
    }

    // Method to delete a user
    private void deleteUser(UserDTO userDTO) {
        // Ask for confirmation before deleting the user
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure you want to remove this user?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            try {
                authenticationBO.deleteUser(userDTO);  // Call the BO layer to delete the user
                loadAllUsers();  // Reload the user list after deletion
            } catch (Exception exception) {
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting the user!").show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (isValied()) {
            if (txtNewPassword.getText().trim().equals(txtConfirmPassword.getText().trim())) {
                UserDTO userDTO = new UserDTO(LoginFormController.userDTO.getUserId(), txtUserName.getText().trim(),
                        PasswordStorage.hashPassword(txtConfirmPassword.getText().trim()), LoginFormController.userDTO.getRole());
                authenticationBO.updateUser(userDTO);
                loadAllUsers();
                txtPassword.clear();
                txtConfirmPassword.clear();
                txtNewPassword.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "Incorrect Confirm Password!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Please Enter All Fields!").show();
        }
    }

    private boolean isValied() {
        return !txtUserName.getText().isEmpty() && !txtPassword.getText().isEmpty() && !txtNewPassword.getText().isEmpty() && !txtConfirmPassword.getText().isEmpty();
    }

    @FXML
    void txtNewPasswordOnAction(ActionEvent event) {
        txtConfirmPassword.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        if (PasswordStorage.checkPassword(txtPassword.getText().trim(), LoginFormController.userDTO.getPassword())) {
            txtNewPassword.requestFocus();
            txtNewPassword.setVisible(true);
            txtConfirmPassword.setVisible(true);
        } else {
            new Alert(Alert.AlertType.ERROR, "Incorrect Password!").show();
        }
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

}
