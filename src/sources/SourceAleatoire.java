package sources;

import information.Information;
import java.util.Random;

public class SourceAleatoire extends Source<Boolean> {

    private final int size;

	/** Une source qui envoie un message aleatoire */
    public SourceAleatoire(int size) {

        this.size = size;
        informationGeneree = new Information<Boolean>();

        generateSignal();

    }

    public void generateSignal() {

        Random rd = new Random();
        for(int i = 0; i < size; i++) {

        	boolean bool = rd.nextBoolean();
        	informationGeneree.add(bool);

        }
    }
}
