package pl.edu.uj.project.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import pl.edu.uj.project.core.FileObserver;

import java.nio.charset.Charset;

public class HeadPane {
    private final Label pathLabel;
    private final TextField pathTextField;
    private final Button getButton;
    private final Label depthLabel;
    private final Spinner<Integer> depthSpinner;
    private final ComboBox<String> charsetComboBox;
    private final ComboBox<String> foElementComboBox;
    private final Label errorLabel;
    private final BorderPane mainPane;

    public HeadPane() {
        this.pathLabel = initPathLabel();
        this.pathTextField = initPathTextField();
        this.getButton = initGetButton();
        this.depthLabel = initDepthLabel();
        this.depthSpinner = initDepthSpinner();
        this.charsetComboBox = initCharsetComboBox();
        this.foElementComboBox = initFOElementComboBox();
        this.errorLabel = initErrorLabel();
        this.mainPane = initMainPane();
        fillMainPane();
    }

    private BorderPane initMainPane() {
        BorderPane mainPane = new BorderPane();
        mainPane.setId("top-pane");
        return mainPane;
    }

    private void fillMainPane() {
        BorderPane chapter = new BorderPane();
        chapter.setLeft(pathLabel);
        chapter.setCenter(pathTextField);
        chapter.setRight(getButton);
        mainPane.setTop(chapter);
        BorderPane.setAlignment(chapter, Pos.TOP_LEFT);
        chapter = new BorderPane();
        chapter.setLeft(depthLabel);
        chapter.setCenter(depthSpinner);
        mainPane.setLeft(chapter);
        BorderPane.setAlignment(chapter, Pos.TOP_LEFT);
        mainPane.setCenter(foElementComboBox);
        BorderPane.setAlignment(foElementComboBox, Pos.TOP_CENTER);
        mainPane.setRight(charsetComboBox);
        BorderPane.setAlignment(charsetComboBox, Pos.TOP_LEFT);
        mainPane.setBottom(errorLabel);
        BorderPane.setAlignment(errorLabel, Pos.TOP_LEFT);
    }

    private Label initPathLabel() {
        Label pathLabel = new Label("PATH");
        pathLabel.setId("general-label");
        return pathLabel;
    }

    public TextField initPathTextField() {
        TextField pathTextField = new TextField();
        pathTextField.setId("general-text-field");
        return pathTextField;
    }

    private Button initGetButton() {
        Button get = new Button("GET");
        get.setId("idle-button");
        get.setOnMouseEntered(event -> get.setId("focus-button"));
        get.setOnMouseExited(event -> get.setId("idle-button"));
        return get;
    }

    private Label initDepthLabel() {
        Label depthLabel = new Label("DEPTH");
        depthLabel.setId("general-label");
        return depthLabel;
    }

    private Spinner<Integer> initDepthSpinner() {
        Spinner<Integer> depthSpinner = new Spinner<>(1, 6, 1, 1);
        depthSpinner.setId("general-spinner");
        depthSpinner.setPrefSize(60, 20);
        return depthSpinner;
    }

    private ComboBox<String> initCharsetComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Charset charset : FileObserver.charsets()) {
            list.add(charset.name());
        }
        ComboBox<String> charsetComboBox = new ComboBox<>(list);
        charsetComboBox.setId("general-combo-box");
        charsetComboBox.setValue(list.get(0));
        return charsetComboBox;
    }

    private ComboBox<String> initFOElementComboBox() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (FileObserver.Element element : FileObserver.Element.values()) {
            list.add(element.name());
        }
        ComboBox<String> foeComboBox = new ComboBox<>(list);
        foeComboBox.setId("general-combo-box");
        foeComboBox.setValue(list.get(0));
        return foeComboBox;
    }

    private Label initErrorLabel() {
        Label errorLabel = new Label();
        errorLabel.setId("general-error-label");
        return errorLabel;
    }

    public Pane getPane() {
        return mainPane;
    }

    public String getStringPath() {
        return pathTextField.getText();
    }

    public Button getGetButton() {
        return getButton;
    }

    public Integer getDepth() {
        return depthSpinner.getValue();
    }

    public FileObserver.Element getFOElement() {
        String text = foElementComboBox.getValue();
        return FileObserver.Element.valueOf(text);
    }

    public Charset getCharset() {
        String text = charsetComboBox.getValue();
        return Charset.forName(text);
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

}
