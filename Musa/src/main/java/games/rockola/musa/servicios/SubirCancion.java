package games.rockola.musa.servicios;

import java.io.*;
import java.net.*;

public class SubirCancion{
    
    static Socket server = null;
    static PrintStream salida = null;
    static byte[] buffer = new byte[4096];
   
    public static void subir(String nombreArtista, String nombreAlbum, File archivo) {
        String artista = nombreArtista;
        String album = nombreAlbum;
        File cancion = archivo;
        
        try {
            server = new Socket(InetAddress.getByName("192.168.100.38"), 55055);
            salida = new PrintStream(server.getOutputStream(), true);
            
            salida.print(nombreArtista);
            salida.print(nombreAlbum);
            salida.print(archivo.getName());
            salida.print(archivo.length());
            
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
