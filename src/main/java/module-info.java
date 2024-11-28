module lk.ijse.culinaryacademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;
    requires com.jfoenix;
    requires jbcrypt;

    opens lk.ijse.culinaryacademy to javafx.fxml;
    exports lk.ijse.culinaryacademy;
}