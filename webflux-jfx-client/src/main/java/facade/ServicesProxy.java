package facade;

import javafx.application.Platform;
import modele.Message;
import modele.Notification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Consumer;

public class ServicesProxy implements Services {
    private WebClient webAuthClient;
    private Optional<String> authorization;
    private Flux<Notification> flux;

    static class Auth {
        public String username;
        public String password;
    }

    @Override
    public boolean isAuthenticated() {
        return authorization.isPresent();
    }

    @Override
    public Optional<String> login(String username, String password) {
        Auth auth = new Auth();
        auth.username = username;
        auth.password = password;
        final WebClient webClientLogin = WebClient.create("http://localhost:8080/auth/login");

        authorization = Optional.empty();

        ClientResponse response = webClientLogin
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(auth))
                .exchange().block();

        authorization = response.headers().header("Authorization").stream().findFirst();

        // si le login réussi, on configure l'entete des requetes suivantes avec le token reçu
        // et on s'abonne aux notifications
        if (response.statusCode().is2xxSuccessful() && authorization.isPresent()) {
            webAuthClient = WebClient.builder()
                    .baseUrl("http://localhost:8080/api/")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, authorization.get())
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
            // s'abonne aux notifications
            flux = webAuthClient
                    .get()
                    .uri(uriBuilder -> uriBuilder.path("messages/subscribe").build())
                    .retrieve()
                    .bodyToFlux(Notification.class);
        }

        return authorization;
    }

    @Override
    public void sendMessage(String texte) {
        ClientResponse result = webAuthClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("messages").build())
                .body(BodyInserters.fromValue(new Message(texte)))
                .exchange()
                .block();

        Mono<Message> messageRetour = result.bodyToMono(Message.class);
    }

    @Override
    public void subscribe(Consumer<Notification> consumer) {
        flux.subscribe(notification -> consumer.accept(notification));
    }
}
