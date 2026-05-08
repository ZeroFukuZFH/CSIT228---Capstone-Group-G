module com.example.csit228capstone {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.csit228capstone to javafx.fxml;
    exports com.example.csit228capstone;
    exports com.example.csit228capstone.controllers;
    opens com.example.csit228capstone.controllers to javafx.fxml;
}