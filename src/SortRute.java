/**
 * Created by PederAugust on 07.04.17.
 */
public class SortRute extends Rute {
    SortRute(int rad, int kolonne, Labyrint lab) {
        super(rad, kolonne, lab);
    }

    public char tilTegn() {
        return '▓';
    }
}
