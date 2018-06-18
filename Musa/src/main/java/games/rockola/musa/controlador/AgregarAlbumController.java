package games.rockola.musa.controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.Dialogo;
import games.rockola.musa.servicios.Imagen;
import games.rockola.musa.servicios.SubirCancion;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.Album;
import games.rockola.musa.ws.pojos.Cancion;
import games.rockola.musa.ws.pojos.Mensaje;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
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
        
        Album album = new Album(tfNombre.getText(), portadaAlbum, "2018-06-18", "Rockola", LoginController.artistaLogueado.getIdArtista());
        HttpUtils.agregarAlbum(album);
        
        Mensaje mensajeAlbum = HttpUtils.recuperarUltimoAlbum();
        Type albumType = new TypeToken<Album>() {}.getType();
        Album albumRecuperado = new Gson().fromJson(mensajeAlbum.getMensaje(), albumType);
        
        File [] canciones = directorioCanciones.listFiles((dir, name) -> {
            return !name.equals(".DS_Store");
        });
        for (File cancion: canciones) {
            Cancion nuevaSong = new Cancion(cancion.getName(), albumRecuperado.getIdAlbum(), LoginController.artistaLogueado.getIdGenero(), "0", 120);
            HttpUtils.agregarCancion(nuevaSong);
            
            SubirCancion subirCancion = new SubirCancion();
            subirCancion.subir(cancion);
        }
        
        Dialogo dialogoExito = new Dialogo("1010", ButtonType.OK);
        dialogoExito.showAndWait();
        cerrarDialogo();
        
    }
    
    private void activarGuardado () {
        if(tfNombre.getText().isEmpty() || tfRutaCanciones.getText().isEmpty() || tfRutaImagen.getText().isEmpty()){
            btnGuardar.setDisable(false);
        } else {
            btnGuardar.setDisable(false);
        }
    }
    
}
