package ru.gb.lesson5;

import java.util.concurrent.ArrayBlockingQueue;

public class QueueTheadCollections {
    public static void main(String[] args) {
//        final List<String> strings = Collections.synchronizedList(new ArrayList<String>());
//
//        final List<String> list = new CopyOnWriteArrayList<>();
//        final CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
//        final Map<Integer, String> map = new ConcurrentHashMap<>();
//
        final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

        Thread producerThread = new Thread(() -> {
            final Producer producer = new Producer(queue);
            for (int i = 0; i < 20; i++) {
                producer.put(i);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumerThread = new Thread(() -> {
            final Consumer consumer = new Consumer(queue);
            for (int i = 0; i < 20; i++) {
                consumer.get();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class Producer {
        final private ArrayBlockingQueue<Integer> queue;

        Producer(ArrayBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public void put(Integer element) {
            System.out.println("Prod add " + element);
            try {
                queue.put(element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer {
        final private ArrayBlockingQueue<Integer> queue;

        Consumer(ArrayBlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        public void get() {
            try {
                final Integer element = queue.take() ;
                System.out.println("Cons get " + element);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
