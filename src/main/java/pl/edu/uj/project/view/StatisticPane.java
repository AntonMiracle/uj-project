package pl.edu.uj.project.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import pl.edu.uj.project.core.FileObserver;
import pl.edu.uj.project.core.FileTree;
import pl.edu.uj.project.service.FileService;
import pl.edu.uj.project.view.Util.STU;
import pl.edu.uj.project.view.Util.STUHead;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;

import static pl.edu.uj.project.core.Util.throwIAEWhenNull;

public class StatisticPane {
    private FileObserver.Element element;
    private FileService service;
    private BorderPane pane;

    public StatisticPane(Path path, Integer depth, Charset charset, FileObserver.Element element) {
        this.element = element;
        this.service = new FileService(path, depth, charset);
        this.pane = new BorderPane();
    }

    public Pane getPane() {
        fillPane();
        return pane;
    }

    private TableView<STUHead> compileTableHead() {
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
        Map<String, Long> map = service.statistic();
        STUHead head = new STUHead(map.get(key1), map.get(key2), map.get(key3), map.get(key4), map.get(key5));
        ObservableList<STUHead> data = FXCollections.observableArrayList(head);
        column1.setCellValueFactory(new PropertyValueFactory<>(head.getFiveElementsVarName()[0]));
        column2.setCellValueFactory(new PropertyValueFactory<>(head.getFiveElementsVarName()[1]));
        column3.setCellValueFactory(new PropertyValueFactory<>(head.getFiveElementsVarName()[2]));
        column4.setCellValueFactory(new PropertyValueFactory<>(head.getFiveElementsVarName()[3]));
        column5.setCellValueFactory(new PropertyValueFactory<>(head.getFiveElementsVarName()[4]));
        column1.setResizable(false);
        column2.setResizable(false);
        column3.setResizable(false);
        column4.setResizable(false);
        column5.setResizable(false);
        TableView<STUHead> table = new TableView<>(data);
        table.getColumns().addAll(column1, column2, column3, column4, column5);
        table.setMinHeight(61);
        table.setMaxHeight(61);
        table.setEditable(false);
        return table;
    }

    private TableView<STU> compileTable(Map<String, Long> statistic) {
        throwIAEWhenNull(statistic, this, "compileTable(Map<String, Long> statistic)");
        String columnName = element.name().substring(0, element.name().length() - 1);
        TableView<STU> table = new TableView<>();
        TableColumn column1 = new TableColumn("COUNT");
        column1.setCellValueFactory(new PropertyValueFactory<>("count"));
        TableColumn column2 = new TableColumn(columnName.toUpperCase());
        column2.setCellValueFactory(new PropertyValueFactory<>("element"));

        table.getColumns().addAll(column1, column2);
        ObservableList<STU> data = FXCollections.observableArrayList();
        for (String key : statistic.keySet()) {
            data.add(new STU(key, statistic.get(key)));
        }
        table.setItems(data);
        return table;
    }

    private void fillPane() {
        pane.setTop(compileTableHead());
        pane.setCenter(compileTable(service.statistic(element)));
    }
}
