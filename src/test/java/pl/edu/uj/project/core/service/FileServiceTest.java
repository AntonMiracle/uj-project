package pl.edu.uj.project.core.service;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pl.edu.uj.project.core.TestHelper;
import pl.edu.uj.project.core.model.FileObserver;
import pl.edu.uj.project.core.model.FileTree;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileServiceTest extends TestHelper {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private int depth;
    private FileService service;
    private FileTree tree;
    private FileObserver observer;

    @Before
    public void before() throws IOException {
        depth = 3;
        root = Paths.get(testFolder.getRoot().toString());
        generationFolderAndFilesForTesting(root.toString(), depth);
        tree = FileTree.of(root, depth);
        observer = FileObserver.of(testFolder.newFile().toPath());
        service = new FileService();
    }

    @Test
    public void setAndGetFileTree() {
        service.setTree(tree);
        assertThat(service.getTree()).isEqualTo(tree);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSetFileTreeWithNullThenThrowIAE() {
        service.setTree(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetFileTreeReturnNullThenThrowIAE() {
        service.getTree();
    }

    @Test
    public void getFileObserverWithPath() {
        assertThat(service.getObserver(observer.getPath())).isEqualTo(observer);
    }

    @Test
    public void getFileObserverWithPathAndCharset() {
        Charset charset = StandardCharsets.UTF_16BE;
        observer = FileObserver.of(observer.getPath(), charset);
        assertThat(service.getObserver(observer.getPath(), charset)).isEqualTo(observer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetFileObserverWithNullThenThrowIAE() {
        service.getObserver(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetFileObserverWithNullNullThenThrowIAE() {
        service.getObserver(null, null);
    }

    @Test
    public void fileServiceOfPath() {
        service = FileService.of(root);
        assertThat(service).isNotNull();
        assertThat(service.getTree().getDepthSearch()).isEqualTo(1);
    }

    @Test
    public void fileServiceOfPathAndDepth() {
        service = FileService.of(root, 4);
        assertThat(service).isNotNull();
        assertThat(service.getTree().getDepthSearch()).isEqualTo(4);
    }

    @Test
    public void checkEqualsAndHashCode() {
        EqualsVerifier.forClass(FileService.class)
                .usingGetClass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void countAllLinesInFileTreeWithDepth() {
        service = FileService.of(root, depth);
        assertThat(service.count(FileObserver.Element.LINES)).isEqualTo(12);
    }

    @Test
    public void countAllWorldsInFilesTreeWithDepth() {
        service = FileService.of(root, depth);
        assertThat(service.count(FileObserver.Element.WORDS)).isEqualTo(42);
    }

    @Test
    public void countSymbolInFilesTreeWithDepth() {
        service = FileService.of(root, depth);
        assertThat(service.count('e')).isEqualTo(18);
    }

    @Test
    public void countFolderInTreeWithDepth() {
        service = FileService.of(root, depth);
        assertThat(service.count(FileTree.Element.FOLDERS)).isEqualTo(4);
    }
    @Test
    public void countFilesInTreeWithDepth() {
        service = FileService.of(root, depth);
        assertThat(service.count(FileTree.Element.FILES)).isEqualTo(7);
    }

    @Test
    public void getStatisticsOfFileTree() {
        service = FileService.of(root, depth);
        Map<String, Long> statistic = service.statistic(FileTree.Element.FILES_AND_FOLDERS);
        assertThat(statistic.get(FileTree.Element.FILES.toString())).isEqualTo(7);
        assertThat(statistic.get(FileTree.Element.FOLDERS.toString())).isEqualTo(4);
    }
    // statistic of lines words symbols
}