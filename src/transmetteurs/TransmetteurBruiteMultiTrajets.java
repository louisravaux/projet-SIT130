package transmetteurs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.sun.source.tree.ArrayAccessTree;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un transmetteur qui transmet des informations float en ajoutant les trajets multiples ainsi que du bruit gaussien.
 */
public class TransmetteurBruiteMultiTrajets extends TransmetteurBruiteAnalogique {

    /** ArrayList contenant les différentes valeurs de décalage */
    private final ArrayList<Integer> tau;

    /** ArrayList contenant les différentes valeurs de coefficient multiplicatif d'amplitude */
    private final ArrayList<Float> ar;

    /** Information<Float> contenant les informations avec trajets multiples */
    private Information<Float> multiTrajet;

    /**
     * Constructeur de la classe TransmetteurBruiteAnalogique.
     *
     * @param snr bruit ajouté au signal (en dB)
     * @param nb_sample nombre d'échantillons par symbole
     */
    public TransmetteurBruiteMultiTrajets(float snr, int nb_sample, ArrayList<Integer> tau, ArrayList<Float> ar) {
        super(snr, nb_sample);
        this.tau = tau;
        this.ar = ar;
        multiTrajet = new Information<>();
    }


    /**
     * Calcul du décalage maximal.
     */
    public void calculateShift() {
        int decalageMax = 0;
        for (Integer i : tau) {
            if (i > decalageMax) {
                decalageMax = i;
            }
        }
        if (decalageMax%nb_sample != 0) {
            decalageMax = decalageMax + (nb_sample - decalageMax%nb_sample);
        }

        //on calcul le decalage max
        for (int i = 0; i < decalageMax; i++) {
            multiTrajet.add(0f);
        }
    }

    /**
     * Effectue une opération de mise à jour sur une collection d'informations émises
     * en utilisant une liste de coefficients multiplicatifs et de décalages temporels.
     * Cette méthode parcourt les éléments de la liste "tau" pour appliquer les calculs
     * sur la liste "informationEmise".
     */
    public void addMultiTrajets() {
        calculateShift();
        for (int i = 0; i < tau.size() && tau.get(i) != 0; i++) {
            for(int j = 0; j < informationRecue.nbElements(); j++) {
                multiTrajet.setIemeElement(j + tau.get(i), multiTrajet.iemeElement(j+ tau.get(i)) + ar.get(i) * informationRecue.iemeElement(j));
            }
        }
    }

    /**
     * Réception des informations, génération du bruit et ajout des trajets multiples.
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {

        for (Float i : information) {
            informationRecue.add(i);
            multiTrajet.add(i);
            p_signal += (float) Math.pow(i, 2);
        }

        addMultiTrajets();

        generateNoise(multiTrajet, informationEmise);

        emettre();
    }

    /**
     * Retourne l'information avec trajets multiples.
     * @return l'information avec trajets multiples
     */
    public Information<Float> getMultiTrajet() {
        return multiTrajet;
    }

}

