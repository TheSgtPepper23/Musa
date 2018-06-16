package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.Imagen;
import games.rockola.musa.MainApp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;

public class PanelConfigController implements Initializable {
    
    @FXML
    private ImageView imgUsuario;

    @FXML
    private JFXRadioButton radioBaja;

    @FXML
    private JFXRadioButton radioNormal;

    @FXML
    private JFXRadioButton radioExtrema;

    @FXML
    private JFXRadioButton radioAuto;

    @FXML
    private JFXTextField tfRuta;

    @FXML
    private JFXButton btnEliminar;

    @FXML
    private JFXButton btnConfig;

    @FXML
    private JFXButton btnLogout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final ToggleGroup calidad = new ToggleGroup();
        radioAuto.setToggleGroup(calidad);
        radioBaja.setToggleGroup(calidad);
        radioNormal.setToggleGroup(calidad);
        radioExtrema.setToggleGroup(calidad);
        radioAuto.setSelected(true);
        //radioAuto.getCssMetaData().stream().map(CssMetaData::getProperty).forEach(System.out::println);
        try {
            imgUsuario.setImage(Imagen.decodificarImagen(LoginController.melomanoLogueado.getFotoPerfil()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }    
    
    @FXML
    public void cerrarSesion(){
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(1);
        } catch (IOException ex) {
        }
    }
    
}
