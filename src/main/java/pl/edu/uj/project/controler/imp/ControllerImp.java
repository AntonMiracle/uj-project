package pl.edu.uj.project.controler.imp;

import pl.edu.uj.project.controler.Controller;

public class ControllerImp implements Controller{
/*
    private TextField path;

    private Button getButton;
    private Spinner<Integer> depth;
    private ToggleGroup charsetButtons;
    private FileTreeView fileTreeView;
    private Charset charset;
    private FileObserver.Element element;
    private Label statistic;
    private ToggleGroup elementButton;
    private BorderPane mainPane;
    private FlowPane topPane;
    private Scene scene;
    private Stage application;

    @Override
    public boolean isReady() {
        if (charsetButtons == null) return false;
        if (path == null) return false;
//        if (statistic == null) return false;
        if (depth == null) return false;
        if (getButton == null) return false;
        if (mainPane == null) return false;
        if (topPane == null) return false;
        if (application == null) return false;
        if (scene == null) return false;
        return true;
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void setStage(Stage application) {
        this.application = application;
    }

    @Override
    public void setFileTreeView(Path root, int depth) {
        this.fileTreeView = new FileTreeView(root, depth);
    }

    @Override
    public void setCharset(String charsetName) {
        if (charsetName.equals("DEFAULT")) {
            charset = Charset.defaultCharset();
        } else {
            charset = Charset.forName(charsetName);
        }
    }

    @Override
    public void setStatisticElement(ToggleGroup elementButton) {
        this.elementButton = elementButton;
    }

    @Override
    public void setElement(String elementName) {
        this.element = FileObserver.Element.valueOf(elementName);
    }

    @Override
    public void set(ToggleGroup charsetButton) {
        this.charsetButtons = charsetButton;
    }

    @Override
    public void set(TextField path) {
        this.path = path;
    }

    @Override
    public void set(Label statistic) {
        this.statistic = statistic;
    }

    @Override
    public void set(Spinner<Integer> depth) {
        this.depth = depth;
    }

    @Override
    public void set(Button get) {
        get.setOnAction(event -> {
            if (isPathExist()) {
                application.setHeight(800);
                init();
                showStatistic();
            } else {
                application.setHeight(200);
            }
        });
        this.getButton = get;
    }

    @Override
    public void init() throws IllegalArgumentException {
        if (!isReady()) throw new IllegalArgumentException("Controller not ready");
        setCharset(((RadioButton) charsetButtons.getSelectedToggle()).getText());
        setElement(((RadioButton) elementButton.getSelectedToggle()).getText());
    }

    @Override
    public boolean isPathExist() {
        if (!Paths.get(path.getText()).toFile().exists()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Path not exist");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private String buildGeneralStatistic(Map<String, Long> statistic) {
        StringBuilder result = new StringBuilder();
        for (String key : statistic.keySet()) {
            result.append(String.format("%12s : %-12s", key, statistic.get(key).toString()));
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public void showStatistic() {
        System.out.println(depth.getValue().intValue());
        FileService service = FileService.of(Paths.get(path.getText()), depth.getValue().intValue());
        setFileTreeView(service.getTree().getRoot(), service.getTree().getDepth());
        mainPane.setLeft(fileTreeView.getViewTree());
        System.out.println(charset);
        String text = buildGeneralStatistic(service.statistic(charset));
        statistic.setText(text);

        mainPane.setCenter(new ScrollPane(statistic));

        fileTreeView.getViewTree().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String[] pathAndDepth = findPathAndDepth(newValue);
                Path newRoot = Paths.get(pathAndDepth[0]);
                int newDepth = Integer.valueOf(pathAndDepth[1]).intValue();
                Map<String, Long> statisticMAP = FileService.of(newRoot, newDepth).statistic(element, charset);
                statistic.setText(buildTable(statisticMAP, 3, element));
            }
        });
    }

    @Override
    public void setMainPane(BorderPane mainPane) {
        this.mainPane = mainPane;
    }

    @Override
    public void setTopPane(FlowPane topPane) {
        this.topPane = topPane;
    }

    @Override
    public String buildTable(Map<String, Long> statistic, int numColumn, FileObserver.Element element) {
        StringBuilder result = new StringBuilder();
        String[] numColumnAndFormat = buildColumnHead(numColumn, element, result);
        numColumn = Integer.valueOf(numColumnAndFormat[0]);
        String format = numColumnAndFormat[1];
        int countColumn = 0;
        for (String key : statistic.keySet()) {
            Long value = statistic.get(key);
            result.append(String.format(format, key, value.toString()));
            if (++countColumn == numColumn) {
                countColumn = 0;
                result.append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    private String[] buildColumnHead(int numColumn, FileObserver.Element element, StringBuilder result) {
        int head = 0;
        String format = "";
        if (element == FileObserver.Element.WORDS) {
            format = "%12s : %-12s";
            while (head++ < numColumn) {
                result.append(String.format(format, "WORD", "COUNT"));
            }
        }
        if (element == FileObserver.Element.LINES) {
            numColumn = 1;
            format = "%2$7s : %1$-12s";
            result.append(String.format(format, "LINE", "COUNT"));
        }
        if (element == FileObserver.Element.SYMBOLS) {
            numColumn *= 2;
            format = "%3s : %-4s";
            while (head++ < numColumn) {
                result.append(String.format(format, "SYMBOL", "COUNT"));
            }
        }
        result.append(System.lineSeparator());
        return new String[]{String.valueOf(numColumn), format};
    }

    private String[] findPathAndDepth(TreeItem<String> element) throws IllegalArgumentException {
        if (element == null) throw new IllegalArgumentException("element is null, can`t findPath");
        int newDepth = 0;
        String result = element.getValue();
        TreeItem<String> tmp = element.getParent();
        while (tmp != null) {
            newDepth++;
            result = tmp.getValue() + File.separator + result;
            tmp = tmp.getParent();
        }
        return new String[]{result, String.valueOf(newDepth)};
    }
*/

}
