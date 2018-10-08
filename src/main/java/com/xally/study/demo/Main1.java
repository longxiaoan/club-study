package com.xally.study.demo;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by xiaoanlong on 2018/10/8.
 */
public class Main1 {

    public static void main(String[] args) {
        //testJoin();
        // waitNotify();
        //runAAfterBC();
        //testCyclicBarrier();
        testCallAble();
    }

    private static void testJoin() {
        Thread B = new Thread(() -> printNum("B"));
        Thread A = new Thread(() -> {
            try {
                B.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printNum("A");
        });
        A.start();
        B.start();
    }

    public static void printNum(String name) {
        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + i + "----");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void waitNotify() {
        Object lock = new Object();
        Thread A = new Thread(() -> {
            synchronized (lock) {
                System.out.println("A1");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("A2");
                System.out.println("A3");
            }

        });

        Thread B = new Thread(() -> {
            synchronized (lock) {
                System.out.println("B1");
                lock.notify();
                System.out.println("B2");
                System.out.println("B3");
            }
        });
        A.start();
        B.start();
    }

    public static void runAAfterBC() {

        CountDownLatch countDownLatch = new CountDownLatch(3);
        Random random = new Random();
        int n = random.nextInt(3);


        Thread A = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
                printNum("A");
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount() + "========================");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        Thread B = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
                printNum("B");
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount() + "========================");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread C = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
                printNum("C");
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount() + "========================");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread D = new Thread(() -> {
            try {
                //TimeUnit.SECONDS.sleep(random.nextInt(5)+12);
                System.out.println(countDownLatch.getCount() + "========================");
                countDownLatch.await();
                printNum("D");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        A.start();
        B.start();
        C.start();
        D.start();
    }

    public static void testCyclicBarrier() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        Random random = new Random();
        Thread A = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
                printNum("A");
                cyclicBarrier.await();
                System.out.println("A===============");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread B = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
                printNum("B");
                cyclicBarrier.await();
                System.out.println("B===============");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread C = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(random.nextInt(3));
                printNum("C");
                cyclicBarrier.await();
                System.out.println("C===============");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        A.start();
        B.start();
        C.start();
    }

    /**
     *
     */
    public static void testCallAble() {
        Callable<Integer> callable = () -> {
            System.out.println("Task starts");
            Thread.sleep(1000);
            int result = 0;
            for (int i = 0; i <= 100; i++) {
                result += i;
            }
            System.out.println("Task finished and return result");
            return result;
        };
        FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        try {
            System.out.println("Before futureTask.get()");
            System.out.println("Result: " + futureTask.get());
            System.out.println("After futureTask.get()");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}

