package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {

    public static void main(String[] args) {
//        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
//        lock.writeLock().lock();
//        lock.writeLock().unlock();
        //这里为了实现简单，将缓存大小设置为4。
        Map<String, String> cacheMap = new HashMap<>(4);
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        for (int i = 0; i < 20; i++) { //同时开启20个线程访问缓存

            final String key = String.valueOf(i % 4);
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        //①读取缓存时获取读锁
                        readWriteLock.readLock().lock();
                        //获取读锁后通过key获取缓存中的值
                        String valueStr = cacheMap.get(key);
                        //缓存值不存在
                        if (valueStr == null) {
                            //③释放读锁后再尝试获取写锁
                            readWriteLock.readLock().unlock();
                            try {
                                //④获取写锁来写入不存在的key值，
                                readWriteLock.writeLock().lock();
                                valueStr = cacheMap.get(key);
                                if (valueStr == null) {
                                    valueStr = key + " --- value";
                                    cacheMap.put(key, valueStr); //写入值
                                    System.out.println(Thread.currentThread().getName() + " --------- put " + valueStr);
                                }
                                Thread.sleep(1000);
                                // ⑥锁降级，避免被其他写线程抢占后再次更新值，保证这一次操作的原子性
                                readWriteLock.readLock().lock();
                                System.out.println(Thread.currentThread().getName() + " --------- get new " + valueStr);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                readWriteLock.writeLock().unlock(); //⑤释放写锁
                            }

                        } else {
                            System.out.println(Thread.currentThread().getName() + " ------ get cache value");
                        }
                    } finally {
                        readWriteLock.readLock().unlock();  //②释放读锁
                    }
                }
            }, String.valueOf(i));
            thread.start();
        }
    }

}
