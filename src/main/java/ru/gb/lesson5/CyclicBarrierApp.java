package ru.gb.lesson5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierApp {
    private static final int THREAD_COUNT = 4;

    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(THREAD_COUNT);
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Getting " + Thread.currentThread().getName());
                    Thread.sleep(500);
                    cyclicBarrier.await();

                    System.out.println(Thread.currentThread().getName() + " Ready, GO!!!...");
                    Thread.sleep(500);
                    cyclicBarrier.await();

                    System.out.println("..." + Thread.currentThread().getName() + " Finished");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
