package pl.edu.project.core.model;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public void whenSetPathNullThenThrowIAE() {
        observer.setPath(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPathWrongThenThrowIAE() {
        observer.setPath(Paths.get("null"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetPathIsFolderThenThrowIAE() {
        observer.setPath(testFolder.getRoot().toPath());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetPathReturnNullThenThrowIAE() {
        observer.getPath();
    }

    @Test
    public void setAndGetStandardCharsets() {
        observer.setCharset(StandardCharsets.UTF_8);
        assertThat(observer.getCharset()).isEqualTo(StandardCharsets.UTF_8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetCharsetsWithNullThenThrowIAE() {
        observer.setCharset(null);
    }

    @Test
    public void whenCharsetNullThenGetCharsetReturnDefaultCharset() {
        assertThat(observer.getCharset()).isEqualTo(Charset.defaultCharset());
    }

    @Test
    public void readFile() {
        observer.setPath(path);
        assertThat(observer.read()).isEqualTo(text);
    }


    @Test
    public void countCharInFile() {
        observer.setPath(path);
        int count = 0;
        char search = 'e';
        for (char symbol : text.toCharArray()) {
            if (search == symbol) ++count;
        }
        assertThat(observer.count(search)).isEqualTo(count);
    }


    @Test
    public void countLinesInFileByUsingTypes() {
        observer.setPath(path);
        int count = 1;
        char search = '\n';
        for (char symbol : text.toCharArray()) {
            if (search == symbol) ++count;
        }
        assertThat(observer.count(FileObserver.Type.LINES)).isEqualTo(count);
    }

    @Test
    public void countWorldsInFileByUsingTypes() {
        observer.setPath(path);
        long count = 0;
        boolean isPreviousAlphabetSymbol = false;
        for (char symbol : observer.read().toCharArray()) {
            if (Pattern.compile("[a-zA-Z]").matcher(String.valueOf(symbol)).matches()) {
                if (!isPreviousAlphabetSymbol) {
                    ++count;
                    isPreviousAlphabetSymbol = true;
                }
            } else {
                isPreviousAlphabetSymbol = false;
            }
        }
        assertThat(observer.count(FileObserver.Type.WORLDS)).isEqualTo(count);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCountWithNullThenThrowIAE() {
        observer.count(null);
    }

    @Test
    public void getWorlds() {
        observer.setPath(path);
        List<String> expected = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        for (char ch : observer.read().toCharArray()) {
            String symbol = String.valueOf(ch);
            if (Pattern.compile("[a-zA-Z]").matcher(symbol).matches()) {
                word.append(symbol);
            } else {
                if (word.length() > 0) {
                    expected.add(word.toString());
                    word.delete(0, word.length());
                }
            }
        }
        List<String> actual = observer.get(FileObserver.Type.WORLDS).collect(Collectors.toList());
        assertThat(Arrays.deepEquals(actual.toArray(), expected.toArray())).isTrue();
    }

    @Test
    public void getLines() {
        observer.setPath(path);
        List<String> expected = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        char[] text = observer.read().toCharArray();
        for (int i = 0; i < text.length; ++i) {

            if (text[i] == '\n') {
                if (line.length() > 0) expected.add(line.toString());
                line.delete(0, line.length());
            } else {
                line.append(text[i]);
                if (i == text.length - 1 && line.length() > 0) expected.add(line.toString());
            }
        }
        List<String> actual = observer.get(FileObserver.Type.LINES).collect(Collectors.toList());
        assertThat(Arrays.deepEquals(actual.toArray(), expected.toArray())).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetWithNullThenThrowIAE() {
        observer.get(null);
    }

    @Test
    public void fileObserverOfPath() {
        observer = FileObserver.of(path);
        assertThat(observer.getPath()).isEqualTo(path);
    }

    @Test
    public void fileObserverOfPathAndCharset() {
        observer = FileObserver.of(path, StandardCharsets.UTF_16LE);
        assertThat(observer.getPath()).isEqualTo(path);
        assertThat(observer.getCharset()).isEqualTo(StandardCharsets.UTF_16LE);
    }

    @Test
    public void getWorldsFromValueOf() {
        assertThat(FileObserver.Type.valueOf("WORLDS")).isSameAs(FileObserver.Type.WORLDS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenValueOfWithWrongStringThenIAE() {
        FileObserver.Type.valueOf("ddd");
    }

    @Test
    public void valuesReturn2() {
        assertThat(FileObserver.Type.values().length).isEqualTo(2);
    }

    private void write(File file, String text) throws IOException {
        Files.write(file.toPath(), text.getBytes());
    }
}