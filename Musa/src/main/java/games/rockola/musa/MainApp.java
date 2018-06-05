package games.rockola.musa;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {
    private static Stage ventana;
    private static Scene login;
    private static Scene registro;
    private static Scene principal;

    public static Stage getVentana() {
        return MainApp.ventana;
    }
    
    public void cambiarEscena(Integer noEscena) throws IOException{
        switch(noEscena){
            case 1:
                ventana.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")), 1152, 720));
                break;
            case 2:
                ventana.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Registro.fxml")), 1152, 720));
                break;
            case 3:
                ventana.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml")), 1152, 720));
                break;
        }
        
    }

    @Override
    public void start(Stage stage) throws Exception {
        ventana = stage;
        stage.setTitle("JavaFX and Maven");
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml")), 1152, 720));
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
