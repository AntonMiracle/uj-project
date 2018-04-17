package pl.edu.uj.project.view;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.uj.project.controler.Controller;

public class Main extends Application {

    public static void main(String[] args) {
        new Main().launch(args);
    }

    //gradle clean jacocoTestReport build
    //c:\UJ\

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller(primaryStage);
        controller.show();
    }
}
