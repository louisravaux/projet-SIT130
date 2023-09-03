package sources;

import information.Information;
import java.util.Random;

public class SourceAleatoire extends Source<Boolean> {
    
	/** Une source qui envoie un message aleatoire */
    public SourceAleatoire() {

        informationGeneree = new Information<Boolean>();

        Random rd = new Random();
        for(int i = 0; i < 6; i++) {

        	boolean bool = rd.nextBoolean();
        	informationGeneree.add(bool);

        }
    }
}
