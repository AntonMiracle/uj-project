package pl.edu.project.core.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManagerTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private Path root;
    private String folderNamePrefix = "Folder";
    private int namePostfix = 1;

    @Before
    public void before() throws IOException {
        root = Paths.get(testFolder.getRoot().toString());
        fillFolder(root.toString(), 3);
    }

    private String getFileName(String fileNamePostfix) {
        return (namePostfix++ * 1000) + fileNamePostfix;
    }

    private String getFolderName() {
        return folderNamePrefix + namePostfix;
    }

    private void writeFile(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            String content = "\nThis is file : " + root.relativize(path) + "\n" + "Hello World !!";
            Files.write(path, content.getBytes());
        }
    }

    private String readFile(Path path) {
        String result = "";
        try {
            if (!Files.isDirectory(path)) result = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void fillFolder(String folderPath, int currentDeep) throws IOException {
        folderPath += File.separator;
        currentDeep--;
        for (int i = 0; i < 2; ++i) {
            writeFile(Files.createFile(Paths.get(folderPath + getFileName(".txt"))));
            if (i % 2 == 0 && currentDeep >= 0) {
                Path path = Files.createDirectory(Paths.get(folderPath + getFolderName()));
                fillFolder(path.toString(), currentDeep);
            }
        }
    }

    @Test
    public void showTestFolder() throws IOException {
        Files.walk(root, 3).forEach(System.out::println);
        Files.walk(root, 3).forEach((f) -> System.out.print(readFile(f.toAbsolutePath())));
    }
}