package destinations;

import information.InformationNonConformeException;
import org.junit.jupiter.api.Test;
import sources.SourceAleatoire;
import sources.SourceFixe;
import transmetteurs.TransmetteurParfait;

import static org.junit.jupiter.api.Assertions.*;

class DestinationFinaleTest {

    @Test
    void recevoir() throws Exception {

        SourceAleatoire s = new SourceAleatoire(6, 1313);
        TransmetteurParfait t = new TransmetteurParfait();
        DestinationFinale d = new DestinationFinale();

        s.connecter(t);
        t.connecter(d);

        s.emettre();

        for (int i = 0; i < s.getInformationEmise().nbElements(); i++) {
            if (s.getInformationEmise().iemeElement(i) != d.getInformationRecue().iemeElement(i)) {
                fail("Les informations recues sont incorrectes ou manquantes");
            }
        }

    }
}