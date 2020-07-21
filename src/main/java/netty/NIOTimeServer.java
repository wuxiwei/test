package netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

public class NIOTimeServer {

    private BlockingQueue<SocketChannel> idleQueue = new LinkedBlockingQueue<>();
    private BlockingQueue<Future<SocketChannel>> workingQueue = new LinkedBlockingQueue<>();

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    {
        new Thread(() -> {
            try {
                while (true) {
                    for (int i = 0; i < idleQueue.size(); i++) {
                        SocketChannel poll = idleQueue.poll();
                        if (poll != null) {
                            System.out.println("idleQueue取出连接：" + poll.socket().getRemoteSocketAddress());
                            Future<SocketChannel> submit = executor.submit(new NIOTimeServerHandle(poll), poll);
                            workingQueue.put(submit);
                        }
                    }

                    for (int i = 0; i < workingQueue.size(); i++) {
                        Future<SocketChannel> poll = workingQueue.poll();
                        if (!poll.isDone()) {
//                            System.out.println("workingQueue取出连接未完成");
                            workingQueue.put(poll);
                            continue;
                        }

                        SocketChannel channel = null;
                        channel = poll.get();
                        System.out.println("workingQueue取出完成的连接：" + channel.socket().getRemoteSocketAddress());
                        idleQueue.put(channel);
                    }

                    Thread.sleep(3000);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) throws IOException {
        NIOTimeServer timeServer = new NIOTimeServer();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(1024));
        while (true) {
            SocketChannel socketChannel = ssc.accept();
            if (socketChannel == null) {
                continue;
            } else {
                socketChannel.configureBlocking(false);
                System.out.println("添加新的连接：" + socketChannel.socket().getRemoteSocketAddress());
                timeServer.idleQueue.add(socketChannel);
            }
        }
    }


}
