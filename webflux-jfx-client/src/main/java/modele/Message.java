package modele;

public class Message {
    private long id;
    private String texte;
    private String utilisateur;

    public Message() {
    }

    public Message(long id, String texte, String utilisateur) {
        this.id = id;
        this.texte = texte;
    }

    public Message(String texte) {
        this.texte = texte;
    }

    public long getId() {
        return id;
    }

    public String getTexte() {
        return texte;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }
}
