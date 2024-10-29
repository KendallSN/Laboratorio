package cr.ac.una.unaplanilla;

import cr.ac.una.unaplanilla.util.FlowController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    public static String parametro;

    @Override
    public void start(Stage stage) throws IOException {
        FlowController.getInstance().InitializeFlow(stage, null);
        stage.getIcons().add(new Image("cr/ac/una/unaplanilla/resources/LogoUNArojo.png"));
        stage.setTitle("UNA PLANILLA");
        if (parametro != null) {
            FlowController.getInstance().goMain();
        } else {
            FlowController.getInstance().goViewInWindow("LogInView");
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            parametro = args[0];
        }
        launch();
    }

}
