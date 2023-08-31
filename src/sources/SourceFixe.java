package sources;

import information.Information;

public class SourceFixe extends Source<Boolean> {
	/** Une source qui envoie toujours le m�me message */
    public SourceFixe() {
        informationGeneree = new Information<Boolean>();
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
        informationGeneree.add(true);
        informationGeneree.add(false);
        informationGeneree.add(true);
    }
    
}
