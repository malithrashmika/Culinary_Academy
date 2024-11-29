package lk.ijse.culinaryacademy.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.culinaryacademy.bo.BOFactory;
import lk.ijse.culinaryacademy.bo.custom.AcademicBO;
import lk.ijse.culinaryacademy.bo.custom.RegistrationBO;
import lk.ijse.culinaryacademy.bo.custom.StudentBO;
import lk.ijse.culinaryacademy.dto.ProgramsDTO;
import lk.ijse.culinaryacademy.entity.Enrollment;
import lk.ijse.culinaryacademy.entity.Student;
import lk.ijse.culinaryacademy.tdm.ViewAllTm;

import java.io.IOException;
import java.util.List;

public class ViewAllFormController {

    @FXML
    private TableColumn<?, ?> colBalance;

    @FXML
    private TableColumn<?, ?> colInstallment;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private TableColumn<?, ?> colRegisterDate;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colStudentName;

    @FXML
    private Label lblStudentCount;

    @FXML
    private ChoiceBox<String> selectPrgramChoiceBox;

    @FXML
    private ImageView viewAll;

    @FXML
    private TableView<ViewAllTm> viewTbl;

    AcademicBO academicBO= (AcademicBO) BOFactory.getBO(BOFactory.BOType.ACADEMIC);
    StudentBO studentBO = (StudentBO) BOFactory.getBO(BOFactory.BOType.STUDENT);
    RegistrationBO registrationBO= (RegistrationBO) BOFactory.getBO(BOFactory.BOType.REGISTRATION);
    public void initialize() {
        setChoiceBoxData();
        setChoiceBoxAction();
        setCellValueFactory();
    }

    private void setChoiceBoxAction() {
        selectPrgramChoiceBox.setOnAction(event -> {
            // Get the selected item
            String programName = selectPrgramChoiceBox.getValue();

            loadTableData(programName.trim());
        });
    }

    private void setCellValueFactory() {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colRegisterDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));
        colInstallment.setCellValueFactory(new PropertyValueFactory<>("installment"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
    }

    private void loadTableData(String programName) {
        viewTbl.getItems().clear();

        List<Object[]> allEqualByProgramName = registrationBO.getAllEqualByProgramName(programName);
        ObservableList<ViewAllTm> viewAllTms = viewTbl.getItems();

        for (Object[] objects : allEqualByProgramName) {
            Student student = (Student) objects[0];
            Enrollment enrollment = (Enrollment) objects[1];
            viewAllTms.add(new ViewAllTm(student.getStudentId(),student.getName(),student.getRegistrationDate(),enrollment.getFirstInstallment(),enrollment.getBalance(),createButton()));
        }
        viewTbl.setItems(viewAllTms);
        lblStudentCount.setText(String.valueOf(allEqualByProgramName.size()));
    }

    private Button createButton(){
        Button button = new Button("Pay");
        button.setStyle("-fx-background-color: blue;-fx-text-fill: white;");

        button.setOnAction((e) -> {
            ViewAllTm selectedItem = viewTbl.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                loadAddPaymentForm(selectedItem,selectPrgramChoiceBox.getValue().trim());
                viewTbl.getSelectionModel().clearSelection();
            } else {
                // Show an alert if no item is selected
                new Alert(Alert.AlertType.INFORMATION, "Select a row before clicking the button!").show();
            }
        });

        return button;
    }

    private void loadAddPaymentForm(ViewAllTm selectedItem, String programName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addPaymentForm.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the controller for addProgramForm
            AddPaymentFormController controller = loader.getController();

            // Pass the selected student to the new form
            controller.initialize(selectedItem,programName);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setChoiceBoxData(){
        List<ProgramsDTO> program = academicBO.getAllProgram();
        ObservableList<String> programNames = FXCollections.observableArrayList();

        for (ProgramsDTO programDTO : program){
            programNames.add(programDTO.getProgramName());
        }
        selectPrgramChoiceBox.setItems(programNames);
    }
    @FXML
    void refreshTblOnClickAction(MouseEvent event) {
        if (!selectPrgramChoiceBox.getValue().isEmpty() || selectPrgramChoiceBox.getValue() != null){
            loadTableData(selectPrgramChoiceBox.getValue().trim());
        }
    }

}

