package sources;

import information.Information;

/**
 * La classe SourceFixe représente une source qui génère toujours le même message, composé de valeurs booléennes prédéfinies.
 * Elle hérite de la classe Source et génère un signal fixe lors de sa création.
 */
public class SourceFixe extends Source<Boolean> {

    /**
     * Constructeur de la classe SourceFixe.
     * Il initialise l'information générée avec un message fixe constitué de valeurs booléennes prédéfinies.
     */
    public SourceFixe() {
        informationGeneree = new Information<Boolean>();
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
    }
}

