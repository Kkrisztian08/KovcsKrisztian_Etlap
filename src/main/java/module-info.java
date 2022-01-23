module com.example.kovcskrisztian_etlap {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.kovcskrisztian_etlap to javafx.fxml;
    exports com.example.kovcskrisztian_etlap;
    exports com.example.kovcskrisztian_etlap.controlls;
    opens com.example.kovcskrisztian_etlap.controlls to javafx.fxml;
}