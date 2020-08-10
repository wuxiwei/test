package socket;

public class ServiceMain {

    public static void main(String[] args) {

        Server server = new Server(1888);
        server.start();

    }

}
