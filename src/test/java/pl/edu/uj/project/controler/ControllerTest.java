package pl.edu.uj.project.controler;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.junit.Before;
import org.junit.Test;
import pl.edu.uj.project.core.FileObserver;

import java.nio.charset.Charset;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ControllerTest extends JFXPanel {
    private Controller controller;

    @Before
    public void getNewControllerWith() {
        controller = new Controller();
    }

    @Test
    public void initPathLabel() {
        Label pathLabel = controller.initPathLabel();
        assertThat(pathLabel.getId()).isNotNull();
        assertThat(pathLabel.getId()).isEqualTo("general-label");
        assertThat(pathLabel.getText()).isNotNull();
        assertThat(pathLabel.getText()).isEqualTo("PATH");
    }

    @Test
    public void initPathTextField() {
        TextField pathTextField = controller.initPathTextField();
        assertThat(pathTextField.getId()).isNotNull();
        assertThat(pathTextField.getId()).isEqualTo("general-text-field");
    }

    @Test
    public void whenInitPathTextFieldThenGetPickedPathNotNull() {
        assertThat(controller.getPickedPath()).isNull();
        controller.initPathTextField();
        assertThat(controller.getPickedPath()).isNotNull();
    }

    @Test
    public void initGetButton() {
        Button get = controller.initGetButton();
        assertThat(get.getText()).isNotNull();
        assertThat(get.getText()).isEqualTo("GET");
        assertThat(get.getId()).isNotNull();
        assertThat(get.getId()).isEqualTo("idle-button");
    }

    @Test
    public void initDepthLabel() {
        Label depthLabel = controller.initDepthLabel();
        assertThat(depthLabel.getText()).isNotNull();
        assertThat(depthLabel.getText()).isEqualTo("DEPTH");
        assertThat(depthLabel.getId()).isNotNull();
        assertThat(depthLabel.getId()).isEqualTo("general-label");
    }

    @Test
    public void initDepthSpinner() {
        Spinner<Integer> depthSpinner = controller.initDepthSpinner();
        assertThat(depthSpinner).isNotNull();
        assertThat(depthSpinner.getPrefWidth()).isEqualTo(60);
        assertThat(depthSpinner.getPrefHeight()).isEqualTo(20);
    }

    @Test
    public void whenInitDepthSpinnerThenGetPickedDepthNotNull() {
        assertThat(controller.getPickedDepth()).isEqualTo(0);
        controller.initDepthSpinner();
        assertThat(controller.getPickedDepth()).isEqualTo(1);
    }

    @Test
    public void initCharsetComboBox() {
        ComboBox<String> charsetComboBox = controller.initCharsetComboBox();
        assertThat(charsetComboBox.getValue()).isNotNull();
        for (String name : charsetComboBox.getItems()) {
            assertThat(FileObserver.charsets().contains(Charset.forName(name))).isTrue();
        }
    }

    @Test
    public void whenInitCharsetComboBoxThenGetPickedCharsetNotNull() {
        assertThat(controller.getPickedCharset()).isNull();
        controller.initCharsetComboBox();
        assertThat(controller.getPickedCharset()).isNotNull();
    }

    @Test
    public void initFileObserverElementsComboBox() {
        ComboBox<String> foeComboBox = controller.initFileObserverElementComboBox();
        assertThat(foeComboBox.getValue()).isNotNull();
        for (String name : foeComboBox.getItems()) {
            assertThat(FileObserver.Element.valueOf(name)).isNotNull();
        }
    }

    @Test
    public void whenInitFileObserverElementsComboBoxThenGetPickedFOENotNull() {
        assertThat(controller.getPickedFOE()).isNull();
        controller.initFileObserverElementComboBox();
        assertThat(controller.getPickedFOE()).isNotNull();
    }

    @Test
    public void initErrorLabel() {
        Label errorLabel = controller.initErrorLabel();
        assertThat(errorLabel.getText()).isNotNull();
        assertThat(errorLabel.getText()).isEqualTo("");
        assertThat(errorLabel.getId()).isNotNull();
        assertThat(errorLabel.getId()).isEqualTo("general-error-label");
    }

    @Test
    public void initFileTreeView() {
        TreeView<String> ftView = controller.initFileTreeView();
        assertThat(ftView).isNotNull();
        assertThat(ftView.isShowRoot()).isTrue();
        assertThat(ftView.getId()).isEqualTo("general-tree-view");
    }

    @Test
    public void initStatisticBorderPane() {
        BorderPane statisticBorderPane = controller.initStatisticBorderPane();
    }

}