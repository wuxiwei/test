package thread;

import java.util.concurrent.Executors;

public class Test1 {

    private static volatile int totalNum = 0;

    public static void main(String[] args) {
        Object lock = new Object();

        for (int i = 0; i <= 99; i++) {
            int num = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (lock) {
                            while (totalNum != num) {
                                lock.wait();
                            }
                            System.out.println("Thread name : " + Thread.currentThread().getName() + ": " + num);

                            totalNum = totalNum + 1;

                            lock.notifyAll();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "thread-" + num);
            thread.start();
        }

        synchronized (lock) {
            totalNum = totalNum + 1;
            lock.notifyAll();
        }

        Executors.newCachedThreadPool();

    }

}
