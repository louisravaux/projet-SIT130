package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe représentant un transmetteur parfait analogique.
 * Cette classe hérite de la classe abstraite Transmetteur et implémente la transmission d'informations analogiques.
 * Ce transmetteur ne fait rien, il transmet uniquement les informations reçues sans les modifier.
 */
public class TransmetteurParfaitAnalogique extends Transmetteur<Float, Float> {

    /**
     * Constructeur de la classe TransmetteurParfaitAnalogique.
     */
    public TransmetteurParfaitAnalogique() {
        informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    /**
     * Méthode pour recevoir une information analogique.
     *
     * @param information L'information analogique à recevoir.
     * @throws InformationNonConformeException si l'information reçue n'est pas du type Float.
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        for (Float i : information) {
            informationRecue.add(i);
        }
        emettre();
    }

    /**
     * Émet l'information aux destinations connectées.
     *
     * @throws InformationNonConformeException Si l'information émise n'est pas conforme.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = informationRecue;
    }
}
