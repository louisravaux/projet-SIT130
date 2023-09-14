package transmetteurs;

import java.util.Random;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurBruiteAnalogique extends Transmetteur<Float, Float> {
	private float snr;
	
	public TransmetteurBruiteAnalogique(float snr) {
        this.snr = (float) Math.pow(10, snr/10);
		informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
    	// vars
    	float p_signal = 0f;
    	float p_noise = 0f;
    	float sigma_noise = 0f;
    	Random a1 = new Random();
        Random a2 = new Random();
        
        // Calcul puissance du signal
        for (Float i : informationRecue) {
    		p_signal += Math.pow(i, 2);
        }
        p_signal /= informationRecue.nbElements();
        
        // Calcul puissance du bruit
        p_noise = p_signal/snr;
        
        // Calcul sigma
        sigma_noise = (float) Math.sqrt(p_noise);
        
        // Generation du bruit gaussien
        for (Float i : information) {
    		i += (float) (sigma_noise*Math.sqrt(-2*Math.log(1-a1.nextFloat()))*Math.cos(2*Math.PI*a2.nextFloat()));
    		informationRecue.add(i);
        }
        
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = informationRecue;
    }
}
