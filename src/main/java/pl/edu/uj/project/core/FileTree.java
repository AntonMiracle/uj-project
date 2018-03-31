package pl.edu.uj.project.core;

/**
 * FileTree is consisted from files and folders that have same root path.
 * Count and get files, folders or statistic.
 *
 * @author Anton Bondarenko. {@code b.anton.m.1986@gmail.com}
 * @version 1.0
 */
public class FileTree {
///*
//    private int depth = 1;
//    private Path root;
//
//    protected FileTree() {
//
//    }
//
//    protected FileTree(Path root, int depth) {
//        setRoot(root);
//        setDepth(depth);
//    }
//
//    /**
//     * Create FileTree by root path and depth.
//     * When {@code depth <= 0} then change {@code depth == 1};
//     *
//     * @param root  path of FileTree.
//     * @param depth of FileTree
//     * @return FileTree.
//     * @throws IllegalArgumentException when root {@code null or not exist}.
//     */
//    public static FileTree of(Path root, int depth) throws IllegalArgumentException {
//        return new FileTree(root, depth);
//    }
//
//    public Stream<Path> search(Element element) {
//        List<Path> paths = new ArrayList<>();
//        getPaths().forEach((f) -> {
//            if (element == Element.FILES && !Files.isDirectory(f)) paths.add(f);
//            if (element == Element.FOLDERS && Files.isDirectory(f)) paths.add(f);
//            if (element == Element.FILES_AND_FOLDERS) paths.add(f);
//        });
//        return paths.stream();
//    }
//
//    protected void setDepth(int depth) {
//        if (depth > 1) {
//            this.depth = depth;
//        }
//    }
//
//    protected void setRoot(Path root) {
//        throwIAEWhenNull(root);
//        throwIAE(!Files.exists(root), "Root is not exist");
//        this.root = root;
//    }
//
//    public int getDepth() {
//        return depth;
//    }
//
//    public Path getRoot() {
//        throwIAEWhenNull(root);
//        return root;
//    }
//
//    protected Stream<Path> getPaths() {
//        Stream<Path> result = null;
//        try {
//            result = Files.walk(getRoot(), getDepth());
//        } catch (IOException e) {
//            throwIAE(true, "Root path is not exist.\nOriginal stack trace" + e);
//        }
//        return result;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        FileTree fileTree = (FileTree) o;
//
//        if (depth != fileTree.depth) return false;
//        return root != null ? root.equals(fileTree.root) : fileTree.root == null;
//    }
//
//    @Override
//    public int hashCode() {
//        int result = depth;
//        result = 31 * result + (root != null ? root.hashCode() : 0);
//        return result;
//    }
//
//    /**
//     * Make statistic for element in FileTree.
//     *
//     * @param element {@link Element}.
//     * @return {@code Map<String,Long>}.
//     * where String is element and long is value of element in FileTree.
//     * @throws IllegalArgumentException when element null or not exist.
//     */
//    public Map<String, Long> statistic(Element element) throws IllegalArgumentException {
//        throwIAEWhenNull(element);
//        Map<String, Long> result = new TreeMap<>();
//        if (element == Element.FILES_AND_FOLDERS) result = statistic();
//        if (element == Element.FILES) result.put(Element.FILES.toString(), statistic().get(Element.FILES.toString()));
//        if (element == Element.FOLDERS)
//            result.put(Element.FOLDERS.toString(), statistic().get(Element.FOLDERS.toString()));
//        return result;
//    }
//
//    private Map<String, Long> statistic() {
//        Map<String, Long> result = new TreeMap<>();
//        String files = Element.FILES.toString();
//        String folders = Element.FOLDERS.toString();
//        result.put(files, 0L);
//        result.put(folders, 0L);
//        getPaths().forEach(path -> {
//            if (Files.isDirectory(path)) {
//                result.put(folders, result.get(folders) + 1L);
//            } else {
//                result.put(files, result.get(files) + 1L);
//            }
//        });
//        return result;
//    }
//
    public enum Element {
        FILES, FOLDERS, FILES_AND_FOLDERS
    }
//
}
