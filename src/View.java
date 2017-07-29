import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

class View {

    private File currentLab;
    private Text tekstFelt;
    private Stage primaryStage;
    private BorderPane mainLayout;
    View(Stage primaryStage){
        this.primaryStage = primaryStage;
        BorderPane mainLayout = new BorderPane();
        this.mainLayout = mainLayout;
        //Tekstinformasjonsfeltet
        tekstFelt = new Text("Her faar du beskjed om ting ;)");
        tekstFelt.setStyle("-fx-font-size: 16px;");
        tekstFelt.setWrappingWidth(1000);
        //Bottom Skriver beskjeder
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(tekstFelt);
        mainLayout.setBottom(bottom);
        //Top: valg som kan tas
        HBox top = new HBox();
        top.setAlignment(Pos.CENTER);
        Button filvelgerKnapp = new Button("Velg fil:");
        Button resetButton = new Button("Reset Labyrint");
        top.getChildren().add(filvelgerKnapp);
        top.getChildren().add(resetButton);
        mainLayout.setTop(top);
        filvelgerKnapp.setOnAction((event)->{
            lagLabyrint(primaryStage, mainLayout);
        });
        resetButton.setOnAction((event)->{
            resetLabyrint(primaryStage, mainLayout);
        });

        //Main layout gridLayout
        Scene foersteScene = new Scene(mainLayout,1000,800);
        primaryStage.setTitle("LabyrintSolver");
        primaryStage.setScene(foersteScene);
        primaryStage.show();
    }

    private void resetLabyrint(Stage primaryStage, BorderPane mainLayout) {
        if (currentLab != null) {
            try {
                Labyrint l = Labyrint.lesFraFil(currentLab);
                GridPane labyrint = l.lagGrid(tekstFelt, this);
                labyrint.setAlignment(Pos.CENTER);
                mainLayout.setCenter(labyrint);
                tekstFelt.setText("Velg en rute aa reise fra");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            tekstFelt.setText("Du har ikke valgt en labyrint");
        }
    }
    private void lagLabyrint(Stage primaryStage, BorderPane mainLayout){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File f = fileChooser.showOpenDialog(primaryStage);
        this.currentLab = f;
        try {
            Labyrint l = Labyrint.lesFraFil(f);
            GridPane labyrint = l.lagGrid(tekstFelt, this);
            labyrint.setAlignment(Pos.CENTER);
            mainLayout.setCenter(labyrint);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
