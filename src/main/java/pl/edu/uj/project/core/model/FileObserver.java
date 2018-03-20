package pl.edu.uj.project.core.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static pl.edu.uj.project.core.model.Util.throwIAE;
import static pl.edu.uj.project.core.model.Util.throwIAEWhenNull;

/**
 * FileObserver is for observe file inside.
 * Count and get symbols,words,lines or statistic.
 *
 * @author Anton Bondarenko. {@code b.anton.m.1986@gmail.com}
 * @version 1.0
 */
public class FileObserver {
    private Path path;
    private Charset charset = Charset.defaultCharset();

    protected FileObserver() {

    }

    protected FileObserver(Path path) {
        setPath(path);
    }

    protected FileObserver(Path path, Charset charset) {
        setPath(path);
        setCharset(charset);
    }

    /**
     * Create FileObserver by path of file.
     * Use default charset for file.
     *
     * @param path of file wich need to observe.
     * @return FileObserver.
     * @throws IllegalArgumentException when path {@code null, not file, not exist or not readable}.
     */
    public static FileObserver of(Path path) throws IllegalArgumentException {
        return new FileObserver(path);
    }

    /**
     * Create FileObserver by path of file and charset of file.
     *
     * @param path    of file wich need to observe.
     * @param charset of file.
     * @return FileObserver.
     * @throws IllegalArgumentException when path {@code null, not file, not exist or not readable}.
     * @throws IllegalArgumentException when charset {@code null}.
     */
    public static FileObserver of(Path path, Charset charset) throws IllegalArgumentException {
        return new FileObserver(path, charset);
    }

    protected void setCharset(Charset charset) {
        throwIAEWhenNull(charset);
        this.charset = charset;
    }

    protected void setPath(Path path) {
        throwIAEWhenNull(path);
        throwIAE(!Files.exists(path), "Path is not exist");
        throwIAE(Files.isDirectory(path), "Path is folder");
        throwIAE(!Files.isReadable(path), "File not readable with path:" + path.toString());
        this.path = path;
    }

    public Path getPath() {
        throwIAEWhenNull(path);
        return path;
    }

    public Charset getCharset() {
        return charset;
    }

    public long count(char search) {
        Stream<Character> symbols = read().chars().mapToObj(i -> (char) i);
        return symbols.parallel().filter(c -> c == search).count();
    }

    /**
     * Count elements in file.
     *
     * @param element {@link Element}.
     * @return long.
     * @throws IllegalArgumentException when element null or not exist.
     */
    public long count(Element element) throws IllegalArgumentException {
        throwIAEWhenNull(element);
        long count = 0;
        if (element == Element.LINES) count = get(Element.LINES).count();
        if (element == Element.WORDS) count = get(Element.WORDS).count();
        if (element == Element.SYMBOLS) count = get(Element.SYMBOLS).count();
        return count;
    }

    protected Stream<String> get(Element element) {
        throwIAEWhenNull(element);
        Stream<String> result = null;
        try {
            if (element == Element.WORDS) result = Pattern.compile("\\PL+").splitAsStream(read());
            if (element == Element.LINES) result = Files.lines(getPath(), getCharset());
            if (element == Element.SYMBOLS) result = read().chars().mapToObj(i -> String.valueOf((char) i));
        } catch (IOException e) {
            throwIAE(true, e.getMessage());
        }
        return result;
    }

    protected String read() {
        try {
            return new String(Files.readAllBytes(getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileObserver that = (FileObserver) o;

        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        return charset != null ? charset.equals(that.charset) : that.charset == null;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (charset != null ? charset.hashCode() : 0);
        return result;
    }

    /**
     * Make statistic for element in file.
     *
     * @param element {@link Element}.
     * @return {@code Map<String,Long>}.
     * where String is element and long is value of element in file.
     * @throws IllegalArgumentException when element null is not exist.
     */
    public Map<String, Long> statisticOf(Element element) throws IllegalArgumentException {
        Map<String, Long> result = new TreeMap<>();
        get(element).forEach(string -> {
            if (result.containsKey(string)) {
                result.put(string, result.get(string) + 1);
            } else {
                result.put(string, 1L);
            }
        });
        return result;
    }

    public enum Element {
        WORDS, LINES, SYMBOLS
    }
}
