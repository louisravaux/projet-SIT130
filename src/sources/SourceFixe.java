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

    /**
     * Crée une instance de la classe SourceFixe en utilisant une chaîne de caractères binaire.
     * Chaque caractère '1' dans la chaîne est converti en valeur booléenne true,
     * et chaque caractère différent de '1' est converti en valeur booléenne false.
     *
     * @param mess La chaîne de caractères binaire à partir de laquelle générer les valeurs booléennes.
     */
    public SourceFixe(String mess) {
        informationGeneree = new Information<>();
        this.mess = mess;

        // Parcourt chaque caractère dans la chaîne et convertit '1' en true, tout autre caractère en false.
        for (int i = 0; i < mess.length(); i++) {
            char c = mess.charAt(i);
            informationGeneree.add(c == '1');
        }
    }

}

