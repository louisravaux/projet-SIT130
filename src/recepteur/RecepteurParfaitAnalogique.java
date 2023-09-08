package recepteur;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class RecepteurParfaitAnalogique extends Recepteur<Double, Double> {
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
