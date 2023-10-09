package tests;

import destinations.DestinationFinaleAnalogique;
import emetteur.EmetteurParfaitAnalogique;
import information.Information;
import information.InformationNonConformeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EmetteurParfaitAnalogiqueTest {

    EmetteurParfaitAnalogique e;
    Information<Boolean> infos;
    DestinationFinaleAnalogique d;

    @BeforeEach
    void init() {
        d = new DestinationFinaleAnalogique();
        e = new EmetteurParfaitAnalogique(0f,1f,6,"RZ");
        infos = new Information<>(); infos.add(true); infos.add(false); infos.add(true);
    }

    @Test
    public void testConvertRZ() throws InformationNonConformeException {
        e.recevoir(infos);
        e.convertRZ();
        Information<Float> informationEmise = e.getInformationEmise();
        assertEquals(36, informationEmise.nbElements());
        assertEquals(0f, informationEmise.iemeElement(0), 0.01f);
        assertEquals(1f, informationEmise.iemeElement(3), 0.01f);
    }

    @Test
    public void testConvertNRZ() throws InformationNonConformeException {
        e.recevoir(infos);
        e.convertNRZ();
        Information<Float> informationEmise = e.getInformationEmise();
        assertEquals(36, informationEmise.nbElements());
        assertEquals(0f, informationEmise.iemeElement(0), 0.01f);
        assertEquals(1f, informationEmise.iemeElement(3), 0.01f);
    }

    @Test
    public void testConvertNRZT() throws InformationNonConformeException {
        e.recevoir(infos);
        e.convertNRZT();
        Information<Float> informationEmise = e.getInformationEmise();
        assertEquals(36, informationEmise.nbElements());
        assertEquals(0f, informationEmise.iemeElement(0), 0.01f);
        assertEquals(1f, informationEmise.iemeElement(3), 0.01f);
    }

    @Test
    public void testRecevoir() throws InformationNonConformeException {
        Information<Boolean> informations = new Information<>(); informations.add(true); informations.add(false); informations.add(true);
        e.recevoir(informations);
        Information<Boolean> informationRecue = e.getInformationRecue();
        assertEquals(3, informationRecue.nbElements());
        assertEquals(true, informationRecue.iemeElement(0));
        assertEquals(false, informationRecue.iemeElement(1));
        assertEquals(true, informationRecue.iemeElement(2));
    }

    @Test
    public void testEmettre() throws InformationNonConformeException {
        e.connecter(d);
        e.recevoir(infos);

        if (d.getInformationRecue().nbElements() != 18) {
            fail("La taille de l'information reçue est incorrecte");
        }

        for (int i = 0; i < d.getInformationRecue().nbElements(); i++) {
            if (!Objects.equals(d.getInformationRecue().iemeElement(i), e.getInformationEmise().iemeElement(i))) {
                fail("Les informations reçues sont incorrectes ou manquantes");
            }
        }
    }

}
