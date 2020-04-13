package pl.kukla.krzys.concurrency.multithreading;

import java.util.Random;

/**
 * @author Krzysztof Kukla
 */
//use WatchDog for DeadLock detection
public class DeadBlock {
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread roadA = new Thread(new RoadA(intersection));
        roadA.setName("first");
        Thread roadB = new Thread(new RoadB(intersection));
        roadB.setName("second");

        roadA.start();
        roadB.start();
    }

    private static class RoadA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        private RoadA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                int randomSleeping = random.nextInt(5);
                try {
                    Thread.sleep(randomSleeping);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoadA();
            }
        }

    }

    private static class RoadB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        private RoadB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                int roadBSleeping = random.nextInt(5);
                try {
                    Thread.sleep(roadBSleeping);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                intersection.takeRoadB();

            }
        }

    }

    private static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by Thread " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is passing thought road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void takeRoadB() {
            //to avoid DeadBlock we have to lock roadA and roadB for both Threads in the same order like below
            synchronized (roadA) {
                System.out.println("Road A is locked by Thread " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is passing thought road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
