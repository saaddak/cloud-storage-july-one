import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final static int SERVER_PORT = 8189;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Сервер запущен на порту " + SERVER_PORT + ".");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Клиент подключен.");
            new Thread(new ChatHandler(socket)).start();
        }
    }
}
