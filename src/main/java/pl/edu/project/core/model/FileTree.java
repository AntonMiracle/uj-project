package pl.edu.project.core.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static pl.edu.project.core.model.Util.throwIAE;
import static pl.edu.project.core.model.Util.throwIAEWhenNull;

public class FileTree {

    private int depthSearch;
    private Path root;

    protected FileTree() {

    }

    protected FileTree(Path root, int depth) {
        setRoot(root);
        setDepthSearch(depth);
    }

    public Stream<Path> search(Type type) {
        List<Path> paths = new ArrayList<>();
        getPaths().forEach((f) -> {
            if (type == Type.FILES && !Files.isDirectory(f)) paths.add(f);
            if (type == Type.FOLDERS && Files.isDirectory(f)) paths.add(f);
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

    public enum Type {
        FILES, FOLDERS
    }
}
