package tests;

import destinations.DestinationFinaleAnalogique;
import emetteur.EmetteurParfaitAnalogique;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sources.SourceAleatoire;
import transmetteurs.TransmetteurParfait;
import transmetteurs.TransmetteurParfaitAnalogique;

import static org.junit.jupiter.api.Assertions.fail;

public class DestinationFinaleAnalogiqueTest {
    EmetteurParfaitAnalogique s;
    TransmetteurParfaitAnalogique t;
    DestinationFinaleAnalogique d;

    @BeforeEach
    void init() throws Exception {
        s = new EmetteurParfaitAnalogique(0,2,30,"NRZ");
        t = new TransmetteurParfaitAnalogique();
        d = new DestinationFinaleAnalogique();

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
