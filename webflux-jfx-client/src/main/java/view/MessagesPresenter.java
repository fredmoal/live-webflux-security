package view;

import controller.Controller;
import controller.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modele.Notification;

import java.io.IOException;


public class MessagesPresenter implements Observer {
    @FXML
    private TextArea textezone;
    @FXML
    private TextField message;
    @FXML
    private Parent root;

    public Parent getRoot() {
        return root;
    }

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void notify(Notification notification) {
        textezone.appendText(notification.getUtilisateur()+": "+notification.getTexte()+"\n");
    }

    public void sendMessage(ActionEvent actionEvent) {
        controller.envoyerMessage(message.getText());
        message.setText("");
    }


    public static MessagesPresenter getInstance(Controller controller) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(LoginPresenter.class.getResourceAsStream("messages.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessagesPresenter instance = loader.getController();
        instance.setController(controller);

        return instance;
    }
}
