package ru.gb.lesson5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Locks {
    public static void main(String[] args) {
        // CAS - compare and swap
        final Lock lock = new ReentrantLock();  // блокирует чтение запись если закрыт
        lock.lock();
        try {
            // критический код
        } finally {
            lock.unlock();
        }

        // блокирует запись, если есть читающий поток(и наоботрот)
        final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

        for (int i = 0; i < 3; i++) {
            final int index = i + 1;
            final Thread read = new Thread(() -> {
                rwLock.readLock().lock();
                try {
                    System.out.println("Start reading " + index);
                    Thread.sleep(1000);
                    System.out.println("stop reading " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    rwLock.readLock().unlock();
                }
            });
            read.start();
        }

        for (int i = 0; i < 2; i++) {
            final int index = i + 1;
            final Thread write = new Thread(() -> {
                rwLock.writeLock().lock();
                try {
                    System.out.println("Start writing " + index);
                    Thread.sleep(1);
                    System.out.println("stop writing " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    rwLock.writeLock().unlock();
                }
            });
            write.start();
        }
    }
}