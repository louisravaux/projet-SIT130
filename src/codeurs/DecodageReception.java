package codeurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

import java.util.LinkedList;

public class DecodageReception extends Codeur<Boolean> {

    public DecodageReception() {
        super();
    }

    public void conversion() {
        for (int i = 0; i < informationRecue.nbElements(); i+=3) {

            Boolean a = informationRecue.iemeElement(i);
            Boolean b = informationRecue.iemeElement(i+1);
            Boolean c = informationRecue.iemeElement(i+2);

            if (!a && !b && !c || !a && b && !c || !a && b && c || a && b && !c) {
                informationEmise.add(false);
            } else {
                informationEmise.add(true);
            }
        }
    }

}
