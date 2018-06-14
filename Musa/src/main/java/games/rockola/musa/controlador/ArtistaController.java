/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import games.rockola.musa.Dialogo;
import games.rockola.musa.Imagenes;
import games.rockola.musa.MainApp;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.Artista;
import games.rockola.musa.ws.pojos.FotoArtista;
import games.rockola.musa.ws.pojos.Mensaje;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author José Andrés Domínguez González
 */
public class ArtistaController implements Initializable {
    
    @FXML
    private Label labelArtista;

    @FXML
    private JFXTextArea areaBio;

    @FXML
    private JFXListView<File> listaImagenes;

    @FXML
    private JFXListView<File> listaAlbumes;

    @FXML
    private JFXButton btnGuardar;
    
    @FXML
    private JFXButton agregarImagen;
    
    Artista artista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        artista = LoginController.artistaLogueado;
        areaBio.setText(artista.getBiografia());
        labelArtista.setText(artista.getNombre());
        System.out.println(artista.getIdArtista());
        
        listaImagenes.setCellFactory(lv -> {
            ImageView vista = new ImageView();
            ListCell<File> cell = new ListCell<>();
            
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
                vista.setFitHeight(100);
                vista.setFitWidth(100);
                if (newItem != null) {
                    try {
                        vista.setImage(Imagenes.archivoAImagen(newItem));
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
    }    
        
    @FXML
    public void anadirImagen(){
        FileChooser seleccionarFoto = new FileChooser();
        seleccionarFoto.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Imágenes", "*.jpg"));
        File archivo = seleccionarFoto.showOpenDialog(MainApp.getVentana());
        if(archivo != null) {
            listaImagenes.getItems().add(archivo);
            if(listaImagenes.getItems().size() > 4) {
                agregarImagen.setDisable(true);
            }
        }
    }
    
    @FXML
    public void guardarCambios(){
        List<FotoArtista> fotos = new ArrayList<>();
        
        listaImagenes.getItems().forEach((archivo) -> {
            try {
                fotos.add(new FotoArtista(Imagenes.codificarImagen(archivo), artista.getIdArtista()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        Mensaje mensaje = HttpUtils.actualizarArtista(artista.getIdArtista(), areaBio.getText());
        Mensaje mensajeEliminar = HttpUtils.eliminarFotosArtista(artista.getIdArtista());
        Mensaje mensajeFotos = HttpUtils.subirFotos(fotos);
        
        if(mensaje.getMensaje().equals("16") && mensajeFotos.getMensaje().equals("16") && 
                mensajeEliminar.getEstado().equals("16")){
            new Dialogo(mensaje.getMensaje(), ButtonType.OK).show();
        } else {
            new Dialogo(mensaje.getMensaje(), ButtonType.OK).show();
        }
    }
    
    @FXML
    public void regresarLogin(){
        MainApp main = new MainApp();
        try {
            main.cambiarEscena(1);
        } catch (IOException ex) {}
    }
}
