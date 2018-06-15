/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author José Andrés Domínguez González
 */
public class ArtistaNuevoController implements Initializable{
    
    @FXML
    private JFXTextArea areaBio;

    @FXML
    private JFXListView<?> listaImagenes;

    @FXML
    private JFXButton agregarImagen;

    @FXML
    private JFXListView<?> listaAlbumes;

    @FXML
    private JFXButton btnCancelar;

    @FXML
    private JFXButton btnGuardar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
