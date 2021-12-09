package ru.gb.lesson5;

import java.util.concurrent.Semaphore;

public class SemaphoreApp {
    public static void main(String[] args) {

        final Semaphore semaphore = new Semaphore(4);

        Runnable runnable = () -> {
            try {
                System.out.println("Thread " + Thread.currentThread().getName() + " whait semaphore");
                semaphore.acquire();
                System.out.println("Thread " + Thread.currentThread().getName() + " working...");
                Thread.sleep(1000);
                System.out.println("Thread " + Thread.currentThread().getName() + " finished.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }

}

