module com.gui.minitask_gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.apache.poi.ooxml;
    requires org.apache.commons.io;

    opens com.gui.minitask_gui to javafx.fxml;
    exports com.gui.minitask_gui;
    exports com.gui.controller;
    opens com.gui.controller to javafx.fxml;
}