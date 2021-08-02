package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyResponse implements AbstractCommand {

    public CopyResponse(Path fromWherePath, String toWherePath) throws IOException {
//        String selectedFile = clientRoot + "/" + clientListView.getSelectionModel().getSelectedItem();
//        Path selectedPath = Paths.get(selectedFile);
        Files.copy(fromWherePath, (new File(toWherePath)).toPath());
    }

    @Override
    public CommandType getType() {
        return CommandType.COPY_RESPONSE;
    }
}
