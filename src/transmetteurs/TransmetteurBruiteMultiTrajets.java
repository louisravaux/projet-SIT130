package transmetteurs;

import java.util.ArrayList;
import java.util.Random;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteMultiTrajets extends TransmetteurBruiteAnalogique {

    /**
     * Constructeur de la classe TransmetteurBruiteAnalogique.
     *
     * @param snr
     * @param nb_sample
     */
    public TransmetteurBruiteMultiTrajets(float snr, int nb_sample) {
        super(snr, nb_sample);
    }



}
