package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurParfait extends Transmetteur<Boolean,Boolean> {
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
		informationRecue = new Information<Boolean>();
		for(int i=0; i<information.nbElements(); i++) {
			informationRecue.add((Boolean)information.iemeElement(i));
		}
		emettre();
	}

	public void emettre() throws InformationNonConformeException {
		informationEmise = new Information<Boolean>();
		//this.informationRecue.setIemeElement(0,false);    //Ajout d'une erreur
		for(int i=0; i<informationRecue.nbElements(); i++) {
			informationEmise.add((Boolean)informationRecue.iemeElement(i));
		}
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
		}
	}
}
