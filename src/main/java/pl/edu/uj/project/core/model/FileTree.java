package pl.edu.uj.project.core.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import static pl.edu.uj.project.core.model.Util.throwIAE;
import static pl.edu.uj.project.core.model.Util.throwIAEWhenNull;

public class FileTree {

    private int depthSearch;
    private Path root;

    protected FileTree() {

    }

    protected FileTree(Path root, int depth) {
        setRoot(root);
        setDepthSearch(depth);
    }

    public FileTree(Path root) {
        setRoot(root);
        setDepthSearch(1);
    }

    public Stream<Path> search(Element element) {
        List<Path> paths = new ArrayList<>();
        getPaths().forEach((f) -> {
            if (element == Element.FILES && !Files.isDirectory(f)) paths.add(f);
            if (element == Element.FOLDERS && Files.isDirectory(f)) paths.add(f);
            if (element == Element.FILES_AND_FOLDERS) paths.add(f);
        });
        return paths.stream();
    }

    protected void setDepthSearch(int searchDeep) {
        this.depthSearch = searchDeep;
    }

    public int getDepthSearch() {
        return depthSearch == 0 ? 1 : depthSearch;
    }

    protected void setRoot(Path root) {
        throwIAEWhenNull(root);
        throwIAE(!Files.exists(root), "Root is not exist");
        this.root = root;
    }

    public Path getRoot() {
        throwIAEWhenNull(root);
        return root;
    }

    protected Stream<Path> getPaths() {
        Stream<Path> result = null;
        try {
            result = Files.walk(getRoot(), getDepthSearch());
        } catch (IOException e) {
            throwIAE(true, "Root path is not exist.\nOriginal stack trace" + e);
        }
        return result;
    }

    public static FileTree of(Path root, int depth) {
        return new FileTree(root, depth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileTree fileTree = (FileTree) o;

        if (depthSearch != fileTree.depthSearch) return false;
        return root != null ? root.equals(fileTree.root) : fileTree.root == null;
    }

    @Override
    public int hashCode() {
        int result = depthSearch;
        result = 31 * result + (root != null ? root.hashCode() : 0);
        return result;
    }

    public static FileTree of(Path root) {
        return new FileTree(root);
    }

    public long count(Element element) {
        return search(element).count();
    }

    private Map<String, Long> statistic() {
        Map<String, Long> result = new TreeMap<>();
        String files = Element.FILES.toString();
        String folders = Element.FOLDERS.toString();
        result.put(files, 0L);
        result.put(folders, 0L);
        getPaths().forEach(path -> {
            if (Files.isDirectory(path)) {
                result.put(folders, result.get(folders) + 1L);
            } else {
                result.put(files, result.get(files) + 1L);
            }
        });
        return result;
    }

    public Map<String, Long> statistic(Element element) {
        Map<String, Long> result = new TreeMap<>();
        if (element == Element.FILES_AND_FOLDERS) result = statistic();
        if (element == Element.FILES) result.put(Element.FILES.toString(), statistic().get(Element.FILES));
        if (element == Element.FOLDERS) result.put(Element.FOLDERS.toString(), statistic().get(Element.FOLDERS));
        return result;
    }

    public enum Element {
        FILES, FOLDERS, FILES_AND_FOLDERS
    }
}
