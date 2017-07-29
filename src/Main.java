
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Lager viewen
        View view = new View(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
