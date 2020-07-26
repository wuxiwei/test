package socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ServerHandler {

    public static final int MAX_DATA_LEN = 1024;

    Socket socket;

    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        new Thread(() -> {
            try {
                InputStream inputStream = socket.getInputStream();
                while (true) {
                    byte[] data = new byte[MAX_DATA_LEN];
                    int len;
                    while ((len = inputStream.read(data)) != -1) {
                        String message = new String(data, 0, len);
                        System.out.println("客户端传来消息: " + message);
                        socket.getOutputStream().write(data);
                    }

                }
            } catch (Exception e) {

            }
        }).start();

    }
}
