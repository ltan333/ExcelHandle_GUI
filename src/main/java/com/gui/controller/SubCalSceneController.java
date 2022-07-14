package com.gui.controller;

import com.gui.minitask_gui.CreateMessBox;
import com.gui.minitask_gui.EmployeeSalaryManager;
import com.gui.minitask_gui.GlobalHandler;
import com.gui.minitask_gui.SalaryDetail;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubCalSceneController implements Initializable {
    @FXML
    private ImageView btnBack;

    @FXML
    private Label cardField1;

    @FXML
    private Label cardField12;

    @FXML
    private Label cardField1234;

    @FXML
    private Label cardField2;

    @FXML
    private Label cardField3;

    @FXML
    private Label cardField34;

    @FXML
    private Label cardField4;

    @FXML
    private Label cashField1;

    @FXML
    private Label cashField12;

    @FXML
    private Label cashField1234;

    @FXML
    private Label cashField2;

    @FXML
    private Label cashField3;

    @FXML
    private Label cashField34;

    @FXML
    private Label cashField4;

    @FXML
    private Button createBtn;

    @FXML
    private Label giftSellField1;

    @FXML
    private Label giftSellField12;

    @FXML
    private Label giftSellField1234;

    @FXML
    private Label giftSellField2;

    @FXML
    private Label giftSellField3;

    @FXML
    private Label giftSellField34;

    @FXML
    private Label giftSellField4;

    @FXML
    private Label giftTakeField1;

    @FXML
    private Label giftTakeField12;

    @FXML
    private Label giftTakeField1234;

    @FXML
    private Label giftTakeField2;

    @FXML
    private Label giftTakeField3;

    @FXML
    private Label giftTakeField34;

    @FXML
    private Label giftTakeField4;

    @FXML
    private Label headerField;

    @FXML
    private CheckBox openCheckbox;

    @FXML
    private Label totalField1;

    @FXML
    private Label totalField12;

    @FXML
    private Label totalField1234;

    @FXML
    private Label totalField2;

    @FXML
    private Label totalField3;

    @FXML
    private Label totalField34;

    @FXML
    private Label totalField4;

    @FXML
    private Label venmoField1;

    @FXML
    private Label venmoField12;

    @FXML
    private Label venmoField1234;

    @FXML
    private Label venmoField2;

    @FXML
    private Label venmoField3;

    @FXML
    private Label venmoField34;

    @FXML
    private Label venmoField4;

    @FXML
    private Label zelleField1;

    @FXML
    private Label zelleField12;

    @FXML
    private Label zelleField1234;

    @FXML
    private Label zelleField2;

    @FXML
    private Label zelleField3;

    @FXML
    private Label zelleField34;

    @FXML
    private Label zelleField4;
    //========================================================================================//
    ArrayList<Label> w1 = new ArrayList<>();
    ArrayList<Label> w2 = new ArrayList<>();
    ArrayList<Label> w12 = new ArrayList<>();
    ArrayList<Label> w3 = new ArrayList<>();
    ArrayList<Label> w4 = new ArrayList<>();
    ArrayList<Label> w34 = new ArrayList<>();
    ArrayList<Label> w1234 = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GlobalHandler.mouseEnteredRotateEffect(btnBack, btnBack, 180);
        eBackBtnClicked();
        eCreateBtnClicked();
        initConfig();
    }

    public void eBackBtnClicked() {
        btnBack.setOnMouseClicked(mouseEvent -> {
            if (createBtn.isDisabled())
                MainSceneController.anchorPanesCal.put(4, new AnchorPane());
            else {
                MainSceneController.anchorPanesCal.put(3, new AnchorPane());

            }
        });
    }

    public void eCreateBtnClicked() {
        createBtn.setOnAction(e -> {
            createBtn.setDisable(true);
            String path = GlobalHandler.getRootDir() + GlobalHandler.day2.split("/")[1] + "\\" + GlobalHandler.getMonthName(Integer.parseInt(GlobalHandler.day2.split("/")[0])) + "\\";
            EmployeeSalaryManager employeeSalaryManager = new EmployeeSalaryManager();
            employeeSalaryManager.readDataFromWeekFile(path);
            employeeSalaryManager.readSalaryDetail(path);
            employeeSalaryManager.writeData(path);
            initConfig();
            if (openCheckbox.isSelected()) {
                String pathSalary = path + "Salary.xlsx";
                Runnable runnable = () -> {
                    try {
                        Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "start " + pathSalary});
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        CreateMessBox.popupBoxMess("Open Salary.xlsx is fail!", 2);
                    }
                };
                Thread t2 = new Thread(runnable);
                t2.start();
            }
            writeFileSalaryDetail(path, "Monthy_Earning_Details");
            CreateMessBox.popupBoxMess("Successful!", 1);
        });
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

    public static String formatNumber(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value).replace(",", ".");
    }

    private void initConfig() {
        initList();
        double cash12 = 0, card12 = 0, venmo12 = 0, zelle12 = 0, giftTakeIn12 = 0, giftSell12 = 0;
        double cash34 = 0, card34 = 0, venmo34 = 0, zelle34 = 0, giftTakeIn34 = 0, giftSell34 = 0;
        for (SalaryDetail s : GlobalHandler.salaryDetails) {
            if (s.getWeek() == 1) {
                cash12 += s.getCash();
                card12 += s.getCre_debt();
                venmo12 += s.getVenmo();
                zelle12 += s.getZelle();
                giftSell12 += s.getGiftSell();
                giftTakeIn12 += s.getGiftTakeIn();
                cashField1.setText(formatNumber(s.getCash()));
                cardField1.setText(formatNumber(s.getCre_debt()));
                venmoField1.setText(formatNumber(s.getVenmo()));
                zelleField1.setText(formatNumber(s.getZelle()));
                giftTakeField1.setText(formatNumber(s.getGiftTakeIn()));
                giftSellField1.setText(formatNumber(s.getGiftSell()));
                totalField1.setText(formatNumber(s.getTotal()));
            } else if (s.getWeek() == 2) {
                cash12 += s.getCash();
                card12 += s.getCre_debt();
                venmo12 += s.getVenmo();
                zelle12 += s.getZelle();
                giftSell12 += s.getGiftSell();
                giftTakeIn12 += s.getGiftTakeIn();
                cashField2.setText(formatNumber(s.getCash()));
                cardField2.setText(formatNumber(s.getCre_debt()));
                venmoField2.setText(formatNumber(s.getVenmo()));
                zelleField2.setText(formatNumber(s.getZelle()));
                giftTakeField2.setText(formatNumber(s.getGiftTakeIn()));
                giftSellField2.setText(formatNumber(s.getGiftSell()));
                totalField2.setText(formatNumber(s.getTotal()));
            } else if (s.getWeek() == 3) {
                cash34 += s.getCash();
                card34 += s.getCre_debt();
                venmo34 += s.getVenmo();
                zelle34 += s.getZelle();
                giftSell34 += s.getGiftSell();
                giftTakeIn34 += s.getGiftTakeIn();
                cashField3.setText(formatNumber(s.getCash()));
                cardField3.setText(formatNumber(s.getCre_debt()));
                venmoField3.setText(formatNumber(s.getVenmo()));
                zelleField3.setText(formatNumber(s.getZelle()));
                giftTakeField3.setText(formatNumber(s.getGiftTakeIn()));
                giftSellField3.setText(formatNumber(s.getGiftSell()));
                totalField3.setText(formatNumber(s.getTotal()));
            } else if (s.getWeek() == 4) {
                cash34 += s.getCash();
                card34 += s.getCre_debt();
                venmo34 += s.getVenmo();
                zelle34 += s.getZelle();
                giftSell34 += s.getGiftSell();
                giftTakeIn34 += s.getGiftTakeIn();
                cashField4.setText(formatNumber(s.getCash()));
                cardField4.setText(formatNumber(s.getCre_debt()));
                venmoField4.setText(formatNumber(s.getVenmo()));
                zelleField4.setText(formatNumber(s.getZelle()));
                giftTakeField4.setText(formatNumber(s.getGiftTakeIn()));
                giftSellField4.setText(formatNumber(s.getGiftSell()));
                totalField4.setText(formatNumber(s.getTotal()));
            }
        }
        cashField12.setText(formatNumber(cash12));
        cardField12.setText(formatNumber(card12));
        venmoField12.setText(formatNumber(venmo12));
        zelleField12.setText(formatNumber(zelle12));
        giftTakeField12.setText(formatNumber(giftTakeIn12));
        giftSellField12.setText(formatNumber(giftSell12));
        totalField12.setText(formatNumber(cash12 + card12 + venmo12 + zelle12 + giftTakeIn12 + giftSell12));

        cashField34.setText(formatNumber(cash34));
        cardField34.setText(formatNumber(card34));
        venmoField34.setText(formatNumber(venmo34));
        zelleField34.setText(formatNumber(zelle34));
        giftTakeField34.setText(formatNumber(giftTakeIn34));
        giftSellField34.setText(formatNumber(giftSell34));
        totalField34.setText(formatNumber(cash34 + card34 + venmo34 + zelle34 + giftTakeIn34 + giftSell34));

        cashField1234.setText(formatNumber(cash12 + cash34));
        cardField1234.setText(formatNumber(card12 + card34) + "");
        venmoField1234.setText(formatNumber(venmo12 + venmo34) + "");
        zelleField1234.setText(formatNumber(zelle12 + zelle34) + "");
        giftTakeField1234.setText(formatNumber(giftTakeIn12 + giftTakeIn34) + "");
        giftSellField1234.setText(formatNumber(giftSell12 + giftSell34) + "");
        totalField1234.setText(formatNumber(cash12 + card12 + venmo12 + zelle12 + giftTakeIn12 + giftSell12 + cash34 + card34 + venmo34 + zelle34 + giftTakeIn34 + giftSell34) + "");
    }

    private void initList() {
        w1.add(cashField1);
        w1.add(cardField1);
        w1.add(venmoField1);
        w1.add(zelleField1);
        w1.add(giftTakeField1);
        w1.add(giftSellField1);
        w1.add(totalField1);

        w2.add(cashField2);
        w2.add(cardField2);
        w2.add(venmoField2);
        w2.add(zelleField2);
        w2.add(giftTakeField2);
        w2.add(giftSellField2);
        w2.add(totalField2);

        w3.add(cashField3);
        w3.add(cardField3);
        w3.add(venmoField3);
        w3.add(zelleField3);
        w3.add(giftTakeField3);
        w3.add(giftSellField3);
        w3.add(totalField3);

        w4.add(cashField4);
        w4.add(cardField4);
        w4.add(venmoField4);
        w4.add(zelleField4);
        w4.add(giftTakeField4);
        w4.add(giftSellField4);
        w4.add(totalField4);

        w12.add(cashField12);
        w12.add(cardField12);
        w12.add(venmoField12);
        w12.add(zelleField12);
        w12.add(giftTakeField12);
        w12.add(giftSellField12);
        w12.add(totalField12);

        w34.add(cashField34);
        w34.add(cardField34);
        w34.add(venmoField34);
        w34.add(zelleField34);
        w34.add(giftTakeField34);
        w34.add(giftSellField34);
        w34.add(totalField34);

        w1234.add(cashField1234);
        w1234.add(cardField1234);
        w1234.add(venmoField1234);
        w1234.add(zelleField1234);
        w1234.add(giftTakeField1234);
        w1234.add(giftSellField1234);
        w1234.add(totalField1234);
    }
}
