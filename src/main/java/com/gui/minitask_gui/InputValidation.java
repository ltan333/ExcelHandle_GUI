package com.gui.minitask_gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class InputValidation {
    public static boolean isEmptyString(String inputString) {
        if(inputString.isEmpty())
            return true;
        else return inputString.isBlank();
    }

    public static boolean checkMaxStringLength(String inputString, int max){
        return inputString.length() > max;
    }

    public static boolean checkMinStringLength(String inputString, int min) {
        //string it hon min thi true
        return inputString.length() < min;
    }
    public static boolean isNumber(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            e.getMessage();
            return false;
        }
    }

    public static String setNameFormat(String name){
        String[] nameArr = name.toLowerCase().split(" ");
        String nameFormat="";
        for(int i =0; i<nameArr.length;i++){
            String[] a = nameArr[i].split("");
            a[0] = a[0].toUpperCase();
            for(String charac:a){
                nameFormat+=charac;
            }
            if(i!=nameArr.length-1)
                nameFormat+=" ";
        }
        return nameFormat;
    }

    public static boolean isEmail(String email){
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

    public static boolean isPhoneNumber(String phone){
        try {
            if(phone.length()!=10)
                return false;
            Long p = Long.parseLong(phone);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date test = new Date();
        return dateFormat.format(test);
    }
    public static String getHour(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getH = getMinAndHour[1].split(":");
        return getH[0];
    }
    public static String getDay(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getD = getMinAndHour[0].split("/");
        return getD[0];
    }
    public static String getMonth(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getM = getMinAndHour[0].split("/");
        return getM[1];
    }
    public static String getYear(String dd_MM_yyyy_HH_ss) {
        String[] getMinAndHour = dd_MM_yyyy_HH_ss.split(" ");
        String[] getY = getMinAndHour[0].split("/");
        return getY[2];
    }

}
