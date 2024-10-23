module com.example.laboration3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.laboration3 to javafx.fxml;
    exports com.example.laboration3;
    exports com.example.laboration3.controller;
    opens com.example.laboration3.controller to javafx.fxml;
}
