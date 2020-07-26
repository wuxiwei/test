package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("服务器启动成功，端口:" + port);
        } catch (IOException e) {
            System.out.println("服务器启动失败");
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    System.out.println("服务器开始监听...");
                    Socket accept = serverSocket.accept();
                    new ServerHandler(accept).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

}
