package emetteur;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class EmetteurParfaitAnalogique extends Emetteur<Boolean, Float> {
	private float vmin;
	private float vmax;
	private int nb_samples;
	private String form;
	
	public EmetteurParfaitAnalogique(float vmin, float vmax, int nb_samples, String form) {
		
		// TODO verifications des vars a faire
		
		this.vmin = vmin;
		this.vmax = vmax;
		this.nb_samples = nb_samples;
		this.form = form;
	}
	
	
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
		int signalLength = informationRecue.nbElements() * nb_samples * 2; 
		float[] rzSignal = new float[signalLength];
		int index = 0;
		
		if(form.equals("RZ")) {
			for(Boolean b : informationRecue) {
				for(int i=0; i<nb_samples; i++) {
					if(b) {
						rzSignal[index++] = vmax;
					}
					else {
						rzSignal[index++] = vmin;
					}
				}
				for(int i=0; i<nb_samples; i++) {
					if(b) {
						rzSignal[index++] = 0.0f;
					}
					else {
						rzSignal[index++] = 0.0f;
					}
				}
			}
		}
		if(form.equals("NRZ")) {
			for(Boolean b : informationRecue) {
				for(int i=0; i<nb_samples; i++) {
					if(b) {
						rzSignal[index++] = vmax;
					}
					else {
						rzSignal[index++] = vmin;
					}
				}
				for(int i=0; i<nb_samples; i++) {
					if(b) {
						rzSignal[index++] = vmax;
					}
					else {
						rzSignal[index++] = vmin;
					}
				}
			}
		}
		if(form.equals("NRZT")) {
			// TODO 
		}	
	}
	
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
	}
}
