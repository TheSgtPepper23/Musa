package games.rockola.musa.controlador;

import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.Dialogo;
import games.rockola.musa.MainApp;
import games.rockola.musa.servicios.Cifrado;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.Artista;
import games.rockola.musa.ws.pojos.Melomano;
import games.rockola.musa.ws.pojos.Mensaje;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 */
public class LoginController implements Initializable {
    
    @FXML
    private JFXTextField tfNombre;

    @FXML
    private JFXPasswordField tfContra;

    @FXML
    private JFXButton btnIniciarSesion;
    
    public static Melomano melomanoLogueado;
    public static Artista artistaLogueado;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnIniciarSesion.setDisable(true);
        tfNombre.textProperty().addListener((observable) -> {
            activarInicio();
        });
        
        tfContra.textProperty().addListener((observable) -> {
            activarInicio();
        });
    }    
    
    @FXML
    public void iniciarSesion(){
        MainApp main = new MainApp();
        Mensaje mensaje = HttpUtils.iniciarSesion(tfNombre.getText(), 
                Cifrado.cifrarCadena(tfContra.getText()));
        switch (mensaje.getMensaje()) {
            case "51":
                try {
                    Mensaje mensajeMelomano = HttpUtils.recuperarMelomano(tfNombre.getText());
                    melomanoLogueado = new Gson().fromJson(mensajeMelomano.getMensaje(), Melomano.class);
                    main.cambiarEscena(3);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }   break;
            case "52":
                try {
                    Mensaje mensajeArtista = HttpUtils.recuperarArtista(tfNombre.getText());
                    artistaLogueado = new Gson().fromJson(mensajeArtista.getMensaje(), Artista.class);
                    main.cambiarEscena(4);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }   break;
            default:
                Dialogo dialogo = new Dialogo(mensaje.getMensaje(), ButtonType.OK);
                dialogo.show();
                break;
        }
    }
    
    @FXML
    public void registrarUsuario() {
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(2);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    public void nuevoArtista() {
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(5);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void activarInicio() {
        if (tfNombre.getText().trim().isEmpty() || tfContra.getText().trim().isEmpty()) {
            btnIniciarSesion.setDisable(true);
        } else {
            btnIniciarSesion.setDisable(false);
        }
    }
    
    @FXML
    public void dialogoRecuperacion(){
        Dialog dialog = new Dialog<>();
        dialog.setTitle(null);
        dialog.setHeaderText("Recuperación de contraseña");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
           getClass().getResource("/styles/Styles.css").toExternalForm());
        dialogPane.getStyleClass().add("Dialogos");
        
        ButtonType enviarCorreoType = new ButtonType("Enviar correo", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(enviarCorreoType, ButtonType.CANCEL);
        
        JFXTextField campoCorreo = new JFXTextField();
        campoCorreo.setPromptText("Correo electrónico");
        campoCorreo.setLabelFloat(true);
        campoCorreo.setPrefWidth(300);
        
        HBox caja = new HBox(campoCorreo);
        caja.setAlignment(Pos.CENTER);
        
        Node enviarCorreo = dialog.getDialogPane().lookupButton(enviarCorreoType);
        enviarCorreo.setDisable(true);
        
        campoCorreo.textProperty().addListener((observable, oldValue, newValue) -> {
            enviarCorreo.setDisable(newValue.trim().isEmpty());
        });
        
        dialog.getDialogPane().setContent(caja);
        Platform.runLater(() -> campoCorreo.requestFocus());
        dialog.getDialogPane().setPrefSize(350, 150);
        dialog.initStyle(StageStyle.UNDECORATED);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == enviarCorreoType) {
                return campoCorreo.getText();
            }
            return null;
        });
        
        Optional<String> correo = dialog.showAndWait();

        if(correo.isPresent()) {
            System.out.println(correo.get());
        }
    }
    
}
