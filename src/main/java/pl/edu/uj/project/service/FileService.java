package pl.edu.uj.project.service;

import pl.edu.uj.project.core.FileObserver;
import pl.edu.uj.project.core.FileTree;
import pl.edu.uj.project.core.Util;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

/**
 * FileService is layer to united {@link FileTree} and {@link FileObserver}.
 * Prepare full statistic of files and folders in {@link FileTree}.
 *
 * @author Anton Bondarenko. {@code b.anton.m.1986@gmail.com}
 * @version 1.0
 */
public class FileService {
    private FileTree fileTree;
    private Charset charset;

    public FileService(Path root, int maxScanDepth, Charset charset) {
        setFileTree(new FileTree(root, maxScanDepth));
        setCharset(charset);
    }

    private void setFileTree(FileTree fileTree) {
        this.fileTree = fileTree;
    }

    private void setCharset(Charset charset) {
        Util.throwIAEWhenNull(charset, this, "setCharset(Charset charset)");
        this.charset = charset;
    }

    public FileTree getFileTree() {
        return fileTree;
    }

    public FileObserver getFileObserver(Path path) {
        return new FileObserver(path, getCharset());
    }

    private Charset getCharset() {
        return charset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileService that = (FileService) o;

        if (fileTree != null ? !fileTree.equals(that.fileTree) : that.fileTree != null) return false;
        return charset != null ? charset.equals(that.charset) : that.charset == null;
    }

    @Override
    public int hashCode() {
        int result = fileTree != null ? fileTree.hashCode() : 0;
        result = 31 * result + (charset != null ? charset.hashCode() : 0);
        return result;
    }

    /**
     * Create statistic which count of all elements {@link FileObserver.Element} and {@link FileTree.Element}.
     *
     * @return {@code Map<String,Long>} where String is element name and long is count of elements in file tree.
     */
    public Map<String, Long> statistic() {
        Map<String, Long> statistic = getFileTree().statistic(FileTree.Element.ALL);
        getFileTree().get(FileTree.Element.ALL).forEach(path ->
                sumMaps(statistic, getFileObserver(path).statistic(FileObserver.Element.ALL))
        );
        return statistic;
    }

    /**
     * Create statistic which count types of elements {@link FileObserver.Element}.
     *
     * @param element {@link FileObserver.Element}.
     * @return {@code Map<String, Long>} where string is representation of elements and long is count of all current representation element.
     * @throws IllegalArgumentException when element is null or not exist.
     */
    public Map<String, Long> statistic(FileObserver.Element element) throws IllegalArgumentException {
        Map<String, Long> statistic = new TreeMap<>();
        getFileTree().get(FileTree.Element.FILES).forEach(path ->
                sumMaps(statistic, getFileObserver(path).statistic(element))
        );
        return statistic;
    }

    private void sumMaps(Map<String, Long> result, Map<String, Long> toSum) {
        for (String key : toSum.keySet()) {
            if (result.containsKey(key)) {
                long value = result.get(key).longValue() + toSum.get(key).longValue();
                result.put(key, Long.valueOf(value));
            } else {
                result.put(key, toSum.get(key));
            }
        }
    }

}
