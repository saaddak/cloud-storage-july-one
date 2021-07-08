import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ChatController implements Initializable {
    private String root = "client/clientFiles";
    private DataInputStream is;
    private DataOutputStream os;
    private byte[] buffer;
    private final int BUFFER_SIZE = 256;
    private final int SERVER_PORT = 8189;

    public ListView<String> listView;

    public TextField statusBar;

    public void send(ActionEvent actionEvent) throws IOException {
        String fileName = listView.getSelectionModel().getSelectedItem();
        Path filePath = Paths.get(root, fileName);

        System.out.println(filePath.toString());

        long size = Files.size(filePath);
        os.writeUTF(fileName);
        os.writeLong(size);
        Files.copy(filePath, os);
        os.flush();
        statusBar.setText("Файл: " + fileName + " отправлен на сервер.");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buffer = new byte[BUFFER_SIZE];
        try {
            File dir = new File(root);
            listView.getItems().clear();
            listView.getItems().addAll(dir.list());
            Socket socket = new Socket("localhost", SERVER_PORT);
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String status = is.readUTF();
                        Platform.runLater(() -> statusBar.setText(status));
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка в процессе чтения.");
                }
            });
            readThread.setDaemon(true);
            readThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
