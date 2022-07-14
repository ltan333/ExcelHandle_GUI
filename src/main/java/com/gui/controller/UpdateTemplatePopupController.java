package com.gui.controller;

import com.gui.minitask_gui.CreateMessBox;
import com.gui.minitask_gui.EmployeeSalaryManager;
import com.gui.minitask_gui.GlobalHandler;
import com.gui.minitask_gui.SalaryDetail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UpdateTemplatePopupController implements Initializable {
    @FXML
    private Button cancelBtn;

    @FXML
    private CheckBox checkBox1;

    @FXML
    private CheckBox checkBox2;

    @FXML
    private CheckBox checkBox3;

    @FXML
    private CheckBox checkBox4;

    @FXML
    private CheckBox checkBoxIsOpen;

    @FXML
    private ToggleGroup chooseDataGroup;

    @FXML
    private Button updateBtn;

    @FXML
    private RadioButton usingSalaryRadio;

    @FXML
    private RadioButton usingWeekRadio;

    @FXML
    private ImageView weekIcon1;

    @FXML
    private ImageView weekIcon2;

    @FXML
    private ImageView weekIcon3;

    @FXML
    private ImageView weekIcon4;

    ////VARIABLE//////VARIABLE//////////VARIABLE/////////VARIABLE/////////VARIABLE//////VARIABLE//////////VARIABLE/////////VARIABLE///////////
    ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    ArrayList<ImageView> icons = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initList();
        eWeekIconClicked();
        eCancelBtnClicked();
        eUpdateBtnClicked();
        if(GlobalHandler.chosenMonthTemplate.size()>1){
            checkBoxIsOpen.setSelected(false);
            checkBoxIsOpen.setDisable(true);
        }
    }

    public void eUpdateBtnClicked() {
        GlobalHandler.chosenWeekTemplate.clear();
        updateBtn.setOnAction(e -> {
            for (int i = 0; i < 4; i++) {
                if (checkBoxes.get(i).isSelected()) {
                    GlobalHandler.chosenWeekTemplate.add(i + 1);
                }
            }
            if (GlobalHandler.chosenWeekTemplate.isEmpty()) {
                CreateMessBox.popupBoxMess("Please choose a week to update!", 2);
            } else {
                if (checkBoxIsOpen.isSelected()) {
                    GlobalHandler.isOpenAfterUpdateTemplate = true;
                } else {
                    GlobalHandler.isOpenAfterUpdateTemplate = false;
                }
                for (Integer i : GlobalHandler.chosenMonthTemplate) {
                    String path = GlobalHandler.getRootDir() + GlobalHandler.yearToUpdateTemplate + "\\" + GlobalHandler.getMonthName(i) + "\\";
                    if(usingWeekRadio.isSelected()){
                        if(!GlobalHandler.checkMonthExistedCreate(GlobalHandler.yearToUpdateTemplate,i)){
                            CreateMessBox.popupBoxMessContent("Not Found Data Input!", "Please Create Data File In " + GlobalHandler.yearToUpdateTemplate + " - " + GlobalHandler.getMonthName(i) + " Folder\n" +
                                    "To Update.", 2);
                            return;
                        }
                    }else {
                        if(!GlobalHandler.checkMonthExistedCal(GlobalHandler.yearToUpdateTemplate,i)){
                            CreateMessBox.popupBoxMessContent("Not Found Data Input!", "Please Create Salary File In " + GlobalHandler.yearToUpdateTemplate + " - " + GlobalHandler.getMonthName(i) + " Folder\n" +
                                    "To Update.", 2);
                            return;
                        }
                    }
                    updateHandle(path,i);
                }
                Stage stage = (Stage) updateBtn.getScene().getWindow();
                stage.close();
            }
        });
    }

    private void updateHandle(String path, int month) {
        EmployeeSalaryManager e = new EmployeeSalaryManager();
        int year = GlobalHandler.yearToUpdateTemplate;
        String templatePath = GlobalHandler.srcTemplate;
        String yearFolder = GlobalHandler.getRootDir() + year + "\\";
        Path src = Paths.get(templatePath);
        Path des = Paths.get(path + "Salary_Using_Template.xlsx");
        //If exist
        if (GlobalHandler.checkFileExist(new File(path+"Salary_Using_Template.xlsx"))) {
            Path backup = Paths.get(path + "\\" + "Salary_Using_Template_Backup" + getNumOfBackupFile(path) + ".xlsx");
            //If using new template
            if(GlobalHandler.usingTemplateFrom ==1){
                try {
                    Files.move(des, backup);
                    Files.copy(src, des);
                } catch (IOException ex) {
                    CreateMessBox.popupBoxMess("Copy Backup File Fail!", 2);
                    return;
                }
            }else if(GlobalHandler.usingTemplateFrom ==2) {
                try {
                    Files.copy(des, backup);
                } catch (IOException ex) {
                    CreateMessBox.popupBoxMess("Copy Backup File Fail!", 2);
                    return;
                }
            }
        //If not exist
        } else {
            try {
                Files.copy(src, des);
            } catch (IOException ex) {
                CreateMessBox.popupBoxMess("Copy template file fail!", 2);
                return;
            }
        }
        // Create new thread to read and write faster.
        new Thread(()->{
            GlobalHandler.isSuccess = false;
            //Using data from ?
            if (usingSalaryRadio.isSelected()) {
                //Using salary file
                e.readDataFromSalaryFile(path);
            } else {
                //Using week file
                e.readDataFromWeekFile(path);
            }

            //handle

            ArrayList<Integer> chosenWeek = GlobalHandler.chosenWeekTemplate;
            Date date=null;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse("1/"+month+"/"+year);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            if (chosenWeek.contains(1)) {
                e.updateDataTemplate(path, 1, date);
            }
            if (chosenWeek.contains(2)) {
                e.updateDataTemplate(path, 2, date);
            }
            if (chosenWeek.contains(3)) {
                e.updateDataTemplate(path, 3, date);
            }
            if (chosenWeek.contains(4)) {
                e.updateDataTemplate(path, 4, date);
            }
            if(checkBoxIsOpen.isSelected()){
                new Thread(()->{
                    try {
                        String pathUpdateFile = path + "Salary_Using_Template.xlsx";
                        Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "start " + pathUpdateFile});
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        CreateMessBox.popupBoxMess("Open Salary_Using_Template.xlsx is fail!", 2);
                    }
                }).start();
            }
            GlobalHandler.isSuccess = true;
        }).start();

    }

    public void eCancelBtnClicked() {
        cancelBtn.setOnAction(e -> {
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        });
    }

    public void eWeekIconClicked() {
        weekIcon1.setOnMouseClicked(e -> {
            if (checkBox1.isSelected()) {
                checkBox1.setSelected(false);
            } else {
                checkBox1.setSelected(true);
            }
        });
        weekIcon2.setOnMouseClicked(e -> {
            if (checkBox2.isSelected()) {
                checkBox2.setSelected(false);
            } else {
                checkBox2.setSelected(true);
            }
        });
        weekIcon3.setOnMouseClicked(e -> {
            if (checkBox3.isSelected()) {
                checkBox3.setSelected(false);
            } else {
                checkBox3.setSelected(true);
            }
        });
        weekIcon4.setOnMouseClicked(e -> {
            if (checkBox4.isSelected()) {
                checkBox4.setSelected(false);
            } else {
                checkBox4.setSelected(true);
            }
        });

    }


    private int getNumOfBackupFile(String path) {
        File f = new File(path);
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

    public void writeFileSalaryDetail(String path, String fileName) {
        try {
            OutputStream outputStream = new FileOutputStream(path + fileName + ".xlsx");
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Salary_Detail");
            createValue(sheet, workbook);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createValue(Sheet sheet, Workbook workbook) {
        ArrayList<SalaryDetail> salaryDetails = GlobalHandler.salaryDetails;
        int cellIndex = 0;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setColor(IndexedColors.RED1.getIndex());
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);
        Row firstRow = sheet.createRow(0);
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(5, 3000);

//        Cell weekCell = firstRow.createCell(cellIndex);
//        weekCell.setCellValue("Week");
        Cell cashCell = firstRow.createCell(cellIndex + 1);
        cashCell.setCellValue("Cash");
        cashCell.setCellStyle(style);
        Cell cardCell = firstRow.createCell(cellIndex + 2);
        cardCell.setCellValue("Card");
        cardCell.setCellStyle(style);
        Cell venmoCell = firstRow.createCell(cellIndex + 3);
        venmoCell.setCellValue("Venmo");
        venmoCell.setCellStyle(style);
        Cell zelleCell = firstRow.createCell(cellIndex + 4);
        zelleCell.setCellValue("Zelle");
        zelleCell.setCellStyle(style);
        Cell giftTakeInCell = firstRow.createCell(cellIndex + 5);
        giftTakeInCell.setCellValue("Gift Take In");
        giftTakeInCell.setCellStyle(style);
        Cell giftSellCell = firstRow.createCell(cellIndex + 6);
        giftSellCell.setCellValue("Gift Sell");
        giftSellCell.setCellStyle(style);
        Cell totalCell = firstRow.createCell(cellIndex + 7);
        totalCell.setCellValue("Total");
        totalCell.setCellStyle(style);
        for (int rowIndex = 1; rowIndex < 8; rowIndex++) {
            Row row = sheet.createRow(rowIndex);
            font.setColor(IndexedColors.BLACK1.getIndex());
            if (rowIndex == 1) {
                Cell week = row.createCell(cellIndex);
                week.setCellValue("Week 1");
                week.setCellStyle(style);
            } else if (rowIndex == 2) {
                Cell week = row.createCell(cellIndex);
                week.setCellValue("Week 2");
                week.setCellStyle(style);
            } else if (rowIndex == 3) {
                Cell week = row.createCell(cellIndex);
                week.setCellValue("Week 3");
                week.setCellStyle(style);
            } else if (rowIndex == 4) {
                Cell week = row.createCell(cellIndex);
                week.setCellValue("Week 4");
                week.setCellStyle(style);
            } else if (rowIndex == 5) {
                Cell week = row.createCell(cellIndex);
                week.setCellValue("Week 1+2");
                week.setCellStyle(style);
            } else if (rowIndex == 6) {
                Cell week = row.createCell(cellIndex);
                week.setCellValue("Week 3+4");
                week.setCellStyle(style);
            } else if (rowIndex == 7) {
                Cell week = row.createCell(cellIndex);
                week.setCellValue("Week 1+2+3+4");
                week.setCellStyle(style);
            }
            if (rowIndex > 0 && rowIndex < 5) {
                Cell cashCell1 = row.createCell(cellIndex + 1);
                cashCell1.setCellValue(salaryDetails.get(rowIndex - 1).getCash());
                Cell cardCell1 = row.createCell(cellIndex + 2);
                cardCell1.setCellValue(salaryDetails.get(rowIndex - 1).getCre_debt());
                Cell venmoCell1 = row.createCell(cellIndex + 3);
                venmoCell1.setCellValue(salaryDetails.get(rowIndex - 1).getVenmo());
                Cell zelleCell1 = row.createCell(cellIndex + 4);
                zelleCell1.setCellValue(salaryDetails.get(rowIndex - 1).getZelle());
                Cell giftTakeInCell1 = row.createCell(cellIndex + 5);
                giftTakeInCell1.setCellValue(salaryDetails.get(rowIndex - 1).getGiftTakeIn());
                Cell giftSellCell1 = row.createCell(cellIndex + 6);
                giftSellCell1.setCellValue(salaryDetails.get(rowIndex - 1).getGiftSell());
                Cell totalCell1 = row.createCell(cellIndex + 7);
                totalCell1.setCellValue(salaryDetails.get(rowIndex - 1).getTotal());
            } else if (rowIndex == 5) {
                Cell cashCell1 = row.createCell(cellIndex + 1);
                cashCell1.setCellValue(salaryDetails.get(0).getCash() + salaryDetails.get(1).getCash());
                Cell cardCell1 = row.createCell(cellIndex + 2);
                cardCell1.setCellValue(salaryDetails.get(0).getCre_debt() + salaryDetails.get(1).getCre_debt());
                Cell venmoCell1 = row.createCell(cellIndex + 3);
                venmoCell1.setCellValue(salaryDetails.get(0).getVenmo() + salaryDetails.get(1).getVenmo());
                Cell zelleCell1 = row.createCell(cellIndex + 4);
                zelleCell1.setCellValue(salaryDetails.get(0).getZelle() + salaryDetails.get(1).getZelle());
                Cell giftTakeInCell1 = row.createCell(cellIndex + 5);
                giftTakeInCell1.setCellValue(salaryDetails.get(0).getGiftTakeIn() + salaryDetails.get(1).getGiftTakeIn());
                Cell giftSellCell1 = row.createCell(cellIndex + 6);
                giftSellCell1.setCellValue(salaryDetails.get(0).getGiftSell() + salaryDetails.get(1).getGiftSell());
                Cell totalCell1 = row.createCell(cellIndex + 7);
                totalCell1.setCellValue(salaryDetails.get(0).getTotal() + salaryDetails.get(1).getTotal());
            } else if (rowIndex == 6) {
                Cell cashCell1 = row.createCell(cellIndex + 1);
                cashCell1.setCellValue(salaryDetails.get(2).getCash() + salaryDetails.get(3).getCash());

                Cell cardCell1 = row.createCell(cellIndex + 2);
                cardCell1.setCellValue(salaryDetails.get(2).getCre_debt() + salaryDetails.get(3).getCre_debt());

                Cell venmoCell1 = row.createCell(cellIndex + 3);
                venmoCell1.setCellValue(salaryDetails.get(2).getVenmo() + salaryDetails.get(3).getVenmo());

                Cell zelleCell1 = row.createCell(cellIndex + 4);
                zelleCell1.setCellValue(salaryDetails.get(2).getZelle() + salaryDetails.get(3).getZelle());

                Cell giftTakeInCell1 = row.createCell(cellIndex + 5);
                giftTakeInCell1.setCellValue(salaryDetails.get(2).getGiftTakeIn() + salaryDetails.get(3).getGiftTakeIn());

                Cell giftSellCell1 = row.createCell(cellIndex + 6);
                giftSellCell1.setCellValue(salaryDetails.get(2).getGiftSell() + salaryDetails.get(3).getGiftSell());

                Cell totalCell1 = row.createCell(cellIndex + 7);
                totalCell1.setCellValue(salaryDetails.get(2).getTotal() + salaryDetails.get(3).getTotal());
            } else if (rowIndex == 7) {
                Cell cashCell1 = row.createCell(cellIndex + 1);
                cashCell1.setCellValue(salaryDetails.get(0).getCash() + salaryDetails.get(1).getCash() + salaryDetails.get(2).getCash() + salaryDetails.get(3).getCash());
                Cell cardCell1 = row.createCell(cellIndex + 2);
                cardCell1.setCellValue(salaryDetails.get(0).getCre_debt() + salaryDetails.get(1).getCre_debt() + salaryDetails.get(2).getCre_debt() + salaryDetails.get(3).getCre_debt());
                Cell venmoCell1 = row.createCell(cellIndex + 3);
                venmoCell1.setCellValue(salaryDetails.get(0).getVenmo() + salaryDetails.get(1).getVenmo() + salaryDetails.get(2).getVenmo() + salaryDetails.get(3).getVenmo());
                Cell zelleCell1 = row.createCell(cellIndex + 4);
                zelleCell1.setCellValue(salaryDetails.get(0).getZelle() + salaryDetails.get(1).getZelle() + salaryDetails.get(2).getZelle() + salaryDetails.get(3).getZelle());
                Cell giftTakeInCell1 = row.createCell(cellIndex + 5);
                giftTakeInCell1.setCellValue(salaryDetails.get(0).getGiftTakeIn() + salaryDetails.get(1).getGiftTakeIn() + salaryDetails.get(2).getGiftTakeIn() + salaryDetails.get(3).getGiftTakeIn());
                Cell giftSellCell1 = row.createCell(cellIndex + 6);
                giftSellCell1.setCellValue(salaryDetails.get(0).getGiftSell() + salaryDetails.get(1).getGiftSell() + salaryDetails.get(2).getGiftSell() + salaryDetails.get(3).getGiftSell());
                Cell totalCell1 = row.createCell(cellIndex + 7);
                totalCell1.setCellValue(salaryDetails.get(0).getTotal() + salaryDetails.get(1).getTotal() + salaryDetails.get(2).getTotal() + salaryDetails.get(3).getTotal());
            }


        }
    }

    private void initList() {
        icons.add(weekIcon1);
        icons.add(weekIcon2);
        icons.add(weekIcon3);
        icons.add(weekIcon4);

        checkBoxes.add(checkBox1);
        checkBoxes.add(checkBox2);
        checkBoxes.add(checkBox3);
        checkBoxes.add(checkBox4);
    }
}
