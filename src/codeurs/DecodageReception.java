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
        for (int i = 0; i < informationRecue.nbElements()-2; i++) {
            if (!informationRecue.iemeElement(i) && !informationRecue.iemeElement(i+1) && !informationRecue.iemeElement(i+2)) {
                informationEmise.add(false);
            } else if (!informationRecue.iemeElement(i) && !informationRecue.iemeElement(i+1) && informationRecue.iemeElement(i+2)) {
                informationEmise.add(true);
            } else if (!informationRecue.iemeElement(i) && informationRecue.iemeElement(i+1) && !informationRecue.iemeElement(i+2)) {
                informationEmise.add(false);
            } else if (!informationRecue.iemeElement(i) && informationRecue.iemeElement(i+1) && informationRecue.iemeElement(i+2)) {
                informationEmise.add(false);
            } else if (informationRecue.iemeElement(i) && !informationRecue.iemeElement(i+1) && !informationRecue.iemeElement(i+2)) {
                informationEmise.add(true);
            } else if (informationRecue.iemeElement(i) && !informationRecue.iemeElement(i+1) && informationRecue.iemeElement(i+2)) {
                informationEmise.add(true);
            } else if (informationRecue.iemeElement(i) && informationRecue.iemeElement(i+1) && !informationRecue.iemeElement(i+2)) {
                informationEmise.add(false);
            }
        }
    }

}
