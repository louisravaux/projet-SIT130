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
		this.vmin = vmin;
		this.vmax = vmax;
		this.nb_samples = nb_samples;
		this.form = form;
		informationRecue = new Information<>(); //boolean
		informationEmise = new Information<>(); // float
	}

	public void convert(Information<Boolean> receivedInformation) {

		if(form.equals("RZ")) {
			for(Boolean b : informationRecue) {
				for(int i=0; i<nb_samples; i++) {
					if(b) {
						informationEmise.add(vmax);
					}
					else {
						informationEmise.add(vmin);
					}
				}
				for(int i=0; i<nb_samples; i++) {
					informationEmise.add(0.0f);
				}
			}
		}
		if(form.equals("NRZ")) {
			for(Boolean b : informationRecue) {
				for(int i=0; i<nb_samples; i++) {
					if(b) {
						informationEmise.add(vmax);
					}
					else {
						informationEmise.add(vmin);
					}
				}
				for(int i=0; i<nb_samples; i++) {
					if(b) {
						informationEmise.add(vmax);
					}
					else {
						informationEmise.add(vmin);
					}
				}
			}
		}
		if(form.equals("NRZT")) {
			// TODO
		}

	}

	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {

		// receiving data
		for (Boolean i : information) {
			informationRecue.add(i);
		}

		// convert data from boolean to float
		convert(informationRecue);

		// instantly emit converted data
		emettre();
	}
	
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
	}
}
