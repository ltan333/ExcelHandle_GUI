package com.gui.controller;

import com.gui.minitask_gui.CreateMessBox;
import com.gui.minitask_gui.GlobalHandler;
import com.gui.minitask_gui.Main;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LocateFileSceneController implements Initializable {
    @FXML
    private Button browserBtn;

    @FXML
    private Label messageField;

    @FXML
    private Button nextBtn;

    @FXML
    private TextField pathField;

    @FXML
    private ImageView statusIconV;
    @FXML
    private ImageView statusIconX;

    @FXML
    private ImageView closeBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eNextBtnClicked();
        eBrowserBtnClicked();
        eCloseBtnClicked();
        ePathFieldKeyClicked();
        GlobalHandler.mouseEnteredRotateEffect(closeBtn, closeBtn,180);
    }

    public void eNextBtnClicked(){
        nextBtn.setOnAction(actionEvent -> {
            File f = new File(pathField.getText());
            if(checkPaymentFileValid(f) && GlobalHandler.checkFileExist(f)){
                GlobalHandler.writeConfigFile(pathField.getText());
                GlobalHandler.setRootDir(pathField.getText());
                Stage stage = (Stage) nextBtn.getScene().getWindow();
                stage.close();
                creatMainStage();
            }else{
                CreateMessBox.popupBoxMess("Please locate Payment_Calculation file!",3);
            }
        });
    }

    public void eBrowserBtnClicked(){
        browserBtn.setOnMouseClicked(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt"));
            Stage chooserStage = new Stage();
            chooserStage.setScene(new Scene(new VBox(), 1, 1));
            chooserStage.initOwner(browserBtn.getScene().getWindow());
            chooserStage.show();

            File f = fileChooser.showOpenDialog(chooserStage);
            if(checkPaymentFileValid(f)){
                pathField.setText(f.getPath());
                updateStatusField(0);
                chooserStage.close();
            }else {
                if(f != null)
                    pathField.setText(f.getPath());
                updateStatusField(1);
                chooserStage.close();
            }
        });
    }

    private boolean checkPaymentFileValid(File f){
        if(f == null){
            return false;
        }else return f.getPath().toLowerCase().split("\\\\")[f.getPath().split("\\\\").length - 1].equalsIgnoreCase("Payment_Calculation.txt");
    }

    private void updateStatusField(int status){
        if(status == 0){
            messageField.setStyle("-fx-text-fill: Green");
            messageField.setText("Click next to continue");
            statusIconV.setVisible(true);
            statusIconX.setVisible(false);
        }else {
            messageField.setStyle("-fx-text-fill: RED");
            messageField.setText("File not found!");
            statusIconV.setVisible(false);
            statusIconX.setVisible(true);
        }
    }

    private void eCloseBtnClicked(){
        closeBtn.setOnMouseClicked(e -> {
            Stage stage = (Stage) browserBtn.getScene().getWindow();
            stage.close();
        });
    }

    public void ePathFieldKeyClicked(){
        pathField.setOnKeyReleased(e -> {
            File f = new File(pathField.getText());
            if(GlobalHandler.checkFileExist(f)){
                updateStatusField(0);
            }else {
                updateStatusField(1);
            }
        });
    }

    private void creatMainStage(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainScene.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load main scene fail!");
        }
        Stage stage = new Stage();
        stage.setTitle("Salary Management");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();
    }


}
