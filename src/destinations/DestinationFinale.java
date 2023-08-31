package destinations;

import information.Information;
import information.InformationNonConformeException;

public class DestinationFinale extends Destination<Boolean> {
	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {	
		informationRecue = new Information<Boolean>();
		for(int i=0; i<information.nbElements(); i++) {
			informationRecue.add((Boolean)information.iemeElement(i));
		}
	}
}
