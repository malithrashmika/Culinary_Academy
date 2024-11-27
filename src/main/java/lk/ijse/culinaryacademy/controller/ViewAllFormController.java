package lk.ijse.culinaryacademy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    private ChoiceBox<?> selectPrgramChoiceBox;

    @FXML
    private ImageView viewAll;

    @FXML
    private TableView<?> viewTbl;

    @FXML
    void refreshTblOnClickAction(MouseEvent event) {

    }

}

