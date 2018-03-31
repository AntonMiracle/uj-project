package pl.edu.uj.project.core;

import pl.edu.uj.project.TestHelper;

public class FileTreeTest extends TestHelper {
    /*
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private FileTree tree;

    @Before
    public void before() throws IOException {
        testRoot = Paths.get(testFolder.getRoot().toString());
        generationFolderAndFilesForTesting(testRoot.toString(), 4);
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
        tree.setRoot(testRoot);
        assertThat(tree.getRoot()).isEqualTo(testRoot);
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
        tree.setRoot(testRoot);
        tree.setDepth(5);
        assertThat(tree.getPaths().count()).isEqualTo(15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetPathsWithWrongRootOrRootNullThenThrowIAE() {
        tree.getPaths();
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSearchWithNullThenThrowIAE() {
        tree.search(null);
    }

    @Test
    public void searchFilesPathsWithDepth1() {
        tree.setRoot(testRoot);
        tree.setDepth(1);
        assertThat(tree.search(FileTree.Element.FILES).count()).isEqualTo(2);
    }

    @Test
    public void searchFoldersPathsWithDepth1() {
        tree.setRoot(testRoot);
        tree.setDepth(1);
        assertThat(tree.search(FileTree.Element.FOLDERS).count()).isEqualTo(2);
    }

    @Test
    public void searchAllPathsWithDepth1() {
        tree.setRoot(testRoot);
        tree.setDepth(1);
        assertThat(tree.search(FileTree.Element.FILES_AND_FOLDERS).count()).isEqualTo(4);
    }

    @Test
    public void fileTreeOfRootAndDepthSearch() {
        tree = FileTree.of(testRoot, 2);
        assertThat(tree).isNotNull();
    }

    @Test
    public void fileTreeOfRootWithDepthSearch1() {
        tree = FileTree.of(testRoot,1);
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
    public void countFilesWithDepth() {
        tree = FileTree.of(testRoot, 3);
        assertThat(tree.statistic(FileTree.Element.FILES).get(FileTree.Element.FILES.toString())).isEqualTo(6L);

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
        tree = FileTree.of(testRoot, 3);
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
    */
}