package modele;


public class Notification {
    private String texte;
    private String utilisateur;

    public Notification() {
    }

    public Notification(String texte, String utilisateur) {
        this.texte = texte;
        this.utilisateur = utilisateur;
    }

    public String getTexte() {
        return texte;
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
