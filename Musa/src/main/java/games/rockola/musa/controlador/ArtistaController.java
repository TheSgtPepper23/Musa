/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import games.rockola.musa.ws.pojos.Artista;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    private JFXListView<?> listaImagenes;

    @FXML
    private JFXListView<?> listaAlbumes;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXButton btnGuardar;
    
    Artista artista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        artista = LoginController.artistaLogueado;
        areaBio.setText(artista.getBiografia());
        labelArtista.setText(artista.getNombre());
    }    
    
}
