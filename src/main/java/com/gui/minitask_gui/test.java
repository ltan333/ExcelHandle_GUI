package com.gui.minitask_gui;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class test {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Thread> ts = new ArrayList<>();
        new Thread(()->{
            int i = 0;
            ts.add(Thread.currentThread());
            while (true)
                i++;
        }).start();
        new Thread(()->{
            int i = 0;
            ts.add(Thread.currentThread());
            while (true)
                i++;
        }).start();
        new Thread(()->{
            int i = 0;
            ts.add(Thread.currentThread());
            while (true)
                i++;
        }).start();
        while (true){
            System.out.println(ts);
            Thread.sleep(2000);
            }
        }
}