package pl.edu.uj.project.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
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
import java.util.*;
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
    public void countAllCharsInFile() {
        observer.setPath(path);
        assertThat(observer.count(FileObserver.Element.SYMBOLS)).isEqualTo(observer.read().toCharArray().length);
    }

    @Test
    public void countLinesInFile() {
        observer.setPath(path);
        int count = 1;
        char search = '\n';
        for (char symbol : text.toCharArray()) {
            if (search == symbol) ++count;
        }
        assertThat(observer.count(FileObserver.Element.LINES)).isEqualTo(count);
    }

    @Test
    public void countWorldsInFile() {
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
        assertThat(observer.count(FileObserver.Element.WORDS)).isEqualTo(count);
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
        List<String> actual = observer.get(FileObserver.Element.WORDS).collect(Collectors.toList());
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
        List<String> actual = observer.get(FileObserver.Element.LINES).collect(Collectors.toList());
        assertThat(Arrays.deepEquals(actual.toArray(), expected.toArray())).isTrue();
    }

    @Test
    public void getSymbols() {
        observer.setPath(path);
        char[] text = observer.read().toCharArray();
        int index = 0;
        for (String actual : observer.get(FileObserver.Element.SYMBOLS).collect(Collectors.toList())) {
            assertThat(actual.equals(String.valueOf(text[index++]))).isTrue();
        }
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
        assertThat(FileObserver.Element.valueOf("WORDS")).isSameAs(FileObserver.Element.WORDS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenValueOfWithWrongStringThenIAE() {
        FileObserver.Element.valueOf("ddd");
    }

    @Test
    public void valuesReturn2() {
        assertThat(FileObserver.Element.values().length).isEqualTo(3);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(FileObserver.class)
                .withPrefabValues(Charset.class, StandardCharsets.UTF_8, StandardCharsets.UTF_16)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void statisticsOfSymbols() {
        observer = FileObserver.of(path);
        Map<String, Long> expected = new TreeMap<>();
        for (char ch : observer.read().toCharArray()) {
            String symbol = String.valueOf(ch);
            if (expected.containsKey(symbol)) {
                expected.put(symbol, expected.get(symbol) + 1);
            } else {
                expected.put(symbol, 1L);
            }
        }
        Map<String, Long> actual = observer.statisticOf(FileObserver.Element.SYMBOLS);
        assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void statisticsOfWords() {
        observer = FileObserver.of(path);
        Map<String, Long> expected = new TreeMap<>();
        for (String word : observer.get(FileObserver.Element.WORDS).collect(Collectors.toList())) {
            if (expected.containsKey(word)) {
                expected.put(word, expected.get(word) + 1);
            } else {
                expected.put(word, 1L);
            }
        }
        Map<String, Long> actual = observer.statisticOf(FileObserver.Element.WORDS);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void statisticsOfLine() {
        observer = FileObserver.of(path);
        Map<String, Long> expected = new TreeMap<>();
        for (String line : observer.get(FileObserver.Element.LINES).collect(Collectors.toList())) {
            if (expected.containsKey(line)) {
                expected.put(line, expected.get(line) + 1);
            } else {
                expected.put(line, 1L);
            }
        }
        Map<String, Long> actual = observer.statisticOf(FileObserver.Element.LINES);
        assertThat(actual).isEqualTo(expected);
    }

    private void write(File file, String text) throws IOException {
        Files.write(file.toPath(), text.getBytes());
    }
}