package ru.gb.lesson5;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicApp {
    public static void main(String[] args){
        final Counter counter = new Counter();
        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                counter.increment();
            }
        });
        t1.start();

        final Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50000; i++) {
                counter.increment();
            }
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter.getCount());
    }

}

class Counter {
    private AtomicInteger count = new AtomicInteger();

    public void increment() {
        count.getAndIncrement();
    }

//    public void decrement() {
//        count--;
//    }
//
    public int getCount() {
        return count.get();
    }
}