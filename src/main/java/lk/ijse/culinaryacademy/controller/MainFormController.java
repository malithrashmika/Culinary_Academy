package lk.ijse.culinaryacademy.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnProgram;

    @FXML
    private JFXButton btnSetting;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private JFXButton btnView;

    @FXML
    private AnchorPane changeForm;

    @FXML
    private AnchorPane dashboardFrom;

    public void initialize() {
        try {
            changeForm.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        try {
            changeForm.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml")));
            defaultDesignButton();
            changeDesignButton(btnDashboard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeDesignButton(JFXButton button) throws IOException {
        button.setStyle("-fx-background-color:  #03001C; -fx-text-fill: #FFFFFF; -fx-border-color: #FFFFFF; -fx-border-width: 3; -fx-border-radius: 5; -fx-background-radius: 10;");
    }

    private void defaultDesignButton() {
        String style = "-fx-background-color: #cdc1ff; -fx-text-fill: #000000; -fx-border-color:  white; -fx-border-width: 3; -fx-border-radius: 5; -fx-background-radius: 10;";
        btnDashboard.setStyle(style);
        btnProgram.setStyle(style);
        btnSetting.setStyle(style);
        btnStudent.setStyle(style);
        btnView.setStyle(style);
    }

    @FXML
    void btnProgramOnAction(ActionEvent event) {
        try {
            changeForm.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/programForm.fxml")));
            defaultDesignButton();
            changeDesignButton(btnProgram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSettingOnAction(ActionEvent event) {
        try {
            changeForm.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/settingForm.fxml")));
            defaultDesignButton();
            changeDesignButton(btnSetting);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) {
        try {
            changeForm.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/studentForm.fxml")));
            defaultDesignButton();
            changeDesignButton(btnStudent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnViewOnAction(ActionEvent event) {
        try {
            changeForm.getChildren().setAll((Node) FXMLLoader.load(this.getClass().getResource("/view/viewAllForm.fxml")));
            defaultDesignButton();
            changeDesignButton(btnView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void logOutAction(MouseEvent event) {
        try {
            Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/loginForm.fxml")));
            Stage stage = (Stage) dashboardFrom.getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

