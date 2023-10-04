package recepteur;

import information.Information;
import information.InformationNonConformeException;

/**
 * Classe représentant un récepteur parfait analogique. Cette classe hérite de la classe abstraite Recepteur
 * et implémente la réception d'informations analogiques et leur conversion en informations binaires.
 */
public class RecepteurParfaitAnalogique extends Recepteur<Float, Boolean> {
	private final int nb_samples;
	private final String form;
	private int i;
	private int j;
	private float somme;
	private final float esperance;
	private final int nbBits;
	private final int thirdSample;

	/**
	 * Constructeur de la classe RecepteurParfaitAnalogique.
	 *
	 * @param vmin      La valeur minimale de l'amplitude analogique.
	 * @param vmax      La valeur maximale de l'amplitude analogique.
	 * @param nb_samples Le nombre d'échantillons à utiliser pour la conversion.
	 * @param form      Le format de conversion (RZ, NRZ ou NRZT).
	 */
	public RecepteurParfaitAnalogique(float vmin, float vmax, int nb_samples, String form) {
		this.nb_samples = nb_samples;
		this.form = form;
		informationRecue = new Information<>(); // float
		informationEmise = new Information<>(); // boolean

		i = 0;
		j = 0;
		somme = 0;
		esperance = (vmin + vmax) / 2;
		nbBits = informationRecue.nbElements()/nb_samples;
		thirdSample = nb_samples / 3;
	}

	/**
	 * Méthode pour recevoir une information analogique en utilisant le format NRZ.
	 */
	public void receiveNRZ() {
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
	}

	/**
	 * Méthode pour recevoir une information analogique en utilisant le format RZ.
	 */
	public void receiveRZ() {
		for (Float f : informationRecue) {
			if (j < thirdSample) {
				j++;
			} else if (j < thirdSample*2){
				j++;
				somme += f;
				if (j >= ((thirdSample*2))){
					if (somme / ((float) thirdSample) > esperance) {
						informationEmise.add(true);
					} else {
						informationEmise.add(false);
					}
				}
			}
			else if (j >= (thirdSample * 2)) {
				j++;
				if (j == nb_samples) {
					j = 0;
					somme = 0;
				}
			}
		}
	}

	/**
	 * Méthode pour recevoir une information analogique en utilisant le format NRZT.
	 */
	public void receiveNRZT() {
		for (Float f : informationRecue) {
			j++;
			// Calcul somme
			if (i == 0) {
				if (j > thirdSample) {
					somme += f;
				}
			} else if (i == nbBits - 1) {
				if (j < 2 * thirdSample) {
					somme += f;
				}
			} else {
				somme += f;
			}
			// Determination si 0 ou 1
			if (j == nb_samples) {
				if (i == 0) {
					if (somme / (2 * thirdSample) > esperance) {
						informationEmise.add(true);
					} else {
						informationEmise.add(false);
					}
				} else if (i == nbBits - 1) {
					if (somme / (2 * thirdSample) > esperance) {
						informationEmise.add(true);
					} else {
						informationEmise.add(false);
					}
				} else if (somme / nb_samples > esperance) {
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

	/**
	 * Méthode pour recevoir une information analogique, la traiter selon le format spécifié,
	 * puis émettre l'information binaire résultante aux destinations connectées.
	 *
	 * @param information L'information analogique à recevoir.
	 * @throws InformationNonConformeException Si l'information reçue n'est pas conforme.
	 */
	public void recevoir(Information<Float> information) throws InformationNonConformeException {

		for (Float i : information) {
			informationRecue.add(i);
		}

        switch (form) {
            case "RZ" -> {
				receiveRZ();
			}
            case "NRZ" -> {
				receiveNRZ();
            }
            case "NRZT" -> {
				receiveNRZT();
            }
        }

		emettre();
	}
}
