/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package games.rockola.musa.servicios;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author lalo
 */
public class BajarCancion {

    Socket server = null;

    public void bajar(String nombre, String autor, String album) {
        
        InputStream in = null;
        
        try {

            server = new Socket(InetAddress.getByName("206.189.124.168"), 7171);
            
            in = server.getInputStream();
            
            File nuevoFile = new File("/Users/lalo/Andres/Descargas/" + autor + "/" + album + "/" + nombre);
            nuevoFile.getParentFile().mkdirs();
            FileOutputStream fos = new FileOutputStream(nuevoFile, false);

            BufferedOutputStream out = new BufferedOutputStream(fos);

            IOUtils.copy(in, out);

            fos.close();
            in.close();
            server.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
