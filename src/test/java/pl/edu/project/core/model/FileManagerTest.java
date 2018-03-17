package pl.edu.project.core.model;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FileManagerTest {
    @ClassRule
    public static TemporaryFolder testFolder = new TemporaryFolder();
    private static Path root;
    private static int nameId = 1;
    private FileManager manager;

    @BeforeClass
    public static void beforeClass() throws IOException {
        root = Paths.get(testFolder.getRoot().toString());
        generationFolderAndFilesForTesting(root.toString(), 4);
        System.out.print("========== file tree for tests ==========");
        Files.walk(root, 5).forEach((f) -> System.out.println(root.relativize(f.toAbsolutePath())));
        System.out.println("========== end file tree for tests ==========");
    }

    @Before
    public void before() {
        manager = new FileManager();
    }

    @Test
    public void setAndGetDeepSearchDeep() {
        manager.setDepthSearch(5);
        assertThat(manager.getDepthSearch()).isEqualTo(5);
    }

    @Test
    public void setAndGetRoot() {
        manager.setRoot(root);
        assertThat(manager.getRoot()).isEqualTo(root);
    }

    @Test
    public void countAllPathsWithDepth5() {
        manager.setRoot(root);
        manager.setDepthSearch(5);
        assertThat(manager.getAllPaths().count()).isEqualTo(15);
    }

    @Test
    public void countFilesPathWithDepth5() {
        manager.setRoot(root);
        manager.setDepthSearch(5);
        assertThat(manager.searchFiles().count()).isEqualTo(10L);
    }

    @Test
    public void countFilesPathWithDepth1() {
        manager.setRoot(root);
        manager.setDepthSearch(1);
        assertThat(manager.searchFiles().count()).isEqualTo(2);
    }

    @Test
    public void readFileByPath() {
        manager.setRoot(root);
        manager.setDepthSearch(1);
        Path path = manager.searchFiles().findFirst().get();
        String expected = "This is file : 1000.txt\nHello World !!\n";
        assertThat(manager.readFile(path)).isEqualTo(expected);
    }

    //expected runtime for readFile + path

    @Test
    public void readAllFilesWithDepth1() {
        manager.setRoot(root);
        manager.setDepthSearch(1);
        String expected = "This is file : 1000.txt\nHello World !!\n"
                + "This is file : 14000.txt\nHello World !!\n";
        assertThat(manager.readFiles()).isEqualTo(expected);
    }


//    @Test
//    public void countFolders() {
//        manager.setRoot(root);
//        manager.setDepthSearch(1);
//assertThat(manager.countFolder()).isEqualTo(1);
//    }

    private static void writeFile(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            String content = "This is file : " + root.relativize(path) + "\nHello World !!\n";
            Files.write(path, content.getBytes());
        }
    }

    private static void generationFolderAndFilesForTesting(String folderPath, int currentDeep) throws IOException {
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