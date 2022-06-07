package com.gui.minitask_gui;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;

public class GlobalHandler {
    public static final File configFile = new File("config.txt");
    private static String rootDir = "";
    public static String day1 = "";
    public static int numOfDay1 = 0;
    public static String day2 = "";
    public static int numOfDay2 = 0;
    public static ArrayList<SalaryDetail> salaryDetails = new ArrayList<>();

    public static void setRootDir(String fullPath) {
        String[] splitList = fullPath.split("\\\\");
        String dir = "";
        for (int i = 0; i < splitList.length - 1; i++) {
            dir += splitList[i];
            dir += "\\";
        }
        rootDir = dir;
    }

    public static String getRootDir() {
        return rootDir;
    }

    public static boolean checkPaymentFileExistInConfig() {
        try {
            Scanner scanner = new Scanner(configFile);
            if (scanner.hasNextLine()) {
                String path = scanner.nextLine();
                try {
                    scanner = new Scanner(new File(path));
                    setRootDir(path);
                    return true;
                } catch (FileNotFoundException e) {
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            try {
                configFile.createNewFile();
            } catch (IOException ex) {
                System.out.println("Create config file fail!");
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static boolean writeConfigFile(String path) {
        try {
            FileWriter fw = new FileWriter(configFile);
            fw.write(path);
            fw.flush();
            fw.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean checkPaymentFileExist(File file) {
        if(file.exists()){
            return true;
        }
        return false;
    }

    public static void mouseEnteredRotateEffect(Node nodeHover, Node nodeRotate, int rotateAngle) {
        nodeHover.setOnMouseEntered(mouseEvent -> {
            RotateTransition rt = new RotateTransition();
            rt.setNode(nodeRotate);
            rt.setByAngle(rotateAngle);
            rt.setDuration(Duration.millis(300));
            rt.setToAngle(180);
            rt.play();
        });

        nodeHover.setOnMouseExited(mouseEvent -> {
            RotateTransition rt = new RotateTransition();
            rt.setNode(nodeRotate);
            rt.setByAngle(-rotateAngle);
            rt.setDuration(Duration.millis(200));
            rt.setToAngle(0);
            rt.play();
        });
    }

    public static String getMonthName(int month) {
        switch (month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "Unknow";
        }

    }

    public static boolean checkMonthExistedCreate(int year, int month) {
        String path = getRootDir() + year + "\\" + getMonthName(month) + "\\";
        File file = new File(path);
        if (!file.exists())
            return false;
        for (int i = 0; i < 4; i++) {
            File f = new File(path + "Week " + (i + 1) + ".xlsx");
            if (!f.exists()) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkMonthExistedCal(int year, int month) {
        String path = getRootDir() + year + "\\" + getMonthName(month) + "\\";
        File file = new File(path);
        if (!file.exists())
            return false;

        File file1 = new File(path+"Salary.xlsx");
        if(!file1.exists())
            return false;
        return true;
    }


    public static int getNumberOfDayInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        YearMonth yearMonthObject = YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return daysInMonth;
    }

    public static String get_dd_MM_EE(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-EEE");
        return simpleDateFormat.format(date);
    }



    public static void writeFileWeekExcel(String path, String fileName, ArrayList<String> names, int valueDefault, int whichWeek, int numOfSheet) {
        try {
            Calendar calendar = Calendar.getInstance();
            try {
                if (whichWeek == 1) {
                    calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("1/" + Integer.parseInt(day1.split("/")[0]) + "/" + day1.split("/")[1]));
                } else if (whichWeek == 2) {
                    calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("8/" + Integer.parseInt(day1.split("/")[0]) + "/" + day1.split("/")[1]));
                } else if (whichWeek == 3) {
                    calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("16/" + Integer.parseInt(day1.split("/")[0]) + "/" + day1.split("/")[1]));
                } else if (whichWeek == 4) {
                    calendar.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("23/" + Integer.parseInt(day1.split("/")[0]) + "/" + day1.split("/")[1]));
                }
            } catch (ParseException e) {

            }

            OutputStream outputStream;
            outputStream = new FileOutputStream(path + fileName + ".xlsx");
            Workbook workbook = new XSSFWorkbook();
            for (int i = 0; i < numOfSheet; i++) {
                Sheet sheet = workbook.createSheet(get_dd_MM_EE(calendar.getTime()));
                createValueWeek(sheet, workbook, names, calendar.getTime(), valueDefault);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createValueWeek(Sheet sheet, Workbook workbook, ArrayList<String> names, Date date, int valueDefault) {
        int[] randomList = {10,15,20,25,30,35,40,45,50,55,60};
        CellStyle style = workbook.createCellStyle();
        sheet.setColumnWidth(0, 3300);
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setColor(IndexedColors.RED1.getIndex());
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        style.setFont(font);
        CreationHelper createHelper = workbook.getCreationHelper();
        style.setDataFormat(createHelper.createDataFormat().getFormat("ddd MM/dd"));
        Row firstRow = sheet.createRow(0);
        Cell dateCell = firstRow.createCell(0);
        dateCell.setCellValue(date);
        dateCell.setCellStyle(style);
        int rowIndex = 1, cellIndex = 1;
        Random rd = new Random();

        while (true) {
            Row row = sheet.createRow(rowIndex);
            if (rowIndex == 1) {
                style = workbook.createCellStyle();
                font = (XSSFFont) workbook.createFont();
                font.setFontHeightInPoints((short) 11);
                font.setColor(IndexedColors.BLACK.getIndex());
                font.setBold(true);
                style.setFont(font);
                for (String name : names) {
                    Cell nameCell = row.createCell(cellIndex);
                    nameCell.setCellValue(name.toUpperCase());
                    nameCell.setCellStyle(style);
                    Cell tipCell = row.createCell(cellIndex + 1);
                    tipCell.setCellValue("Tip");
                    Cell cashCell = row.createCell(cellIndex + 2);
                    cashCell.setCellValue("Cash");
                    cellIndex += 3;
                }
                Cell giftSellCell = row.createCell(cellIndex);
                font.setColor(IndexedColors.RED1.getIndex());
                style.setFont(font);
                giftSellCell.setCellValue("GIFT SELL");
                giftSellCell.setCellStyle(style);
                cellIndex = 1;
            } else if (rowIndex >= 2 && rowIndex <= 14) {
                for (String name : names) {
                    if (valueDefault == 0) {
                        break;
                    } else if (valueDefault == 1) {
                        Cell cell = row.createCell(cellIndex);
                        cell.setCellValue("0");
                        Cell cell1 = row.createCell(cellIndex + 1);
                        cell1.setCellValue("0");
                        Cell cell2 = row.createCell(cellIndex + 2);
                        cell2.setCellValue("0");
                        cellIndex += 3;
                    } else if (valueDefault == 2) {
                        Cell cell = row.createCell(cellIndex);
                        cell.setCellValue(randomList[rd.nextInt(11)]);
                        Cell cell1 = row.createCell(cellIndex + 1);
                        cell1.setCellValue(rd.nextInt(10) + 1);
                        Cell cell2 = row.createCell(cellIndex + 2);
                        cell2.setCellValue(rd.nextInt(10) + 1);
                        cellIndex += 3;
                    }
                }
                Cell giftSellCell = row.createCell(cellIndex);
                if (valueDefault == 1)
                    giftSellCell.setCellValue("0");
                else if (valueDefault == 2)
                    giftSellCell.setCellValue(rd.nextInt(100) + 1);

                cellIndex = 1;
            } else if (rowIndex == 19) {
                Cell cellTitle = row.createCell(0);
                cellTitle.setCellValue("TOTAL");
                for (String name : names) {
                    style = workbook.createCellStyle();
                    style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    Cell cell = row.createCell(cellIndex, CellType.FORMULA);
                    String colName = CellReference.convertNumToColString(cell.getColumnIndex());
                    cell.setCellFormula("SUM(" + colName + "3:" + colName + "19)");
                    cell.setCellStyle(style);
                    Cell cell1 = row.createCell(cellIndex + 1, CellType.FORMULA);
                    String colName1 = CellReference.convertNumToColString(cell1.getColumnIndex());
                    cell1.setCellFormula("SUM(" + colName1 + "3:" + colName1 + "19)");
                    Cell cell2 = row.createCell(cellIndex + 2, CellType.FORMULA);
                    String colName2 = CellReference.convertNumToColString(cell2.getColumnIndex());
                    cell2.setCellFormula("SUM(" + colName2 + "3:" + colName2 + "19)");
                    cellIndex += 3;
                }
                Cell c = row.createCell(cellIndex, CellType.FORMULA);
                String colName = CellReference.convertNumToColString(c.getColumnIndex());
                c.setCellFormula("SUM(" + colName + "3:" + colName + "19)");
                c.setCellStyle(style);
                cellIndex = 1;
            } else if (rowIndex == 20) {
                style = workbook.createCellStyle();
                font = (XSSFFont) workbook.createFont();
                font.setFontHeightInPoints((short) 11);
                font.setColor(IndexedColors.GREEN.getIndex());
                font.setBold(true);
                style.setFont(font);
                Cell cell = row.createCell(0);
                cell.setCellValue("TOTAL ALL");
                cell.setCellStyle(style);
                //Cell value
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                Cell cell1 = row.createCell(22,CellType.FORMULA);
                cell1.setCellStyle(style);
                String colName = CellReference.convertNumToColString(names.size()*3+1);
                cell1.setCellFormula("SUM(B20:"+colName+"20)");

            } else if (rowIndex == 21) {
                style = workbook.createCellStyle();
                font = (XSSFFont) workbook.createFont();
                font.setFontName("Calibri");
                font.setFontHeightInPoints((short) 11);
                font.setColor(IndexedColors.VIOLET.getIndex());
                font.setBold(true);
                style.setFont(font);
                Cell cell = row.createCell(0);
                cell.setCellValue("CASH");
                cell.setCellStyle(style);

                //Total Cash cell value
                Cell totalCash = row.createCell(19,CellType.FORMULA);
                totalCash.setCellFormula("SUM(B22:S22)");

                //Match cell
                CellStyle styleMatch = workbook.createCellStyle();
                styleMatch.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                styleMatch.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                Cell cellMatch = row.createCell(21);
                cellMatch.setCellValue("MATH");
                cellMatch.setCellStyle(style);

                //Match cell value
                Cell cellValueMatch = row.createCell(22,CellType.FORMULA);
                cellValueMatch.setCellStyle(styleMatch);
                cellValueMatch.setCellFormula("SUM(T22:T26)");

                //Create value
                for(int i = 0; i<18;i++){
                    if(valueDefault == 0){
                        break;
                    }else if(valueDefault == 1){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue("0");
                    }else if(valueDefault == 2){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue(rd.nextInt(100)+1);
                    }
                }
            } else if (rowIndex == 22) {
                Cell cell = row.createCell(0);
                cell.setCellValue("CRE/DEBT");
                cell.setCellStyle(style);

                //total CRE/DEBT
                Cell totalCre = row.createCell(19,CellType.FORMULA);
                totalCre.setCellFormula("SUM(B23:S23)");

                //Create value
                for(int i = 0; i<18;i++){
                    if(valueDefault == 0){
                        break;
                    }else if(valueDefault == 1){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue("0");
                    }else if(valueDefault == 2){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue(rd.nextInt(100)+1);
                    }
                }
            } else if (rowIndex == 23) {
                Cell cell = row.createCell(0);
                cell.setCellValue("VENMO");
                cell.setCellStyle(style);

                //total CRE/DEBT
                Cell totalVENMO = row.createCell(19,CellType.FORMULA);
                totalVENMO.setCellFormula("SUM(B24:S24)");

                //Create value
                for(int i = 0; i<18;i++){
                    if(valueDefault == 0){
                        break;
                    }else if(valueDefault == 1){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue("0");
                    }else if(valueDefault == 2){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue(rd.nextInt(100)+1);
                    }
                }
            } else if (rowIndex == 24) {
                Cell cell = row.createCell(0);
                cell.setCellValue("ZELLE");
                cell.setCellStyle(style);

                //total ZELLE
                Cell totalZELLE = row.createCell(19,CellType.FORMULA);
                totalZELLE.setCellFormula("SUM(B25:S25)");

                //Create value
                for(int i = 0; i<18;i++){
                    if(valueDefault == 0){
                        break;
                    }else if(valueDefault == 1){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue("0");
                    }else if(valueDefault == 2){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue(rd.nextInt(100)+1);
                    }
                }
            } else if (rowIndex == 25) {
                Cell cell = row.createCell(0);
                cell.setCellValue("GIFT TAKE IN");
                cell.setCellStyle(style);

                //total GIFT TAKE IN
                Cell totalGIFT = row.createCell(19,CellType.FORMULA);
                totalGIFT.setCellFormula("SUM(B26:S26)");

                //Create value
                for(int i = 0; i<18;i++){
                    if(valueDefault == 0){
                        break;
                    }else if(valueDefault == 1){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue("0");
                    }else if(valueDefault == 2){
                        Cell cellValue = row.createCell(cellIndex+i);
                        cellValue.setCellValue(rd.nextInt(100)+1);
                    }
                }
                break;
            }
            rowIndex++;
        }

    }


}
