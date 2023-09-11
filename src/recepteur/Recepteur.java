package recepteur;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

public abstract class Recepteur <R,E> implements  DestinationInterface <R>, SourceInterface <E> {
	protected LinkedList <DestinationInterface <E>> destinationsConnectees;
	protected Information <R>  informationRecue;
	protected Information <E>  informationEmise;
	protected int vmax;
	protected int vmin;
	
	
	public Recepteur() {
		destinationsConnectees = new LinkedList <DestinationInterface <E>> ();
		informationRecue = null;
		informationEmise = null;
		vmax = 0;
		vmin = 0;
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

	public static float convertLogAnalog(Boolean boolv) {
		return boolv ? 1.0f : 0.0f;
	}
	
	public static boolean convertAnalogLog(Float floatv) {
		return floatv != 0.0f;
	}
	
	public  abstract void recevoir(Information <R> information) throws InformationNonConformeException;
	
	public  abstract void emettre() throws InformationNonConformeException;
	
	
}
