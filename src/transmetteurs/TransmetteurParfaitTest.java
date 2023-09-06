package transmetteurs;

import destinations.DestinationFinale;
import org.junit.jupiter.api.Test;
import sources.SourceAleatoire;

import static org.junit.jupiter.api.Assertions.*;

class TransmetteurParfaitTest {

    private SourceAleatoire s;
    private TransmetteurParfait t;
    private DestinationFinale d;

    /**
     * Cette méthode de classe permet la construction de la maquette de tests.
     * les attributs s, t et d sont instanciés à chaque appel de la fonction
     * */
    void init() throws Exception {

        s = new SourceAleatoire(6, 99);
        t = new TransmetteurParfait();
        d = new DestinationFinale();

        s.connecter(t);
        t.connecter(d);

        s.emettre();
    }

    /**
     * Test de la méthode recevoir()
     * */
    @Test
    void recevoir() throws Exception {
        init();
        for (int i = 0; i < s.getInformationEmise().nbElements(); i++) {
            if (s.getInformationEmise().iemeElement(i) != t.getInformationRecue().iemeElement(i)) {
                fail("Les informations recues sont incorrectes ou manquantes");
            }
        }
    }

    /**
     * Test de la méthode emettre()
     * */
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