package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.MainApp;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;

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
        
    }    
    
    @FXML
    public void iniciarSesion(){
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(3);
        } catch (IOException ex) {
        }
    }
    
    @FXML
    public void registrarUsuario() {
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(2);
        } catch (IOException ex) {
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
