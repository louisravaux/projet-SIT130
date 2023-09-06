package sources;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SourceAleatoireTest {

    @Test
    void generateSignal() throws Exception {
        SourceAleatoire src = new SourceAleatoire(6, 88);

        // checking signal for seed 88
        assertTrue(src.getInformationGeneree().iemeElement(0));
        assertTrue(src.getInformationGeneree().iemeElement(1));
        assertTrue(src.getInformationGeneree().iemeElement(2));
        assertFalse(src.getInformationGeneree().iemeElement(3));
        assertTrue(src.getInformationGeneree().iemeElement(4));
        assertFalse(src.getInformationGeneree().iemeElement(5));


        // size = -1 is not valid
        // TODO: make assert for this test
        new SourceAleatoire(-1, 88);

    }
}