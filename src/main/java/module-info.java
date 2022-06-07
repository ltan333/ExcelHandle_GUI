module com.gui.minitask_gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.apache.poi.ooxml;

    opens com.gui.minitask_gui to javafx.fxml;
    exports com.gui.minitask_gui;
}