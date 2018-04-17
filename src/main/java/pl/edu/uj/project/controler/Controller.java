package pl.edu.uj.project.controler;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.uj.project.core.FileObserver;
import pl.edu.uj.project.core.Util;
import pl.edu.uj.project.view.FileTreePane;
import pl.edu.uj.project.view.HeadPane;
import pl.edu.uj.project.view.StatisticPane;

import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {
    private final Stage application;
    private final BorderPane mainPane;
    private final HeadPane headPane;
    private Path pickedPath;
    private Integer pickedDepth;
    private FileObserver.Element pickedElement;
    private Charset pickedCharset;
    private FileTreePane fileTreePane;
    private StatisticPane statisticPane;

    public Controller(Stage application) {
        Util.throwIAEWhenNull(application,this,"constructor");
        this.mainPane = initMainPane(application);
        this.application = initApplication(application);
        this.headPane = initHeadPane(mainPane);
        setGetButtonOnAction(headPane.getGetButton());
    }

    private HeadPane initHeadPane(BorderPane mainPane) {
        HeadPane headPane = new HeadPane();
        mainPane.setTop(headPane.getPane());
        return headPane;
    }

    private BorderPane initMainPane(Stage application) {
        BorderPane mainPane = new BorderPane();
        Label footer = new Label("Developed by Anton Bondarenko | e-mail: b.anton.m.1986@gmail.com");
        footer.setId("footer");
        BorderPane.setAlignment(footer, Pos.CENTER);
        mainPane.setBottom(footer);
        mainPane.getStylesheets().add("css/stylesheets.css");
        mainPane.setId("background");
        application.setScene(new Scene(mainPane, 700, 200));
        return mainPane;
    }

    private Stage initApplication(Stage application) {
        application.setTitle("Statistic");
        return application;
    }

    public void show() {
        application.sizeToScene();
        application.show();
    }

    public void setGetButtonOnAction(Button getButtonOnAction) {
        getButtonOnAction.setOnAction(event -> {
            initFileTreePane(false);
            initStatisticPane(false);
            if (headPane.getStringPath() == null || headPane.getStringPath().length() == 0) {
                headPane.getErrorLabel().setText("Path not exist or not readable");
            } else {
                try {
                    headPane.getErrorLabel().setText("");
                    this.pickedPath = Paths.get(headPane.getStringPath());
                    this.pickedDepth = headPane.getDepth();
                    this.pickedElement = headPane.getFOElement();
                    this.pickedCharset = headPane.getCharset();
                    initFileTreePane(true);
                    initStatisticPane(true);
                } catch (InvalidPathException ex) {
                    headPane.getErrorLabel().setText("Path not exist or not readable");
                }

            }
        });
    }

    private void initStatisticPane(boolean isShow) {
        if (isShow) {
            this.statisticPane = new StatisticPane(pickedPath, pickedDepth, pickedCharset, pickedElement);
            mainPane.setCenter(statisticPane.getPane());
        } else {
            mainPane.setCenter(new BorderPane());
        }
    }

    private void initFileTreePane(boolean isShow) {
        if (isShow) {
            this.fileTreePane = new FileTreePane(pickedPath, pickedDepth, pickedCharset);
            fileTreePane.getTreeView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                fileTreePane.findPathAndDepth(newValue);
                pickedPath = fileTreePane.getPickedPath();
                pickedDepth = fileTreePane.getPickedDepth();
                initStatisticPane(true);
            });
            mainPane.setLeft(fileTreePane.getPane());
        } else {
            mainPane.setLeft(new BorderPane());
        }
    }
}
