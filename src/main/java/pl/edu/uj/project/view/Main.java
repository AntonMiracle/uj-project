package pl.edu.uj.project.view;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        new Main().launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
        String labelFormat = "%-15s : ";
        Controller controler = new ControllerImp();


        //////////////////////MANE FRAME///////////
        BorderPane main = new BorderPane();
        Scene scene = new Scene(main, 1050, 165);
        main.getStylesheets().add("css/stylesheets.css");
        main.setId("background");
        controler.setMainPane(main);
        controler.setScene(scene);
        controler.setStage(primaryStage);
        /////////////////////TOP PANE /////////////////////////////
        FlowPane topPane = new FlowPane();
        topPane.setId("top-pane");
        controler.setTopPane(topPane);

        Label pathLabel = new Label(String.format(labelFormat, "PATH"));
        pathLabel.setId("general-label");

        TextField pathText = new TextField();
        pathText.setId("general-text-field");
        controler.set(pathText);

        Button get = new Button("GET");
        get.setId("idle-button");
        get.setOnMouseEntered(event -> get.setId("focus-button"));
        get.setOnMouseExited(event -> get.setId("idle-button"));
        controler.set(get);

        Label depthLabel = new Label("DEPTH:");
        depthLabel.setId("general-label");
        Spinner<Integer> depth = new Spinner<>(1, 6, 1, 1);
        depth.setPrefSize(60, 20);
        controler.set(depth);

        Label charsetLabel = new Label(String.format(labelFormat, "CHARSET"));
        charsetLabel.setId("general-label");
        ToggleGroup charset = new ToggleGroup();
        RadioButton defaultCharset = new RadioButton("DEFAULT");
        RadioButton utf8 = new RadioButton(StandardCharsets.UTF_8.toString());
        RadioButton utf16 = new RadioButton(StandardCharsets.UTF_16.toString());
        RadioButton iso8859 = new RadioButton(StandardCharsets.ISO_8859_1.toString());
        RadioButton usascii = new RadioButton(StandardCharsets.US_ASCII.toString());
        defaultCharset.setSelected(true);
        defaultCharset.setToggleGroup(charset);
        utf8.setToggleGroup(charset);
        utf16.setToggleGroup(charset);
        iso8859.setToggleGroup(charset);
        usascii.setToggleGroup(charset);
        Label invisible = new Label();
        invisible.setMinWidth(400);
        controler.set(charset);

        Label elementLabel = new Label(String.format(labelFormat, "STATISTIC OF"));
        elementLabel.setId("general-label");
        ToggleGroup statisticElement = new ToggleGroup();
        RadioButton words = new RadioButton(FileObserver.Element.WORDS.toString());
        RadioButton lines = new RadioButton(FileObserver.Element.LINES.toString());
        RadioButton symbols = new RadioButton(FileObserver.Element.SYMBOLS.toString());
        words.setSelected(true);
        words.setToggleGroup(statisticElement);
        lines.setToggleGroup(statisticElement);
        symbols.setToggleGroup(statisticElement);
        controler.setStatisticElement(statisticElement);

        topPane.getChildren().add(pathLabel);
        topPane.getChildren().add(pathText);
        topPane.getChildren().add(depthLabel);
        topPane.getChildren().add(depth);
        topPane.getChildren().add(get);
        topPane.getChildЁЁren().add(charsetLabel);
        topPane.getChildren().addAll(defaultCharset, utf8, utf16, iso8859, usascii);
        topPane.getChildren().add(invisible);
        topPane.getChildren().add(elementLabel);
        topPane.getChildren().addAll(words, lines, symbols);

        /////////////////////////////// LABEL RESULT OF STATISTIC
        Label statisticLabel = new Label();
        controler.set(statisticLabel);

        //////////////////////////END STATISTIC
        ///////////////////////LEFT PANE


//////////////////////TREE/////////////////////////


        main.setTop(topPane);
//        main.setCenter(statisticLabel);
//        main.setLeft();

        primaryStage.setTitle("Statistic");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
        */
    }

}
