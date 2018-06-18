package games.rockola.musa.servicios;

import java.io.*;
import java.net.*;

public class SubirCancion{
    
    Integer PUERTO = 55055;
    Socket server = null;
    OutputStream salida = null;
    byte[] buffer = new byte[1024];
   
    public void subir(String nombreArtista, String nombreAlbum, File archivo) {
        
        try {
            server = new Socket(InetAddress.getByName("192.168.0.18"), PUERTO);
            salida = server.getOutputStream();
            
            salida.write(nombreArtista.getBytes());
            salida.flush();
            salida.write(nombreAlbum.getBytes());
            salida.flush();
            salida.write(archivo.getName().getBytes());
            salida.flush();
            salida.write(String.valueOf(archivo.length()).getBytes());
            salida.flush();
            
            FileInputStream fis = new FileInputStream(archivo);
            
            while (fis.read(buffer) > 0) {
                salida.write(buffer);
            }
            
            fis.close();
            salida.close();
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
