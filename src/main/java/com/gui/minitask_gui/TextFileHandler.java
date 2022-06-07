package com.gui.minitask_gui;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Scanner;

public class TextFileHandler {

    public static File file = new File(GlobalHandler.getRootDir()+"Payment_Calculation.txt");
    public static File manualFile = new File("Manual.txt");

    public static Scanner readData(){
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Scanner scan = new Scanner(fileInputStream);
            return scan;

        } catch (FileNotFoundException e) {
            System.out.println("Can't read Payment_Calculation.txt");
            CreateMessBox.popupBoxMess("Can't read Payment_Calculation.txt",2);
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkManFileExist(){
        String header = "<header> This Is Header";
        String title = "<title> This is title";
        String content = "<content> To change this manual, look for a file named Manual.txt located in the same directory as this program. Note: Please follow the syntax provided in the file for the contents to be displayed properly. Thank you. To change this manual, look for a file named Manual.txt located in the same directory as this program. Note: Please follow the syntax provided in the file for the contents to be displayed properly. Thank you. To change this manual, look for a file named Manual.txt located in the same directory as this program. Note: Please follow the syntax provided in the file for the contents to be displayed properly. Thank you.";

        try {
            if (!manualFile.exists()) {
                manualFile.createNewFile();
                FileWriter fw = new FileWriter(manualFile);
                fw.write(header+"\n");
                fw.write(title+"\n");
                fw.write(content+"\n");
                fw.write(content+"\n");
                fw.write(title+"\n");
                fw.write(content+"\n");
                fw.flush();
                fw.close();
            }
            return true;
        }catch (IOException e){
            CreateMessBox.popupBoxMess("Can't create Manual.txt!",2);
            return false;
        }
    }

    public static Scanner readDataManFile(){
        try {
            checkManFileExist();
            FileInputStream fileInputStream = new FileInputStream(manualFile);
            Scanner scan = new Scanner(fileInputStream);
            return scan;
        } catch (FileNotFoundException e) {
            System.out.println("Can't read Payment_Calculation.txt");
            CreateMessBox.popupBoxMess("Can't read Manual.txt",2);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Can't read Payment_Calculation.txt");
            CreateMessBox.popupBoxMess("Can't read Manual.txt",2);
        }
        return null;
    }

    public static ArrayList<String> getManualContent(){
        ArrayList<String> contentList = new ArrayList<>();
        Scanner scan = readDataManFile();
        while (scan.hasNextLine()){
            String content = scan.nextLine().strip();
            if(content.startsWith("<header>") || content.startsWith("<title>") || content.startsWith("<content>")){
                contentList.add(content);
            }
        }
        scan.close();
        return contentList;
    }
    public static Hashtable defineHowToCalculate(String employeeName){
        Hashtable<Integer,String> ht = new Hashtable<>();
        Scanner scan = readData();
        while (scan.hasNextLine()){
            String name = scan.nextLine();
            if(name.strip().toLowerCase().contains(employeeName.toLowerCase()) && name.startsWith("Name")){
                for (int i = 0; i < 7; i++) {
                    String s = scan.nextLine().strip();
                    try{
                        ht.put(i+1,s.split("=")[1]);
                    }catch (ArrayIndexOutOfBoundsException e){
                        ht.put(i+1,"");
                    }
                }
                return ht;
            }
        }
        for (int i = 0; i < 7; i++) {
            ht.put(i+1,"");
        }
        return ht;
    }

    public static ArrayList<String> getEmployeeName(String path){
        ArrayList<String> names = new ArrayList<>();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(fileInputStream);
        while (scan.hasNextLine()){
            String name = scan.nextLine();
            if(name.startsWith("Name")){
                names.add(name.split("=")[1]);
            }
        }
        Collections.sort(names);
        return names;
    }

}
