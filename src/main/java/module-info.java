module lk.ijse.culinaryacademy {
    requires javafx.controls;
    requires javafx.fxml;


    opens lk.ijse.culinaryacademy to javafx.fxml;
    exports lk.ijse.culinaryacademy;
}