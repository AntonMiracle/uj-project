package pl.edu.uj.project.service;

import pl.edu.uj.project.core.FileObserver;
import pl.edu.uj.project.core.FileTree;

/**
 * FileService is layer to united {@link FileTree} and {@link FileObserver}.
 * Prepare full statistic files and folders in {@link FileTree}.
 *
 * @author Anton Bondarenko. {@code b.anton.m.1986@gmail.com}
 * @version 1.0
 */
public class FileService {
//
//    private FileTree tree;
//
//    protected FileService() {
//
//    }
//
//    protected FileService(Path path, int depth) {
//        setTree(FileTree.of(path, depth));
//    }
//
//    /**
//     * Create FileService with FileTree with root path and depth.
//     *
//     * @param path  of FileTree.
//     * @param depth of FileTree.
//     * @return FileService.
//     * @throws IllegalArgumentException when {@link FileTree#of(Path, int)}
//     */
//    public static FileService of(Path path, int depth) throws IllegalArgumentException {
//        return new FileService(path, depth);
//    }
//
//    protected void setTree(FileTree tree) {
//        throwIAEWhenNull(tree);
//        this.tree = tree;
//    }
//
//    /**
//     * @return current FileTree.
//     * @throws IllegalArgumentException if current FileTree is null.
//     */
//    public FileTree getTree() throws IllegalArgumentException {
//        throwIAEWhenNull(tree);
//        return tree;
//    }
//
//    /**
//     * {@link FileObserver#of(Path, Charset)}.
//     *
//     * @param path    for FileObserver.
//     * @param charset for FileObserver.
//     * @return FileObserver.
//     * @throws IllegalArgumentException when {@link FileObserver#of(Path, Charset)}
//     */
//    public FileObserver getObserver(Path path, Charset charset) throws IllegalArgumentException {
//        return FileObserver.of(path, charset);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        FileService that = (FileService) o;
//
//        return tree != null ? tree.equals(that.tree) : that.tree == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return tree != null ? tree.hashCode() : 0;
//    }
//
//    /**
//     * Count element.
//     *
//     * @param element {@link FileObserver.Element}.
//     * @return long.
//     * @throws IllegalArgumentException when element is null.
//     */
//    public long count(FileObserver.Element element, Charset charset) throws IllegalArgumentException {
//        throwIAEWhenNull(element);
//        return count(element, (char) -100, charset);
//    }
//
//    /**
//     * Count symbol.
//     *
//     * @param symbol in file.
//     * @return long.
//     */
//    public long count(char symbol, Charset charset) {
//        return count(null, symbol, charset);
//    }
//
//    private long count(FileObserver.Element element, char symbol, Charset charset) {
//        long result = 0;
//        Long value;
//        for (Path path : getTree().search(FileTree.Element.FILES).collect(Collectors.toList())) {
//            if (element != null) {
//                Map<String, Long> statistic = getObserver(path, charset).statisticOf(element);
//                for (String key : statistic.keySet()) {
//                    value = statistic.get(key);
//                    result += value != null ? value.longValue() : 0;
//                }
//            } else {
//                value = getObserver(path, StandardCharsets.UTF_8).statisticOf(FileObserver.Element.SYMBOLS).get(String.valueOf(symbol));
//                result += value != null ? value.longValue() : 0;
//            }
//        }
//        return result;
//    }
//
//    /**
//     * @param element {@link FileTree.Element}.
//     * @return long.
//     * @throws IllegalArgumentException when element null or not exist.
//     */
//    public long count(FileTree.Element element) throws IllegalArgumentException {
//        long count = 0;
//        Map<String, Long> statistic = getTree().statistic(element);
//        for (String key : statistic.keySet()) {
//            count += statistic.get(key).longValue();
//        }
//        return count;
//    }
//
//    /**
//     * {@link FileTree#statistic(FileTree.Element)}.
//     *
//     * @return {@code Map<String,Long>}.
//     */
//    public Map<String, Long> statistic(Charset charset) {
//        Map<String, Long> statistic = new TreeMap<>();
//        statistic.put(FileTree.Element.DIRECTORIES.toString(), count(FileTree.Element.DIRECTORIES));
//        statistic.put(FileTree.Element.FILES.toString(), count(FileTree.Element.FILES));
////        statistic.put(FileObserver.Element.LINES.toString(), count(FileObserver.Element.LINES, charset));
//        statistic.put(FileObserver.Element.WORDS.toString(), count(FileObserver.Element.WORDS, charset));
//        statistic.put(FileObserver.Element.SYMBOLS.toString(), count(FileObserver.Element.SYMBOLS, charset));
//        return statistic;
//    }
//
//    /**
//     * {@link FileObserver#statisticOf(FileObserver.Element)}.
//     *
//     * @param element {@link FileObserver.Element}.
//     * @param charset for files in FileTree.
//     * @return {@code Map<String,Long>}.
//     * where String is element and long is value of element in file.
//     * @throws IllegalArgumentException when {@link FileObserver#statisticOf(FileObserver.Element)}.
//     * @throws IllegalArgumentException when {@link FileService#getObserver(Path, Charset)}.
//     */
//    public Map<String, Long> statistic(FileObserver.Element element, Charset charset) throws IllegalArgumentException {
//        Map<String, Long> result = new TreeMap<>();
//        getTree().search(FileTree.Element.FILES).forEach(path -> {
//            Map<String, Long> file = getObserver(path, charset).statisticOf(element);
//            for (String symbol : file.keySet()) {
//                if (result.containsKey(symbol)) {
//                    result.put(symbol, result.get(symbol) + file.get(symbol));
//                } else {
//                    result.put(symbol, file.get(symbol));
//                }
//            }
//        });
//        return result;
//    }
//
}
