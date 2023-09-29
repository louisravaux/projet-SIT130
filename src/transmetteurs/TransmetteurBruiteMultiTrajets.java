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

    public void calculateSignalPower() {
        p_signal /= informationRecue.nbElements();
    }

    /**
     * Calcul de la puissance du bruit.
     */
    public void calculateNoisePower() {
        p_noise = p_signal/snr;
    }

    /**
     * Calcul de l'écart type du bruit.
     */
    public void calculateSigmaNoise() {
        sigma_noise = (nb_sample*p_noise)/(2*snr);
    }

    /**
     * Génération du bruit gaussien.
     */
    public void generateNoise(Information<Float> informationEntree, Information<Float> informationSortie) {

        calculateSignalPower();
        calculateNoisePower();
        calculateSigmaNoise();

        int j=0;
        // Generation du bruit gaussien
        for(Float i : informationEntree) {
            float bruit = (float) (sigma_noise * Math.sqrt(-2 * Math.log(1 - a1.nextFloat())) * Math.cos(2 * Math.PI * a2.nextFloat()));
            informationSortie.setIemeElement(j, i+bruit);
            j++;
        }
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

    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        informationRecue = information;
        //informationEmise = new Information<Float>(informationRecue.nbElements() + tau );
        int tauMAx = tauMax();

        // TODO
        for(int i = 0; i < informationRecue.nbElements() + tauMAx; i++) {
            if(i<informationRecue.nbElements()) {
                informationEmise.add(informationRecue.iemeElement(i));
            }
        }

        addMultiTrajets();

        generateNoise(multiTrajets, informationEmise);

        emettre();
    }

}

