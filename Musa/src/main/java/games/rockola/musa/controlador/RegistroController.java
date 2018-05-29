package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void regresoInicio() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            
        } catch (IOException ex) {
        }
        Stage inicio = new Stage();
        Scene escenaLogin = new Scene(parent, 1152, 720);
        inicio.setScene(escenaLogin);
        inicio.show();
        Stage registro = (Stage) btnGuardar.getScene().getWindow();
        registro.close();
    }
    
}
