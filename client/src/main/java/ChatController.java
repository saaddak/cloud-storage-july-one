import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.CopyRequest;
import model.CopyResponse;
import model.ListResponse;

public class ChatController implements Initializable {

    public ListView<String> clientListView;
    public ListView<String> serverListView;
    public TextField statusBar;
    private final String clientRoot = "client/clientFiles";
    private final Path clientPath = Paths.get(clientRoot);

    private final String serverRoot = "server/serverFiles";
    //private final Path serverPath = Paths.get(serverRoot);

    private NettyNetwork network;

    public void send(ActionEvent actionEvent) throws IOException {
        /*statusBar.clear();
        statusBar.setText();*/
        String selectedFile = clientRoot + "/" + clientListView.getSelectionModel().getSelectedItem();
        Path selectedPath = Paths.get(selectedFile);
        Path targetPath = Paths.get(serverRoot + "/" + clientListView.getSelectionModel().getSelectedItem());
        //Files.copy(selectedPath, (new File(serverRoot + "/" + clientListView.getSelectionModel().getSelectedItem())).toPath());
        network.writeMessage(new CopyRequest());
    }

    public void refresh() throws IOException {
        List<String> names = Files.list(clientPath)
                .map(p -> p.getFileName().toString())
                .collect(Collectors.toList());
        clientListView.getItems().clear();
        clientListView.getItems().addAll(names);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }

        network = new NettyNetwork(command -> {
            ListResponse files = (ListResponse) command;
            switch (command.getType()) {
                case LIST_RESPONSE:
                    Platform.runLater(() -> {
                        serverListView.getItems().clear();
                        serverListView.getItems().addAll(files.getNames());
                    });
                    break;
                case COPY_RESPONSE:

                    // здесь должна быть логика копирования

                    Platform.runLater(() -> {
                        serverListView.getItems().clear();
                        serverListView.getItems().addAll(files.getNames());
                    });
                    break;
            }
        });
    }

}
