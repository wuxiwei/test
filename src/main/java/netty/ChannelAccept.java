package netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelAccept {

    public static final String GREETING = "Hello I must be going.\r\n";

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 1024;
        ByteBuffer buffer = ByteBuffer.wrap(GREETING.getBytes());
        ServerSocketChannel ssc = ServerSocketChannel.open();
//        ssc.configureBlocking(false);
//        ssc.socket().bind(new InetSocketAddress(port));
        ssc.bind(new InetSocketAddress(port));
        while (true) {
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("没有连接请求");
                Thread.sleep(2000);
            } else {
//                sc.configureBlocking(false);
                ByteBuffer allocate = ByteBuffer.allocateDirect(16 * 1024);
                while (sc.read(allocate) > 0) {
                    allocate.flip();
                    while (allocate.hasRemaining()) {
                        System.out.println(String.valueOf(allocate.get()));
                    }
                    allocate.clear();
                }
                System.out.println("Incoming connection from: " + sc.socket().getRemoteSocketAddress());

                buffer.rewind();
                sc.write(buffer);
                sc.close();

            }

        }

    }

}
