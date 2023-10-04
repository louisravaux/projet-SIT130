package codeurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

import java.util.LinkedList;

/**
 * Classe représentant un décodeur. Elle fonctionne de pair avec la classe CodageEmission
 * Elle décode les 101 en 1 et les 010 en 0
 */
public class DecodageReception extends Codeur<Boolean> {

    /**
     * ඞ
     * Décode le signal codé reçu
     * */
    public void conversion() {
        for (int i = 0; i < informationRecue.nbElements()-1; i+=3) {

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
