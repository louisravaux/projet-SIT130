package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * La classe TransmetteurParfait est une implémentation simple d'un transmetteur
 * dans un système de communication. Ce transmetteur ne fait aucun traitement
 * sur les données reçues et les transmet telles quelles aux destinations connectées.
 * Il s'agit d'un transmetteur parfait sans altération du signal.
 */
public class TransmetteurParfait extends Transmetteur<Boolean, Boolean> {

    /**
     * Constructeur par défaut de la classe TransmetteurParfait.
     * Initialise les informations reçues et émises comme des informations vides.
     */
    public TransmetteurParfait() {
        super();
        informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    /**
     * Méthode permettant de recevoir une information booléenne en entrée et de la stocker
     * pour émission ultérieure.
     *
     * @param information L'information booléenne reçue.
     * @throws InformationNonConformeException Si l'information reçue est non conforme.
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        for (Boolean i : information) {
            informationRecue.add(i);
        }
        emettre();
    }

    /**
     * Méthode permettant d'émettre l'information stockée vers toutes les destinations connectées.
     *
     * @throws InformationNonConformeException Si l'information émise est non conforme.
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationRecue);
        }
        this.informationEmise = informationRecue;
    }
}
