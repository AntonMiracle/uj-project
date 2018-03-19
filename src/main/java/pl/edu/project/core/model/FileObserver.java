package pl.edu.project.core.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static pl.edu.project.core.model.Util.throwIAE;
import static pl.edu.project.core.model.Util.throwIAEWhenNull;

public class FileObserver {
    private static FileObserver instance;
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

    public long count(Type type) {
        throwIAEWhenNull(type);
        long count = 0;
        if (type == Type.LINES) count = get(Type.LINES).count();
        if (type == Type.WORLDS) count = get(Type.WORLDS).count();
        return count;
    }

    protected void setCharset(Charset charset) {
        throwIAEWhenNull(charset);
        this.charset = charset;
    }

    public Charset getCharset() {
        return charset != null ? charset : Charset.defaultCharset();
    }

    public Stream<String> get(Type type) {
        throwIAEWhenNull(type);
        Stream<String> result = null;
        try {
            if (type == Type.WORLDS) result = Pattern.compile("\\PL+").splitAsStream(read());
            if (type == Type.LINES) result = Files.lines(getPath(), getCharset());
        } catch (IOException e) {
            throwIAE(true, e.getMessage());
        }
        return result;
    }

    public enum Type {
        WORLDS, LINES
    }

}
