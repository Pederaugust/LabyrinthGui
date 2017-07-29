
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * Created by PederAugust on 07.04.17.
 */
public class Labyrint {
    private Rute[][] ruter;
    private int rader, kolonner;
    private List<StringComparable> utveier;
    private GridPane ruterGrid;
    private Labyrint(int kolonner, int rader){
        this.ruter = new Rute[kolonner][rader];   //Lag en ny tabell
        this.rader = rader; this.kolonner = kolonner;
    }

    /**
     * leser inn en fil og kaller en private konstruktør og returnerer det resulterende objektet.
     * Det returnerte objektet skal ha all datastruktur ferdig oppsatt.
     * @return ferdig utfylt labyrint.
     */
    static Labyrint lesFraFil(File fil) throws FileNotFoundException{
        Scanner leser = new Scanner(fil);               //Lag en Scanner

        int antRader = Integer.parseInt(leser.next());  //Les inn de to forste tallene og sett de som antall rader og antall kolonner
        int antKolonner = Integer.parseInt(leser.next());
        Labyrint labyrinten = new Labyrint(antKolonner, antRader);
        for (int rad = 0; rad < antRader; rad++) {      //For hver rad
            String input = leser.next();                 //Les hver linje i filen og legg de i en variabel "input"
            for (int kolonne = 0; kolonne < antKolonner; kolonne++) {
                Rute ny = labyrinten.lagRute(kolonne,rad,input.charAt(kolonne),labyrinten);   //Les hver character i input og lag tilsvarende (sort, hvit eller aapning) rute. Med lagRute()
                labyrinten.ruter[kolonne][rad] = ny;                               //Legg ruten inn paa riktig plass i rutenettet til lab.
            }
        }
        leser.close();
        return labyrinten;
    }

    /**
     * Hjelpemetode som returnerer en ferdig laget rute med riktig himmelretninger og av riktig type(klasse)
     * @return Ruter.Rute ruten som blir laget
     */
    private Rute lagRute(int rad, int kolonne, char typen, Labyrint lab){
        Rute ruten = new SortRute(rad, kolonne, lab); //Setter foerst ruten som en sort rute
        if (typen == '.'){                            //Hvis ruten er hvit saa endrer vi ruten til en av de to hvite rutene
            if (aapning(rad,kolonne)){                //Hvis ruten er en aapning saa lager vi en hvit rute av type aapning.
                ruten = new Aapning(rad,kolonne,lab);
            }else {                                   //Ellers bare lag en hvit rute
                ruten = new HvitRute(rad,kolonne,lab);
            }
        }
        settHimmelRetninger(rad, kolonne, ruten, lab);
        return ruten;
    }

    /**
     * Hjelpemetode som returnerer om rad og kolonne er paa en av de fire kantene av labyrinten.
     * Hvis de er det saa er koordinatene en potensiell aapning hvis ruten som leses er hvit.
     * @param rad
     * @param kolonne
     * @return
     */
    @Contract(pure = true)
    private boolean aapning(int rad, int kolonne){
        return rad == 0 || kolonne == 0 || rad == rader - 1 || kolonne == kolonner - 1;
    }

    /**
     * Hjelpemetode som setter himmelretningene til en rute.
     */
    private void settHimmelRetninger(int kolonne, int rad, Rute rute, Labyrint lab) {
        if (rad != 0){
            rute.setNordBegge(lab.ruter[kolonne][rad-1]);
        }
        if (kolonne != 0){
            rute.setVestBegge(lab.ruter[kolonne-1][rad]);
        }
    }

    List<StringComparable> finnUtveiFra(int kol, int rad){
        utveier = ruter[kol-1][rad-1].finnUtvei();
        utveier.sort(StringComparable::compareTo);
        return utveier;
    }
    @Override
    public String toString(){
        String utskrift = "";
        for (int rad = 0; rad < rader; rad++){
            for (int kolonne = 0; kolonne<kolonner; kolonne++){
                utskrift += ruter[kolonne][rad].tilTegn();
            }
            if (rad != rader-1) {
                utskrift += "\n";
            }
        }
        return utskrift;
    }

    /**
     * Lager en gridpane som modelerer labyrinten
     * @param tekstFelt
     * @param view
     * @return
     */
    GridPane lagGrid(Text tekstFelt, View view){
        GridPane pane = new GridPane();
        for (int i = 0; i < rader; i++){
            for (int j = 0; j < kolonner; j++){
                Button b = new Button(" ");
                ruter[j][i].setRuteButton(b, view);
                ruter[j][i].setHoved(tekstFelt);
                ruter[j][i].updateRute();
                pane.add(b, j, i);
            }
        }
        this.ruterGrid = pane;
        return pane;
    }

    int getRader() {
        return rader;
    }
}
