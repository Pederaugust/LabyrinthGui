import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

public abstract class Rute {
    private int rad;
    private int kolonne;
    private Rute nord;
    private Rute oest;
    private Rute syd;
    private Rute vest;
    private Labyrint labyrint;
    boolean paaVeien;
    private Button ruteButton;
    private Text tekstFelt;
    private View view;
    Rute(int kolonne, int rad, Labyrint lab) {
        this.rad = rad;
        this.kolonne = kolonne;
        this.labyrint = lab;
    }
    void setHoved(Text tekstFelt){
        this.tekstFelt = tekstFelt;
    }
    private void gaa(Rute forrige, String utskrift, List<StringComparable> utvei){
        this.paaVeien = true;
        this.updateRute();
        if (this instanceof Aapning){
            String nyUtskrift = utskrift+ "("+(kolonne+1)+","+ (rad+1)+")";
            utvei.add(new StringComparable(nyUtskrift));
        } else {
            boolean kunneGaa = false;
            String nyUtskrift = utskrift + "(" + (kolonne + 1) + "," + (rad + 1) + ") --> ";
            if (syd instanceof HvitRute && syd != forrige && !syd.paaVeien) {
                syd.gaa(this, nyUtskrift, utvei);
                if (((HvitRute) syd).paaVeien){
                    kunneGaa = true;
                }
            }
            if (oest instanceof HvitRute && oest != forrige && !oest.paaVeien) {
                oest.gaa(this, nyUtskrift, utvei);
                if (((HvitRute) oest).paaVeien){
                    kunneGaa = true;
                }
            }
            if (vest instanceof HvitRute && vest != forrige && !vest.paaVeien) {
                vest.gaa(this, nyUtskrift, utvei);
                if (((HvitRute) vest).paaVeien){
                    kunneGaa = true;
                }
            }
            if (nord instanceof HvitRute && nord != forrige && !nord.paaVeien) {
                nord.gaa(this, nyUtskrift, utvei);
                if (((HvitRute) nord).paaVeien){
                    kunneGaa = true;
                }
            }
            this.paaVeien = kunneGaa;

            this.updateRute();
        }
    }
    List<StringComparable> finnUtvei(){
        List<StringComparable> utvei = new LinkedList<>();
        this.gaa( null, "", utvei);
        return utvei;
    }
    /*_________________
    SETTERS
    _________________*/
     /*
    Disse setterne er litt annerledes siden de fungerer ''''begge'''' veier.
    Altsaa naar noe blir satt som nord saa faar den ruta som blir satt som nord
    en ny syd denne ruten.(this)
     */
    void setNordBegge(Rute nord) {
        this.nord = nord;
        this.nord.setSyd(this);
    }

    void setVestBegge(Rute vest) {
        this.vest = vest;
        this.vest.setOest(this);
    }

    /*
    Og disse setterne er normale
     */

    private void setOest(Rute oest) {
        this.oest = oest;
    }

    private void setSyd(Rute syd) {
        this.syd = syd;
    }
    /*_________________
    GETTERS
    __________________*/
    public abstract char tilTegn();
    void setRuteButton(Button b, View view){
        this.ruteButton = b;
        this.view = view;
        //Setter action til aa finne utvei fra ruten
        if (this instanceof HvitRute)
            ruteButton.setOnAction((event)->tekstFelt.setText(labyrint.finnUtveiFra(this.kolonne+1, this.rad+1).get(0).toString()));
    }

    /*
    ___________________
    GUIMETODER
    ___________________
     */
    /**
     * Oppdaterer ruteknappene, med riktig farger
     */
    void updateRute(){
        int fontSize;
        if (labyrint.getRader() > 50){
            fontSize = 4;
        }else if (labyrint.getRader() > 30){
            fontSize = 6;
        } else if (labyrint.getRader() > 20){
            fontSize = 14;
        } else {
            fontSize = 16;
        }
        if (this.paaVeien){
            this.ruteButton.setStyle("-fx-background-radius: 0, 0; -fx-background-color: #FF0000; -fx-font-size: "+fontSize+"px; ");
        } else if ( this instanceof SortRute){
            this.ruteButton.setStyle("-fx-background-radius: 0, 0;-fx-background-color: #aec6cf; -fx-font-size: "+fontSize+"px; ");
        } else {
            this.ruteButton.setStyle("-fx-background-radius: 0, 0;-fx-background-color: #FFFFFF; -fx-font-size: "+fontSize+"px; ");
        }
    }
    @Override
    public String toString(){
        return ""+this.tilTegn();
    }
}
