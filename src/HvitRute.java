/**
 * Created by PederAugust on 07.04.17.
 */
public class HvitRute extends Rute {
    public HvitRute(int rad, int kolonne, Labyrint lab) {
        super(rad, kolonne, lab);
        this.paaVeien = false;
    }

    public char tilTegn() {
        if (this.paaVeien){
            return '*';
        }
        return ' ';
    }
}
