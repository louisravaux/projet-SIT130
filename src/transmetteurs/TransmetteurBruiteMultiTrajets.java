package transmetteurs;

import java.util.ArrayList;
import java.util.Random;

import com.sun.source.tree.ArrayAccessTree;
import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteMultiTrajets extends TransmetteurBruiteAnalogique {

    ArrayList<Float> tau;
    ArrayList<Float> a;
    /**
     * Constructeur de la classe TransmetteurBruiteAnalogique.
     *
     * @param snr bruit ajouté au signal (en dB)
     * @param nb_sample nombre d'échantillons par symbole
     */
    public TransmetteurBruiteMultiTrajets(float snr, int nb_sample, ArrayList<Float> tau, ArrayList<Float> a) {
        super(snr, nb_sample);
        this.tau = tau;
        this.a = a;
    }

    public void addMultiTrajets(Information<Float> information) {
        for(Float i : information) {
            for(Float t : tau) {
                //multiX = a*;
                //informationEmise.add(i+multiX);
            }
        }
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        super.recevoir(information);

        generateNoise();

        for(Float i : information) {

        }
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        super.emettre();
    }
}
