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

		int j = 0;
		float somme = 0;
		float esperance = (vmin + vmax) / 2;

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

		emettre();
	}

	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}
}
