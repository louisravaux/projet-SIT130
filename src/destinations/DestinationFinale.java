package destinations;

import information.Information;
import information.InformationNonConformeException;

/**
 * La classe DestinationFinale représente une destination pour une information de type booléen.
 * Elle hérite de la classe Destination et permet de recevoir une Information<Boolean> et de stocker son contenu.
 */
public class DestinationFinale extends Destination<Boolean> {

	/**
	 * Constructeur par défaut de la classe DestinationFinale.
	 * Il crée une nouvelle instance de DestinationFinale et initialise l'information reçue avec une nouvelle
	 * instance d'Information<Boolean>.
	 */
	public DestinationFinale() {
		super();
		informationRecue = new Information<Boolean>();
	}

	/**
	 * Méthode permettant de recevoir une Information<Boolean> et de stocker son contenu.
	 *
	 * @param information L'information de type booléen à recevoir.
	 * @throws InformationNonConformeException Si l'information n'est pas conforme.
	 */
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {

		for (Boolean i : information) {
			informationRecue.add(i);
		}

	}
}

