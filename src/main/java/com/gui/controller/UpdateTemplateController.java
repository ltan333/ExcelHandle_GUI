package com.gui.controller;

import com.gui.minitask_gui.CreateMessBox;
import com.gui.minitask_gui.GlobalHandler;
import com.gui.minitask_gui.InputValidation;
import com.gui.minitask_gui.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UpdateTemplateController implements Initializable {
    @FXML
    private AnchorPane anchorpaneRoot;

    @FXML
    private Button browseBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private CheckBox checkbox1;

    @FXML
    private CheckBox checkbox10;

    @FXML
    private CheckBox checkbox11;

    @FXML
    private CheckBox checkbox12;

    @FXML
    private CheckBox checkbox2;

    @FXML
    private CheckBox checkbox3;

    @FXML
    private CheckBox checkbox4;

    @FXML
    private CheckBox checkbox5;

    @FXML
    private CheckBox checkbox6;

    @FXML
    private CheckBox checkbox7;

    @FXML
    private CheckBox checkbox8;

    @FXML
    private CheckBox checkbox9;
    @FXML
    private CheckBox checkBoxUsingExistTemplate;
    @FXML
    private ImageView createIcon1;

    @FXML
    private ImageView createIcon10;

    @FXML
    private ImageView createIcon11;

    @FXML
    private ImageView createIcon12;

    @FXML
    private ImageView createIcon2;

    @FXML
    private ImageView createIcon3;

    @FXML
    private ImageView createIcon4;

    @FXML
    private ImageView createIcon5;

    @FXML
    private ImageView createIcon6;

    @FXML
    private ImageView createIcon7;

    @FXML
    private ImageView createIcon8;

    @FXML
    private ImageView createIcon9;

    @FXML
    private ImageView monthIcon1;

    @FXML
    private ImageView monthIcon10;

    @FXML
    private ImageView monthIcon11;

    @FXML
    private ImageView monthIcon12;

    @FXML
    private ImageView monthIcon2;

    @FXML
    private ImageView monthIcon3;

    @FXML
    private ImageView monthIcon4;

    @FXML
    private ImageView monthIcon5;

    @FXML
    private ImageView monthIcon6;

    @FXML
    private ImageView monthIcon7;

    @FXML
    private ImageView monthIcon8;

    @FXML
    private ImageView monthIcon9;

    @FXML
    private Button openFolderBtn;

    @FXML
    private TextField pathTemplateTextfield;

    @FXML
    private Button refeshBtn;

    @FXML
    private ImageView reloadIcon;

    @FXML
    private ImageView updateIcon1;

    @FXML
    private ImageView updateIcon10;

    @FXML
    private ImageView updateIcon11;

    @FXML
    private ImageView updateIcon12;

    @FXML
    private ImageView updateIcon2;

    @FXML
    private ImageView updateIcon3;

    @FXML
    private ImageView updateIcon4;

    @FXML
    private ImageView updateIcon5;

    @FXML
    private ImageView updateIcon6;

    @FXML
    private ImageView updateIcon7;

    @FXML
    private ImageView updateIcon8;

    @FXML
    private ImageView updateIcon9;

    @FXML
    private TextField yearField;
    ////VARIABLE//////VARIABLE//////////VARIABLE/////////VARIABLE/////////VARIABLE//////VARIABLE//////////VARIABLE/////////VARIABLE///////////
    ArrayList<ImageView> icons = new ArrayList<>();
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<ImageView> updateIcons= new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createInitList();
        GlobalHandler.mouseEnteredRotateEffect(refeshBtn, reloadIcon, 180);
        yearField.setText(Calendar.getInstance().get(Calendar.YEAR) + "");
        showCreatedIcon();
        eRefeshBtnClicked();
        eOpenFolderBtnClicked();
        eMonthIconBtn();
        eBrowseBtnClicked();
        eUpdateBtnClicked();
        eUsingExistTemplateCheckboxSelected();
    }

    public void eUpdateBtnClicked(){
        updateBtn.setOnAction(e->{
            String yearFolderPath = GlobalHandler.getRootDir()+yearField.getText()+"\\";
            String[] templatePath = pathTemplateTextfield.getText().strip().toLowerCase().split("\\.");
            int count=0;
            LinkedList<Integer> selectedMonth = new LinkedList<>() ;
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    count++;
                    selectedMonth.add(i+1);
                }
            }
            if (count == 0) {
                CreateMessBox.popupBoxMess("Please Check 1 Month To Create!", 2);
                return;
            }

            if(!checkBoxUsingExistTemplate.isSelected()){
                if(templatePath.length<=1){
                    CreateMessBox.popupBoxMess("Invalid Template File!",2);
                    return;
                }
                if(!templatePath[(templatePath.length-1)].equalsIgnoreCase("xlsx")){
                    CreateMessBox.popupBoxMess("Invalid Template File!",2);
                    return;
                }
                if(!GlobalHandler.checkFileExist(new File(pathTemplateTextfield.getText()))){
                    CreateMessBox.popupBoxMess("Invalid Template File!",2);
                    return;
                }
                //Which data to use
                GlobalHandler.usingTemplateFrom =1;
            }else {
                for(Integer c: selectedMonth){
                    if(!GlobalHandler.checkFileExist(new File(yearFolderPath+GlobalHandler.getMonthName(c)+"\\"+"Salary_Using_Template.xlsx"))){
                        CreateMessBox.popupBoxMess("Template File Not Found In "+ yearField.getText() + " - " + GlobalHandler.getMonthName(c) + " Folder\n",2);
                        return;
                    }
                }
                //Which data to use
                GlobalHandler.usingTemplateFrom =2;
            }
            GlobalHandler.chosenMonthTemplate.clear();
            GlobalHandler.chosenMonthTemplate = selectedMonth;
            GlobalHandler.yearToUpdateTemplate = Integer.parseInt(yearField.getText());
            GlobalHandler.srcTemplate = pathTemplateTextfield.getText();
            showUpdatePopup();
            //Refesh icon created file
            showCreatedIcon();
        });

    }

    private void showUpdatePopup(){
        Stage popupStage = new Stage();
        popupStage.initOwner(updateBtn.getScene().getWindow());
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.setResizable(false);
        popupStage.setTitle("Update");
        popupStage.setScene(new Scene(getUpdatePopupScene()));
        popupStage.showAndWait();
    }

    private AnchorPane getUpdatePopupScene(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("UpdateTemplatePopupScene.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = new AnchorPane((AnchorPane) fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load Sub Calculate scene fail!");
            e.printStackTrace();
        }
        return anchorPane;
    }

    public void eUsingExistTemplateCheckboxSelected(){
        checkBoxUsingExistTemplate.setOnMouseClicked(e ->{
            if(checkBoxUsingExistTemplate.isSelected()){
                pathTemplateTextfield.setDisable(true);
                browseBtn.setDisable(true);
            }else {
                pathTemplateTextfield.setDisable(false);
                browseBtn.setDisable(false);
            }
        });
    }

    public void eOpenFolderBtnClicked(){
        openFolderBtn.setOnAction(e->{
            if(InputValidation.isEmptyString(yearField.getText())){
                CreateMessBox.popupBoxMess("Year must be not empty!",2);
                return;
            }
            if(!InputValidation.isNumber(yearField.getText())){
                CreateMessBox.popupBoxMess("Year must be a integer number!",2);
                return;
            }
            try {
                if(Integer.parseInt(yearField.getText()) < 1990){
                    CreateMessBox.popupBoxMess("Year must be greater than 1990!",2);
                    return;
                }
            }catch (NumberFormatException ex){
                CreateMessBox.popupBoxMess("Year must be a integer number!",2);
                return;
            }
            if(Integer.parseInt(yearField.getText()) > Calendar.getInstance().get(Calendar.YEAR)+100){
                CreateMessBox.popupBoxMess("Year must be greater than "+(Calendar.getInstance().get(Calendar.YEAR)+100)+"!",2);
                return;
            }
            File f = new File(GlobalHandler.getRootDir()+yearField.getText());
            showCreatedIcon();
            if(f.exists()){
                try {
                    Runtime.getRuntime().exec("explorer " + f.getPath());
                } catch (IOException ex) {
                    CreateMessBox.popupBoxMess("Can't open folder!",2);
                }
            }else {
                f.mkdirs();
                try {
                    Runtime.getRuntime().exec("explorer " + f.getPath());
                } catch (IOException ex) {
                    CreateMessBox.popupBoxMess("Can't open folder!",2);
                }
            }
        });
    }

    public void eRefeshBtnClicked(){
        refeshBtn.setOnAction(actionEvent -> {
            if(InputValidation.isEmptyString(yearField.getText())){
                CreateMessBox.popupBoxMess("Year must be not empty!",2);
                return;
            }
            if(!InputValidation.isNumber(yearField.getText())){
                CreateMessBox.popupBoxMess("Year must be a integer number!",2);
                return;
            }
            try {
                if(Integer.parseInt(yearField.getText()) < 1990){
                    CreateMessBox.popupBoxMess("Year must be greater than 1990!",2);
                    return;
                }
            }catch (NumberFormatException e){
                CreateMessBox.popupBoxMess("Year must be a integer number!",2);
                return;
            }
            if(Integer.parseInt(yearField.getText()) > Calendar.getInstance().get(Calendar.YEAR)+100){
                CreateMessBox.popupBoxMess("Year must be greater than "+(Calendar.getInstance().get(Calendar.YEAR)+100)+"!",2);
                return;
            }
            showCreatedIcon();
        });
    }

    private int getNumOfBackupFile(String pathFolder) {
        File f = new File(pathFolder);
        File[] files = f.listFiles();
        ArrayList<Integer> nums = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.getName().toLowerCase().contains("template_backup")) {
                String[] a = file.getName().split("\\.*[a-zA-Z]+");
                for(String c:a){
                    try {
                        int i = Integer.parseInt(c);
                        nums.add(i);
                    }catch (NumberFormatException ignored){
                    }
                }
            }
        }

        try {
            return Collections.max(nums) + 1;
        } catch (NoSuchElementException e) {
            return 1;
        }
    }

    private void showCreatedIcon(){
        for (ImageView img:icons){
            img.setVisible(false);
        }
        for(int i =0; i<12; i++){
            if(GlobalHandler.checkMonthExistedUpdateTemplate(Integer.parseInt(yearField.getText()),i+1)){
                icons.get(i).setVisible(true);
            }
        }
    }

    public void eBrowseBtnClicked(){
        browseBtn.setOnMouseClicked(e->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("EXCEL files (*.xlsx)", "*.xlsx"));
            Stage chooserStage = new Stage();
            chooserStage.setScene(new Scene(new VBox(), 1, 1));
            chooserStage.initOwner(browseBtn.getScene().getWindow());
            chooserStage.show();

            try{
                File f = fileChooser.showOpenDialog(chooserStage);
                pathTemplateTextfield.setText(f.getPath());
            }catch (NullPointerException ex){
                chooserStage.close();
            }finally {
                chooserStage.close();
            }
        });
    }

    public void eMonthIconBtn(){
        monthIcon1.setOnMouseClicked(e->{
            if(checkbox1.isSelected()){
                checkbox1.setSelected(false);
            }else {
                checkbox1.setSelected(true);
            }
        });
        monthIcon2.setOnMouseClicked(e->{
            if(checkbox2.isSelected()){
                checkbox2.setSelected(false);
            }else {
                checkbox2.setSelected(true);
            }
        });
        monthIcon3.setOnMouseClicked(e->{
            if(checkbox3.isSelected()){
                checkbox3.setSelected(false);
            }else {
                checkbox3.setSelected(true);
            }
        });
        monthIcon4.setOnMouseClicked(e->{
            if(checkbox4.isSelected()){
                checkbox4.setSelected(false);
            }else {
                checkbox4.setSelected(true);
            }
        });
        monthIcon5.setOnMouseClicked(e->{
            if(checkbox5.isSelected()){
                checkbox5.setSelected(false);
            }else {
                checkbox5.setSelected(true);
            }
        });
        monthIcon6.setOnMouseClicked(e->{
            if(checkbox6.isSelected()){
                checkbox6.setSelected(false);
            }else {
                checkbox6.setSelected(true);
            }
        });
        monthIcon7.setOnMouseClicked(e->{
            if(checkbox7.isSelected()){
                checkbox7.setSelected(false);
            }else {
                checkbox7.setSelected(true);
            }
        });
        monthIcon8.setOnMouseClicked(e->{
            if(checkbox8.isSelected()){
                checkbox8.setSelected(false);
            }else {
                checkbox8.setSelected(true);
            }
        });
        monthIcon9.setOnMouseClicked(e->{
            if(checkbox9.isSelected()){
                checkbox9.setSelected(false);
            }else {
                checkbox9.setSelected(true);
            }
        });
        monthIcon10.setOnMouseClicked(e->{
            if(checkbox10.isSelected()){
                checkbox10.setSelected(false);
            }else {
                checkbox10.setSelected(true);
            }
        });
        monthIcon11.setOnMouseClicked(e->{
            if(checkbox11.isSelected()){
                checkbox11.setSelected(false);
            }else {
                checkbox11.setSelected(true);
            }
        });
        monthIcon12.setOnMouseClicked(e->{
            if(checkbox12.isSelected()){
                checkbox12.setSelected(false);
            }else {
                checkbox12.setSelected(true);
            }
        });

    }

    private void createInitList() {
        icons.add(createIcon1);
        icons.add(createIcon2);
        icons.add(createIcon3);
        icons.add(createIcon4);
        icons.add(createIcon5);
        icons.add(createIcon6);
        icons.add(createIcon7);
        icons.add(createIcon8);
        icons.add(createIcon9);
        icons.add(createIcon10);
        icons.add(createIcon11);
        icons.add(createIcon12);

        checkBoxes.add(checkbox1);
        checkBoxes.add(checkbox2);
        checkBoxes.add(checkbox3);
        checkBoxes.add(checkbox4);
        checkBoxes.add(checkbox5);
        checkBoxes.add(checkbox6);
        checkBoxes.add(checkbox7);
        checkBoxes.add(checkbox8);
        checkBoxes.add(checkbox9);
        checkBoxes.add(checkbox10);
        checkBoxes.add(checkbox11);
        checkBoxes.add(checkbox12);

        updateIcons.add(updateIcon1);
        updateIcons.add(updateIcon2);
        updateIcons.add(updateIcon3);
        updateIcons.add(updateIcon4);
        updateIcons.add(updateIcon5);
        updateIcons.add(updateIcon6);
        updateIcons.add(updateIcon7);
        updateIcons.add(updateIcon8);
        updateIcons.add(updateIcon9);
        updateIcons.add(updateIcon10);
        updateIcons.add(updateIcon11);
        updateIcons.add(updateIcon12);

    }
}
