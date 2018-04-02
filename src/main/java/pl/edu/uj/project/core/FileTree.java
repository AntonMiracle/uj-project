package pl.edu.uj.project.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static pl.edu.uj.project.core.Util.throwIAE;
import static pl.edu.uj.project.core.Util.throwIAEWhenNull;

/**
 * FileTree is consisted from files and folders that have same root path.
 * And in the max scan depth from root.
 * Count and get files, folders or statistic.
 *
 * @author Anton Bondarenko. {@code b.anton.m.1986@gmail.com}
 * @version 1.0
 */
public class FileTree {
    private Path root;
    private int maxScanDepth;

    /**
     * Create tree with root and max scn depth.
     *
     * @param root         path of FileTree.
     * @param maxScanDepth max scan depth from root, for files which will bw included to FileTree. {@code If depth < 0} then depth == 1.
     * @throws IllegalArgumentException when
     *                                  root is null, not exist, readable == false.
     */
    public FileTree(Path root, int maxScanDepth) throws IllegalArgumentException {
        setRoot(root);
        setMaxScanDepth(maxScanDepth);
    }

    private void setRoot(Path root) {
        throwIAEWhenNull(root, this, "setRoot(Path root)");
        throwIAE(Files.notExists(root), this, "setRoot(Path root)", "Path not exist: " + root.toString());
        this.root = root;
    }

    private void setMaxScanDepth(int maxScanDepth) {
        maxScanDepth = maxScanDepth > 0 ? maxScanDepth : 1;
        this.maxScanDepth = maxScanDepth;
    }

    public Path getRoot() {
        return root;
    }

    public int getMaxScanDepth() {
        return maxScanDepth;
    }

    /**
     * Get elements in File tree.
     *
     * @param element {@link Element}.
     * @return {@code Stream<Path>}.
     * @throws IllegalArgumentException when element is null or not exist.
     */
    public Stream<Path> get(Element element) throws IllegalArgumentException {
        throwIAEWhenNull(element, this, "get(Element element)");
        if (element != Element.ALL) {
            List<Path> elements = new ArrayList<>();
            getAll().forEach(f -> {
                        if (element == Element.DIRECTORIES && Files.isDirectory(f)) elements.add(f);
                        if (element == Element.FILES && !Files.isDirectory(f)) elements.add(f);
                    }
            );
            return elements.stream();
        } else {
            return getAll();
        }
    }

    private Stream<Path> getAll() {
        Stream<Path> paths = null;
        try {
            paths = Files.walk(getRoot(), getMaxScanDepth());
        } catch (IOException e) {
            throwIAE(true, this, "getPaths()", "IOException : " + e.getMessage());
        }
        return paths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileTree fileTree = (FileTree) o;

        if (maxScanDepth != fileTree.maxScanDepth) return false;
        return root != null ? root.equals(fileTree.root) : fileTree.root == null;
    }

    @Override
    public int hashCode() {
        int result = root != null ? root.hashCode() : 0;
        result = 31 * result + maxScanDepth;
        return result;
    }

    /**
     * Make statistic for element.
     *
     * @param element {@link Element}.
     * @return {@code Map<String,Long>}.
     * where String {@link Element#name()} and long is count of elements in FileTree.
     * @throws IllegalArgumentException when element null or not exist.
     */
    public Map<String, Long> statistic(Element element) throws IllegalArgumentException {
        throwIAEWhenNull(element, this, "statistic(Element element)");
        Map<String, Long> result = new TreeMap<>();
        String key = element.name();
        if (element == Element.ALL) result = statistic();
        if (element == Element.FILES) result.put(key, statistic().get(key));
        if (element == Element.DIRECTORIES) result.put(key, statistic().get(key));
        return result;
    }

    private Map<String, Long> statistic() {
        Map<String, Long> result = new TreeMap<>();
        result.put(Element.DIRECTORIES.name(), get(Element.DIRECTORIES).count());
        result.put(Element.FILES.name(), get(Element.FILES).count());
        return result;
    }

    public enum Element {
        FILES, DIRECTORIES, ALL
    }
}
