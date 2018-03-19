package pl.edu.uj.project.core.service;

import pl.edu.uj.project.core.model.FileObserver;
import pl.edu.uj.project.core.model.FileTree;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static pl.edu.uj.project.core.model.Util.throwIAEWhenNull;

public class FileService {
    private FileTree tree;

    protected FileService() {

    }

    protected FileService(Path root) {
        setTree(FileTree.of(root));
    }

    protected FileService(Path path, int depth) {
        setTree(FileTree.of(path, depth));
    }

    protected void setTree(FileTree tree) {
        throwIAEWhenNull(tree);
        this.tree = tree;
    }

    public FileTree getTree() {
        throwIAEWhenNull(tree);
        return tree;
    }

    public FileObserver getObserver(Path path) {
        return FileObserver.of(path);
    }

    public FileObserver getObserver(Path path, Charset charset) {
        return FileObserver.of(getObserver(path).getPath(), charset);
    }

    public static FileService of(Path root) {
        return new FileService(root);
    }

    public static FileService of(Path path, int depth) {
        return new FileService(path, depth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileService that = (FileService) o;

        return tree != null ? tree.equals(that.tree) : that.tree == null;
    }

    @Override
    public int hashCode() {
        return tree != null ? tree.hashCode() : 0;
    }

    public long count(FileObserver.Element element) {
        return count(element, (char) -100);
    }

    public long count(char symbol) {
        return count(null, symbol);
    }

    private long count(FileObserver.Element element, char symbol) {
        long result = 0;
        for (Path path : getTree().search(FileTree.Element.FILES).collect(Collectors.toList())) {
            if (element != null) {
                result += getObserver(path).count(element);
            } else {
                result += getObserver(path).count(symbol);
            }

        }
        return result;
    }

    public long count(FileTree.Element element) {
        return getTree().count(element);
    }

    public Map<String, Long> statistic(FileTree.Element element) {
        return getTree().statistic(element);
    }
}
