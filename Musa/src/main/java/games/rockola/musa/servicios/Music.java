/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.servicios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author lalo
 */
public class Music extends Thread {
    
    private static Music music;
    private static String ruta = "";
    
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(ruta);
            Player player = new Player(fileInputStream);
            player.play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
    
    public static void reproducir(String direccion) {
        ruta = direccion;
        music = new Music();
        music.start();
    }
    
    public static void detener() {
        music.stop();
    }
}
