package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class TransmetteurParfaitAnalogique extends Transmetteur<Float, Float> {

    public TransmetteurParfaitAnalogique() {
        informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        for (Float i : information) {
            informationRecue.add(i);
        }
        emettre();
    }

    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = informationRecue;
    }
}
