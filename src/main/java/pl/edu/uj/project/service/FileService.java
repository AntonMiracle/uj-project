package pl.edu.uj.project.service;

import pl.edu.uj.project.core.model.FileObserver;
import pl.edu.uj.project.core.model.FileTree;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static pl.edu.uj.project.core.model.Util.throwIAEWhenNull;

/**
 * FileService is layer to united {@link FileTree} and {@link FileObserver}.
 * Prepare full statistic files and folders in {@link FileTree}.
 *
 * @author Anton Bondarenko. {@code b.anton.m.1986@gmail.com}
 * @version 1.0
 */
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

    /**
     * Create FileService with FileTree with root path.
     *
     * @param root of FileTree.
     * @return FIleService.
     * @throws IllegalArgumentException when {@link FileTree#of(Path)}
     */
    public static FileService of(Path root) throws IllegalArgumentException {
        return new FileService(root);
    }

    /**
     * Create FileService with FileTree with root path and depth.
     *
     * @param path  of FileTree.
     * @param depth of FileTree.
     * @return FileService.
     * @throws IllegalArgumentException when {@link FileTree#of(Path, int)}
     */
    public static FileService of(Path path, int depth) throws IllegalArgumentException {
        return new FileService(path, depth);
    }

    protected void setTree(FileTree tree) {
        throwIAEWhenNull(tree);
        this.tree = tree;
    }

    /**
     * @return current FileTree.
     * @throws IllegalArgumentException if current FileTree is null.
     */
    public FileTree getTree() throws IllegalArgumentException {
        throwIAEWhenNull(tree);
        return tree;
    }

    /**
     * {@link FileObserver#of(Path)}.
     *
     * @param path for FileObserver.
     * @return FileObserver.
     * @throws IllegalArgumentException when {@link FileObserver#of(Path)}
     */
    public FileObserver getObserver(Path path) throws IllegalArgumentException {
        return FileObserver.of(path);
    }

    /**
     * {@link FileObserver#of(Path, Charset)}.
     *
     * @param path    for FileObserver.
     * @param charset for FileObserver.
     * @return FileObserver.
     * @throws IllegalArgumentException when {@link FileObserver#of(Path, Charset)}
     */
    public FileObserver getObserver(Path path, Charset charset) throws IllegalArgumentException {
        return FileObserver.of(getObserver(path).getPath(), charset);
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

    /**
     * {@link FileObserver#count(FileObserver.Element)}.
     *
     * @param element {@link FileObserver.Element}.
     * @return long.
     * @throws IllegalArgumentException when {@link FileObserver#count(FileObserver.Element)}.
     */
    public long count(FileObserver.Element element) throws IllegalArgumentException {
        throwIAEWhenNull(element);
        return count(element, (char) -100);
    }

    /**
     * {@link FileObserver#count(char)}.
     *
     * @param symbol wich count in file.
     * @return long.
     */
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

    /**
     * @param element {@link FileTree.Element}.
     * @return long.
     * @throws IllegalArgumentException when element null or not exist.
     */
    public long count(FileTree.Element element) throws IllegalArgumentException {
        return getTree().count(element);
    }

    /**
     * {@link FileTree#statistic(FileTree.Element)}.
     *
     * @param element {@link FileTree.Element}.
     * @return {@code Map<String,Long>}.
     * @throws IllegalArgumentException when {@link FileTree#statistic(FileTree.Element)}.
     */
    public Map<String, Long> statistic(FileTree.Element element) throws IllegalArgumentException {
        return getTree().statistic(element);
    }

    /**
     * {@link FileObserver#statisticOf(FileObserver.Element)}.
     *
     * @param element {@link FileObserver.Element}.
     * @param charset for files in FileTree.
     * @return {@code Map<String,Long>}.
     * where String is element and long is value of element in file.
     * @throws IllegalArgumentException when {@link FileObserver#statisticOf(FileObserver.Element)}.
     * @throws IllegalArgumentException when {@link FileService#getObserver(Path, Charset)}.
     */
    public Map<String, Long> statistic(FileObserver.Element element, Charset charset) throws IllegalArgumentException {
        Map<String, Long> result = new TreeMap<>();
        getTree().search(FileTree.Element.FILES).forEach(path -> {
            Map<String, Long> file = getObserver(path, charset).statisticOf(element);
            for (String symbol : file.keySet()) {
                if (result.containsKey(symbol)) {
                    result.put(symbol, result.get(symbol) + file.get(symbol));
                } else {
                    result.put(symbol, file.get(symbol));
                }
            }
        });
        return result;
    }
}
