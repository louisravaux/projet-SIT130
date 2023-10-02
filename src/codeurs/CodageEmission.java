package codeurs;

public class CodageEmission extends Codeur<Boolean>{

    public CodageEmission() {
        super();
    }

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
