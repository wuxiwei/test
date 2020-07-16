package thread;

import java.util.stream.IntStream;

/***************************************
 * @author:Alex Wang
 * @Date:2017/2/17 QQ:532500648
 * QQ交流群:286081824
 ***************************************/
public class ThreadNotifyAll {
    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(() -> {
//            IntStream.range(1, 1000)
//                    .forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
//        });
//        Thread t2 = new Thread(() -> {
//            IntStream.range(1, 1000)
//                    .forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
//        });
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//
//        Optional.of("All of tasks finish done.").ifPresent(System.out::println);
//        IntStream.range(1, 1000)
//                .forEach(i -> System.out.println(Thread.currentThread().getName() + "->" + i));
//
//
//


//        Object LOCK = new Object();
//
//        IntStream.range(1, 1000)
//                .forEach(i -> new Thread(() -> {
//                    synchronized (LOCK) {
//                        try {
//                            System.out.println("A线程" + Thread.currentThread().getName() + "wait...");
//                            LOCK.wait();
//                            System.out.println("A线程" + Thread.currentThread().getName() + "wait end.");
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, i + "").start());
//
//        synchronized (LOCK) {
//            LOCK.notifyAll();
//        }
//
//        IntStream.range(1, 1000)
//                .forEach(i -> new Thread(() -> {
//                    synchronized (LOCK) {
//                            System.out.println("B线程" + Thread.currentThread().getName() + "doing.");
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, i + "").start());


        Student student = new Student();


        new Thread(() -> {
            int i = 0;
            while (true) {
//                synchronized (student) {
                    if (!student.isFlag()) {
                        if (i == 0) {
                            student.setName("张三");
                            student.setAge("10");
                        } else {
                            student.setName("李四");
                            student.setAge("20");
                        }
                        i = (i+1)%2;
//                        student.notify();
                        student.setFlag(true);
                    } else {
//                        try {
//                            student.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }

                }
            }
        }, "1").start();

        new Thread(() -> {
            while (true) {
//                synchronized (student) {
                    if (student.isFlag()) {
                        System.out.println(student);
//                        student.notify();
                        student.setFlag(false);
                    } else {
//                        try {
//                            student.wait();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }

                }
            }
        }, "2").start();

    }

//    public synchronized static void work() {
//
//    }

    static class Student {
        String name;
        String age;
        //线程通讯标识
        boolean flag = false;

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(String age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }
    }
}

