package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

public class TransmetteurParfait extends Transmetteur<Boolean, Boolean>{
    public TransmetteurParfait() {

    }

    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {

    }

    @Override
    public void emettre() throws InformationNonConformeException {

    }
}
