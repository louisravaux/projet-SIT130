package transmetteurs;

import java.util.ArrayList;

public class TransmetteurBruiteMultiTrajets {
    private float snr;
    private int nb_sample;
    private ArrayList<Float> tau;
    private ArrayList<Float> a;
    public TransmetteurBruiteMultiTrajets(float snr, int nb_sample) {
        this.snr = snr;
        this.nb_sample = nb_sample;
    }
}
