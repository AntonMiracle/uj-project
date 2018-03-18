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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileObserverTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private Path path;
    private FileObserver observer;
    private String text;

    @Before
    public void before() throws IOException {
        observer = new FileObserver();
        File file = testFolder.newFile("test.txt");
        text = "This is file : " + file.getAbsolutePath() + "\n"
                + "Hello world! This is test\n"
                + "It will be OK!!!!\nNext...";
        write(file, text);
        path = file.toPath();
    }


    @Test
    public void setAndGetPath() {
        observer.setPath(path);
        assertThat(observer.getPath()).isEqualTo(path);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPathNullThenIAE() {
        observer.setPath(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPathWrongThenIAE() {
        observer.setPath(Paths.get("null"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPathIsFolderThenIAE() {
        observer.setPath(testFolder.getRoot().toPath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetPathReturnNullThenIAE() {
        observer.getPath();
    }

    @Test
    public void readFile() {
        observer.setPath(path);
        assertThat(observer.read()).isEqualTo(text);
    }

    @Test
    public void fileObserverOfPath() {
        observer = FileObserver.of(path);
        assertThat(observer.getPath()).isEqualTo(path);
    }

    @Test
    public void countCharInFile() {
        observer.setPath(path);
        int count = 0;
        char search = 'e';
        for (char symbol : text.toCharArray()) {
            if (search == symbol) ++count;
        }
        System.out.println(observer.count('\n'));
        assertThat(observer.count(search)).isEqualTo(count);
    }

//    private create enum and add method with rnum + null test
    private void write(File file, String text) throws IOException {
        Files.write(file.toPath(), text.getBytes());
    }
}