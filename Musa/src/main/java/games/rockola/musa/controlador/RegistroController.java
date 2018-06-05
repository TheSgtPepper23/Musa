package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.stage.FileChooser;

public class RegistroController implements Initializable {
    
    @FXML
    private JFXTextField tfCorreo;

    @FXML
    private JFXTextField tfNombreUsuario;

    @FXML
    private JFXTextField tfNombre;

    @FXML
    private JFXTextField tfApellidos;

    @FXML
    private JFXPasswordField tfContra;

    @FXML
    private JFXPasswordField tfConfirmContra;

    @FXML
    private JFXTextField tfRuta;

    @FXML
    private JFXButton btnAbrir;

    @FXML
    private JFXButton btnGuardar;

    @FXML
    private Hyperlink linkInicio;
    
    final FileChooser selectorArchivos = new FileChooser();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    @FXML
    public void regresoInicio() {
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(1);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    public void seleccionarFoto(){
        System.out.println("");
    }
    
}
