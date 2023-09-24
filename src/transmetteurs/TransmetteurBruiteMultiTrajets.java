package transmetteurs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.sun.source.tree.ArrayAccessTree;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteMultiTrajets extends TransmetteurBruiteAnalogique {

    private final int tau;
    private final float ar;
    private final Information<Float> multiTrajets;

    /**
     * Constructeur de la classe TransmetteurBruiteAnalogique.
     *
     * @param snr bruit ajouté au signal (en dB)
     * @param nb_sample nombre d'échantillons par symbole
     */
    public TransmetteurBruiteMultiTrajets(float snr, int nb_sample, int tau, float ar) {
        super(snr, nb_sample);
        multiTrajets = new Information<>();
        this.tau = tau;
        this.ar = ar;
    }

    public void addMultiTrajets() {
        for (int i = tau; i < informationRecue.nbElements(); i++) {
            // r(t) = s(t) + a*r(t-tau)
            multiTrajets.add(informationRecue.iemeElement(i) + ar * informationRecue.iemeElement(i - tau));
        }
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        // reception des informations
        for (Float i : information) {
            informationRecue.add(i);
            p_signal += (float) Math.pow(i, 2);
        }
        addMultiTrajets();

        generateNoise(multiTrajets, informationEmise);

        emettre();
    }

}

