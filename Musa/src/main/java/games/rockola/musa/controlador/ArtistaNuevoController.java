package games.rockola.musa.controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import games.rockola.musa.ws.pojos.Genero;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.Dialogo;
import games.rockola.musa.servicios.Imagen;
import games.rockola.musa.MainApp;
import static games.rockola.musa.controlador.LoginController.artistaLogueado;
import games.rockola.musa.servicios.Cifrado;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.Artista;
import games.rockola.musa.ws.pojos.FotoArtista;
import games.rockola.musa.ws.pojos.Mensaje;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ArtistaNuevoController implements Initializable{
    
    @FXML
    private JFXTextField tfNombre;

    @FXML
    private JFXTextArea areaBio;

    @FXML
    private JFXTextField tfCorreo;

    @FXML
    private JFXPasswordField tfPass;

    @FXML
    private JFXPasswordField tfConfirm;

    @FXML
    private JFXListView<Image> listaImagenes;

    @FXML
    private JFXButton agregarImagen;

    @FXML
    private JFXButton btnGuardar;
    
    @FXML
    private JFXComboBox<Genero> comboGenero;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnGuardar.setDisable(true);
        tfNombre.textProperty().addListener((observable) -> {
            activarBotonGuardar();
        });
        areaBio.textProperty().addListener((observable) -> {
            activarBotonGuardar();
        });
        tfPass.textProperty().addListener((observable) -> {
            activarBotonGuardar();
        });
        tfConfirm.textProperty().addListener((observable) -> {
            activarBotonGuardar();
        });
        tfCorreo.textProperty().addListener((observable) -> {
            activarBotonGuardar();
        });
        
        listaImagenes.setCellFactory(lv -> {
            ImageView vista = new ImageView();
            ListCell<Image> cell = new ListCell<>();
            
            ContextMenu contextMenu = new ContextMenu();
            MenuItem eliminar = new MenuItem("Eliminar");
            eliminar.setOnAction((event) -> {
                listaImagenes.getItems().remove(cell.getItem());
                if(listaImagenes.getItems().size() < 5){
                    agregarImagen.setDisable(false);
                }  
            });
            contextMenu.getItems().addAll(eliminar);

            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            
            cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                vista.setFitHeight(30);
                vista.setFitWidth(30);
                if (newItem != null) {
                    try {
                        vista.setImage(newItem);
                    } catch (Exception ex) {}
                }
            });
            
            cell.emptyProperty().addListener((obs, wasEmpty, isEmpty) -> {
                if (isEmpty) {
                    cell.setGraphic(null);
                } else {
                    cell.setGraphic(vista);
                }
            });
            cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            return cell ;
        });
        
        Mensaje mensajeGeneros = HttpUtils.recuperarGeneros();
        Type tipoGeenero = new TypeToken<ArrayList<Genero>>(){}.getType();
        List<Genero> generos = new Gson().fromJson(mensajeGeneros.getMensaje(), tipoGeenero);
        ObservableList<Genero> generosObservables = FXCollections.observableArrayList(generos);
        comboGenero.setItems(generosObservables);
    }
    
    @FXML
    public void regresaLogin() {
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void activarBotonGuardar(){
        if(areaBio.getText().trim().isEmpty() || tfNombre.getText().trim().isEmpty() || 
                tfPass.getText().trim().isEmpty() || tfConfirm.getText().trim().isEmpty() || 
                tfCorreo.getText().trim().isEmpty() || comboGenero.getSelectionModel().isEmpty()){
            btnGuardar.setDisable(true);
        } else {
            btnGuardar.setDisable(false);
        }
    }
    
    @FXML
    public void guardarArtista(){
        if(tfPass.getText().equals(tfConfirm.getText())){
            Artista artista = new Artista(tfNombre.getText(), areaBio.getText(), 
                    comboGenero.getSelectionModel().getSelectedItem().getIdGenero(), 
                    tfCorreo.getText().toLowerCase(), Cifrado.cifrarCadena(tfPass.getText()));
            Mensaje mensaje = HttpUtils.agregarArtista(artista);
            
            if (mensaje.getMensaje().equals("12")) {
                if (subirFotos().equals("16")){
                    new Dialogo(mensaje.getMensaje(), ButtonType.OK).show();
                    limpiarCampos();
                } else {
                    new Dialogo("13", ButtonType.OK).show();
                }
            }
        } else {
            new Dialogo("300", ButtonType.OK).show();
        }
    }
    
    @FXML
    public void anadirImagen(){
        FileChooser seleccionarFoto = new FileChooser();
        seleccionarFoto.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Imágenes", "*.jpg"));
        File archivo = seleccionarFoto.showOpenDialog(MainApp.getVentana());
        if(archivo != null) {
            try {
                listaImagenes.getItems().add(Imagen.archivoAImagen(archivo));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if(listaImagenes.getItems().size() > 4) {
                agregarImagen.setDisable(true);
            }
        }
    }
    
    private String subirFotos() {
        Mensaje mensajeArtista = HttpUtils.recuperarArtista(tfCorreo.getText());
        Artista artista = new Gson().fromJson(mensajeArtista.getMensaje(), Artista.class);
        List<FotoArtista> fotos = new ArrayList<>();
        
        listaImagenes.getItems().forEach((imagen) -> {
            try {
                fotos.add(new FotoArtista(Imagen.imagenAString(imagen), artista.getIdArtista()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Mensaje mensajeFotos = HttpUtils.subirFotos(fotos);
        return mensajeFotos.getMensaje();
    }
    
    private void limpiarCampos() {
        tfNombre.setText("");
        areaBio.setText("");
        tfCorreo.setText("");
        tfPass.setText("");
        tfConfirm.setText("");
        listaImagenes.setItems(null);
    }
}
