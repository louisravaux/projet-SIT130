package tests;

import information.Information;
import information.InformationNonConformeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import recepteur.RecepteurParfaitAnalogique;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecepteurParfaitAnalogiqueTest {
    RecepteurParfaitAnalogique r;
    Information<Float> infos;



    @BeforeEach
    void init() throws Exception {
        r = new RecepteurParfaitAnalogique(0f, 1f, 6, "RZ");
        infos = new Information<>(); infos.add(0.2f); infos.add(0.8f); infos.add(0.7f); infos.add(0.9f); infos.add(0.1f); infos.add(0.5f);
    }

    @Test
    public void testReceiveRZ() throws InformationNonConformeException {
        r.recevoir(infos);
        r.receiveRZ();
        Information<Boolean> informationEmise = r.getInformationEmise();
        assertEquals(2, informationEmise.nbElements());
        assertEquals(true, informationEmise.iemeElement(0));
        assertEquals(true, informationEmise.iemeElement(1));
    }

    @Test
    public void testReceiveNRZ() throws InformationNonConformeException {
        r.recevoir(infos);
        r.receiveNRZ();
        Information<Boolean> informationEmise = r.getInformationEmise();
        assertEquals(2, informationEmise.nbElements());
        assertEquals(true, informationEmise.iemeElement(0));
        assertEquals(true, informationEmise.iemeElement(1));
    }

    @Test
    public void testReceiveNRZT() throws InformationNonConformeException {
        r.recevoir(infos);
        r.receiveNRZT();
        Information<Boolean> informationEmise = r.getInformationEmise();
        assertEquals(2, informationEmise.nbElements());
        assertEquals(true, informationEmise.iemeElement(0));
        assertEquals(true, informationEmise.iemeElement(1));
    }

    @Test
    public void testRecevoir() throws InformationNonConformeException {
        Information<Float> information = new Information<>(); information.add(0.2f); information.add(0.8f); information.add(0.5f); information.add(0.7f); information.add(0.9f); information.add(0.1f);
        r.recevoir(information);
        assertEquals(information, r.getInformationRecue());
    }
}
