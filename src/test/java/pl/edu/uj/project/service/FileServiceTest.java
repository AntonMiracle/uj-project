package pl.edu.uj.project.service;

import pl.edu.uj.project.TestHelper;

public class FileServiceTest extends TestHelper {
    /*
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private int testDepth;
    private FileService service;
    private FileTree tree;
    private FileObserver observer;

    @Before
    public void before() throws IOException {
        testDepth = 3;
        testRoot = Paths.get(testFolder.getRoot().toString());
        generationFolderAndFilesForTesting(testRoot.toString(), testDepth);
        tree = FileTree.of(testRoot, testDepth);
        observer = FileObserver.of(testFolder.newFile().toPath(), StandardCharsets.UTF_8);
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
        assertThat(service.getObserver(observer.getPath(), observer.getCharset())).isEqualTo(observer);
    }

    @Test
    public void getFileObserverWithPathAndCharset() {
        Charset charset = StandardCharsets.UTF_16BE;
        observer = FileObserver.of(observer.getPath(), charset);
        assertThat(service.getObserver(observer.getPath(), charset)).isEqualTo(observer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetFileObserverWithPathNullThenThrowIAE() {
        service.getObserver(null, StandardCharsets.UTF_8);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetFileObserverWithCharsetNullNullThenThrowIAE() {
        service.getObserver(observer.getPath(), null);
    }

    @Test
    public void fileServiceOfPathAndDepth() {
        service = FileService.of(testRoot, 4);
        assertThat(service).isNotNull();
        assertThat(service.getTree().getDepth()).isEqualTo(4);
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
        service = FileService.of(testRoot, testDepth);

        assertThat(service.count(FileObserver.Element.LINES, charset)).isEqualTo(12);
    }

    @Test
    public void countAllWorldsInFileTreeWithDepth() {
        service = FileService.of(testRoot, testDepth);
        assertThat(service.count(FileObserver.Element.WORDS, charset)).isEqualTo(42);
    }

    @Test
    public void countSymbolsInFileTreeWithDepth() {
        service = FileService.of(testRoot, testDepth);
        assertThat(service.count(FileObserver.Element.SYMBOLS, charset)).isEqualTo(306);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCountFileObserverElementWithNullThenThrowIAE() {
        service.count(null, charset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCountWithNullThenThrowIAE() {
        service.count(null);
    }

    @Test
    public void countSymbolInFileTreeWithDepth() {
        service = FileService.of(testRoot, testDepth);
        assertThat(service.count('e', charset)).isEqualTo(18);
    }

    @Test
    public void countFolderInTreeWithDepth() {
        service = FileService.of(testRoot, testDepth);
        assertThat(service.count(FileTree.Element.DIRECTORIES)).isEqualTo(4);
    }

    @Test
    public void countFilesInTreeWithDepth() {
        service = FileService.of(testRoot, testDepth);
        assertThat(service.count(FileTree.Element.FILES)).isEqualTo(7);
    }

    @Test
    public void getStatistic() {
        service = FileService.of(testRoot, testDepth);
        Map<String, Long> statistic = service.statistic(charset);
        assertThat(statistic.get(FileTree.Element.FILES.toString())).isEqualTo(7);
        assertThat(statistic.get(FileTree.Element.DIRECTORIES.toString())).isEqualTo(4);
        assertThat(statistic.get(FileObserver.Element.LINES.toString())).isEqualTo(12);
        assertThat(statistic.get(FileObserver.Element.WORDS.toString())).isEqualTo(42);
    }

    @Test
    public void getStatisticOfWordsFileObserver() throws IOException {
        service = FileService.of(testRoot, testDepth);
        Map<String, Long> expected = new TreeMap<>();
        expected.put("Folder", 6L);
        expected.put("Hello", 6L);
        expected.put("This", 6L);
        expected.put("World", 6L);
        expected.put("file", 6L);
        expected.put("is", 6L);
        expected.put("txt", 6L);
        Map<String, Long> statistic = service.statistic(FileObserver.Element.WORDS, Charset.defaultCharset());
        assertThat(statistic).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetStatisticOfFileObserverWithNullThenThrowIAE() {
        service.statistic(null, null);
    }
*/
}