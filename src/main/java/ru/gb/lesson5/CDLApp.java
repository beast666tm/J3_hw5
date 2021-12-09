package ru.gb.lesson5;

import java.util.concurrent.CountDownLatch;

public class CDLApp {
    private static final int THREAD_COUNT = 4;

    public static void main(String[] args) {
        final CountDownLatch cdl = new CountDownLatch(THREAD_COUNT);
        System.out.println("Start...");
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                System.out.println("...Getting " + Thread.currentThread().getName() + " ready...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("..." + Thread.currentThread().getName() + " ready!");
                cdl.countDown();
            }).start();
        }

        try {
            System.out.println("...waiting for Threads to be ready...");
            cdl.await();
            System.out.println("Done");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
