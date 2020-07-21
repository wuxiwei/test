package netty;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeServerHandle {

    Socket socket;

    public TimeServerHandle(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        new Thread(() -> {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());

                while (true) {
                    String request = reader.readLine();
                    if (!"GET CURRENT TIME".equals(request)) {
                        writer.println("BAD_REQUEST");
                    } else {
                        writer.println(Calendar.getInstance().getTime().toLocaleString());
                    }
                    writer.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                    reader.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
