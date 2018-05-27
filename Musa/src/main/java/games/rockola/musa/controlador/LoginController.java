package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 */
public class LoginController implements Initializable {
    
    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtContra;

    @FXML
    private JFXButton btnIniciarSesion;

    @FXML
    private Hyperlink linkRegistro;

    @FXML
    private Hyperlink linkRecuperacion;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void iniciarSesion(){
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/fxml/Principal.fxml"));
            
        } catch (IOException ex) {
        }
        Stage stagePrincipal = new Stage();
        Scene escenaPrincipal = new Scene(parent, 1152, 720);
        stagePrincipal.setScene(escenaPrincipal);
        Stage login = (Stage) btnIniciarSesion.getScene().getWindow();
        login.close();
        stagePrincipal.show();
    }
    
}
