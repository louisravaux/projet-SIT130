package emetteur;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

public abstract class Emetteur <R,E> implements  DestinationInterface <R>, SourceInterface <E> {

	protected LinkedList <DestinationInterface <E>> destinationsConnectees; // liste des destinations connectées
	protected Information <R>  informationRecue; // information reçue en entrée
	protected Information <E>  informationEmise; // information émise en sortie
	protected float vmax; // valeur maximale du signal analogique
	protected float vmin; // valeur minimale du signal analogique

	/**
	 * Constructeur de la classe Emetteur.
	 */
	public Emetteur() {
		destinationsConnectees = new LinkedList <DestinationInterface <E>> ();
		informationRecue = null;
		informationEmise = null;
		vmax = 0;
		vmin = 0;
	}

	/**
	 * Récupère l'information reçue en entrée.
	 *
	 * @return L'information reçue en entrée.
	 */
	public Information <R>  getInformationRecue() {
		return this.informationRecue;
	}

	/**
	 * Récupère l'information émise en sortie.
	 *
	 * @return L'information émise en sortie.
	 */
	public Information <E>  getInformationEmise() {
		return this.informationEmise;
	}

	/**
	 * Connecte une destination à l'émetteur.
	 *
	 * @param destination La destination à connecter.
	 */
	public void connecter (DestinationInterface <E> destination) {
		destinationsConnectees.add(destination); 
	}

	/**
	 * Déconnecte une destination de l'émetteur.
	 *
	 * @param destination La destination à déconnecter.
	 */
	public void deconnecter (DestinationInterface <E> destination) {
		destinationsConnectees.remove(destination); 
	}

	/**
	 * Convertit un signal de type R en un signal de type E.
	 */
	public abstract void convert();

	/**
	 * Reçoit une information.
	 *
	 * @param information L'information reçue.
	 */
	public  abstract void recevoir(Information <R> information) throws InformationNonConformeException;

	/**
	 * Émet une information vers les destinations connectées.
	 */
	public  abstract void emettre() throws InformationNonConformeException;
	
}
