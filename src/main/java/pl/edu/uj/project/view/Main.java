package pl.edu.uj.project.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import pl.edu.uj.project.controler.Controller;


public class Main extends Application {
    public static void main(String[] args) {
        new Main().launch(args);
    }
//c:\UJ\UJjava\
    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        BorderPane main = new BorderPane();
        Scene scene = new Scene(main,800,126);
        main.getStylesheets().add("css/stylesheets.css");
        main.setId("background");
        Label pathLabel = controller.initPathLabel();
        TextField pathTextField = controller.initPathTextField();
        Button getButton = controller.initGetButton();
        Label depthLabel = controller.initDepthLabel();
        Spinner<Integer> depthSpinner = controller.initDepthSpinner();
        ComboBox charsetComboBox = controller.initCharsetComboBox();
        ComboBox foeComboBox = controller.initFileObserverElementComboBox();
        Label errorLabel = controller.initErrorLabel();
        FlowPane topPane = new FlowPane();
        topPane.setId("top-pane");
        topPane.getChildren().addAll(pathLabel, pathTextField, getButton, depthLabel, depthSpinner, charsetComboBox, foeComboBox, errorLabel);
        main.setTop(topPane);
        TreeView<String> treeView = controller.initFileTreeView();
        main.setLeft(treeView);
        BorderPane statisticBorderPane = controller.initStatisticBorderPane();
        main.setCenter(new ScrollPane(statisticBorderPane));
        controller.setApplicationWindow(primaryStage);
        primaryStage.setTitle("Statistic");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

}
