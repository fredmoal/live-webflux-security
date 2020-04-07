package controller;


import facade.Services;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.Notification;
import view.LoginPresenter;
import view.MessagesPresenter;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    // la fenetre principale ; une seule Stage ici, pour faire plaisir à Yohan B. ;)
    private Stage stage;
    // les écouteurs du modèle, ici la vue messages uniquement...
    private List<Observer> observers;
    // le proxy de la facade sur le modele
    private Services services;

    public Controller(Stage stage, Services services) {
        this.stage = stage;
        this.services = services;
        observers = new ArrayList<>();
    }

    public void showLogin() {
        LoginPresenter login = LoginPresenter.getInstance(this);
        Scene scene = new Scene(login.getRoot());
        stage.setScene(scene);
        stage.show();
    }
    public void showMessages() {
        MessagesPresenter messagesPresenter = MessagesPresenter.getInstance(this);
        enregistrerObservateur(messagesPresenter);
        Scene scene = new Scene(messagesPresenter.getRoot(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    // abonne le controleur aux notifications du modèle
    // à chaque notification reçue, elle est envoyée à tous les observateurs
    public void subscribeNotifications() {
        services.subscribe(notification -> {
            Platform.runLater(()->notifierObservateurs(notification));
        });
    }

    // accès aux services
    public void tenteLogin(String username, String password) {
        services.login(username,password);
        if (services.isAuthenticated()) {
            subscribeNotifications();
            showMessages();
        }
    }
    public void envoyerMessage(String text) {
        services.sendMessage(text);
    }

    // Observateurs
    public void enregistrerObservateur(Observer listener) {
        observers.add(listener);
    }
    public void supprimerObservateur(Observer listener) {
        observers.remove(listener);
    }
    public void notifierObservateurs(Notification notification) {
        for (Observer observer : observers) {
            observer.notify(notification);
        }
    }
}
