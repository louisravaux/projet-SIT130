package emetteur;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class EmetteurParfaitAnalogique extends Emetteur<Double, Double> {
	public void recevoir(Information<Double> information) throws InformationNonConformeException {
		for (Double i : information) {
            informationRecue.add(i);
        }
	}
	
	public void emettre() throws InformationNonConformeException {
		for (DestinationInterface<Double> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
		this.informationEmise = informationRecue;
	}
}
