package ru.gb.lesson5;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Exchanger;

public class TwoTracks {
    private static final int THREAD_COUNT = 4;
    private static final Exchanger<String> EXCHANGER = new Exchanger<>();

    public static void main(String[] args) {
        // маршрут A --=E=-- D груз из => в (A => D, A => C)
        // марштут B --=E=-- C груз из => в (B => C, B => D)
        // E - точка пересечения

        final Thread track1 = new Thread(new Track(1, new String[]{"[Bananas]", "[Apples]"}, "A", "D"));
        final Thread track2 = new Thread(new Track(2, new String[]{"[Pineapple]", "[Kiwi]"}, "B", "C"));

        track1.start();
        track2.start();
    }

    static class Track implements Runnable {

        private final int trackNumber;
        private final String depPoint;
        private final String destPoint;
        private final String[] goods;

        public Track(int trackNumber, String[] goods, String depPoint, String destPoint) {
            this.trackNumber = trackNumber;
            this.depPoint = depPoint;
            this.destPoint = destPoint;
            this.goods = goods;
        }

        @Override
        public void run() {
            final Random random = new Random();
            System.out.println("To track " + trackNumber + " loaded " + Arrays.toString(goods) + " left the point " + "\"" + depPoint + "\"");
            try {
                Thread.sleep(random.nextInt(1000));
                System.out.println("Track " + trackNumber + " arrived at the point E");

                goods[0] = EXCHANGER.exchange(goods[0]);
                Thread.sleep(random.nextInt(1000));
                System.out.println("Track " + trackNumber + " arrived at the point " + "\"" + destPoint + "\"" + " goods: " + Arrays.toString(goods));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
