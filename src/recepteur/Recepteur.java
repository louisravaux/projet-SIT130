package recepteur;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

/**
 * Classe abstraite d'un récepteur. Elle implémente les interfaces DestinationInterface et SourceInterface.
 *
 * @param <R> Le type de données reçues par le récepteur.
 * @param <E> Le type de données émises par le récepteur.
 */
public abstract class Recepteur<R, E> implements DestinationInterface<R>, SourceInterface<E> {
	protected LinkedList<DestinationInterface<E>> destinationsConnectees;
	protected Information<R> informationRecue;
	protected Information<E> informationEmise;
	protected int vmax;
	protected int vmin;

	/**
	 * Constructeur par défaut de la classe Recepteur.
	 * Initialise les attributs à leurs valeurs par défaut.
	 */
	public Recepteur() {
		destinationsConnectees = new LinkedList<DestinationInterface<E>>();
		informationRecue = null;
		informationEmise = null;
		vmax = 0;
		vmin = 0;
	}

	/**
	 * Obtient l'information reçue par le récepteur.
	 *
	 * @return L'information reçue.
	 */
	public Information<R> getInformationRecue() {
		return this.informationRecue;
	}

	/**
	 * Obtient l'information émise par le récepteur.
	 *
	 * @return L'information émise.
	 */
	public Information<E> getInformationEmise() {
		return this.informationEmise;
	}

	/**
	 * Connecte ce récepteur à une destination.
	 *
	 * @param destination La destination à connecter.
	 */
	public void connecter(DestinationInterface<E> destination) {
		destinationsConnectees.add(destination);
	}

	/**
	 * Déconnecte ce récepteur d'une destination.
	 *
	 * @param destination La destination à déconnecter.
	 */
	public void deconnecter(DestinationInterface<E> destination) {
		destinationsConnectees.remove(destination);
	}

	/**
	 * Méthode statique pour convertir une valeur logique en valeur analogique.
	 *
	 * @param boolv La valeur logique à convertir.
	 * @return La valeur analogique correspondante (1.0f pour vrai, 0.0f pour faux).
	 */
	public static float convertLogAnalog(Boolean boolv) {
		return boolv ? 1.0f : 0.0f;
	}

	/**
	 * Méthode statique pour convertir une valeur analogique en valeur logique.
	 *
	 * @param floatv La valeur analogique à convertir.
	 * @return La valeur logique correspondante (vrai si différente de 0.0f, faux sinon).
	 */
	public static boolean convertAnalogLog(Float floatv) {
		return floatv != 0.0f;
	}

	/**
	 * Méthode abstraite pour recevoir une information de type R.
	 *
	 * @param information L'information à recevoir.
	 * @throws InformationNonConformeException Si l'information reçue n'est pas conforme.
	 */
	public abstract void recevoir(Information<R> information) throws InformationNonConformeException;

	/**
	 * Émet l'information aux destinations connectées.
	 *
	 * @throws InformationNonConformeException Si l'information émise n'est pas conforme.
	 */
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<E> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	};
}
