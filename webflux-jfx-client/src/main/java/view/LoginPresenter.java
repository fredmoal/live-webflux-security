package view;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginPresenter {
    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Parent root;

    public Parent getRoot() {
        return root;
    }

    public void login(ActionEvent actionEvent) {
        controller.tenteLogin(username.getText(),password.getText());
    }

    public static LoginPresenter getInstance(Controller controller) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(LoginPresenter.class.getResourceAsStream("login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginPresenter instance = loader.getController();
        instance.setController(controller);

        return instance;
    }
}
