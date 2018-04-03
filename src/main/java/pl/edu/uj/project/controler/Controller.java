package pl.edu.uj.project.controler;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.uj.project.core.FileObserver;
import pl.edu.uj.project.core.FileTree;
import pl.edu.uj.project.service.FileService;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static pl.edu.uj.project.core.Util.throwIAE;
import static pl.edu.uj.project.core.Util.throwIAEWhenNull;

public class Controller {
    private TextField pathTextField;
    private String pickedPath;
    private Label pathLabel;
    private Button get;
    private Label depthLabel;
    private int pickedDepth;
    private Spinner<Integer> depthSpinner;
    private ComboBox<String> charsetComboBox;
    private Charset pickedCharset;
    private ComboBox<String> foeComboBox;
    private FileObserver.Element pickedFOE;
    private Label errorLabel;
    private TreeView<String> treeView;
    private FileService fileService;
    private BorderPane statisticBorderPane;
    private Stage applicationWindow;

    private boolean isControllerReady() {
        if (pathLabel == null) return false;
        if (pathTextField == null) return false;
        if (get == null) return false;
        if (depthLabel == null) return false;
        if (depthSpinner == null) return false;
        if (charsetComboBox == null) return false;
        if (foeComboBox == null) return false;
        if (errorLabel == null) return false;
        if (treeView == null) return false;
        if (applicationWindow == null) return false;
        return true;
    }


    public TextField initPathTextField() {
        pathTextField = new TextField();
        pathTextField.setId("general-text-field");
        pickedPath = "";
        return pathTextField;
    }

    public Label initPathLabel() {
        pathLabel = new Label("PATH");
        pathLabel.setId("general-label");
        return pathLabel;
    }

    public Button initGetButton() {
        get = new Button("GET");
        get.setId("idle-button");
        get.setOnMouseEntered(event -> get.setId("focus-button"));
        get.setOnMouseExited(event -> get.setId("idle-button"));
        get.setOnAction(event -> {
            throwIAE(!isControllerReady(), this, "initGetButton()", "button#setOnAction, but controller not ready");
            pickedPath = pathTextField.getText();
            pickedDepth = depthSpinner.getValue();
            pickedCharset = Charset.forName(charsetComboBox.getValue());
            pickedFOE = FileObserver.Element.valueOf(foeComboBox.getValue());
            if (setFileService()) {
                compileTreeView();
                applicationWindow.setHeight(900);
            }
        });
        return get;
    }

    private boolean setFileService() {
        try {
            if (pickedPath.length() == 0) throw new IllegalArgumentException();
            fileService = new FileService(Paths.get(pickedPath), pickedDepth, pickedCharset);
            errorLabel.setText("");
        } catch (IllegalArgumentException ex) {
            errorLabel.setText("PATH incorrect");
            applicationWindow.setHeight(170);
            return false;
        }
        return true;
    }

    public Label initDepthLabel() {
        depthLabel = new Label("DEPTH");
        depthLabel.setId("general-label");
        return depthLabel;
    }

    public Spinner<Integer> initDepthSpinner() {
        depthSpinner = new Spinner<>(1, 6, 1, 1);
        depthSpinner.setPrefSize(60, 20);
        pickedDepth = 1;
        return depthSpinner;
    }

