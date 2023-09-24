package transmetteurs;

import java.util.Random;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe d'un transmetteur qui transmet des informations float en ajoutant un bruit gaussien.
 *
 */
public class TransmetteurBruiteAnalogique extends Transmetteur<Float, Float> {
	protected final float snr;
    protected float p_signal;
    protected float p_noise;
    protected float sigma_noise;
    protected final Random a1;
    protected final Random a2;
    protected final int nb_sample;

    /**
     * Constructeur de la classe TransmetteurBruiteAnalogique.
     * @param snr bruit ajouté au signal (en dB)
     * @param nb_sample nombre d'échantillons par symbole
     */
	public TransmetteurBruiteAnalogique(float snr, int nb_sample) {
        this.nb_sample = nb_sample;
        this.snr = (float) Math.pow(10, snr/10);
        a1 = new Random();
        a2 = new Random();
		informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    /**
     * Calcul de la puissance du signal
     */
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

        // Generation du bruit gaussien
        for (Float i : informationEntree) {
            float bruit = (float) (sigma_noise * Math.sqrt(-2 * Math.log(1 - a1.nextFloat())) * Math.cos(2 * Math.PI * a2.nextFloat()));
            informationSortie.add(i + bruit);
        }
    }

    /**
     * Réception des informations et génération du bruit.
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {

        // reception des informations
        for (Float i : information) {
            informationRecue.add(i);
            p_signal += (float) Math.pow(i, 2);
        }

        // génération du bruit
        generateNoise(informationRecue, informationEmise);

        // émission des informations apres réception et génération du bruit
        emettre();
    }

    /**
     * Emission des informations à toutes les destinations connectées.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
