package thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static java.lang.Thread.sleep;

public class RefreshFlag {

    private volatile static boolean flag = false;

//    private volatile static int increaseI = 0;
    private static int increaseI = 0;

    public static void main(String[] args) throws InterruptedException {
//        refreshFlag();
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

//                    var = increaseI; //步骤1：将increaseI的值加载到寄存器var
//                    var = var + 1;//步骤2：将寄存器var的值增加1
//                    increaseI = var;//步骤3：将寄存器var的值写入increaseI

                    // 只用volatile
                    // 即便是volatile变量，最终结果也不是10000，volatile只能保证变量的读写是原子性的，
                    // 而increaseI++是一个复合操作，volatile只能保证第一步和第三步单个操作的原子性，
                    // 第二和第三的顺序不能保证，并不能保证整个自增和自减过程的原子性，需要实现结果10000，需要结合synchronized
                    increaseI++;

                    // 只用synchronized
                    // 最后结果可以保证10000
//                    synchronized (RefreshFlag.class) {
//                        increaseI++;
//                    }
                    // volatile和synchronized同时使用也可以


                    // double-check问题上必须同时使用
//                    if (null == instance) {   //步骤一
//                        synchronized (DoubleCheck.class) {
//                            if (null == instance) {   //步骤二
//                                instance = new DoubleCheck();   //步骤三
//                            }
//                        }
//                    }
//                    return instance;
//                    3.1 为DoubleCheck分配内存地址 alloc memory address
//                    3.2 初始化对象DoubleCheck init DoubleCheck
//                    3.3 将引用地址指向instance instance > memory address
                    // 如果只是synchronized，在步骤3.2和3.3不能保证顺序，如果先执行的3.3，此时instance != null，但是没有初始化对象。
                    // 如果只是volatile，会导致重复初始化
                }
            }, String.valueOf(i));
            thread.start();
        }

        while(Thread.activeCount()>1)
            Thread.yield();
        System.out.println(increaseI);
    }

    private static void refreshFlag() throws InterruptedException {

        Thread threadA = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!flag) {
                    //do something
                    System.out.println("threadA ing");
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {

            @Override
            public void run() {

                flag = true;
            }
        });

        DateFormat dateFormat  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        System.out.println("threadA start" + dateFormat.format(new java.util.Date()));
        threadA.start();

        sleep(100);

        threadB.start();

        threadA.join();
        System.out.println("threadA end" + dateFormat.format(new java.util.Date()));
    }

}
