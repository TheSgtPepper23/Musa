package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.Dialogo;
import games.rockola.musa.Imagenes;
import games.rockola.musa.MainApp;
import games.rockola.musa.ws.Cifrado;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.Melomano;
import games.rockola.musa.ws.pojos.Mensaje;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
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
    
    final FileChooser seleccionarFoto = new FileChooser();
    
    File imagen = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        seleccionarFoto.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter extensiones = new FileChooser.ExtensionFilter("Imágenes", "*.jpg");
        seleccionarFoto.getExtensionFilters().add(extensiones);
        btnGuardar.setDisable(true);
        
        tfNombre.textProperty().addListener((observable) -> {
            activarBoton();
        });
        
        tfNombreUsuario.textProperty().addListener((observable) -> {
            activarBoton();
        });
        
        tfApellidos.textProperty().addListener((observable) -> {
            activarBoton();
        });
        
        tfContra.textProperty().addListener((observable) -> {
            activarBoton();
        });
        
        tfConfirmContra.textProperty().addListener((observable) -> {
            activarBoton();
        });
        
        tfCorreo.textProperty().addListener((observable) -> {
            activarBoton();
        });
        
        tfRuta.textProperty().addListener((observable) -> {
            activarBoton();
        });
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
        imagen = seleccionarFoto.showOpenDialog(MainApp.getVentana());
        if(imagen != null)
            tfRuta.setText(imagen.getAbsolutePath());
    }
    
    private void activarBoton(){
        if(tfNombreUsuario.getText().isEmpty() || tfNombre.getText().isEmpty() || 
                tfApellidos.getText().isEmpty() || tfContra.getText().isEmpty() || 
                tfCorreo.getText().isEmpty() || tfRuta.getText().isEmpty() || 
                tfConfirmContra.getText().isEmpty()) {
            btnGuardar.setDisable(true);
        } else {
            btnGuardar.setDisable(false);
        }
    }
    
    private void limpiar(){
        imagen = null;
        tfNombreUsuario.setText("");
        tfNombre.setText("");
        tfApellidos.setText("");
        tfContra.setText("");
        tfConfirmContra.setText("");
        tfCorreo.setText("");
        tfRuta.setText("");
    }
    
    @FXML
    public void guardarUsuario() throws IOException{
        if(tfContra.getText().equals(tfConfirmContra.getText())){
            Melomano nuevo = new Melomano();
            nuevo.setNombreMelomano(tfNombreUsuario.getText());
            nuevo.setCorreoElectronico(tfCorreo.getText());
            nuevo.setNombre(tfNombre.getText());
            nuevo.setApellidos(tfApellidos.getText());
            nuevo.setPassword(Cifrado.cifrarCadena(tfContra.getText()));
            nuevo.setFotoPerfil(Imagenes.codificarImagen(imagen));
            Mensaje mensaje = HttpUtils.agregarUsuario(nuevo);
            limpiar();
            Dialogo dialogo = new Dialogo(mensaje.getMensaje(), ButtonType.OK);
            dialogo.show();
        } else {
            Dialogo dialogo = new Dialogo("300", ButtonType.OK);
            dialogo.show();
        }
        
    }
}
