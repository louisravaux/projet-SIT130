package recepteur;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class RecepteurParfaitAnalogique extends Recepteur<Float, Boolean> {
	private float vmin;
	private float vmax;
	private int nb_samples;
	private String form;

	public RecepteurParfaitAnalogique(float vmin, float vmax, int nb_samples, String form) {
		this.vmin = vmin;
		this.vmax = vmax;
		this.nb_samples = nb_samples;
		this.form = form;
		informationRecue = new Information<>(); // float
		informationEmise = new Information<>(); // boolean
	}

	public void recevoir(Information<Float> information) throws InformationNonConformeException {

		for (Float i : information) {
			informationRecue.add(i);
		}
		int i = 0;
		int j = 0;
		float somme = 0;
		float esperance = (vmin + vmax) / 2;
		int nbBits = informationRecue.nbElements()/nb_samples;

		if (form.equals("RZ") ||form.equals("NRZ")) {
			for (Float f : informationRecue) {
				j++;
				somme += f;
				if (j >= nb_samples) {
					if (somme / j > esperance) {
						informationEmise.add(true);
					} else {
						informationEmise.add(false);
					}
					j = 0;
					somme = 0;
				}
			}
		} else if (form.equals("NRZT")) {
			for (Float f : informationRecue) {
				j++;
				// Calcul somme
				if (i == 0) {
					if (j > (int)(nb_samples/3)) {
						somme += f;
					}
				}
				else if (i == nbBits-1) {
					if (j < 2*(int)(nb_samples/3)) {
						somme += f;
					}
				}
				else {
					somme += f;
				}
				// Determination si 0 ou 1
				if (j == nb_samples) {
					if (i == 0) {
						if (somme / (2*(int)(nb_samples/3)) > esperance) {
							informationEmise.add(true);
						} else {
							informationEmise.add(false);
						}
					}
					else if (i == nbBits - 1) {
						if (somme / (2*(int)(nb_samples/3)) > esperance) {
							informationEmise.add(true);
						} else {
							informationEmise.add(false);
						}
					}
					else if (somme / nb_samples > esperance) {
						informationEmise.add(true);
					} else {
						informationEmise.add(false);
					}
					i++;
					j = 0;
					somme = 0;
				}
			}
		}


		emettre();
	}

	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}
}
