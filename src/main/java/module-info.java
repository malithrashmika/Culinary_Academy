module lk.ijse.culinaryacademy {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires static lombok;
    requires jakarta.persistence;
    requires com.jfoenix;
    requires jbcrypt;
    requires java.sql;
    requires javafx.graphics;

    opens lk.ijse.culinaryacademy.controller to javafx.fxml; // Alternatively, you can open the package if needed
    opens lk.ijse.culinaryacademy.entity to org.hibernate.orm.core;
    opens lk.ijse.culinaryacademy.tdm to javafx.base;

    exports lk.ijse.culinaryacademy;
    exports lk.ijse.culinaryacademy.controller to javafx.fxml;  // Add this line


}