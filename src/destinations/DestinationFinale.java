package destinations;

import information.Information;
import information.InformationNonConformeException;

public class DestinationFinale extends Destination<Boolean> {

	public DestinationFinale() {
		informationRecue = new Information<Boolean>();
	}

	public void recevoir(Information<Boolean> information) throws InformationNonConformeException {	

		for(int i=0; i<information.nbElements(); i++) {
			informationRecue.add(information.iemeElement(i));
		}

	}
}
