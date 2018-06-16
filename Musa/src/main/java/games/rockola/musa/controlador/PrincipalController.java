package games.rockola.musa.controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.Imagen;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.ListaCancion;
import games.rockola.musa.ws.pojos.Melomano;
import games.rockola.musa.ws.pojos.Mensaje;
import games.rockola.musa.ws.pojos.Playlist;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class PrincipalController implements Initializable {
    
    @FXML
    private ImageView imageAlbum;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelArtista;

    @FXML
    private JFXSlider slideVolumen;

    @FXML
    private Label labelInicio;

    @FXML
    private Label labelFin;

    @FXML
    private JFXButton btnRepetir;

    @FXML
    private JFXButton btnAtras;

    @FXML
    private JFXButton btnPlay;

    @FXML
    private JFXButton btnAdelante;

    @FXML
    private JFXButton btnAleatorio;

    @FXML
    private ImageView imagenUsuario;

    @FXML
    private Hyperlink linkUsuario;

    @FXML
    private JFXTextField tfBuscar;
    
    @FXML
    private JFXDrawer drawerPrincipal;
    
    @FXML
    private TableView<Playlist> tablaListas;

    @FXML
    private TableColumn columnaListas;

    @FXML
    private TableView<ListaCancion> tablaCanciones;

    @FXML
    private TableColumn columnaTitulo;

    @FXML
    private TableColumn columnaAlbum;

    @FXML
    private TableColumn columnaArtista;

    @FXML
    private TableColumn columnaDuracion;
    
    static Melomano melomano;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Parent config = FXMLLoader.load(getClass().getResource("/fxml/PanelConfig.fxml"));
            drawerPrincipal.setSidePane(config);
            drawerPrincipal.setDisable(true);
        } catch (IOException ex) {}
        
        linkUsuario.setOnAction((event) -> {
           drawerPrincipal.open();
           drawerPrincipal.setDisable(false);
        });
        
        try {
            imagenUsuario.setImage(Imagen.decodificarImagen(
                    LoginController.melomanoLogueado.getFotoPerfil()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        linkUsuario.setText(LoginController.melomanoLogueado.getNombreMelomano());
        llenarListaCanciones();
        llenarPlaylists();
    }    
    
    @FXML
    public void ocultarDrawer() {
        if (!drawerPrincipal.isOpened()) {
            drawerPrincipal.setDisable(true);
        }
    }
    
    private void llenarListaCanciones() {
        List<ListaCancion> canciones;
        
        Mensaje mensaje = HttpUtils.recuperarTodasCanciones();
        Type listaCanciones = new TypeToken<ArrayList<ListaCancion>>(){}.getType();
        canciones = new Gson().fromJson(mensaje.getMensaje(), listaCanciones);
        
        ObservableList<ListaCancion> listaObservable = FXCollections.observableArrayList(canciones);
        columnaTitulo.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnaAlbum.setCellValueFactory(new PropertyValueFactory("album"));
        columnaArtista.setCellValueFactory(new PropertyValueFactory("artista"));
        columnaDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));
        tablaCanciones.setItems(listaObservable);
    }
    
    private void llenarPlaylists() {
        List<Playlist> playlists;
        
        Mensaje mensaje = HttpUtils.recuperarPlaylistsMelomano(
                LoginController.melomanoLogueado.getIdMelomano());
        Type typePlay = new TypeToken<ArrayList<Playlist>>(){}.getType();
        playlists = new Gson().fromJson(mensaje.getMensaje(), typePlay);
        
        ObservableList<Playlist> listaObservable = FXCollections.observableArrayList(playlists);
        columnaListas.setCellValueFactory(new PropertyValueFactory("nombre"));
        tablaListas.setItems(listaObservable);
    }
    
    @FXML
    public void onEnter(ActionEvent ae) {
        tablaCanciones.setItems(null);
        List<ListaCancion> canciones;
        
        Mensaje mensaje = HttpUtils.buscarCancion(tfBuscar.getText());
        Type listaCanciones = new TypeToken<ArrayList<ListaCancion>>(){}.getType();
        canciones = new Gson().fromJson(mensaje.getMensaje(), listaCanciones);
        
        if (canciones != null) {
            ObservableList<ListaCancion> listaObservable = FXCollections.observableArrayList(canciones);
            columnaTitulo.setCellValueFactory(new PropertyValueFactory("nombre"));
            columnaAlbum.setCellValueFactory(new PropertyValueFactory("album"));
            columnaArtista.setCellValueFactory(new PropertyValueFactory("artista"));
            columnaDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));
            tablaCanciones.setItems(listaObservable);
        }   
    }
    
}
