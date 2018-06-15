/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import games.rockola.musa.MainApp;
import games.rockola.musa.ws.HttpUtils;
import games.rockola.musa.ws.pojos.Artista;
import games.rockola.musa.ws.pojos.Mensaje;
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

/**
 *
 * @author José Andrés Domínguez González
 */
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
    private JFXListView<?> listaImagenes;

    @FXML
    private JFXButton agregarImagen;

    @FXML
    private JFXButton btnCancelar;

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
        
        Mensaje mensajeGeneros = HttpUtils.recuperarGeneros();
        List<Genero> generos = new Gson().fromJson(mensajeGeneros.getMensaje(), 
                new TypeToken<ArrayList<Genero>>(){}.getType());
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
        if(areaBio.getText().isEmpty() || tfNombre.getText().isEmpty() || tfPass.getText().isEmpty()
                || tfConfirm.getText().isEmpty() || tfCorreo.getText().isEmpty()){
            btnGuardar.setDisable(true);
        } else {
            btnGuardar.setDisable(false);
        }
    }
    
    @FXML
    public void guardarArtista(){
        Artista artista = new Artista(tfNombre.getText(), areaBio.getText(), "", 
                tfCorreo.getText(), tfPass.getText());
    }
}
