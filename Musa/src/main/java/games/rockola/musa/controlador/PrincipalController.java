package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
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
    private JFXTreeTableView<?> tablaListas;

    @FXML
    private TreeTableColumn<?, ?> colListas;

    @FXML
    private JFXTextField tfBuscar;

    @FXML
    private JFXTreeTableView<?> tablaCanciones;

    @FXML
    private TreeTableColumn<?, ?> colTitulo;

    @FXML
    private TreeTableColumn<?, ?> colArtista;

    @FXML
    private TreeTableColumn<?, ?> colAlbum;

    @FXML
    private TreeTableColumn<?, ?> colFecha;

    @FXML
    private TreeTableColumn<?, ?> colDuracion;
    
    @FXML
    private JFXDrawer drawerPrincipal;

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
    }    
    
    @FXML
    public void ocultarDrawer() {
        if (!drawerPrincipal.isOpened()) {
            drawerPrincipal.setDisable(true);
        }
    }
    
}
