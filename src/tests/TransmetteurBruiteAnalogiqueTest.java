package tests;

import static org.junit.jupiter.api.Assertions.*;

import destinations.DestinationFinaleAnalogique;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import information.Information;
import information.InformationNonConformeException;
import transmetteurs.TransmetteurBruiteAnalogique;

public class TransmetteurBruiteAnalogiqueTest {

    private TransmetteurBruiteAnalogique transmetteur;

    @BeforeEach
    public void setUp() {
        // Initialisation du transmetteur avec un SNR de 10 dB et 5 échantillons par symbole
        transmetteur = new TransmetteurBruiteAnalogique(10, 5);
    }

    @Test
    public void testRecevoirEtEmettre() throws InformationNonConformeException {
        // Création d'une information de test
        Information<Float> infoTest = new Information<>();
        infoTest.add(1.0f);
        infoTest.add(2.0f);
        infoTest.add(3.0f);

        // Destination de test
        DestinationFinaleAnalogique  destinationTest = new DestinationFinaleAnalogique();

        // Connexion de la destination de test au transmetteur
        transmetteur.connecter(destinationTest);

        // Envoi de l'information au transmetteur
        transmetteur.recevoir(infoTest);
    }

    @Test
    public void testCalculSigmaNoise() {
        // Calcul attendu de sigma_noise pour un SNR de 10 dB et 5 échantillons par symbole
        float expectedSigmaNoise = (float) Math.sqrt((5 * transmetteur.getP_signal()) / (2 * transmetteur.getSnr()));

        // Appel de la méthode de calcul de sigma_noise
        transmetteur.calculateSigmaNoise();

        // Vérification que le résultat obtenu est égal au résultat attendu
        assertEquals(expectedSigmaNoise, transmetteur.getSigma(), 0.001); // Utilisation d'une tolérance de 0.001
    }
}

