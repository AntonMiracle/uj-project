package pl.edu.uj.project.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;
import pl.edu.uj.project.TestHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileTreeTest extends TestHelper {
    private FileTree tree;

    @Before
    public void before() {
        tree = new FileTree(testRoot, testDepth);
    }

    @Test
    public void fileTreeWithRootPathAndMaxScanDepth() {
        tree = new FileTree(testRoot, testDepth);
        assertThat(tree).isNotNull();
    }

    @Test
    public void rootPathIsFile() throws IOException {
        tree = new FileTree(testFolder.newFile().toPath(), testDepth);
        assertThat((tree.getRoot().toFile().isFile())).isTrue();
    }

    @Test
    public void rootPathIsDirectory() throws IOException {
        tree = new FileTree(testRoot, testDepth);
        assertThat(Files.isDirectory(tree.getRoot())).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPathNotExistThenThrowIAE() {
        new FileTree(Paths.get("wrong path"), testDepth);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenPathNullThenThrowIAE() {
        new FileTree(null, testDepth);
    }

    @Test
    public void whenMaxDepthZeroOrBelowThenDepthIsOne() {
        assertThat(new FileTree(testRoot, 0).getMaxScanDepth()).isEqualTo(1);
        assertThat(new FileTree(testRoot, -1).getMaxScanDepth()).isEqualTo(1);
    }

    @Test
    public void getAllElementsFromTree() throws IOException {
        List<Path> actual = tree.get(FileTree.Element.ALL).collect(Collectors.toList());
        List<Path> expected = Files.walk(testRoot, testDepth).collect(Collectors.toList());
        assertThat(actual.size() == expected.size()).isTrue();
        for (int i = 0; i < actual.size() && actual.size() == expected.size(); ++i) {
            assertThat(actual.get(i)).isEqualTo(expected.get(i));
        }
    }

    @Test
    public void getFilesElementsFromTree() throws IOException {
        List<Path> actual = tree.get(FileTree.Element.FILES).collect(Collectors.toList());
        List<Path> expected = Files.walk(testRoot, testDepth).filter(path -> !Files.isDirectory(path)).collect(Collectors.toList());
        assertThat(actual.size() == expected.size()).isTrue();
        for (int i = 0; i < actual.size() && actual.size() == expected.size(); ++i) {
            assertThat(actual.get(i)).isEqualTo(expected.get(i));
        }
    }

    @Test
    public void getDirectoriesElementsFromTree() throws IOException {
        List<Path> actual = tree.get(FileTree.Element.DIRECTORIES).collect(Collectors.toList());
        List<Path> expected = Files.walk(testRoot, testDepth).filter(path -> Files.isDirectory(path)).collect(Collectors.toList());
        assertThat(actual.size() == expected.size()).isTrue();
        for (int i = 0; i < actual.size() && actual.size() == expected.size(); ++i) {
            assertThat(actual.get(i)).isEqualTo(expected.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetWithNullElementThenThrowIAE() {
        tree.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetWithWrongElementsValueOfThenThrowIAE() {
        tree.get(FileTree.Element.valueOf("WrongName"));
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(FileTree.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }


    @Test
    public void getFileTreeElementFromValueOfString() {
        assertThat(FileTree.Element.valueOf("FILES")).isEqualTo(FileTree.Element.FILES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFileTreeElementValueOfWithWrongStringThenIAE() {
        assertThat(FileTree.Element.valueOf("null")).isEqualTo(FileTree.Element.FILES);
    }

    @Test
    public void fileTreeElementValuesReturnThree() {
        assertThat(FileTree.Element.values().length).isEqualTo(3);
    }

    @Test
    public void getStatisticAllElements() throws IOException {
        Map<String, Long> actual = tree.statistic(FileTree.Element.ALL);
        long files = Files.walk(testRoot, testDepth).filter(path -> !Files.isDirectory(path)).count();
        long directories = Files.walk(testRoot, testDepth).filter(path -> Files.isDirectory(path)).count();
        Map<String, Long> expected = new HashMap<>();
        expected.put(FileTree.Element.FILES.toString(), Long.valueOf(files));
        expected.put(FileTree.Element.DIRECTORIES.toString(), Long.valueOf(directories));
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void getStatisticDirectoriesElements() throws IOException {
        Map<String, Long> actual = tree.statistic(FileTree.Element.DIRECTORIES);
        long directories = Files.walk(testRoot, testDepth).filter(path -> Files.isDirectory(path)).count();
        Map<String, Long> expected = new HashMap<>();
        expected.put(FileTree.Element.DIRECTORIES.toString(), Long.valueOf(directories));
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void getStatisticFilesElements() throws IOException {
        Map<String, Long> actual = tree.statistic(FileTree.Element.FILES);
        long files = Files.walk(testRoot, testDepth).filter(path -> !Files.isDirectory(path)).count();
        Map<String, Long> expected = new HashMap<>();
        expected.put(FileTree.Element.FILES.toString(), Long.valueOf(files));
        assertThat(expected).isEqualTo(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetStatisticWithNullElementThenThrowIAE() {
        tree.statistic(null);
    }

}