package lk.ijse.culinaryacademy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.AcademicBO;
import lk.ijse.culinaryacademy.bo.custom.RegistrationBO;
import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dto.StudentDTO;
import lk.ijse.culinaryacademy.tdm.StudyAllStudentTm;

import java.util.List;

public class DashboardController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane dashboardForm;

    @FXML
    private Label lblStudentCount;

    @FXML
    private Label lblTotalPrograms;

    @FXML
    private Label lblTotalStudent;

    @FXML
    private TableView<StudyAllStudentTm> tblStudyAll;

    RegistrationBO registrationBO= (RegistrationBO) BOFactory.getBO(BOFactory.BOType.REGISTRATION);
    AcademicBO academicBO= (AcademicBO) BOFactory.getBO(BOFactory.BOType.ACADEMIC);
    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    public void initialize() {
        setCellValueFactory();
        setTotals();
        loadTableData();
    }

    private void loadTableData() {
        // Clear any existing data in the table
        tblStudyAll.getItems();

        // Fetch student data from the BO layer
        List<StudentDTO> allProgramStudents = studentBO.getAllStudent();

        // Create an ObservableList to hold the table items
        ObservableList<StudyAllStudentTm> studentTms = FXCollections.observableArrayList();

        // Map StudentDTO objects to StudyAllStudentTm objects and add them to the ObservableList
        for (StudentDTO studentDTO : allProgramStudents) {
            // Log each student for debugging
            System.out.println("Loaded Student: " + studentDTO.getName() + ", ID: " + studentDTO.getStudentId() + ", Reg Date: " + studentDTO.getRegistrationDate());

            // Add the mapped object to the ObservableList
            studentTms.add(new StudyAllStudentTm(
                    studentDTO.getStudentId(),
                    studentDTO.getName(),
                    studentDTO.getRegistrationDate()
            ));
        }

        // Bind the ObservableList to the TableView
        tblStudyAll.setItems(studentTms);

        // Update the student count label
        lblStudentCount.setText(String.valueOf(studentTms.size()));
    }



    private void setTotals() {
        lblTotalPrograms.setText(String.valueOf(academicBO.getProgramCount()));
        lblTotalStudent.setText(String.valueOf(studentBO.getStudentCount()));
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
    }

}
