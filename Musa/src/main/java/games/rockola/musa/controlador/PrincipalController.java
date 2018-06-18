package games.rockola.musa.controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.Imagen;
import games.rockola.musa.servicios.SubirCancion;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

public class PrincipalController implements Initializable {

    @FXML
    private ImageView imageAlbum;

    @FXML
    private Label labelTitulo;
    
    @FXML
    private Label labelPlaylistTitulo;

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
    static ArrayList<Playlist> playlistMelomano;
    static ListaCancion actual;
    
    static ArrayList<ListaCancion> listaReproduccionSiguientes = new ArrayList();
    static ArrayList<ListaCancion> listaReproduccionAnteriores = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Parent config = FXMLLoader.load(getClass().getResource("/fxml/PanelConfig.fxml"));
            drawerPrincipal.setSidePane(config);
            drawerPrincipal.setDisable(true);
        } catch (IOException ex) {
        }

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
        llenarPlaylists();
        llenarListaCanciones();
        agregarMenus();
        
        tablaCanciones.setRowFactory( tv -> {
            TableRow <ListaCancion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ListaCancion cancion = row.getItem();
                    reproducir(cancion);
                }
            });
            return row;
        });
        tablaListas.setRowFactory( tv -> {
            TableRow <Playlist> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    llenarListaDeCancionesDePlaylist(row.getItem());
                }
            });
            return row;
        });

    }

    @FXML
    public void ocultarDrawer() {
        if (!drawerPrincipal.isOpened()) {
            drawerPrincipal.setDisable(true);
        }
    }
    
    private void llenarListaDeCancionesDePlaylist(Playlist playlist) {
        labelPlaylistTitulo.setText(playlist.getNombre());
        List<ListaCancion> canciones;

        Mensaje mensaje = HttpUtils.recuperarCancionesDePlaylist(
                LoginController.melomanoLogueado.getIdMelomano(), playlist.getIdPlaylist());
        Type listaCanciones = new TypeToken<ArrayList<ListaCancion>>() {
        }.getType();
        canciones = new Gson().fromJson(mensaje.getMensaje(), listaCanciones);

        ObservableList<ListaCancion> listaObservable = FXCollections.observableArrayList(canciones);
        columnaTitulo.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnaAlbum.setCellValueFactory(new PropertyValueFactory("album"));
        columnaArtista.setCellValueFactory(new PropertyValueFactory("artista"));
        columnaDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));
        tablaCanciones.setItems(listaObservable);
    }

    private void llenarListaCanciones() {
        List<ListaCancion> canciones;

        Mensaje mensaje = HttpUtils.recuperarTodasCanciones();
        Type listaCanciones = new TypeToken<ArrayList<ListaCancion>>() {
        }.getType();
        canciones = new Gson().fromJson(mensaje.getMensaje(), listaCanciones);

        ObservableList<ListaCancion> listaObservable = FXCollections.observableArrayList(canciones);
        columnaTitulo.setCellValueFactory(new PropertyValueFactory("nombre"));
        columnaAlbum.setCellValueFactory(new PropertyValueFactory("album"));
        columnaArtista.setCellValueFactory(new PropertyValueFactory("artista"));
        columnaDuracion.setCellValueFactory(new PropertyValueFactory("duracion"));
        tablaCanciones.setItems(listaObservable);
        
        for(ListaCancion cancion: canciones) {
            System.out.println(cancion.getDuracion());
        }
    }

    private void llenarPlaylists() {
        List<Playlist> playlists;

        Mensaje mensaje = HttpUtils.recuperarPlaylistsMelomano(
                LoginController.melomanoLogueado.getIdMelomano());
        Type typePlay = new TypeToken<ArrayList<Playlist>>() {
        }.getType();
        playlists = new Gson().fromJson(mensaje.getMensaje(), typePlay);

        playlistMelomano = (ArrayList)playlists;

        ObservableList<Playlist> listaObservable = FXCollections.observableArrayList(playlists);
        columnaListas.setCellValueFactory(new PropertyValueFactory("nombre"));
        tablaListas.setItems(listaObservable);
    }
    
    private void agregarMenus() {
        MenuItem agregarFinalMI = new MenuItem("Agregar canción al final");
        agregarFinalMI.setOnAction((ActionEvent event) -> {
            listaReproduccionSiguientes.add(tablaCanciones.getSelectionModel().getSelectedItem());
        });

        MenuItem agregarContinuacionMI = new MenuItem("Agregar canción a continuación");
        agregarContinuacionMI.setOnAction((ActionEvent event) -> {
            listaReproduccionSiguientes.add(0, tablaCanciones.getSelectionModel().getSelectedItem());
        });

        Menu agregarPlaylist = new Menu("Agregar a playlist");

        playlistMelomano.forEach((item) -> {
            MenuItem lista = new MenuItem(item.getNombre());
            lista.setOnAction((ActionEvent event) -> {
                Mensaje mensaje = HttpUtils.agregaAPlaylist(item.getIdPlaylist(), 
                        tablaCanciones.getSelectionModel().getSelectedItem().getIdCancion());
            });
            agregarPlaylist.getItems().add(lista);
        });

        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(agregarPlaylist, agregarFinalMI, agregarContinuacionMI);
        tablaCanciones.setContextMenu(menu);
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        labelPlaylistTitulo.setText("Musa");
        tablaCanciones.setItems(null);
        List<ListaCancion> canciones;

        Mensaje mensaje = HttpUtils.buscarCancion(tfBuscar.getText());
        Type listaCanciones = new TypeToken<ArrayList<ListaCancion>>() {
        }.getType();
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
    
    private void reproducir(ListaCancion cancion) {
        actual = cancion;
        labelTitulo.setText(cancion.getNombre());
        labelArtista.setText(cancion.getArtista());
        labelFin.setText(cancion.getDuracion());
        
        //Aquí va el método para recuperar el archivo de la canción
    }
    
    @FXML
    public void retrocederCancion(){
        if (listaReproduccionAnteriores != null) {
            reproducir(listaReproduccionAnteriores.get(listaReproduccionAnteriores.size() - 1));
            listaReproduccionAnteriores.add(0, actual);
            listaReproduccionAnteriores.remove(listaReproduccionAnteriores.size() - 1);
        } else {
            //¿Diálogo?
        }
    }
    
    @FXML
    public void pasarCancion(){
        if (listaReproduccionSiguientes != null) {
            reproducir(listaReproduccionSiguientes.get(0));
            listaReproduccionAnteriores.add(actual);
            listaReproduccionSiguientes.remove(0);
        } else {
            //Diálogo de no hay más canciones siguientes
        }
    }
}
