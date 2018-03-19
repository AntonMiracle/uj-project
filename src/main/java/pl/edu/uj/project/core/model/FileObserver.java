package pl.edu.uj.project.core.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class FileObserver {
    private Path path;
    private Charset charset;

    protected FileObserver() {

    }

    protected FileObserver(Path path) {
        setPath(path);
    }

    protected FileObserver(Path path, Charset charset) {
        setPath(path);
        setCharset(charset);
    }

    protected String read() {
        try {
            return new String(Files.readAllBytes(getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setPath(Path path) {
        Util.throwIAEWhenNull(path);
        Util.throwIAE(!Files.exists(path), "Path is not exist");
        Util.throwIAE(Files.isDirectory(path), "Path is folder");
        Util.throwIAE(!Files.isReadable(path), "File not readable with path:" + path.toString());
        this.path = path;
    }

    public Path getPath() {
        Util.throwIAEWhenNull(path);
        return path;
    }

    public static FileObserver of(Path path) {
        return new FileObserver(path);
    }

    public static FileObserver of(Path path, Charset charset) {
        return new FileObserver(path, charset);
    }

    public long count(char search) {
        Stream<Character> symbols = read().chars().mapToObj(i -> (char) i);
        return symbols.parallel().filter(c -> c == search).count();
    }

    public long count(Element element) {
        Util.throwIAEWhenNull(element);
        long count = 0;
        if (element == Element.LINES) count = get(Element.LINES).count();
        if (element == Element.WORDS) count = get(Element.WORDS).count();
        if (element == Element.SYMBOLS) count = get(Element.SYMBOLS).count();
        return count;
    }

    protected void setCharset(Charset charset) {
        Util.throwIAEWhenNull(charset);
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset != null ? charset : Charset.defaultCharset();
    }

    public Stream<String> get(Element element) {
        Util.throwIAEWhenNull(element);
        Stream<String> result = null;
        try {
            if (element == Element.WORDS) result = Pattern.compile("\\PL+").splitAsStream(read());
            if (element == Element.LINES) result = Files.lines(getPath(), getCharset());
            if (element == Element.SYMBOLS) result = read().chars().mapToObj(i -> String.valueOf((char) i));
        } catch (IOException e) {
            Util.throwIAE(true, e.getMessage());
        }
        return result;
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

    public Map<String, Long> statisticOf(Element element) {
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
