package lk.ijse.culinaryacademy.controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.exception.InUseException;
import lk.ijse.culinaryacademy.tdm.StudentTm;
import lk.ijse.culinaryacademy.util.Regex;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StudentFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colProgram;

    @FXML
    private TableColumn<?, ?> colRegisterDate;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private ChoiceBox<String> programChoiceBox;

    @FXML
    private DatePicker registerDatePicker;

    @FXML
    private AnchorPane studentForm;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtInstallment;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private Pane visiblePane;

    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);

    public void initialize() {
        setCellValueFactory();
        loadAllStudent();
        setChoiceBoxData();
    }

    private void setChoiceBoxData() {
        List<ProgramsDTO> programs=studentBO.getAllProgram();
        ObservableList<String> programName= FXCollections.observableArrayList();
        for(ProgramsDTO program:programs){
            programName.add(program.getProgramName());
        }
        programChoiceBox.setItems(programName);
    }


    private void loadAllStudent() {
        List<StudentDTO> allStudent= studentBO.getAllStudent();
        tblStudent.getItems().clear();
        ObservableList<StudentTm> studentTms= tblStudent.getItems();
        for (StudentDTO studentDTO : allStudent) {
            studentTms.add(new StudentTm(studentDTO.getStudentId(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getTel(),studentDTO.getRegistrationDate(),createButton()));
        }
        tblStudent.setItems(studentTms);
    }

    private Button createButton() {
        Button button = new Button("ADD");
        button.setStyle("-fx-background-color: blue;-fx-text-fill: white;");

        button.setOnAction((e) -> {
            StudentTm selectedItem = (StudentTm) tblStudent.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                loadAddProgramForm(selectedItem);
                tblStudent.getSelectionModel().clearSelection();
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Select a row before clicking the button!").show();
            }
        });
        return button;
    }

    private void loadAddProgramForm(StudentTm selectedItem) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addProgramForm.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the controller for addProgramForm
            AddProgramFormController controller = loader.getController();

            // Pass the selected student to the new form
            controller.setSelectedStudent(selectedItem);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellValueFactory() {
      colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
      colName.setCellValueFactory(new PropertyValueFactory<>("name"));
      colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
      colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
      colRegisterDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
      colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
    }

    private void clearData(){
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtTel.clear();
        registerDatePicker.setValue(null);
        txtInstallment.clear();
        programChoiceBox.setValue(null);
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearData();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws InUseException {
        if (isValidStudent()){
            studentBO.deleteStudent(studentBO.getStudent(txtId.getText().trim()));
            loadAllStudent();
            clearData();
        }
    }

    private StudentDTO getObject(){
        return new StudentDTO(txtId.getText(),txtName.getText(),txtAddress.getText(),Long.parseLong(txtTel.getText()), Date.valueOf(registerDatePicker.getValue()),new ArrayList<>());
    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (isValidStudent()&& isValidEnroll()) {
            studentBO.saveStudentWithProgram(getObject(),programChoiceBox.getValue(),Double.parseDouble(txtInstallment.getText()));
            clearData();
            loadAllStudent();
        } else {
            new Alert(Alert.AlertType.WARNING,"Please enter all the fields before saving!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (isValidStudent()){
            studentBO.updateStudent(getObject());
            clearData();
            loadAllStudent();
        }
    }

    @FXML
    void tblStudentOnClickAction(MouseEvent event) {
        StudentTm selectedItem = tblStudent.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtId.setText(selectedItem.getStudentId());
            txtName.setText(selectedItem.getName());
            txtAddress.setText(selectedItem.getAddress());
            txtTel.setText(String.valueOf(selectedItem.getTel()));
            registerDatePicker.setValue(selectedItem.getRegistrationDate().toLocalDate());
        }
    }



    @FXML
    void txtAddressOnAction(ActionEvent event) {
        txtName.requestFocus();
    }
    @FXML
    void txtIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtAddress.requestFocus();
    }

    @FXML
    void txtNameKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.NAME, txtName);
    }
    @FXML
    void txtAddressKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.ADDRESS, txtAddress);
    }
    @FXML
    void txtInstallmentKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PRICE, txtInstallment);
    }

    @FXML
    void txtTelKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.TEL, txtTel);
    }
    @FXML
    void txtIdKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.STUDENTID, txtId);
    }

    private boolean isValidEnroll() {
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.STUDENTID, txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.NAME, txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.ADDRESS, txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.TEL, txtTel)) return false;
        if (txtId.getText().isEmpty() && registerDatePicker.getValue() == null) return false;
        return true;
    }

    private boolean isValidStudent() {
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PRICE, txtInstallment)) return false;
        if (programChoiceBox.getValue() == null) return false;
        return true;
    }

}

