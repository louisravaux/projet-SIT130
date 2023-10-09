package destinations;

import information.Information;
import information.InformationNonConformeException;

public class DestinationFinaleAnalogique extends Destination<Float> {

    public DestinationFinaleAnalogique() {
        informationRecue = new Information<>(); // float
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        for (Float i : information) {
            informationRecue.add(i);
        }
    }
}
