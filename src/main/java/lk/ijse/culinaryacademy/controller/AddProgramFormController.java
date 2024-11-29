package lk.ijse.culinaryacademy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.AcademicBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.tdm.StudentTm;
import lk.ijse.culinaryacademy.util.Regex;
import lombok.Setter;

import java.util.List;

public class AddProgramFormController {

    @FXML
    private AnchorPane addProgramForm;

    @FXML
    private ChoiceBox<String> selectProgramChoiceBox;

    @FXML
    private TextField txtInstallment;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;
    @Setter
    private StudentTm selectedStudent;

    AcademicBO academicBO= (AcademicBO) BOFactory.getBO(BOFactory.BOType.ACADEMIC);
    public void initialize() {
        setChoiceBoxData();
    }

    private void setChoiceBoxData() {
        List<ProgramsDTO> program = academicBO.getAllProgram();
        ObservableList<String> programNames = FXCollections.observableArrayList();

        for (ProgramsDTO programDTO : program){
            programNames.add(programDTO.getProgramName());
        }
        selectProgramChoiceBox.setItems(programNames);
    }

    public void setSelectedStudent(StudentTm selectedStudent) {
        txtStudentId.setText(selectedStudent.getStudentId());
        txtStudentName.setText(selectedStudent.getName());
    }
    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) addProgramForm.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (isValied() && selectProgramChoiceBox.getValue() != null){
            academicBO.registerStudentToProgram(txtStudentId.getText().trim(),selectProgramChoiceBox.getValue().trim(), Double.parseDouble(txtInstallment.getText().trim()));
            new Alert(Alert.AlertType.CONFIRMATION,"Program Added Successfully").show();
            btnCancelOnAction(event);
        } else {
            new Alert(Alert.AlertType.WARNING,"Please Select a Program").show();
        }
    }

    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PRICE, txtInstallment)) return false;
        return true;
    }

    @FXML
    void txtInstallmentKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PRICE, txtInstallment);
    }

}
