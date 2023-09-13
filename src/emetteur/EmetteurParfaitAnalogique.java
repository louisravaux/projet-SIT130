package emetteur;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class EmetteurParfaitAnalogique extends Emetteur<Boolean, Float> {
	private final float vmin;
	private final float vmax;
	private final int nb_samples;
	private final String form;
	
	public EmetteurParfaitAnalogique(float vmin, float vmax, int nb_samples, String form) {
		this.vmin = vmin;
		this.vmax = vmax;
		this.nb_samples = nb_samples;
		this.form = form;
		informationRecue = new Information<>(); // boolean
		informationEmise = new Information<>(); // float
	}

	public void convertRZ() {
		for (Boolean b : informationRecue) {
			for (int i = 0; i < nb_samples / 3; i++) {
				informationEmise.add(0.0f);
			}
			for (int i = 0; i < nb_samples / 3; i++) {
				if (b) {
					informationEmise.add(vmax);
				} else {
					informationEmise.add(vmin);
				}
			}
			for (int i = 0; i < nb_samples / 3; i++) {
				informationEmise.add(0.0f);
			}
		}
	}

	public void convertNRZ() {
		for (Boolean b : informationRecue) {
			for (int i = 0; i < nb_samples; i++) {
				if (b) {
					informationEmise.add(vmax);
				} else {
					informationEmise.add(vmin);
				}
			}
		}
	}

	public void convertNRZT() {
		for (int i = 0; i<informationRecue.nbElements(); i++) {

			if (i != 0 && informationRecue.iemeElement(i-1) == informationRecue.iemeElement(i) ) {
				// TODO remove duplicate code
				// convert 1 or 0 to vmax or vmin
				for (int j = 0; j < nb_samples / 3; j++) {
					if (informationRecue.iemeElement(i)) {
						informationEmise.add(vmax);
					} else {
						informationEmise.add(vmin);
					}
				}
			} else {
				// first ramp
				for (int j = 0; j < nb_samples / 3; j++) {
					// checking the ramp orientation
					if (informationRecue.iemeElement(i)) informationEmise.add(j * vmax / (nb_samples / 3));
					else informationEmise.add(j * vmin / (nb_samples / 3));
				}
			}

			// TODO remove duplicate code
			// convert 1 or 0 to vmax or vmin
			for (int j = 0; j < nb_samples / 3; j++) {
				if (informationRecue.iemeElement(i)) {
					informationEmise.add(vmax);
				} else {
					informationEmise.add(vmin);
				}
			}

			if (i != informationRecue.nbElements()-1 && informationRecue.iemeElement(i+1) == informationRecue.iemeElement(i) ) {
				// TODO remove duplicate code
				// convert 1 or 0 to vmax or vmin
				for (int j = 0; j < nb_samples / 3; j++) {
					if (informationRecue.iemeElement(i)) {
						informationEmise.add(vmax);
					} else {
						informationEmise.add(vmin);
					}
				}
			} else {
				//second ramp
				for (int j = 0; j < nb_samples / 3; j++) {
					// checking the ramp orientation
					if (informationRecue.iemeElement(i)) informationEmise.add(vmax - (j * vmax) / (nb_samples / 3));
					else informationEmise.add(vmin - (j * vmin) / (nb_samples / 3));
				}
			}

		}
	}

	public void convert(Information<Boolean> receivedInformation) {

		if (form.equals("RZ")) {
			convertRZ();
		}
		if (form.equals("NRZ")) {
			convertNRZ();
		}
		if (form.equals("NRZT")) {
			convertNRZT();
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
