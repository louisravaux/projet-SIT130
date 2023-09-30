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
     * Effectue une opération de mise à jour sur une collection d'informations émises
     * en utilisant une liste de coefficients multiplicatifs et de décalages temporels.
     * Cette méthode parcourt les éléments de la liste "tau" pour appliquer les calculs
     * sur la liste "informationEmise".
     */
    public void addMultiTrajets() {
        multiTrajet = informationRecue;
        for (int i = 0; i < tau.size() && tau.get(i) != 0; i++) {
            for(int j = 0; j < multiTrajet.nbElements(); j++) {
                if(j < tau.get(i)) {
                    multiTrajet.setIemeElement(j, multiTrajet.iemeElement(j));
                }
                else {
                    multiTrajet.setIemeElement(j, multiTrajet.iemeElement(j) + ar.get(i) * informationRecue.iemeElement(j-tau.get(i)));
                }
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
            p_signal += (float) Math.pow(i, 2);
        }

        addMultiTrajets();

        generateNoise(informationRecue, informationEmise);

        emettre();
    }

}

