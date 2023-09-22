package transmetteurs;

import java.util.Random;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteAnalogique extends Transmetteur<Float, Float> {
	private float snr;
    private float p_signal;
    private float p_noise;
    private float sigma_noise;
    private float bruit;
    private Random a1;
    private Random a2;
    private final int nb_sample;

	public TransmetteurBruiteAnalogique(float snr, int nb_sample) {
        this.snr = (float) Math.pow(10, snr/10);
        a1 = new Random();
        a2 = new Random();
        this.nb_sample = nb_sample;
		informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    public void calculateNoisePower() {
        // Calcul puissance du signal
        p_signal /= informationRecue.nbElements();

        // Calcul puissance du bruit
        p_noise = p_signal/snr;
    }

    public void generateNoise(Information<Float> information) {

        calculateNoisePower();

        // Calcul ecart type du bruit
        sigma_noise = (float) Math.sqrt(p_noise);

        // Generation du bruit gaussien
        for (Float i : information) {
            bruit = (float) (sigma_noise*Math.sqrt(-2*Math.log(1-a1.nextFloat()))*Math.cos(2*Math.PI*a2.nextFloat()));
            informationEmise.add(i+bruit);
        }
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {

        // reception des informations
        for (Float i : information) {
            informationRecue.add(i);
            p_signal += (float) Math.pow(i, 2);
        }

        // generating noise
        generateNoise(information);
        
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
