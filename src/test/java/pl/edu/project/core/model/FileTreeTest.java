package pl.edu.project.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
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
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThat;

public class FileTreeTest {
    @ClassRule
    public static TemporaryFolder testFolder = new TemporaryFolder();
    private static Path root;
    private static int nameId = 1;
    private FileTree fileTree;

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
        fileTree = new FileTree();
    }

    @Test
    public void setAndGetDepthSearch() {
        fileTree.setDepthSearch(5);
        assertThat(fileTree.getDepthSearch()).isEqualTo(5);
    }

    @Test
    public void whenDepthSearchNotSetThenGetDepthSearchReturnOne() {
        assertThat(fileTree.getDepthSearch()).isEqualTo(1);
    }

    @Test
    public void setAndGetRoot() {
        fileTree.setRoot(root);
        assertThat(fileTree.getRoot()).isEqualTo(root);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetRootWithNullThenIAE() {
        fileTree.setRoot(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetRootWithNotExistPathThenIAE() {
        fileTree.setRoot(Paths.get("wrong/path"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetRootReturnNullThenIAE() {
        fileTree.getRoot();
    }

    @Test
    public void getPaths() {
        fileTree.setRoot(root);
        fileTree.setDepthSearch(5);
        assertThat(fileTree.getPaths().count()).isEqualTo(15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetPathsWithWrongRootOrRootNullThenIAE() {
        fileTree.getPaths();
    }

    @Test
    public void countFilesPathWithDepth5() {
        fileTree.setRoot(root);
        fileTree.setDepthSearch(5);
        assertThat(fileTree.search(FileTree.Type.FILES).count()).isEqualTo(10L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSearchWithNullThenIAE() {
        fileTree.search(null);
    }

    @Test
    public void countFilesPathWithDepth1() {
        fileTree.setRoot(root);
        fileTree.setDepthSearch(1);
        assertThat(fileTree.search(FileTree.Type.FILES).count()).isEqualTo(2);
    }

    @Test
    public void countFolders() {
        fileTree.setRoot(root);
        fileTree.setDepthSearch(1);
        assertThat(fileTree.search(FileTree.Type.FOLDERS).count()).isEqualTo(2);
    }

    @Test
    public void fileManagerOfRootAndDepthSearch() {
        fileTree = FileTree.of(root, 2);
        assertThat(fileTree).isNotNull();
    }

    @Test
    public void checkEqualsAndHashCode() throws NoSuchAlgorithmException {
        EqualsVerifier.forClass(FileTree.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void getFileTreeTypeFromValueOfString() {
        assertThat(FileTree.Type.valueOf("FILES")).isEqualTo(FileTree.Type.FILES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenValueOfWithWrongStringThenIAE() {
        assertThat(FileTree.Type.valueOf("null")).isEqualTo(FileTree.Type.FILES);
    }

    @Test
    public void valuesReturn2() {
        assertThat(FileTree.Type.values().length).isEqualTo(2);
    }

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