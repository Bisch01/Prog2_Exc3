module at.ac.fhcampuswien.fhmdb {
    requires javafx.fxml;
    requires com.jfoenix;
    requires javafx.controls;
    requires okhttp3;
    requires com.google.gson;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;

    opens at.ac.fhcampuswien.fhmdb.models to javafx.fxml, com.google.gson;
    exports at.ac.fhcampuswien.fhmdb.models;

    opens at.ac.fhcampuswien.fhmdb.ui to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb.ui;
}
