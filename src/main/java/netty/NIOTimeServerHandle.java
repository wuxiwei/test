package netty;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;

public class NIOTimeServerHandle implements Runnable {

    SocketChannel socketChannel;
    ExecutorService executorService;

    public NIOTimeServerHandle(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            ByteBuffer requestBuffer = ByteBuffer.allocate("GET CURRENT TIME".length());
            int bytesRead = socketChannel.read(requestBuffer);
            // 如果没有读取到数据，说明当前socket并没有发送消息，不需要处理
            if (bytesRead <= 0) {
                System.out.println("连接没有数据：" + socketChannel.socket().getRemoteSocketAddress());
                return;
            } else {
                System.out.println(new String(requestBuffer.array()));
            }
            // 如果读取到了数据，则需要考虑粘包、解包的问题，这个while代码是为了读取一个完整的请求信息"GET CURRENT TIME"
            System.out.println("连接有数据开始读：" + socketChannel.socket().getRemoteSocketAddress());
            while (requestBuffer.hasRemaining()) {
                socketChannel.read(requestBuffer);
            }
            System.out.println("连接有数据结束读：" + socketChannel.socket().getRemoteSocketAddress());

            String requestStr = new String(requestBuffer.array());
            if (!"GET CURRENT TIME".equals(requestStr)) {
                String badRequest = "BAD_REQUEST";
                ByteBuffer responseBuffer = ByteBuffer.allocate(badRequest.length());
                responseBuffer.put(badRequest.getBytes());
                responseBuffer.flip();
                socketChannel.write(responseBuffer);
            } else {
                String badRequest = Calendar.getInstance().getTime().toLocaleString();
                ByteBuffer responseBuffer = ByteBuffer.allocate(badRequest.length());
                responseBuffer.put(badRequest.getBytes());
                responseBuffer.flip();
                socketChannel.write(responseBuffer);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
