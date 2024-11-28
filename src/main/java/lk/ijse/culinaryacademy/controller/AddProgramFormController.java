package lk.ijse.culinaryacademy.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.culinaryacademy.tdm.StudentTm;
import lombok.Setter;

public class AddProgramFormController {

    @FXML
    private AnchorPane addProgramForm;

    @FXML
    private ChoiceBox<?> selectProgramChoiceBox;

    @FXML
    private TextField txtInstallment;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;
    @Setter
    private StudentTm selectedStudent;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void txtInstallmentKeyAction(KeyEvent event) {

    }

}
