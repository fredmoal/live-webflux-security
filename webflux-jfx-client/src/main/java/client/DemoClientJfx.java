package client;

import controller.Controller;
import facade.Services;
import facade.ServicesProxy;
import javafx.application.Application;
import javafx.stage.Stage;

public class DemoClientJfx extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Services services = new ServicesProxy();
        Controller controller = new Controller(stage,services);
        controller.showLogin();
    }

}
