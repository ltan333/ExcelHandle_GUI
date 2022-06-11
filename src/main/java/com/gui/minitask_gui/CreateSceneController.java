package com.gui.minitask_gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CreateSceneController implements Initializable {
    @FXML
    private AnchorPane anchorpaneRoot;

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
    private Button createBtn;

    @FXML
    private Button refeshBtn;

    @FXML
    private Button openFolderBtn;

    @FXML
    private ImageView reloadIcon;

    @FXML
    private ImageView createIcon1;

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
    private ImageView createIcon10;
    @FXML
    private ImageView createIcon11;
    @FXML
    private ImageView createIcon12;

    @FXML
    private ImageView monthIcon1;
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
    private ImageView monthIcon10;
    @FXML
    private ImageView monthIcon11;
    @FXML
    private ImageView monthIcon12;
    @FXML
    private TextField yearField;
//==================================================================================================//
    ArrayList<ImageView> icons = new ArrayList<>();
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createInitList();
        yearField.setText(Calendar.getInstance().get(Calendar.YEAR)+"");
        GlobalHandler.mouseEnteredRotateEffect(refeshBtn,reloadIcon,180);
        showCreatedIcon();
        eCreateBtnClicked();
        eRefeshBtnClicked();
        eOpenFolderBtnClicked();
        eMonthIconBtn();
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

    public void eCreateBtnClicked(){
        createBtn.setOnMouseClicked(e->{
            int count=0;
            for (CheckBox checkBox:checkBoxes ) {
                if(checkBox.isSelected()){
                    count++;
                }
            }
            if(count == 0){
                CreateMessBox.popupBoxMess("Please Check 1 Month To Create!",2);
                return;
            }
            if(count >1){
                CreateMessBox.popupBoxMess("Please Only Check 1 Month!",2);
                return;
            }

            for (int i = 0; i < checkBoxes.size(); i++) {
                if(checkBoxes.get(i).isSelected()){
                    if(GlobalHandler.checkMonthExistedCreate(Integer.parseInt(yearField.getText()),i+1)){
                        CreateMessBox.popupBoxMess("The daily earning for this month is already created.\n" +
                                "Please either delete the "+yearField.getText()+" - "+GlobalHandler.getMonthName(i+1)+" folder or\n" +
                                "rename to create new daily earnings.",2);
                        return;
                    }else {
                        Calendar calendar = Calendar.getInstance();
                        try {
                            calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("1/"+(i+1)+"/"+yearField.getText()));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        GlobalHandler.day1 = (i+1)+"/"+yearField.getText();
                        GlobalHandler.numOfDay1 = GlobalHandler.getNumberOfDayInMonth(calendar.getTime());
                    }
                }
            }
            MainSceneController.anchorPanesCreate.clear();
            MainSceneController.anchorPanesCreate.put(1,anchorpaneRoot);
            MainSceneController.anchorPanesCreate.put(2,getCalScene());
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


    private void showCreatedIcon(){
        for (ImageView img:icons){
            img.setVisible(false);
        }
        for(int i =0; i<12; i++){
            if(GlobalHandler.checkMonthExistedCreate(Integer.parseInt(yearField.getText()),i+1)){
                icons.get(i).setVisible(true);
            }
        }
    }

    private AnchorPane getCalScene(){
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SubCreateScene.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = new AnchorPane((AnchorPane)fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Load Sub CreateScene scene fail!");
            e.printStackTrace();
        }
        return anchorPane;
    }

    private void createInitList(){
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

    }
}
