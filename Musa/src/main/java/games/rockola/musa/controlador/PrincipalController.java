package games.rockola.musa.controlador;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import games.rockola.musa.servicios.BajarCancion;
import games.rockola.musa.servicios.Dialogo;
import games.rockola.musa.servicios.Imagen;
import games.rockola.musa.servicios.Music;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.ListaCancion;
import games.rockola.musa.ws.pojos.Melomano;
import games.rockola.musa.ws.pojos.Mensaje;
import games.rockola.musa.ws.pojos.Playlist;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

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

    @FXML
    private Button btnAgregarPlaylist;
    
    static Melomano melomano;
    static ArrayList<Playlist> playlistMelomano;
    static ListaCancion actual;

    static ArrayList<ListaCancion> listaReproduccionSiguientes = new ArrayList();
    static ArrayList<ListaCancion> listaReproduccionAnteriores = new ArrayList();

    static boolean reproduciendo = false;

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

        tablaCanciones.setRowFactory(tv -> {
            TableRow<ListaCancion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    ListaCancion cancion = row.getItem();
                    reproducir(cancion);
                }
            });
            return row;
        });
        tablaListas.setRowFactory(tv -> {
            TableRow<Playlist> row = new TableRow<>();
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

        for (ListaCancion cancion : canciones) {
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

        playlistMelomano = (ArrayList) playlists;

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

    @FXML
    public void reproducir(ListaCancion cancion) {
        
        Mensaje mensajeRuta = HttpUtils.rutaCancionID(cancion.getIdCancion());
        String ruta = mensajeRuta.getMensaje();
        
        BajarCancion bajar = new BajarCancion();
        bajar.bajar(ruta, cancion.getNombre(), cancion.getArtista(), cancion.getAlbum());
        
        if(reproduciendo) {
            pausar();
        }
        actual = cancion;
        labelTitulo.setText(cancion.getNombre());
        labelArtista.setText(cancion.getArtista());
        labelFin.setText(cancion.getDuracion());
        Music.reproducir("/Users/lalo/Andres/Descargas/" + cancion.getArtista() + "/" + 
                cancion.getAlbum() + "/" + cancion.getNombre());
        reproduciendo = true;
    }

    @FXML
    public void pausar() {
        Music.detener();
        reproduciendo = false;
    }

    @FXML
    public void retrocederCancion() {
        if (!listaReproduccionAnteriores.isEmpty()) {
            reproducir(listaReproduccionAnteriores.get(listaReproduccionAnteriores.size() - 1));
            listaReproduccionAnteriores.add(0, actual);
            listaReproduccionAnteriores.remove(listaReproduccionAnteriores.size() - 1);
        } else {
            Dialogo dialogo = new Dialogo("700", ButtonType.OK);
            dialogo.show();
        }
    }

    @FXML
    public void pasarCancion() {
        if (!listaReproduccionSiguientes.isEmpty()) {
            reproducir(listaReproduccionSiguientes.get(0));
            listaReproduccionAnteriores.add(actual);
            listaReproduccionSiguientes.remove(0);
        } else {
            Dialogo dialogo = new Dialogo("800", ButtonType.OK);
            dialogo.show();
        }
    }
    
    @FXML
    public void agregarPlayList() {
        Dialog dialog = new Dialog<>();
        dialog.setTitle(null);
        dialog.setHeaderText("Agregar nueva playlist");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
           getClass().getResource("/styles/Styles.css").toExternalForm());
        dialogPane.getStyleClass().add("Dialogos");
        
        ButtonType listaType = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(listaType, ButtonType.CANCEL);
        
        JFXTextField campoNombre = new JFXTextField();
        campoNombre.setPromptText("Nombre playlist");
        campoNombre.setLabelFloat(true);
        campoNombre.setPrefWidth(300);
        
        JFXTextField rutaImagen = new JFXTextField();
        rutaImagen.setPromptText("Ruta imagen");
        rutaImagen.setLabelFloat(true);
        rutaImagen.setPrefWidth(300);
        rutaImagen.setEditable(false);
        
        VBox cajaV = new VBox(campoNombre);
        cajaV.setAlignment(Pos.CENTER);
        
        JFXButton btnRuta = new JFXButton("Abrir");
        btnRuta.setOnAction((event) -> {
            FileChooser seleccionador = new FileChooser();
            seleccionador.setInitialDirectory(new File(System.getProperty("user.home")));
            seleccionador.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                    "Imágenes", "*.jpg"));
            File imagen = seleccionador.showOpenDialog(cajaV.getScene().getWindow());
            if (imagen != null)
                rutaImagen.setText(imagen.getPath());
            
        });
        
        HBox cajaH = new HBox(rutaImagen);
        cajaH.getChildren().add(btnRuta);
        cajaH.setPrefWidth(300);
        cajaV.getChildren().add(cajaH);
        cajaV.setSpacing(20);
        cajaH.setSpacing(20);
        
        Node guardarLista = dialog.getDialogPane().lookupButton(listaType);
        guardarLista.setDisable(true);
        
        campoNombre.textProperty().addListener((observable, oldValue, newValue) -> {
            guardarLista.setDisable(newValue.trim().isEmpty() || 
                    rutaImagen.getText().trim().isEmpty());
        });
        
        rutaImagen.textProperty().addListener((observable, oldValue, newValue) -> {
            guardarLista.setDisable(newValue.trim().isEmpty() ||
                    campoNombre.getText().trim().isEmpty());
        });
        
        dialog.getDialogPane().setContent(cajaV);
        Platform.runLater(() -> campoNombre.requestFocus());
        dialog.getDialogPane().setPrefSize(400, 200);
        dialog.initStyle(StageStyle.UNDECORATED);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == listaType) {
                return campoNombre.getText();
            }
            return null;
        });
        
        Optional<String> nombreLista = dialog.showAndWait();

        if(nombreLista.isPresent()) {
            System.out.println(nombreLista.get());
        }
    }
}
