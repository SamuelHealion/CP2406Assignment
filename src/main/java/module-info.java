module rainfallanalyser.cp2406assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens rainfallanalyser.cp2406assignment to javafx.fxml;
    exports rainfallanalyser.cp2406assignment;
}