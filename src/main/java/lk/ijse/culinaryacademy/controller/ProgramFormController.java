package lk.ijse.culinaryacademy.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.AcademicBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.exception.InUseException;
import lk.ijse.culinaryacademy.exception.UserAlreadyExistsException;
import lk.ijse.culinaryacademy.tdm.ProgramTm;
import lk.ijse.culinaryacademy.util.Regex;

import java.util.ArrayList;
import java.util.List;

public class ProgramFormController {

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableView<ProgramTm> tblProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    AcademicBO academicBO= (AcademicBO) BOFactory.getBO(BOFactory.BOType.ACADEMIC);

    public void initialize() {
        setCellValueFactory();
        loadAllPrograms();
        generateProgramId();
    }

    private void generateProgramId() {
        String programId =academicBO.getGeneratedProgramId(); // Call BO to get the generated ID
        txtId.setText(programId);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
    }

    private void loadAllPrograms() {
        List<ProgramsDTO> programs =academicBO.getAllProgram();
        tblProgram.getItems().clear();
        ObservableList<ProgramTm> programTms = tblProgram.getItems();
        for (ProgramsDTO program : programs) {
            String duration = convertDurationToString(program.getDuration());
            programTms.add(new ProgramTm(program.getProgramId(),program.getProgramName(),duration,program.getFee()));
        }
        tblProgram.setItems(programTms);
    }

    private String convertDurationToString(int duration) {
        String durationString = "";

        if(duration > 11){
            int years = duration / 12;
            int months = duration % 12;

            if (months == 0){
                durationString = years + " years";
            } else {
                durationString = years + " years " + months + " months";
            }
        } else {
            durationString = duration + " months";
        }
        return durationString;
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearData();
    }

    private void clearData() {
        txtDuration.clear();
        txtFee.clear();
        txtId.clear();
        txtName.clear();
    }

    private ProgramsDTO getObject(){
        return new ProgramsDTO(txtId.getText(),txtName.getText(),Integer.parseInt(txtDuration.getText()),Double.parseDouble(txtFee.getText()),new ArrayList<>());
    }


    private int convertDurationToInt(String duration){
        int years = 0;
        int months = 0;

        // Split the input string by spaces to identify the number and unit
        String[] parts = duration.split(" ");

        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equalsIgnoreCase("year") || parts[i].equalsIgnoreCase("years")) {
                years = Integer.parseInt(parts[i - 1]); // Get the value before "year" or "years"
            } else if (parts[i].equalsIgnoreCase("month") || parts[i].equalsIgnoreCase("months")) {
                months = Integer.parseInt(parts[i - 1]); // Get the value before "month" or "months"
            }
        }

        // Convert years to months and add to months
        return (years * 12) + months;
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) throws InUseException {
        if (!LoginFormController.userDTO.getRole().equals("Admissions Coordinator")){
            if (isValied() && !txtId.getText().isEmpty()){
                academicBO.deleteProgram(academicBO.getProgram(txtId.getText().trim()));
                clearData();
                loadAllPrograms();
                generateProgramId();

            } else {
                new Alert(Alert.AlertType.WARNING,"Please Enter All Fields !!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING,"You cannot access this !!").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws UserAlreadyExistsException {
        if (!LoginFormController.userDTO.getRole().equals("Admissions Coordinator")){
            if (isValied() && !txtId.getText().isEmpty()){
                ProgramsDTO programsDTO = new ProgramsDTO(txtId.getText(),txtName.getText(),Integer.parseInt(txtDuration.getText()),Double.parseDouble(txtFee.getText()),new ArrayList<>());
                academicBO.saveProgram(programsDTO);
                clearData();
                loadAllPrograms();
                generateProgramId();

            } else {
                new Alert(Alert.AlertType.WARNING,"Please Enter All Fields !!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING,"You cannot access this !!").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (!LoginFormController.userDTO.getRole().equals("Admissions Coordinator")){
            if (isValied() && !txtId.getText().isEmpty()){
                academicBO.updateProgram(academicBO.getProgram(txtId.getText().trim()));
                clearData();
                loadAllPrograms();
                generateProgramId();

            } else {
                new Alert(Alert.AlertType.WARNING,"Please Enter All Fields !!").show();
            }
        } else {
            new Alert(Alert.AlertType.WARNING,"You cannot access this !!").show();
        }
    }

    @FXML
    void tblProgramOnClickAction(MouseEvent event) {
        ProgramTm programTm = tblProgram.getSelectionModel().getSelectedItem();
        if (programTm != null) {
            txtId.setText(programTm.getId());
            txtName.setText(programTm.getProgramName());
            txtDuration.setText(String.valueOf(convertDurationToInt(programTm.getDuration().trim())));
            txtFee.setText(String.valueOf(programTm.getFee()));
        }
    }

    @FXML
    void txtDurationKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.MONTH, txtDuration);
    }

    @FXML
    void txtDurationOnAction(ActionEvent event) {
        txtFee.requestFocus();
    }

    @FXML
    void txtFeeKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PRICE, txtFee);
    }

    @FXML
    void txtIdKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PROGRAMID, txtId);
    }

    @FXML
    void txtIdOnAction(ActionEvent event) {
        txtName.requestFocus();
    }

    @FXML
    void txtNameKeyAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.NAME, txtName);
    }

    @FXML
    void txtNameOnAction(ActionEvent event) {
        txtDuration.requestFocus();
    }
    private boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PROGRAMID, txtId)) return false;
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.NAME, txtName)) return false;
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.MONTH, txtDuration)) return false;
        if (!Regex.setTextColor(lk.ijse.culinaryacademy.util.TextField.PRICE, txtFee)) return false;
        return true;
    }

}

