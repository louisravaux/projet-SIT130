package sources;

import information.Information;

/**
 * La classe SourceFixe représente une source qui génère toujours le même message, composé de valeurs booléennes prédéfinies.
 * Elle hérite de la classe Source et génère un signal fixe lors de sa création.
 */
public class SourceFixe extends Source<Boolean> {

    String mess;

    /**
     * Constructeur de la classe SourceFixe.
     * Il initialise l'information générée avec un message fixe constitué de valeurs booléennes prédéfinies.
     */
    public SourceFixe() {
        informationGeneree = new Information<>();
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
    }

    public SourceFixe(String mess) {
        informationGeneree = new Information<>();
        this.mess = mess;

        int c;
        for (int i = 0; i < mess.length(); i++) {
            c = mess.charAt(i);
            informationGeneree.add(c == '1');
        }

    }
}

