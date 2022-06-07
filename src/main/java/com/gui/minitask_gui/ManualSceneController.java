package com.gui.minitask_gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class ManualSceneController implements Initializable {

    @FXML
    private Label headerField;

    @FXML
    private ScrollPane scrollpaneContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initConfig();
    }

    public void initConfig(){
        GridPane gridPane = new GridPane();
        scrollpaneContent.setContent(gridPane);
        gridPane.setVgap(10);
        scrollpaneContent.setPadding(new Insets(10,0,0,20));
        int row = 0;
        for(String content: TextFileHandler.getManualContent()){
            if(content.startsWith("<header>")){
                headerField.setText(content.split(">")[1].strip());
            }else if(content.startsWith("<title>")){
                Label titleField = new Label(content.split(">")[1].strip());
                titleField.setFont(Font.font("System", FontWeight.BOLD,15));
                titleField.setWrapText(true);
                titleField.setPrefWidth(650);
                gridPane.add(titleField,0,row++);
            }else if(content.startsWith("<content>")){
                Label contentField = new Label(content.split(">")[1].strip());
                contentField.setFont(Font.font("System", FontWeight.NORMAL,12));
                contentField.setPrefWidth(650);
                contentField.setTextAlignment(TextAlignment.JUSTIFY);
                contentField.setWrapText(true);
                gridPane.add(contentField,0,row++);
            }
        }
    }
}
