package transmetteurs;

import destinations.DestinationFinale;
import information.InformationNonConformeException;
import org.junit.jupiter.api.Test;
import sources.SourceAleatoire;

import static org.junit.jupiter.api.Assertions.*;

class TransmetteurParfaitTest {

    SourceAleatoire s;
    TransmetteurParfait t;
    DestinationFinale d;

    void init() throws Exception {

        s = new SourceAleatoire(6, 99);
        t = new TransmetteurParfait();
        d = new DestinationFinale();

        s.connecter(t);
        t.connecter(d);

        s.emettre();
    }

    @Test
    void recevoir() throws Exception {
        init();
        for (int i = 0; i < s.getInformationEmise().nbElements(); i++) {
            if (s.getInformationEmise().iemeElement(i) != t.getInformationRecue().iemeElement(i)) {
                fail("Les informations recues sont incorrectes ou manquantes");
            }
        }
    }

    @Test
    void emettre() throws Exception {
        init();

        for (int i = 0; i < s.getInformationEmise().nbElements(); i++) {
            if (t.getInformationEmise().iemeElement(i) != d.getInformationRecue().iemeElement(i)) {
                fail("Les informations recues sont incorrectes ou manquantes");
            }
        }

    }
}