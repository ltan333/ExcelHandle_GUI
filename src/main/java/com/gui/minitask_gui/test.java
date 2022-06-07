package com.gui.minitask_gui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class test {
    public static ObservableList<Integer> l = FXCollections.observableArrayList();

    public static void main(String[] args) throws ParseException, IOException {
        String path = "C:\\Users\\truon\\OneDrive\\Desktop\\root\\2022\\April\\Salary.xlsx";
        Runtime r = Runtime.getRuntime();

        r.exec(new String[]{"cmd.exe","/c","start "+path});
    }
}
