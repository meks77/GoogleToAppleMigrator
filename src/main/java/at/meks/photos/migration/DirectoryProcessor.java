package at.meks.photos.migration;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static at.meks.validation.args.ArgValidator.validate;

class DirectoryProcessor {

    void processDirectory(File filePath, boolean recursive) {
        Path contentRoot = contentRoot(filePath);
        if (recursive) {
            processContentRootRecursive(contentRoot);
        }

    }

    private Path contentRoot(File filePath) {
        Path contentRoot = filePath.toPath();
        boolean directory = Files.isDirectory(contentRoot);
        validate().that(directory)
                .withMessage(() -> "Path must be a directory")
                .isTrue();
        return contentRoot;
    }

    @SneakyThrows
    private void processContentRootRecursive(Path contentRoot) {
        Files.walk(contentRoot)
                .filter(this::isImage)
                .forEach(this::processPath);
    }

    private boolean isImage(Path path) {
        return isFile(path) &&
                (path.endsWith(".jpeg") || path.endsWith(".jpg"));
    }

    private boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }

    private void processPath(Path path) {

    }
}
