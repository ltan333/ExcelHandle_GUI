package com.gui.minitask_gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class CreateMessBox {
    /**
     * Hiển thị popup
     * @param message Thông điệp.
     * @param popuptype Loại thông điệp: 1: Info, 2:Warning, 3:Err
     */
        public static void popupBoxMess(String message, int popuptype){
            Alert alert;
            if(popuptype==1){
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
            }
            else if(popuptype==2){
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
            }

            alert.setHeaderText(message);
            alert.setContentText(null);
            alert.showAndWait();
        }

        public static String popupBoxGetText(String title, String requestString) {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle(title);
            dialog.setHeaderText(null);
            dialog.setContentText(requestString+": ");
            Optional<String> result = dialog.showAndWait();
            return result.get();
        }

        public static boolean popupChoose(String title){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(title);

            Optional<ButtonType> result = alert.showAndWait();
            return result.get() == ButtonType.OK;
        }

    public static void popupBoxMessContent(String header, String content, int popuptype){
        Alert alert;
        if(popuptype==1){
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
        }
        else if(popuptype==2){
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
        }
        else{
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
        }

        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



    }


