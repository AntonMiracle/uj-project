package pl.edu.uj.project.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;
import pl.edu.uj.project.TestHelper;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileObserverTest extends TestHelper {
    private FileObserver observer;

    @Before
    public void before() {
        observer = new FileObserver(testFile, testCharset);
    }

    @Test
    public void newFileObserverWithPathAndCharset() {
        assertThat(observer).isNotNull();
    }

    @Test
    public void newFileObserverWithDirectoryPath() {
        observer = new FileObserver(testDirectory, testCharset);
        assertThat(Files.isDirectory(observer.getPath())).isTrue();
    }

    @Test
    public void newFileObserverWithFilePath() {
        assertThat(Files.isDirectory(observer.getPath())).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNewFileObserverByNullPathThenThrowIAE() {
        new FileObserver(null, testCharset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNewFileObserverWithNotExistingPathThenThrowIAE() {
        new FileObserver(Paths.get("testWrongPath"), testCharset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNewFileObserverWithNullCharsetThenThrowIAE() {
        new FileObserver(testFile, null);
    }

    @Test
    public void getAvailableCharset() {
        Charset[] expected = new Charset[]{
                StandardCharsets.UTF_16LE,
                StandardCharsets.UTF_16,
                StandardCharsets.UTF_8,
                StandardCharsets.UTF_16BE,
                StandardCharsets.ISO_8859_1,
                StandardCharsets.US_ASCII,
        };
        Set<Charset> charsets = FileObserver.charsets();
        for (Charset charset : expected) {
            assertThat(charsets.contains(charset)).isTrue();
        }
    }

    @Test
    public void getFileTextWhenPathIsFile() {
        assertThat(observer.getText()).isEqualTo(testFileText);
    }

    @Test
    public void getFileTextWhenPathIsDirectory() {
        observer = new FileObserver(testDirectory, testCharset);
        assertThat(observer.getText()).isEqualTo("");
    }

    @Test
    public void getLines() {
        String[] expected = testFileText.split(System.lineSeparator());
        List<String> actual = observer.get(FileObserver.Element.LINES).collect(Collectors.toList());
        for (int i = 0; i < actual.size() && actual.size() == expected.length; ++i) {
            assertThat(actual.get(i)).isEqualTo(expected[i]);
        }
    }

    @Test
    public void getWords() {
        List<String> expected = Pattern.compile("\\PL+").splitAsStream(testFileText).collect(Collectors.toList());
        List<String> actual = observer.get(FileObserver.Element.WORDS).collect(Collectors.toList());
        for (int i = 0; i < actual.size() && actual.size() == expected.size(); ++i) {
            assertThat(actual.get(i)).isEqualTo(expected.get(i));
        }
    }

    @Test
    public void getSymbols() {
        List<String> expected = testFileText.chars().mapToObj(i -> String.valueOf((char) i)).collect(Collectors.toList());
        List<String> actual = observer.get(FileObserver.Element.SYMBOLS).collect(Collectors.toList());
        for (int i = 0; i < actual.size() && actual.size() == expected.size(); ++i) {
            assertThat(actual.get(i)).isEqualTo(expected.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetWithNullElementThenThrowIAE() {
        observer.get(null);
    }

    @Test
    public void getElementWorldsFromValueOf() {
        assertThat(FileObserver.Element.valueOf("WORDS")).isSameAs(FileObserver.Element.WORDS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenElementValueOfWithWrongStringThenIAE() {
        FileObserver.Element.valueOf("ddd");
    }

    @Test
    public void valuesReturnFour() {
        assertThat(FileObserver.Element.values().length).isEqualTo(4);
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
    public void getStatisticWords() {
        Map<String, Long> actual = observer.statistic(FileObserver.Element.WORDS);
        Map<String, Long> expected = new TreeMap<>();
        for (String line : testFileText.split("\\PL+")) {
            if (expected.containsKey(line)) {
                expected.put(line, expected.get(line) + 1);
            } else {
                expected.put(line, 1L);
            }
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getStatisticSymbols() {
        Map<String, Long> actual = observer.statistic(FileObserver.Element.SYMBOLS);
        Map<String, Long> expected = new TreeMap<>();
        for (char ch : testFileText.toCharArray()) {
            String symbol = String.valueOf(ch);
            if (expected.containsKey(symbol)) {
                expected.put(symbol, expected.get(symbol) + 1L);
            } else {
                expected.put(symbol, 1L);
            }
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getStatisticLines() {
        Map<String, Long> actual = observer.statistic(FileObserver.Element.LINES);
        Map<String, Long> expected = new TreeMap<>();
        for (String line : testFileText.split(System.lineSeparator())) {
            if (expected.containsKey(line)) {
                expected.put(line, expected.get(line) + 1);
            } else {
                expected.put(line, 1L);
            }
        }
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getStatisticAll() {
        Map<String, Long> actual = observer.statistic(FileObserver.Element.ALL);
        Map<String, Long> expected = new TreeMap<>();
        expected.put(FileObserver.Element.LINES.toString(), 6L);
        expected.put(FileObserver.Element.SYMBOLS.toString(), 100L);
        expected.put(FileObserver.Element.WORDS.toString(), 15L);
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetStatisticWithNullElementThenThrowIAE() {
        observer.statistic(null);
    }


}