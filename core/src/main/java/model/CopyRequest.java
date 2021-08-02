package model;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyRequest implements AbstractCommand {
    @Override
    public CommandType getType() {
        return CommandType.COPY_REQUEST;
    }

    /*private Path sourcePath;
    private Path targetPath;
    public CopyRequest(Path sourcePath, Path targetPath) {
        sourcePath = this.sourcePath;
        targetPath = this.targetPath;
    }

    public Path getSourcePath() { return sourcePath; }
    public Path getTargetPath() { return targetPath; }*/
}
