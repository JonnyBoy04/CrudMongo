module com.pros.crudmongo {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;

    opens com.pros.crudmongo to javafx.fxml;
    exports com.pros.crudmongo;
}