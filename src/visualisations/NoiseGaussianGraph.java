package visualisations;

import destinations.DestinationInterface;
import emetteur.Emetteur;
import emetteur.EmetteurParfaitAnalogique;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceFixe;
import transmetteurs.TransmetteurBruiteAnalogique;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class NoiseGaussianGraph implements DestinationInterface<Float> {

    Information<Float> information;

    public NoiseGaussianGraph() {
        information = new Information<>();
    }

    public Information<Float> getInformationRecue() {
        return information;
    }

    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        for (Float i : information) {
            this.information.add(i);
        }
    }

    public static void main(String[] args) throws Exception {
        NoiseGaussianGraph graph = new NoiseGaussianGraph();
        SourceFixe src = new SourceFixe("0000000000");
        Emetteur<Boolean, Float> emetteur = new EmetteurParfaitAnalogique(-2, 2, 100000, "NRZ");
        TransmetteurBruiteAnalogique transmetteur = new TransmetteurBruiteAnalogique(-10, 100000);

        src.connecter(emetteur);
        emetteur.connecter(transmetteur);

        transmetteur.connecter(graph);

        src.emettre();

        System.out.println(transmetteur.getSigma());

        // Enregistrement dans un fichier CSV
        String csvFileName = "noise_gaussian.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            // Écriture de l'en-tête du CSV (facultatif)
            writer.println("Bruit gaussien");

            // Écriture des valeurs dans le fichier CSV
            for (Float value : graph.getInformationRecue()) {
                writer.println(value);
            }

            System.out.println("Données enregistrées dans " + csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
