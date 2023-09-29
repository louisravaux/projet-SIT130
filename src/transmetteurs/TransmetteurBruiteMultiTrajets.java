package transmetteurs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.sun.source.tree.ArrayAccessTree;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteMultiTrajets extends TransmetteurBruiteAnalogique {
    private final ArrayList<Integer> tau;
    private final ArrayList<Float> ar;

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

    //TODO remove this and modify TransmetteurBruiteAnalogique
    public void generateNoise(Information<Float> informationEntree, Information<Float> informationSortie) {

        calculateSignalPower();
        calculateNoisePower();
        calculateSigmaNoise();

        // Generation du bruit gaussien
        for(int i = 0; i < informationEntree.nbElements(); i++) {
            float bruit = (float) (sigma_noise * Math.sqrt(-2 * Math.log(1 - a1.nextFloat())) * Math.cos(2 * Math.PI * a2.nextFloat()));
            informationSortie.setIemeElement(i, informationEntree.iemeElement(i)+bruit);
        }
    }

    public void addMultiTrajets() {
        for(int i=0; tau.get(i) != 0; i++) {
            for(int j=0; j<informationEmise.nbElements(); j++) {
                if(j < tau.get(i)) {
                    informationEmise.setIemeElement(j, informationEmise.iemeElement(j));
                }
                else {
                    informationEmise.setIemeElement(j, informationEmise.iemeElement(j) + ar.get(i) * informationEmise.iemeElement(j-tau.get(i)));
                }
            }
        }
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {

        for (Float i : information) {
            informationRecue.add(i);
            informationEmise.add(i);
            p_signal += (float) Math.pow(i, 2);
        }

        addMultiTrajets();

        generateNoise(informationRecue, informationEmise);

        emettre();
    }

}

