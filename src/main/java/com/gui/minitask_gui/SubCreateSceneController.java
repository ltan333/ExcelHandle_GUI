package com.gui.minitask_gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SubCreateSceneController implements Initializable {
    @FXML
    private Button createBtn;

    @FXML
    private ImageView btnBack;

    @FXML
    private ScrollPane scrollpaneEmployee;

    @FXML
    private CheckBox checkBoxOpen;

    @FXML
    private Label dayField11;

    @FXML
    private Label dayField12;

    @FXML
    private Label dayField13;

    @FXML
    private Label dayField14;

    @FXML
    private Label dayField15;

    @FXML
    private Label dayField16;

    @FXML
    private Label dayField17;

    @FXML
    private Label dayField21;

    @FXML
    private Label dayField22;

    @FXML
    private Label dayField23;

    @FXML
    private Label dayField24;

    @FXML
    private Label dayField25;

    @FXML
    private Label dayField26;

    @FXML
    private Label dayField27;

    @FXML
    private Label dayField28;

    @FXML
    private Label dayField31;

    @FXML
    private Label dayField32;

    @FXML
    private Label dayField33;

    @FXML
    private Label dayField34;

    @FXML
    private Label dayField35;

    @FXML
    private Label dayField36;

    @FXML
    private Label dayField37;

    @FXML
    private Label dayField41;

    @FXML
    private Label dayField42;

    @FXML
    private Label dayField43;

    @FXML
    private Label dayField44;

    @FXML
    private Label dayField45;

    @FXML
    private Label dayField46;

    @FXML
    private Label dayField47;

    @FXML
    private Label dayField48;

    @FXML
    private Label dayField49;

    @FXML
    private ToggleGroup fillstyle1;

    @FXML
    private ToggleGroup fillstyle2;

    @FXML
    private ToggleGroup fillstyle3;

    @FXML
    private ToggleGroup fillstyle4;

    @FXML
    private Label headerField;

    @FXML
    private Label labelW4;
    //============================================================================//
    ArrayList<Label> days = new ArrayList<>();
    ArrayList<CheckBox> employeeCheckboxs = new ArrayList<>();
    Set<CheckBox> tempName = new HashSet<CheckBox>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initConfig();
        GlobalHandler.mouseEnteredRotateEffect(btnBack, btnBack, 180);
        eBackBtnClicked();
        eCreateBtnClicked();
    }

    public void eBackBtnClicked() {
        btnBack.setOnMouseClicked(mouseEvent -> {
            MainSceneController.anchorPanesCreate.put(3, new AnchorPane());
        });
    }

    public void eCreateBtnClicked() {
        createBtn.setOnAction(e -> {
            String path = GlobalHandler.getRootDir() + GlobalHandler.day1.split("/")[1] + "\\" + GlobalHandler.getMonthName(Integer.parseInt(GlobalHandler.day1.split("/")[0])) + "\\";
            File file = new File(path);
            //Create folder
            if (!file.exists()) {
                file.mkdirs();
            }
            // Begin create excel file
            GlobalHandler.writeFileWeekExcel(path, "Week 1", getSelectedEmployees(), getRadioSelected()[0], 1, 7);
            GlobalHandler.writeFileWeekExcel(path, "Week 2", getSelectedEmployees(), getRadioSelected()[1], 2, 8);
            GlobalHandler.writeFileWeekExcel(path, "Week 3", getSelectedEmployees(), getRadioSelected()[2], 3, 7);

            int numOfMonth = GlobalHandler.numOfDay1-14-8;
            GlobalHandler.writeFileWeekExcel(path, "Week 4", getSelectedEmployees(), getRadioSelected()[3], 4, numOfMonth);


            //Open folder if selected open checkbox.
            if (checkBoxOpen.isSelected()) {
                try {
                    Runtime.getRuntime().exec("explorer " + path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            //Save new employee
            saveEmployee();

            //Close detail window -> show previous window
            MainSceneController.anchorPanesCreate.put(4, new AnchorPane());
        });
    }

    private void saveEmployee() {
        try {
            FileWriter fw = new FileWriter(GlobalHandler.configFile);
            fw.write(GlobalHandler.getRootDir() + "Payment_Calculation.txt");
            fw.write("\n");
            for (CheckBox checkBox : tempName) {
                fw.write(checkBox.getText() + ",");
            }
            fw.flush();
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEmployeeFormConfig() {
        try {
            Scanner scan = new Scanner(GlobalHandler.configFile);
            scan.nextLine();
            while (scan.hasNextLine()) {
                String nameLine = scan.nextLine();
                String[] names = nameLine.split(",");
                for (String name : names) {
                    if (!InputValidation.isEmptyString(name)) {
                        tempName.add(new CheckBox(name));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int[] getRadioSelected() {
        int[] l = {0, 0, 0, 0};
        RadioButton r1 = (RadioButton) fillstyle1.getSelectedToggle();
        if (r1.getText().equalsIgnoreCase("Blank Values")) {
            l[0] = 0;
        } else if (r1.getText().equalsIgnoreCase("Zero")) {
            l[0] = 1;
        } else if (r1.getText().equalsIgnoreCase("Random")) {
            l[0] = 2;
        }

        RadioButton r2 = (RadioButton) fillstyle2.getSelectedToggle();
        if (r2.getText().equalsIgnoreCase("Blank Values")) {
            l[1] = 0;
        } else if (r2.getText().equalsIgnoreCase("Zero")) {
            l[1] = 1;
        } else if (r2.getText().equalsIgnoreCase("Random")) {
            l[1] = 2;
        }

        RadioButton r3 = (RadioButton) fillstyle3.getSelectedToggle();
        if (r3.getText().equalsIgnoreCase("Blank Values")) {
            l[2] = 0;
        } else if (r3.getText().equalsIgnoreCase("Zero")) {
            l[2] = 1;
        } else if (r3.getText().equalsIgnoreCase("Random")) {
            l[2] = 2;
        }

        RadioButton r4 = (RadioButton) fillstyle4.getSelectedToggle();
        if (r4.getText().equalsIgnoreCase("Blank Values")) {
            l[3] = 0;
        } else if (r4.getText().equalsIgnoreCase("Zero")) {
            l[3] = 1;
        } else if (r4.getText().equalsIgnoreCase("Random")) {
            l[3] = 2;
        }
        return l;
    }

    private ArrayList<String> getSelectedEmployees() {
        ArrayList<String> es = new ArrayList<>();
        for (CheckBox c : employeeCheckboxs) {
            if (c.isSelected()) {
                es.add(c.getText());
            }
        }

        for (CheckBox c : tempName) {
            if (c.isSelected()) {
                es.add(c.getText());
            }
        }
        return es;
    }

    public void initConfig() {
        initList();
        //Set Header
        headerField.setText("Creating daily earning for " + GlobalHandler.getMonthName(Integer.parseInt(GlobalHandler.day1.split("/")[0])) + " " + GlobalHandler.day1.split("/")[1]);


        //Set dd-MM-EEE
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("1/" + GlobalHandler.day1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < GlobalHandler.numOfDay1; i++) {
            days.get(i).setVisible(true);
            days.get(i).setText(GlobalHandler.get_dd_MM_EE(calendar.getTime()));
            if (i != GlobalHandler.numOfDay1 - 1)
                calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        //set Text Week 4
        labelW4.setText("Week 4 (" + (GlobalHandler.getNumberOfDayInMonth(calendar.getTime()) - 14 - 8) + " days):");

        //Load employee from file config.txt
        loadEmployeeFormConfig();

        //Set employee list
        addCheckboxList();
        createCheckboxEmployeeArea();
    }

    private void createCheckboxEmployeeArea() {
        GridPane gridPane = new GridPane();
        scrollpaneEmployee.setContent(gridPane);
        gridPane.setHgap(20);
        gridPane.setVgap(15);
        gridPane.setPadding(new Insets(10, 0, 0, 10));
        int x = 0, y = 0;
        for (CheckBox c : employeeCheckboxs) {
            gridPane.add(c, x++, y);
            if (x == 5) {
                x = 0;
                y++;
            }
        }
        for (CheckBox checkBox : tempName) {
            gridPane.add(checkBox, x++, y);
            if (x == 5) {
                x = 0;
                y++;
            }
        }
        ImageView iw = createAddNameIcon();
        gridPane.add(iw, x, y);
    }

    private void addCheckboxList() {
        for (String name : TextFileHandler.getEmployeeName(GlobalHandler.getRootDir() + "Payment_Calculation.txt")) {
            CheckBox c = new CheckBox(name);
            c.setSelected(true);
            employeeCheckboxs.add(c);
        }
    }

    private ImageView createAddNameIcon() {
        File f = new File(".\\src\\main\\resources\\com\\gui\\icon\\add.png");
        Image img = new Image(f.toURI().toString());
        ImageView addIcon = new ImageView(img);
        addIcon.setStyle("-fx-cursor: hand");
        addIcon.setFitWidth(30);
        addIcon.setFitHeight(30);
        addIcon.setOnMouseClicked(e -> {
            String names = "";
            try {
                names = CreateMessBox.popupBoxGetText("Add Name", "Please enter name\n" +
                        "(You can enter multiple\nnames separated by commas)");
                names = names.strip().replace("\s", "");
                String[] nameList = names.split(",");
                for (String name : nameList) {
                    if (!InputValidation.isEmptyString(name))
                        tempName.add(new CheckBox(name.toUpperCase()));
                }
                createCheckboxEmployeeArea();
            } catch (NoSuchElementException ex) {
                names = "";
            }
        });
        return addIcon;
    }

    private void initList() {
        days.add(dayField11);
        days.add(dayField12);
        days.add(dayField13);
        days.add(dayField14);
        days.add(dayField15);
        days.add(dayField16);
        days.add(dayField17);

        days.add(dayField21);
        days.add(dayField22);
        days.add(dayField23);
        days.add(dayField24);
        days.add(dayField25);
        days.add(dayField26);
        days.add(dayField27);
        days.add(dayField28);

        days.add(dayField31);
        days.add(dayField32);
        days.add(dayField33);
        days.add(dayField34);
        days.add(dayField35);
        days.add(dayField36);
        days.add(dayField37);

        days.add(dayField41);
        days.add(dayField42);
        days.add(dayField43);
        days.add(dayField44);
        days.add(dayField45);
        days.add(dayField46);
        days.add(dayField47);
        days.add(dayField48);
        days.add(dayField49);
        for (Label l : days) {
            l.setVisible(false);
        }
    }
}
