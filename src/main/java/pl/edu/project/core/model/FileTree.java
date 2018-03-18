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

    public FileTree(Path root, int depth) {
        setRoot(root);
        setDepthSearch(depth);
    }

    public String readFiles() {
        StringBuilder result = new StringBuilder();
        search(FileTree.Type.FILE).forEach((f) -> result.append(readFile(f)));
        return result.toString();
    }

    protected String readFile(Path path) {
        throwIAEWhenNull(path);
        String result = null;
        try {
            result = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            throwIAE(true, "Path not exist.\n" + e.getMessage());
        }
        return result;
    }

    public Stream<Path> search(Type type) {
        List<Path> paths = new ArrayList<>();
        getPaths(getRoot(), getDepthSearch()).forEach((f) -> {
            if (type == Type.FILE && !Files.isDirectory(f)) paths.add(f);
            if (type == Type.FOLDER && Files.isDirectory(f)) paths.add(f);
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


    protected Stream<Path> getPaths(Path root, int depth) {
        Stream<Path> result = null;
        try {
            result = Files.walk(root, depth);
        } catch (IOException e) {
            throwIAE(true, "Root path is not exist.\nOriginal stack trace" + e);
        }
        return result;
    }

    public static FileTree of(Path root, int depth) {
        return new FileTree(root, depth);
    }

    public enum Type {
        FILE, FOLDER
    }
}
