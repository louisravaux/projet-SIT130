package emetteur;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

public abstract class Emetteur <R,E> implements  DestinationInterface <R>, SourceInterface <E> {
	protected LinkedList <DestinationInterface <E>> destinationsConnectees;
	protected Information <R>  informationRecue;
	protected Information <E>  informationEmise;
	protected int tmax;
	protected int tmin;
	
	public Emetteur() {
		destinationsConnectees = new LinkedList <DestinationInterface <E>> ();
		informationRecue = null;
		informationEmise = null;
		tmax = 0;
		tmin = 0;
	}
	
	public Information <R>  getInformationRecue() {
		return this.informationRecue;
	}
	
	public Information <E>  getInformationEmise() {
		return this.informationEmise;
	}
	
	public void connecter (DestinationInterface <E> destination) {
		destinationsConnectees.add(destination); 
	}
	
	public void deconnecter (DestinationInterface <E> destination) {
		destinationsConnectees.remove(destination); 
	}
	
	public  abstract void recevoir(Information <R> information) throws InformationNonConformeException;
	
	public  abstract void emettre() throws InformationNonConformeException;
	
}
