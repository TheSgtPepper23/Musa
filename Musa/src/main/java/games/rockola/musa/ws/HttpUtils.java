package games.rockola.musa.ws;

import games.rockola.musa.Imagenes;
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
import java.util.Arrays;
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
                    Arrays.toString(Imagenes.string2bytes(melomano.getFotoPerfil())), melomano.getCorreoElectronico());
        } catch (Exception ex) {
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return invocarServicioWeb("melomano/agregar", "POST", params);
    }
    
    public static Mensaje iniciarSesion(String nombreUsuario, String pass) {
        String params = String.format("nombreUsuario=%s&password=%s", nombreUsuario, pass);
        return invocarServicioWeb("melomano/login", "POST", params);
    }
    
    public static Mensaje recuperarMelomano(String nombreMelomano) {
        String params = String.format("nombreMelomano=%s", nombreMelomano);
        return invocarServicioWeb("melomano/recuperar", "POST", params);
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
                //----PASAR PARÁMETROS EN EL CUERPO DEL MENSAJE POST, PUT y DELETE----//
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
