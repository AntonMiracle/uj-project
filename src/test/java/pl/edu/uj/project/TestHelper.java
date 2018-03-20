package pl.edu.uj.project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class to help testing {@link pl.edu.uj.project.core.model.FileTree} and {@link pl.edu.uj.project.core.model.FileObserver}.
 *
 * @author Anton Bondarenko
 * @version 1.0 {@code b.anton.m.1986@gmail.com}
 */
public class TestHelper {
    private static int nameId = 1;
    protected static Path root;

    private static void writeFile(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            String content = "This is file : " + root.relativize(path) + "\nHello World !!\n";
            Files.write(path, content.getBytes());
        }
    }

    protected static void generationFolderAndFilesForTesting(String folderPath, int currentDeep) throws IOException {
        folderPath += File.separator;
        --currentDeep;
        for (int i = 0; i < 2; ++i) {
            writeFile(Files.createFile(Paths.get(folderPath + (nameId++ * 1000) + ".txt")));
            if (i % 2 == 0 && currentDeep >= 0) {
                Path path = Files.createDirectory(Paths.get(folderPath + "Folder" + nameId++));
                generationFolderAndFilesForTesting(path.toString(), currentDeep);
            }
        }
    }
}
