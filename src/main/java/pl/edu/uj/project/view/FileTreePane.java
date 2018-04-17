package pl.edu.uj.project.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import pl.edu.uj.project.core.FileTree;
import pl.edu.uj.project.service.FileService;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTreePane {
    private final FileService service;
    private final TreeView<String> treeView;
    private final TreeItem<String> treeItem;
    private Path pickedPath;
    private int pickedDepth;

    public FileTreePane(Path path, Integer depth, Charset charset) {
        this.service = new FileService(path, depth, charset);
        this.treeItem = initTreeItem();
        this.treeView = initTreeView();
    }

    private TreeItem<String> initTreeItem() {
        ImageView iconFile = new ImageView("image/file-icon.png");
        ImageView iconFolder = new ImageView("image/folder-icon.png");
        ImageView rootIcon = Files.isDirectory(service.getFileTree().getRoot()) ? iconFolder : iconFile;
        Path root = service.getFileTree().getRoot();
        TreeItem<String> treeItem = new TreeItem<>(root.toString(), rootIcon);
        service.getFileTree().get(FileTree.Element.ALL).forEach(el -> {
            if (!root.equals(el)) {
                Path relative = root.relativize(el);
                compileTreeItem(treeItem, relative, !Files.isDirectory(el));
            }
        });
        return treeItem;
    }

    private void compileTreeItem(TreeItem<String> root, Path path, boolean isFile) {
        ImageView iconFile = new ImageView("image/file-icon.png");
        ImageView iconFolder = new ImageView("image/folder-icon.png");
        if (path.getNameCount() > 0) {
            if (path.getNameCount() > 1) {
                boolean isExist = false;
                TreeItem<String> newRoot = new TreeItem<>(path.subpath(0, 1).toString());
                for (TreeItem<String> tmp : root.getChildren()) {
                    if (tmp.getValue().equals(newRoot.getValue())) {
                        newRoot = tmp;
                        isExist = true;
                    }
                }
                if (!isExist) {
                    root.getChildren().add(newRoot);
                }
                root.setGraphic(iconFolder);
                Path newPath = path.subpath(1, path.getNameCount());
                compileTreeItem(newRoot, newPath, isFile);
            } else {
                TreeItem<String> end = new TreeItem<>(path.subpath(0, 1).toString());
                if (isFile) {
                    end.setGraphic(iconFile);
                } else {
                    end.setGraphic(iconFolder);
                }
                root.getChildren().add(end);
            }
        }
    }

    private TreeView<String> initTreeView() {
        TreeView<String> view = new TreeView<>();
        view.setRoot(treeItem);
        view.showRootProperty();
        return view;
    }

    public void findPathAndDepth(TreeItem<String> element) throws IllegalArgumentException {
        if (element != null) {
            int newDepth = 0;
            String result = element.getValue();
            TreeItem<String> tmp = element.getParent();
            while (tmp != null) {
                newDepth++;
                result = tmp.getValue() + File.separator + result;
                tmp = tmp.getParent();
            }
            this.pickedPath = Paths.get(result);
            this.pickedDepth = newDepth;
        }
    }

    public Path getPickedPath() {
        return pickedPath;
    }

    public int getPickedDepth() {
        return pickedDepth;
    }

    public TreeView<String> getTreeView() {
        return treeView;
    }

    public Pane getPane() {
        BorderPane pane = new BorderPane(treeView);
        pane.setMinWidth(350);
        pane.setMaxWidth(350);
        return pane;
    }
}
