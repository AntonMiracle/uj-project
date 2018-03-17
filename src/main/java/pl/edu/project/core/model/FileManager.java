package pl.edu.project.core.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileManager {

    private int depthSearch;
    private Path root;

    public String readFiles() {
        StringBuilder result = new StringBuilder();
        searchFiles().forEach((f) -> result.append(readFile(f)));
        return result.toString();
    }

    protected String readFile(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException e) {

        }
        return null;
    }

    public Stream<Path> searchFiles() {
        List<Path> paths = new ArrayList<>();
        getAllPaths().forEach((f) -> {
            if (!Files.isDirectory(f)) paths.add(f);
        });
        return paths.stream();
    }
//    public int countFolder() {
//        int count = 0;
//        try {
//            Files.walk(root, depthSearch).forEach((f) -> {
//                if (!Files.isDirectory(f)) paths.add(f);
//            });
//        } catch (IOException e) {
//            throw new RuntimeException("Root path is not exist.\nOriginal stack trace" + e);
//        }
//    }
    public void setDepthSearch(int searchDeep) {
        this.depthSearch = searchDeep;
    }

    public int getDepthSearch() {
        return depthSearch;
    }

    public void setRoot(Path root) {
        this.root = root;
    }

    public Path getRoot() {
        return root;
    }



    protected Stream<Path> getAllPaths() {
        try {
            return Files.walk(root, depthSearch);
        } catch (IOException e) {
            throw new RuntimeException("Root path is not exist.\nOriginal stack trace" + e);
        }
    }
}
