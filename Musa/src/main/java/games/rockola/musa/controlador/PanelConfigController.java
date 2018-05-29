package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    }    
    
    @FXML
    public void cerrarSesion(){
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            
        } catch (IOException ex) {
        }
        Stage stageLogin = new Stage();
        Scene escenaLogin = new Scene(parent, 1152, 720);
        stageLogin.setScene(escenaLogin);
        stageLogin.show();
        Stage config = (Stage) btnLogout.getScene().getWindow();
        config.close();
    }
    
}
