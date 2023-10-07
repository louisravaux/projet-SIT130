package tests;

import destinations.DestinationFinale;
import information.InformationNonConformeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sources.SourceAleatoire;
import sources.SourceFixe;
import transmetteurs.TransmetteurParfait;

import static org.junit.jupiter.api.Assertions.*;

class DestinationFinaleTest {

    SourceAleatoire s;
    TransmetteurParfait t;
    DestinationFinale d;

    @BeforeEach
    void init() throws Exception {
        s = new SourceAleatoire(6);
        t = new TransmetteurParfait();
        d = new DestinationFinale();

        s.connecter(t);
        t.connecter(d);

        s.emettre();
    }

    @Test
    void recevoir() {

        for (int i = 0; i < s.getInformationEmise().nbElements(); i++) {
            if (s.getInformationEmise().iemeElement(i) != d.getInformationRecue().iemeElement(i)) {
                fail("Les informations recues sont incorrectes ou manquantes");
            }
        }

    }
}