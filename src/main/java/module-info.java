module fr.perrot54u.restaurationdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens fr.perrot54u.restaurationdb to javafx.fxml;
    exports fr.perrot54u.restaurationdb;

    opens fr.perrot54u.restaurationdb.controllers to javafx.fxml;
    exports fr.perrot54u.restaurationdb.controllers;

    exports fr.perrot54u.restaurationdb.utils;
    opens fr.perrot54u.restaurationdb.utils to javafx.fxml;
}