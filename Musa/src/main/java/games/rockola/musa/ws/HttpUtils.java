package games.rockola.musa.ws;

import games.rockola.musa.ws.pojos.Album;
import games.rockola.musa.ws.pojos.Artista;
import games.rockola.musa.ws.pojos.Cancion;
import games.rockola.musa.ws.pojos.FotoArtista;
import games.rockola.musa.ws.pojos.Mensaje;
import games.rockola.musa.ws.pojos.Melomano;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpUtils {

    private static final String URL = "http://127.0.0.1:5555/";
    
    public static Mensaje agregarUsuario(Melomano melomano) {
        String params = null;
        try {
            params = String.format("nombreMelomano=%s&nombre=%s&apellidos=%s&password=%s&"
                    + "fotoPerfil=%s&correoElectronico=%s", melomano.getNombreMelomano(),
                    melomano.getNombre(), melomano.getApellidos(), melomano.getPassword(), 
                    melomano.getFotoPerfil(), melomano.getCorreoElectronico());
        } catch (Exception ex) {
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invocarServicioWeb("melomano/agregar", "POST", params);
    }
    
    public static Mensaje iniciarSesion(String username, String pass) {
        String params = String.format("username=%s&password=%s", username, pass);
        return invocarServicioWeb("login", "POST", params);
    }
    
    public static Mensaje recuperarMelomano(String nombreMelomano) {
        String params = String.format("nombreMelomano=%s", nombreMelomano);
        return invocarServicioWeb("melomano/recuperar", "POST", params);
    }
    
    public static Mensaje agregarCancion(Cancion cancion){
        String params = String.format("nombre=%s&idAlbum=%s&idGenero=%s&cancion=%s&duracion=%s", 
                cancion.getNombre(), cancion.getIdAlbum(), cancion.getIdGenero(), 
                cancion.getCancion(), cancion.getCancion());
        return invocarServicioWeb("cancion/agregar", "POSt", params);
    }
    
    public static Mensaje agregarAlbum(Album album){
        String params = String.format("nombre=%s&portada=%s&fechaLanzamiento=%s&"
                + "companiaDiscografica=%s&idArtista=%s,", album.getNombre(), album.getPortada(),
                album.getFechaLanzamiento(), album.getCompaniaDiscografica(), album.getIdArtista());
        return invocarServicioWeb("album/agregar", "POST", params);       
    }
    
    public static Mensaje agregarArtista(Artista artista) {
        String params = String.format("nombre=%s&biografia=%s&genero=%s&correoElectronico=%s"
                + "&password=%s", artista.getNombre(), artista.getBiografia(), artista.getGenero(),
                artista.getCorreoElectronico(), artista.getPassword());
        return invocarServicioWeb("artista/agregar", "POST", params);
    }
    
    public static Mensaje recuperarArtista(String nombre){
        String params = String.format("nombre=%s",  nombre);
        return invocarServicioWeb("artista/recuperarArtista", "POST", params);
    }
    
    public static Mensaje actualizarArtista(Integer idArtista, String biografia) {
        String params = String.format("idArtista=%s&biografia=%s", idArtista, biografia);
        return invocarServicioWeb("artista/actualizar", "POST", params);
    }
    
    public static Mensaje subirFotos(List<FotoArtista> listaFotos){
        Mensaje mensaje = new Mensaje();
        mensaje.setMensaje("16");
        mensaje.setEstado(200);
        
        for (FotoArtista foto : listaFotos) {
            String params = String.format("idArtista=%s&foto=%s", foto.getIdArtista(), foto.getFoto());
            Mensaje fotoMensaje = invocarServicioWeb("artista/subirFoto", "POST", params);
            if (fotoMensaje.getMensaje().equals("17")) {
                mensaje.setMensaje(fotoMensaje.getMensaje());
                mensaje.setEstado(fotoMensaje.getEstado());
            }
        }
        return mensaje;
    }
    
    private static Mensaje invocarServicioWeb(String url, String tipoinvocacion, String parametros){
        HttpURLConnection c = null;
        URL u = null;
        Mensaje mensaje = null;
        try {
            if(tipoinvocacion.compareToIgnoreCase("GET")==0){
                u = new URL(URL+url+((parametros!=null)?parametros:""));
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod(tipoinvocacion);
                c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.connect();
            }else{
                u = new URL(URL+url);
                c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod(tipoinvocacion);
                c.setDoOutput(true);
                
                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        c.getOutputStream(), "UTF-8"))) {
                    bw.write(parametros);
                    bw.flush();
                }
            }
            mensaje = new Mensaje();
            mensaje.setEstado(c.getResponseCode());
            if(mensaje.getEstado()!=200 && mensaje.getEstado()!=201){
                mensaje.setError(true);
            }
            if(c.getInputStream()!=null) {
                StringBuilder sb;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                    sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                }
                mensaje.setMensaje(sb.toString());
            }
        } catch (MalformedURLException ex) {
            mensaje.setError(true);
            mensaje.setMensaje(ex.getMessage());
        } catch (IOException ex) {
            mensaje.setError(true);
            mensaje.setMensaje(ex.getMessage());
        } finally {
            if (c != null) {
                c.disconnect();
            }
        }
        return mensaje;
    }
}
