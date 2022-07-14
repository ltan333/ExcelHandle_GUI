package com.gui.controller;

import com.gui.minitask_gui.GlobalHandler;
import com.gui.minitask_gui.Main;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private Label rootPathField;

    @FXML
    private Button changeBtn;

    @FXML
    private Button calculateBtn;

    @FXML
    private Button updateTemplateBtn;

    @FXML
    private ImageView closeBtn;

    @FXML
    private Button createBtn;

    @FXML
    private ImageView minimizeBtn;

    @FXML
    private BorderPane borderPane;
    //============================================================================//
    public static ObservableMap<Integer, AnchorPane> anchorPanesCreate = FXCollections.observableHashMap();
    public static ObservableMap<Integer, AnchorPane> anchorPanesCal = FXCollections.observableHashMap();
    private final ArrayList<Button> buttons = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GlobalHandler.mouseEnteredRotateEffect(closeBtn, closeBtn, 180);
        GlobalHandler.mouseEnteredRotateEffect(minimizeBtn, minimizeBtn, 180);
        eCloseAndMinimizeClicked();
        eCreateBtnClicked();
        eCalculateBtnClicked();
        eUpdateTemplateBtnClicked();
        borderPane.setCenter(getManScene());
        eSubListener();
        rootPathField.setText(GlobalHandler.getRootDir());
        eChangeBtnClicked();
        eRootPathClicked();
        buttons.add(calculateBtn);
        buttons.add(updateTemplateBtn);
        buttons.add(createBtn);
        focusBtnEffect(buttons);
    }

    public void eChangeBtnClicked() {
        changeBtn.setOnAction(e -> {
            Stage stage1 = (Stage) createBtn.getScene().getWindow();
            stage1.close();
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LocateFileScene.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException ex) {
                System.out.println("Load locate scene fail!");
                ex.printStackTrace();
            }
            stage.setTitle("Working Folder");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.show();
        });
    }


    public void eRootPathClicked() {
        rootPathField.setOnMouseClicked(e -> {
            try {
                Runtime.getRuntime().exec("explorer " + GlobalHandler.getRootDir());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void eSubListener() {
        anchorPanesCreate.addListener((MapChangeListener<Integer, AnchorPane>) change -> {
            if (!anchorPanesCreate.isEmpty()) {
                if (anchorPanesCreate.size() == 3) {
                    if (anchorPanesCreate.containsKey(3))
                        borderPane.setCenter(anchorPanesCreate.get(1));
                    else if(anchorPanesCreate.containsKey(4)){
                        borderPane.setCenter(getCreateScene());
                    }
                } else {
                    borderPane.setCenter(anchorPanesCreate.get(2));

                }
            }
        });
        anchorPanesCal.addListener((MapChangeListener<Integer, AnchorPane>) change -> {
            if (!anchorPanesCal.isEmpty()) {
                if (anchorPanesCal.size() == 3) {
                    if(anchorPanesCal.containsKey(3))
                        borderPane.setCenter(anchorPanesCal.get(1));
                    else if(anchorPanesCal.containsKey(4)){
                        borderPane.setCenter(getCalScene());
                    }
                } else {
                    borderPane.setCenter(anchorPanesCal.get(2));
                }
            }
        });
    }

    public void eCreateBtnClicked() {
        createBtn.setOnMouseClicked(e -> borderPane.setCenter(getCreateScene()));
    }

    public void eCalculateBtnClicked() {
        calculateBtn.setOnMouseClicked(e -> borderPane.setCenter(getCalScene()));
    }

    public void eUpdateTemplateBtnClicked() {
        updateTemplateBtn.setOnMouseClicked(e -> borderPane.setCenter(getUpdateTemplateScene()));
    }

    public void eCloseAndMinimizeClicked() {
        closeBtn.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) createBtn.getScene().getWindow();
            stage.close();
        });
        minimizeBtn.setOnMouseClicked(mouseEvent -> {
            Stage stage = (Stage) createBtn.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    private AnchorPane getCreateScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CreateScene.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = new AnchorPane((AnchorPane) fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load create scene fail!");
        }
        return anchorPane;
    }

    private AnchorPane getCalScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("CalculateScene.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = new AnchorPane((AnchorPane) fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load calculate scene fail!");
            e.printStackTrace();
        }
        return anchorPane;
    }

    private AnchorPane getManScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ManualScene.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = new AnchorPane((AnchorPane) fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load manual scene fail!");
        }
        return anchorPane;
    }

    private AnchorPane getUpdateTemplateScene(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UpdateTemplateScene.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = new AnchorPane((AnchorPane) fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load manual scene fail!");
        }
        return anchorPane;

    }

    public void focusBtnEffect(ArrayList<Button> btns){
        Runnable runnable = () -> {
            HashMap<Button,Boolean> isFocusButtons = new HashMap<>();
            for(Button b : btns){
                isFocusButtons.put(b,false);
            }
            ///FOCUS/////FOCUS//////FOCUS//////FOCUS//////////FOCUS///////////FOCUS///
            for(Button btn : btns){
                btn.setOnAction(e ->{
                    isFocusButtons.put(btn,true);
                    btn.setStyle(" -fx-background-color:  #8e9bdc;\n" +
                            "    -fx-cursor: default;\n" +
                            "    -fx-font-size: 14px;");
                    for(Button btn2 : btns){
                        if(btn2!=btn) {
                            isFocusButtons.put(btn2,false);
                            btn2.setStyle("    -fx-cursor: hand;\n" +
                                    "-fx-background-color: #6b739e;\n" +
                                    "    -fx-font-size: 13px;\n" +
                                    "    -fx-text-fill: white;\n" +
                                    "    -fx-focus-color:  transparent;" +
                                    "");
                        }

                    }


                });
            }
            ///HOVER////////HOVER////////HOVER////////HOVER/////////HOVER//////HOVER///////////
            for(Button b: btns){
                b.setOnMouseEntered(e ->{
                    if(!isFocusButtons.get(b)){
                        b.setStyle("-fx-background-color:  #8e9bdc;\n" +
                                "    -fx-cursor: hand;\n" +
                                "    -fx-font-size: 14px;");
                    }

                });
                b.setOnMouseExited(e->{
                    if(!isFocusButtons.get(b)){
                        b.setStyle("    -fx-cursor: default;\n" +
                                "-fx-background-color: #6b739e;\n" +
                                "    -fx-font-size: 13px;\n" +
                                "    -fx-text-fill: white;\n" +
                                "    -fx-focus-color:  transparent;" +
                                "");
                    }
                });
            }

        };
        Thread t = new Thread(runnable);
        t.start();
    }


    private Scene getLocateScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LocateScene.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load locate scene fail!");
        }
        return scene;
    }
}
