package com.gui.minitask_gui;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class test extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root,400,400);

            Button bSecondStage = new Button("Show second Stage");
            bSecondStage.setOnAction(e -> {
                WindowController wc = new WindowController();
                wc.showStage();
                System.out.println(wc.getData());
            });

            root.setCenter(bSecondStage);


            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    class WindowController {
        private String data;

        void showStage() {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            VBox root = new VBox();
            Scene scene = new Scene(root);
            TextField tf = new TextField();
            Button submit = new Button("Submit");

            submit.setOnAction(e -> {
                data = tf.getText();
                stage.close();
            });

            root.getChildren().addAll(tf, submit);
            stage.setScene(scene);
            stage.showAndWait();
        }

        String getData() {
            return data;
        }
    }
}