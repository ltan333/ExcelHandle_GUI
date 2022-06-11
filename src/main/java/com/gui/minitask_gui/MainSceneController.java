package com.gui.minitask_gui;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    @FXML
    private Label rootPathField;

    @FXML
    private Button changeBtn;

    @FXML
    private Button calculateBtn;



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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GlobalHandler.mouseEnteredRotateEffect(closeBtn, closeBtn, 180);
        GlobalHandler.mouseEnteredRotateEffect(minimizeBtn, minimizeBtn, 180);
        eCloseAndMinimizeClicked();
        eCreateBtnClicked();
        eCalculateBtnClicked();
        borderPane.setCenter(getManScene());
        eSubListener();
        rootPathField.setText(GlobalHandler.getRootDir());
        eChangeBtnClicked();
        eRootPathClicked();
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


    private void a() {
        createBtn.setOnMouseEntered(e -> {
        });
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
