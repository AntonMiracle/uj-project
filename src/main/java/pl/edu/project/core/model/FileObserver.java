package pl.edu.project.core.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static pl.edu.project.core.model.Util.throwIAE;
import static pl.edu.project.core.model.Util.throwIAEWhenNull;

public class FileObserver {
    private static FileObserver instance;
    private Path path;

    protected FileObserver() {

    }

    protected FileObserver(Path path) {
        setPath(path);
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
        this.path = path;
    }

    public Path getPath() {
        throwIAEWhenNull(path);
        return path;
    }

    public static FileObserver of(Path path) {
        return new FileObserver(path);
    }

    public long count(char search) {
        Stream<Character> symbols = read().chars().mapToObj(i -> (char) i);
        return symbols.parallel().filter(c -> c == search).count();
    }


}
