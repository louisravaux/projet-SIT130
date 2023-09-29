package transmetteurs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.sun.source.tree.ArrayAccessTree;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteMultiTrajets extends TransmetteurBruiteAnalogique {
    protected float p_signal;
    protected float p_noise;
    private final ArrayList<Integer> tau;
    private final ArrayList<Float> ar;
    private Information<Float> multiTrajets;

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
    }

    private int tauMax() {
        int max = tau.get(0);

        for(int val : tau) {
            if(val > max) {
                max = val;
            }
        }
        return max;
    }

    int j = 0;
    public void addMultiTrajets() {
        multiTrajets = informationRecue;
        for(int i=0; tau.get(i) != 0; i++) {
            for(int j=0; j<multiTrajets.nbElements(); j++) {
                if(j < tau.get(i)) {
                    multiTrajets.setIemeElement(j, multiTrajets.iemeElement(j));
                }
                else {
                    multiTrajets.setIemeElement(j, multiTrajets.iemeElement(j) + ar.get(i) * informationRecue.iemeElement(j-tau.get(i)));
                }
            }
        }
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        int tauMAx = tauMax();

        for(int i = 0; i < information.nbElements() + tauMAx; i++) {
            if(i<information.nbElements()) {
                informationRecue.add(information.iemeElement(i));
            }
            else {
                informationRecue.add(0f);
            }
        }

        addMultiTrajets();

        generateNoise(multiTrajets, informationEmise);

        emettre();
    }

}

