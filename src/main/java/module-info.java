module com.example.csit228capstone {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.csit228capstone to javafx.fxml;
    exports com.example.csit228capstone;
    exports com.example.csit228capstone.data;
    opens com.example.csit228capstone.data to javafx.fxml;
    exports com.example.csit228capstone.controllers.accounts;
    opens com.example.csit228capstone.controllers.accounts to javafx.fxml;
    exports com.example.csit228capstone.controllers.dashboard;
    opens com.example.csit228capstone.controllers.dashboard to javafx.fxml;
    exports com.example.csit228capstone.controllers.categories;
    opens com.example.csit228capstone.controllers.categories to javafx.fxml;
    exports com.example.csit228capstone.controllers.login;
    opens com.example.csit228capstone.controllers.login to javafx.fxml;
    exports com.example.csit228capstone.controllers.transactions;
    opens com.example.csit228capstone.controllers.transactions to javafx.fxml;
    exports com.example.csit228capstone.controllers.transfer;
    opens com.example.csit228capstone.controllers.transfer to javafx.fxml;
    exports com.example.csit228capstone.controllers.register;
    opens com.example.csit228capstone.controllers.register to javafx.fxml;
    exports com.example.csit228capstone.controllers.utils;
    opens com.example.csit228capstone.controllers.utils to javafx.fxml;
}