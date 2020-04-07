package controller;

import modele.Notification;

@FunctionalInterface
public interface Observer {
    void notify(Notification event);
}
