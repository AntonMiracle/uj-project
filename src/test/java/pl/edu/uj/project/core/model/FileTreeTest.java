package pl.edu.uj.project.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pl.edu.uj.project.TestHelper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class FileTreeTest extends TestHelper {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private FileTree tree;

    @Before
    public void before() throws IOException {
        root = Paths.get(testFolder.getRoot().toString());
        generationFolderAndFilesForTesting(root.toString(), 4);
        tree = new FileTree();
    }

    @Test
    public void setAndGetDepth() {
        tree.setDepth(5);
        assertThat(tree.getDepth()).isEqualTo(5);
    }

    @Test
    public void whenDepthNotSetThenDepthOne() {
        assertThat(tree.getDepth()).isEqualTo(1);
    }

    @Test
    public void whenSetDepthWithZeroThenDepthOne() {
        tree.setDepth(0);
        assertThat(tree.getDepth()).isEqualTo(1);
    }
    @Test
    public void whenSetDepthWithNegativeThenDepthOne() {
        tree.setDepth(-1);
        assertThat(tree.getDepth()).isEqualTo(1);
    }

    @Test
    public void setAndGetRoot() {
        tree.setRoot(root);
        assertThat(tree.getRoot()).isEqualTo(root);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetRootWithNullThenThrowIAE() {
        tree.setRoot(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetRootWithNotExistPathThenThrowIAE() {
        tree.setRoot(Paths.get("wrong/path"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetRootReturnNullThenThrowIAE() {
        tree.getRoot();
    }

    @Test
    public void getPaths() {
        tree.setRoot(root);
        tree.setDepth(5);
        assertThat(tree.getPaths().count()).isEqualTo(15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetPathsWithWrongRootOrRootNullThenThrowIAE() {
        tree.getPaths();
    }

    @Test
    public void countFilesPathWithDepth5() {
        tree.setRoot(root);
        tree.setDepth(5);
        assertThat(tree.count(FileTree.Element.FILES)).isEqualTo(10L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSearchWithNullThenThrowIAE() {
        tree.search(null);
    }

    @Test
    public void searchFilesPathsWithDepth1() {
        tree.setRoot(root);
        tree.setDepth(1);
        assertThat(tree.search(FileTree.Element.FILES).count()).isEqualTo(2);
    }

    @Test
    public void searchFoldersPathsWithDepth1() {
        tree.setRoot(root);
        tree.setDepth(1);
        assertThat(tree.search(FileTree.Element.FOLDERS).count()).isEqualTo(2);
    }

    @Test
    public void searchAllPathsWithDepth1() {
        tree.setRoot(root);
        tree.setDepth(1);
        assertThat(tree.search(FileTree.Element.FILES_AND_FOLDERS).count()).isEqualTo(4);
    }

    @Test
    public void fileTreeOfRootAndDepthSearch() {
        tree = FileTree.of(root, 2);
        assertThat(tree).isNotNull();
    }

    @Test
    public void fileTreeOfRootWithDepthSearch1() {
        tree = FileTree.of(root);
        assertThat(tree.getDepth()).isEqualTo(1);
        assertThat(tree).isNotNull();
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(FileTree.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void countFolderWithDepth() {
        tree = FileTree.of(root, 3);
        assertThat(tree.count(FileTree.Element.FOLDERS)).isEqualTo(4L);
    }

    @Test
    public void countFilesWithDepth() {
        tree = FileTree.of(root, 3);
        assertThat(tree.count(FileTree.Element.FILES)).isEqualTo(6L);

    }

    @Test
    public void getFileTreeTypeFromValueOfString() {
        assertThat(FileTree.Element.valueOf("FILES")).isEqualTo(FileTree.Element.FILES);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenValueOfWithWrongStringThenIAE() {
        assertThat(FileTree.Element.valueOf("null")).isEqualTo(FileTree.Element.FILES);
    }

    @Test
    public void valuesReturn2() {
        assertThat(FileTree.Element.values().length).isEqualTo(3);
    }

    @Test
    public void getStatistics() {
        tree = FileTree.of(root, 3);
        Map<String, Long> expected = new TreeMap<>();
        expected.put(FileTree.Element.FILES.toString(), 6L);
        expected.put(FileTree.Element.FOLDERS.toString(), 4L);
        Map<String, Long> actual = tree.statistic(FileTree.Element.FILES_AND_FOLDERS);
        assertThat(expected).isEqualTo(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetStatisticWithNullThenThrowIAE() {
        tree.statistic(null);
    }
}