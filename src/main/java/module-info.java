module fr.perrot54u.restaurationdb {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.perrot54u.restaurationdb to javafx.fxml;
    exports fr.perrot54u.restaurationdb;
}