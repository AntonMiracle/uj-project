package pl.edu.uj.project;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.rules.TemporaryFolder;
import pl.edu.uj.project.core.FileObserver;
import pl.edu.uj.project.core.FileTree;
import pl.edu.uj.project.core.Util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class to help testing {@link FileTree} and {@link FileObserver}.
 * Create, before each test, temporary folders and files for testing.
 *
 * @author Anton Bondarenko
 * @version 1.0 {@code b.anton.m.1986@gmail.com}
 */
public class TestHelper {
    @ClassRule
    public static TemporaryFolder testFolder = new TemporaryFolder();
    protected static Path testRoot;
    protected static int testDepth;
    protected static Path testFile;
    protected static String testFileText;
    protected static Path testDirectory;
    protected static final Charset testCharset = StandardCharsets.UTF_8;
    private static boolean isTestFileExist;
    private static boolean isTestDirectoryExist;
    private static int nameId = 1;
    private static boolean isShowTestFileTree;

    @BeforeClass
    public static void beforeClass() {
        initData();
        try {
            if (!isShowTestFileTree) {
                System.out.println("--------------- TEST FILE TREE ---------------");
                Files.walk(testRoot, testDepth).forEach(System.out::println);
                System.out.println("TEST FILE: " + testFile);
                System.out.println("TEST DIRECTORY: " + testDirectory);
                System.out.println("--------------------- END --------------------");
                isShowTestFileTree = true;
            }
            testFileText = new String(Files.readAllBytes(testFile), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initData() {
        testRoot = Paths.get(testFolder.getRoot().toString());
        testDepth = 4;
        try {
            generationFolderAndFilesForTesting(testRoot.toString(), testDepth);
        } catch (IOException e) {
            Util.throwIAE(true, TestHelper.class, "initFilesAndFolders", "IOException");
            e.printStackTrace();
        }
    }

    private static void writeFile(Path path) throws IOException {
        if (!Files.isDirectory(path)) {
            String content = "This is file : " + testRoot.relativize(path) + System.lineSeparator() +
                    "Hello World !!" + System.lineSeparator() +
                    "Another line. Next will be 2 empty line" + System.lineSeparator() +
                    System.lineSeparator() +
                    System.lineSeparator() +
                    "Last printLN" + System.lineSeparator();
            Files.write(path, content.getBytes());
            if (!isTestFileExist) {
                testFile = path;
                isTestFileExist = true;
            }
        }
    }

    private static void generationFolderAndFilesForTesting(String folderPath, int currentDeep) throws IOException {
        folderPath += File.separator;
        --currentDeep;
        for (int i = 0; i < 2; ++i) {
            writeFile(Files.createFile(Paths.get(folderPath + (nameId++ * 1000) + ".txt")));
            if (i % 2 == 0 && currentDeep >= 0) {
                Path path = Files.createDirectory(Paths.get(folderPath + "Folder" + nameId++));
                if (!isTestDirectoryExist) {
                    testDirectory = path;
                    isTestDirectoryExist = true;
                }
                generationFolderAndFilesForTesting(path.toString(), currentDeep);
            }
        }
    }
}
