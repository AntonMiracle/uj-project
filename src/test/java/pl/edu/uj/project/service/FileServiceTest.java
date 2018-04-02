package pl.edu.uj.project.service;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;
import pl.edu.uj.project.TestHelper;
import pl.edu.uj.project.core.FileObserver;
import pl.edu.uj.project.core.FileTree;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileServiceTest extends TestHelper {
    private FileService service;

    @Before
    public void before() {
        service = new FileService(testRoot, testDepth, testCharset);
    }

    @Test
    public void getNewFileServiceWithPathAndMaxScanDepth() {
        service = new FileService(testRoot, testDepth, testCharset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetNewFileServiceWithNullPathThenThrowIAE() {
        new FileService(null, testDepth, testCharset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetNewFileServiceWithNullCharsetThenThrowIAE() {
        new FileService(testRoot, testDepth, null);
    }

    @Test
    public void whenGetNewFileServiceWithMaxScanDepthZeroOrBelowThenMaxScanDepthIsOne() {
        service = new FileService(testRoot, 0, testCharset);
        assertThat(service.getFileTree().getMaxScanDepth()).isEqualTo(1);
        service = new FileService(testRoot, -1, testCharset);
        assertThat(service.getFileTree().getMaxScanDepth()).isEqualTo(1);
    }

    @Test
    public void getFileObserverWithPath() throws IOException {
        File file = testFolder.newFile();
        service = new FileService(testRoot, 0, testCharset);
        FileObserver observer = service.getFileObserver(file.toPath());
        assertThat(observer.getPath()).isEqualTo(file.toPath());
        assertThat(observer.getCharset()).isEqualTo(testCharset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetFileObserverWithNullPathThenThrowIAE() {
        service.getFileObserver(null);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(FileService.class)
                .usingGetClass()
                .withPrefabValues(Charset.class, StandardCharsets.UTF_8, StandardCharsets.UTF_16)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void getStatisticAll() {
        service = new FileService(testRoot, 0, testCharset);
        Map<String, Long> actual = service.statistic();
        Map<String, Long> expected = new TreeMap<>();
        String key = FileTree.Element.FILES.name();
        Long value = service.getFileTree().get(FileTree.Element.FILES).count();
        expected.put(key, value);
        key = FileTree.Element.DIRECTORIES.name();
        value = service.getFileTree().get(FileTree.Element.DIRECTORIES).count();
        expected.put(key, value);
        service.getFileTree().get(FileTree.Element.FILES).forEach(path -> {
            Map<String, Long> result = new TreeMap<>();
            result.put(FileObserver.Element.LINES.name(), service.getFileObserver(path).get(FileObserver.Element.LINES).count());
            result.put(FileObserver.Element.WORDS.name(), service.getFileObserver(path).get(FileObserver.Element.WORDS).count());
            result.put(FileObserver.Element.SYMBOLS.name(), service.getFileObserver(path).get(FileObserver.Element.SYMBOLS).count());
            sumMaps(expected, result);
        });
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getStatisticLines() {
        service = new FileService(testRoot, 1, testCharset);
        Map<String, Long> actual = service.statistic(FileObserver.Element.LINES);
        Map<String, Long> expected = new TreeMap<>();
        service.getFileTree().get(FileTree.Element.FILES).forEach(path -> {
            Map<String, Long> result = service.getFileObserver(path).statistic(FileObserver.Element.LINES);
            sumMaps(expected, result);
        });
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getStatisticWords() {
        service = new FileService(testRoot, 1, testCharset);
        Map<String, Long> actual = service.statistic(FileObserver.Element.WORDS);
        Map<String, Long> expected = new TreeMap<>();
        service.getFileTree().get(FileTree.Element.FILES).forEach(path -> {
            Map<String, Long> result = service.getFileObserver(path).statistic(FileObserver.Element.WORDS);
            sumMaps(expected, result);
        });
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void getStatisticSymbols() {
        service = new FileService(testRoot, 1, testCharset);
        Map<String, Long> actual = service.statistic(FileObserver.Element.SYMBOLS);
        Map<String, Long> expected = new TreeMap<>();
        service.getFileTree().get(FileTree.Element.FILES).forEach(path -> {
            Map<String, Long> result = service.getFileObserver(path).statistic(FileObserver.Element.SYMBOLS);
            sumMaps(expected, result);
        });
        assertThat(actual.keySet().size()).isEqualTo(expected.keySet().size());
        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetStatisticWithNullElementsThenThrowIAE() {
        service.statistic(null);
    }

    private void sumMaps(Map<String, Long> sum, Map<String, Long> needToSum) {
        for (String resultKey : needToSum.keySet()) {
            if (sum.containsKey(resultKey)) {
                Long newValue = sum.get(resultKey) + needToSum.get(resultKey);
                sum.put(resultKey, newValue);
            } else {
                sum.put(resultKey, needToSum.get(resultKey));
            }
        }
    }
}