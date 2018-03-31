package pl.edu.uj.project.view;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.nio.file.Path;

public class FileTreeView {
    private TreeView<String> viewTree;

    public FileTreeView(Path root, int depth) {
        /*
        ImageView icon = new ImageView("image/file-icon.png");
        if (Files.isDirectory(root)) {
            icon = new ImageView("image/folder-icon.png");
        }
        TreeItem<String> treeItem = new TreeItem<>(root.toString(), icon);
        FileTree.of(root, depth).search(FileTree.Element.FILES).forEach(el -> {
            Path relative = root.relativize(el);
            createFileTree(treeItem, relative, !Files.isDirectory(el));
        });
        viewTree = new TreeView<>(treeItem);
        viewTree.setShowRoot(true);
        viewTree.setId("general-tree-view");
        viewTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            statisticLabel.setText(FileObserver.of(Paths.get(findPath(newValue))).statisticOf(FileObserver.Element.WORDS).toString());
//          System.out.println("PATH: "+findPath(newValue));
        });
        */
    }

    public TreeView<String> getViewTree() {
        return viewTree;
    }

    private void createFileTree(TreeItem<String> root, Path path, boolean isFile) {
        /*
        ImageView folderIcon = new ImageView("image/folder-icon.png");
        ImageView fileIcon = new ImageView("image/file-icon.png");
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
                root.setGraphic(folderIcon);
                Path newPath = path.subpath(1, path.getNameCount());
                createFileTree(newRoot, newPath, isFile);
            } else {
                root.setGraphic(folderIcon);
                TreeItem<String> end = new TreeItem<>(path.subpath(0, 1).toString());
                if (isFile) {
                    end.setGraphic(fileIcon);
                } else {
                    end.setGraphic(folderIcon);
                }
                root.getChildren().add(end);
            }
        }
        */
    }
}
