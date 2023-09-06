package sources;

import information.Information;
import java.util.Random;

/**
 * La classe SourceAleatoire représente une source générant une information aléatoire de type booléen.
 * Elle hérite de la classe Source et génère un signal aléatoire de la taille spécifiée lors de sa création.
 */
public class SourceAleatoire extends Source<Boolean> {

    private final int size;
    private final int seed;

    /**
     * Constructeur de la classe SourceAleatoire.
     *
     * @param size La taille du signal aléatoire à générer.
     */
    public SourceAleatoire(int size, int seed) throws Exception{

        if (size < 1) {
            throw new Exception("La taille doit etre supérieure à 0");
        }

        this.size = size;
        this.seed = seed;

        informationGeneree = new Information<Boolean>();

        generateSignal();

    }

    /**
     * Méthode permettant de générer un signal aléatoire de la taille spécifiée lors de la création de la source.
     * Le signal généré est constitué de valeurs booléennes aléatoires.
     */
    public void generateSignal() {

        Random rd = new Random();
        rd.setSeed(seed);
        for (int i = 0; i < size; i++) {

            boolean bool = rd.nextBoolean();
            informationGeneree.add(bool);

        }
    }

    public Information<Boolean> getInformationGeneree() {
        return informationGeneree;
    }

}
