package pl.edu.uj.project.controler;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import pl.edu.uj.project.core.model.FileObserver;

import java.nio.file.Path;
import java.util.Map;

public interface Controller {
    void setFileTreeView(Path root, int depth);

    void setCharset(String charsetName);

    void setStatisticElement(ToggleGroup radioButtons);

    void setElement(String elementName);

    void set(ToggleGroup radioButtons);

    void set(TextField path);

    void set(Label statistic);

    void set(Spinner<Integer> depth);

    void set(Button get);

    void init();

    boolean isPathExist();

    void showStatistic();

    void setMainPane(BorderPane mainPane);

    void setTopPane(FlowPane topPane);

    String buildTable(Map<String, Long> statistic, int column, FileObserver.Element element);

    boolean isReady();

    void setScene(Scene scene);

    void setStage(Stage application);
}
