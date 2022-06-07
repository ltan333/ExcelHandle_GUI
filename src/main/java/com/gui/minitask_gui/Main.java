package com.gui.minitask_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if(!GlobalHandler.checkPaymentFileExistInConfig()){
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LocateFileScene.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Working Folder");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
        }else {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScene.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Salary Management");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
        }
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}