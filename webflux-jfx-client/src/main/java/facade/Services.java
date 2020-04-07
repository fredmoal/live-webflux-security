package facade;

import modele.Notification;

import java.util.Optional;
import java.util.function.Consumer;

public interface Services {
    Optional<String> login(String username, String password);
    void sendMessage(String texte);
    void subscribe(Consumer<Notification> consumer);
    boolean isAuthenticated();
}
