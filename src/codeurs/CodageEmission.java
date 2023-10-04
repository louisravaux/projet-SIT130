package codeurs;

/**
 * Classe CodageEmission. Cette classe permet de coder le signal reçu.
 * Ici, un 1 est représenté par 101 et un 0 par 010.
 */
public class CodageEmission extends Codeur<Boolean>{

    /**
     * ඞ
     * Code le signal reçu
     * */
    public void conversion() {
        for (Boolean information : informationRecue) {
            if (information) {
                informationEmise.add(true);
                informationEmise.add(false);
                informationEmise.add(true);
            } else {
                informationEmise.add(false);
                informationEmise.add(true);
                informationEmise.add(false);
            }
        }
    }

}
