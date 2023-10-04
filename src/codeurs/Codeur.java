package codeurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

import java.util.LinkedList;

/**
 * Classe abstraite Codeur. Cette classe définit les méthodes et les attributs communs aux codeurs et décodeurs.
 * @param <T> le type de l'information à traiter
 */
public abstract class Codeur<T> implements DestinationInterface<T>, SourceInterface <T> {

    protected LinkedList<DestinationInterface <T>> destinationsConnectees;
    protected Information <T>  informationRecue;
    protected Information <T>  informationEmise;


    public Codeur() {
        destinationsConnectees = new LinkedList<>();
        informationRecue = new Information<>();
        informationEmise = new Information<>();
    }

    /**
     * Méthode de conversion du signal
     */
    public abstract void conversion();

    /**
     * @return informationRecue
     */
    @Override
    public Information<T> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * @param information l'information  à recevoir
     * @throws InformationNonConformeException
     */
    @Override
    public void recevoir(Information<T> information) throws InformationNonConformeException {
        for (T i : information) {
            informationRecue.add(i);
        }
        conversion();
        emettre();
    }

    /**
     * @return informationEmise
     */
    @Override
    public Information<T> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * @param destination la destination à connecter
     */
    @Override
    public void connecter(DestinationInterface<T> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * @throws InformationNonConformeException
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<T> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
