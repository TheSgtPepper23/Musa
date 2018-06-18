/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.Imagen;
import games.rockola.musa.servicios.SubirCancion;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AgregarAlbumController implements Initializable {
    
    @FXML
    private JFXTextField tfNombre;

    @FXML
    private JFXTextField tfRutaImagen;

    @FXML
    private JFXTextField tfRutaCanciones;

    @FXML
    private JFXButton btnGuardar;
    
    private File directorioCanciones;
    private String portadaAlbum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(LoginController.artistaLogueado.getNombre());
        btnGuardar.setDisable(true);
        tfNombre.textProperty().addListener((observable) -> {
            activarGuardado();
        });
        tfRutaCanciones.textProperty().addListener((observable) -> {
            activarGuardado();
        });
        tfRutaImagen.textProperty().addListener((observable) -> {
            activarGuardado();
        });
    }    
    
    @FXML
    public void seleccionarFolder() {
        DirectoryChooser seleccionarCarpeta = new DirectoryChooser();
        seleccionarCarpeta.setTitle("Seleccionar folder de canciones");
        seleccionarCarpeta.setInitialDirectory(new File(System.getProperty("user.home")));
        File temp = seleccionarCarpeta.showDialog(btnGuardar.getScene().getWindow());
        
        if(temp != null){
            directorioCanciones = temp;
            tfRutaCanciones.setText(temp.getPath());
        }
    }
    
    @FXML
    public void seleccionarPortada() {
        FileChooser seleccionarFoto = new FileChooser();
        seleccionarFoto.setInitialDirectory(new File(System.getProperty("user.home")));
        seleccionarFoto.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Imágenes", "*.jpg"));
        File archivo = seleccionarFoto.showOpenDialog(btnGuardar.getScene().getWindow());
        if(archivo != null) {
            tfRutaImagen.setText(archivo.getPath());
            try {
                portadaAlbum = Imagen.codificarImagen(archivo);
            } catch (IOException ex) {ex.printStackTrace();}
        }
    }
    
    @FXML
    public void cerrarDialogo() {
        Stage dialogo = (Stage)tfNombre.getScene().getWindow();
        dialogo.close();
    }
    
    @FXML
    public void guardarAlbum() {
        File [] canciones = directorioCanciones.listFiles((dir, name) -> {
            return !name.equals(".DS_Store");
        });
        
        for (File cancion : canciones) {
            new SubirCancion().subir(LoginController.artistaLogueado.getNombre(), tfNombre.getText(), cancion);
        }
    }
    
    private void activarGuardado () {
        if(tfNombre.getText().isEmpty() || tfRutaCanciones.getText().isEmpty() || tfRutaImagen.getText().isEmpty()){
            btnGuardar.setDisable(true);
        } else {
            btnGuardar.setDisable(false);
        }
    }
    
}
