package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sources.SourceAleatoire;

import static org.junit.jupiter.api.Assertions.*;

class SourceAleatoireTest {

    SourceAleatoire src;

    @BeforeEach
    void init() throws Exception {
        src = new SourceAleatoire(6, 88);

    }

    @Test
    void generateSignal() throws Exception {

        // checking signal for seed 88
        assertTrue(src.getInformationGeneree().iemeElement(0));
        assertTrue(src.getInformationGeneree().iemeElement(1));
        assertTrue(src.getInformationGeneree().iemeElement(2));
        assertFalse(src.getInformationGeneree().iemeElement(3));
        assertTrue(src.getInformationGeneree().iemeElement(4));
        assertFalse(src.getInformationGeneree().iemeElement(5));
    }

    @Test
    void testInvalidSize() {
        // Test with an invalid size (-1)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new SourceAleatoire(-1, 88));
        assertEquals("La taille doit être supérieure à 0", exception.getMessage());
    }


}