package emetteur;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class EmetteurParfaitAnalogique extends Emetteur<Double, Double> {
	private float amin;
	private float amax;
	private int nb_samples;
	private String form;
	
	public EmetteurParfaitAnalogique(float amin, float amax, int nb_samples, String form) {
		
		// TODO verifications des vars a faire
		
		this.amin = amin;
		this.amax = amax;
		this.nb_samples = nb_samples;
		this.form = form;
	}
	
	public void recevoir(Information<Double> information) throws InformationNonConformeException {
		if(form.equals("RZ")) {
			// TODO 
		}
		if(form.equals("NRZ")) {
			// TODO 
		}
		if(form.equals("NRZT")) {
			// TODO 
		}
	}
	
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Double> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
		this.informationEmise = informationRecue;
	}
}
