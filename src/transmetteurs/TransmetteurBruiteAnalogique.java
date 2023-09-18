package transmetteurs;

import java.util.Random;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteAnalogique extends Transmetteur<Float, Float> {
	private float snr;
    private int nb_sample;
	
	public TransmetteurBruiteAnalogique(float snr, int nb_sample) {
        this.snr = (float) Math.pow(snr/10, 10);
        this.nb_sample = nb_sample;
		informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
    	// vars
    	float p_signal = 0f;
    	float p_noise;
    	float sigma_noise;
        float bruit = 0f;
    	Random a1;
        Random a2;
        
        // Calcul puissance du signal
        for (Float i : information) {
            informationRecue.add(i);
    		p_signal += (float) Math.pow(i, 2);
        }
        p_signal /= informationRecue.nbElements();
        
        // Calcul puissance du bruit
        p_noise = p_signal/snr;
        
        // Calcul sigma
        sigma_noise = (float) Math.sqrt(p_noise);
        
        // Generation du bruit gaussien
        for (Float i : information) {
            a1 = new Random();
            a2 = new Random();
    		bruit = (float) (sigma_noise*Math.sqrt(-2*Math.log(1-a1.nextFloat()))*Math.cos(2*Math.PI*a2.nextFloat()));
    		informationRecue.add(i);
            informationEmise.add(i+bruit);
        }
        
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