    public ComboBox<String> initCharsetComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Charset charset : FileObserver.charsets()) {
            list.add(charset.name());
        }
        charsetComboBox = new ComboBox<>(list);
        charsetComboBox.setValue(list.get(0));
        pickedCharset = Charset.forName(list.get(0));
        return charsetComboBox;
    }

    public ComboBox<String> initFileObserverElementComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (FileObserver.Element element : FileObserver.Element.values()) {
            list.add(element.name());
        }
        foeComboBox = new ComboBox<>(list);
        foeComboBox.setValue(list.get(0));
        pickedFOE = FileObserver.Element.valueOf(list.get(0));
        return foeComboBox;
    }

    public Label initErrorLabel() {
        errorLabel = new Label();
        errorLabel.setId("general-error-label");
        return errorLabel;
    }

    public Charset getPickedCharset() {
        return pickedCharset;
    }

    public FileObserver.Element getPickedFOE() {
        return pickedFOE;
    }


    public String getPickedPath() {
        return pickedPath;
    }

    public int getPickedDepth() {
        return pickedDepth;
    }

    private boolean isOptionsReady() {
        if (getPickedPath() == null) return false;
        if (getPickedCharset() == null) return false;
        if (getPickedFOE() == null) return false;
        if (getPickedDepth() == 0) return false;
        return true;
    }

    public TreeView<String> initFileTreeView() {
        treeView = new TreeView<>();
        treeView.setShowRoot(true);
        treeView.setId("general-tree-view");
        return treeView;
    }

    public BorderPane initStatisticBorderPane() {
        statisticBorderPane = new BorderPane();
        return statisticBorderPane;
    }

    private TableView<StatisticUnit> compileTable(Map<String, Long> statistic) {
        throwIAEWhenNull(statistic, this, "compileTable(Map<String, Long> statistic)");
        String columnName = pickedFOE.name().substring(0, pickedFOE.name().length() - 1);
        TableView<StatisticUnit> table = new TableView<>();
        TableColumn column1 = new TableColumn("COUNT");
        column1.setCellValueFactory(new PropertyValueFactory<>("count"));
        TableColumn column2 = new TableColumn(columnName.toUpperCase());
        column2.setCellValueFactory(new PropertyValueFactory<>("element"));

        table.getColumns().addAll(column1, column2);
        ObservableList<StatisticUnit> data = FXCollections.observableArrayList();
        for (String key : statistic.keySet()) {
            data.add(new StatisticUnit(key, statistic.get(key)));
        }
        table.setItems(data);
        return table;
    }

    private TableView<HeadStatisticUnit> compileHeadTable() {
        String key1 = FileTree.Element.DIRECTORIES.name();
        String key2 = FileTree.Element.FILES.name();
        String key3 = FileObserver.Element.LINES.name();
        String key4 = FileObserver.Element.WORDS.name();
        String key5 = FileObserver.Element.SYMBOLS.name();
        TableColumn column1 = new TableColumn(key1);
        TableColumn column2 = new TableColumn(key2);
        TableColumn column3 = new TableColumn(key3);
        TableColumn column4 = new TableColumn(key4);
        TableColumn column5 = new TableColumn(key5);
        column1.setCellValueFactory(new PropertyValueFactory<>("element1"));
        column2.setCellValueFactory(new PropertyValueFactory<>("element2"));
        column3.setCellValueFactory(new PropertyValueFactory<>("element3"));
        column4.setCellValueFactory(new PropertyValueFactory<>("element4"));
        column5.setCellValueFactory(new PropertyValueFactory<>("element5"));
        TableView<HeadStatisticUnit> table = new TableView<>();
        table.getColumns().addAll(column1, column2, column3, column4, column5);
        setFileService();
        Map<String, Long> map = fileService.statistic();
        ObservableList<HeadStatisticUnit> data = FXCollections.observableArrayList(
                new HeadStatisticUnit(map.get(key1), map.get(key2), map.get(key3), map.get(key4), map.get(key5))
        );
        table.setItems(data);
        table.setMaxHeight(61);
        table.setEditable(false);
        return table;
    }

    private void fillStatisticBorderPane() {
        throwIAE(statisticBorderPane == null, this, "fillStatisticBorderPane(TableView<StatisticUnit>... tables)", "Statistic border pane is null");
        setFileService();
        statisticBorderPane.setTop(new Label());
        statisticBorderPane.setCenter(new Label());
        statisticBorderPane.setTop(compileHeadTable());
        Map<String, Long> actual = fileService.statistic(pickedFOE);
        statisticBorderPane.setCenter(compileTable(actual));
    }

    private void compileTreeView() {
        throwIAE(fileService == null, this, "compileTreeView()", " Try to generate TreeView");
        //need to set application size bigger to show TreeVie
        ImageView iconFile = new ImageView("image/file-icon.png");
        ImageView iconFolder = new ImageView("image/folder-icon.png");
        ImageView rootIcon = Files.isDirectory(fileService.getFileTree().getRoot()) ? iconFolder : iconFile;

        Path root = fileService.getFileTree().getRoot();
        TreeItem<String> treeItem = new TreeItem<>(root.toString(), rootIcon);
        fileService.getFileTree().get(FileTree.Element.ALL).forEach(el -> {
            if (!root.equals(el)) {
                Path relative = root.relativize(el);
                compileTreeItem(treeItem, relative, !Files.isDirectory(el));
            }
        });
        fillStatisticBorderPane();
        treeView.setRoot(treeItem);
        treeView.showRootProperty();
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            findPathAndDepth(newValue);
            fillStatisticBorderPane();
        });
    }

    //METHOD OK
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

    private void findPathAndDepth(TreeItem<String> element) throws IllegalArgumentException {
        if (element != null) {
            int newDepth = 0;
            String result = element.getValue();
            TreeItem<String> tmp = element.getParent();
            while (tmp != null) {
                newDepth++;
                result = tmp.getValue() + File.separator + result;
                tmp = tmp.getParent();
            }
            pickedPath = result;
            pickedDepth = newDepth;
        }
    }

    public void setApplicationWindow(Stage applicationWindow) {
        this.applicationWindow = applicationWindow;
    }
}
