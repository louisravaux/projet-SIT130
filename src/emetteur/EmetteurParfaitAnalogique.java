package emetteur;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * La classe EmetteurParfaitAnalogique permet de convertir un signal booléen en signal analogique.
 * Cette conversion peut se faire selon trois formats : RZ, NRZ ou NRZT.
 * L'émetteur génère un signal analogique en fonction des valeurs booléennes d'entrée et des paramètres
 * de conversion spécifiés lors de l'initialisation de l'objet.
 *
 * @param <T> Le type de données d'entrée (Boolean ici).
 * @param <R> Le type de données de sortie (Float ici).
 */
public class EmetteurParfaitAnalogique extends Emetteur<Boolean, Float> {

	private final float vmin; // Valeur maximal du signal analogique
	private final float vmax; // Valeur minimal du signal analogique
	private final int nb_samples; // Nombre d'échantillons par symbole
	private final String form; // Format de conversion (RZ, NRZ, NRZT)

	/**
	 * Constructeur de la classe EmetteurParfaitAnalogique.
	 *
	 * @param vmin       La valeur minimale du signal analogique.
	 * @param vmax       La valeur maximale du signal analogique.
	 * @param nb_samples Le nombre d'échantillons par symbole.
	 * @param form       Le format de conversion (RZ, NRZ, NRZT).
	 */
	public EmetteurParfaitAnalogique(float vmin, float vmax, int nb_samples, String form) {
		this.vmin = vmin;
		this.vmax = vmax;
		this.nb_samples = nb_samples;
		this.form = form;
		informationRecue = new Information<>(); // boolean
		informationEmise = new Information<>(); // float
	}

	/**
	 * Convertit le signal booléen en signal analogique au format RZ (Return-to-Zero).
	 */
	public void convertRZ() {
		for (Boolean b : informationRecue) {
			for (int i = 0; i < (int) (nb_samples / 3); i++) {
				informationEmise.add(0.0f);
			}
			for (int i = 0; i < nb_samples - 2 * ((int) (nb_samples / 3)); i++) {
				if (b) {
					informationEmise.add(vmax);
				} else {
					informationEmise.add(vmin);
				}
			}
			for (int i = 0; i < (int) (nb_samples / 3); i++) {
				informationEmise.add(0.0f);
			}
		}
	}

	/**
	 * Convertit le signal booléen en signal analogique au format NRZ (Non-Return-to-Zero).
	 */
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

	/**
	 * Convertit le signal booléen en signal analogique au format NRZT.
	 */
	public void convertNRZT() {
		for (int i = 0; i<informationRecue.nbElements(); i++) {
			// Premier 1/3
			if (i != 0 && informationRecue.iemeElement(i-1) == informationRecue.iemeElement(i) ) {
				for (int j = 0; j < (int) (nb_samples / 3); j++) {
					if (informationRecue.iemeElement(i)) {
						informationEmise.add(vmax);
					} else {
						informationEmise.add(vmin);
					}
				}
			} else {
				for (int j = 0; j < (int) (nb_samples / 3); j++) {
					// checking the ramp orientation
					if (informationRecue.iemeElement(i)) {
						informationEmise.add(j * vmax / (nb_samples / 3));
					}
					else {
						informationEmise.add(j * vmin / (nb_samples / 3));
					}
				}
			}

			// Deuxieme 1/3
			for (int j = 0; j < nb_samples - 2 * ((int) (nb_samples / 3)); j++) {
				if (informationRecue.iemeElement(i)) {
					informationEmise.add(vmax);
				} else {
					informationEmise.add(vmin);
				}
			}

			// Troisieme 1/3
			if (i != informationRecue.nbElements()-1 && informationRecue.iemeElement(i+1) == informationRecue.iemeElement(i) ) {
				for (int j = 0; j < (int) (nb_samples / 3); j++) {
					if (informationRecue.iemeElement(i)) {
						informationEmise.add(vmax);
					} else {
						informationEmise.add(vmin);
					}
				}
			} else {
				for (int j = 0; j < (int) (nb_samples / 3); j++) {
					// checking the ramp orientation
					if (informationRecue.iemeElement(i)) {
						informationEmise.add(vmax - (j * vmax) / (nb_samples / 3));
					}
					else {
						informationEmise.add(vmin - (j * vmin) / (nb_samples / 3));
					}
				}
			}

		}
	}

	/**
	 * Convertit le signal booléen en signal analogique en fonction du format spécifié.
	 */
	public void convert() {

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

	/**
	 * Reçoit une information booléenne et l'ajoute à la liste des informations reçues.
	 * Convertit ensuite les données reçues en données analogiques et les ajoute à la liste des informations émises.
	 * Enfin, émet les données converties.
	 *
	 * @param information L'information booléenne reçue.
	 * @throws InformationNonConformeException Si l'information reçue est invalide.
	 */
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {

		// receiving data
		for (Boolean i : information) {
			informationRecue.add(i);
		}

		// convert data from boolean to float
		convert();

		// instantly emit converted data
		emettre();
	}

	/**
	 * Émet les données converties.
	 *
	 * @throws InformationNonConformeException Si l'information émise est invalide.
	 */
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
	}
}
