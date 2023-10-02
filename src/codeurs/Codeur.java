package codeurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import sources.SourceInterface;

import java.util.LinkedList;

public abstract class Codeur<Boolean> implements DestinationInterface<Boolean>, SourceInterface <Boolean> {

    protected LinkedList<DestinationInterface <Boolean>> destinationsConnectees;
    protected Information <Boolean>  informationRecue;
    protected Information <Boolean>  informationEmise;


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
    public Information<Boolean> getInformationRecue() {
        return this.informationRecue;
    }

    /**
     * @param information l'information  à recevoir
     * @throws InformationNonConformeException
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        for (Boolean i : information) {
            informationRecue.add(i);
        }
        conversion();
        emettre();
    }

    /**
     * @return informationEmise
     */
    @Override
    public Information<Boolean> getInformationEmise() {
        return this.informationEmise;
    }

    /**
     * @param destination la destination à connecter
     */
    @Override
    public void connecter(DestinationInterface<Boolean> destination) {
        destinationsConnectees.add(destination);
    }

    /**
     * @throws InformationNonConformeException
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
