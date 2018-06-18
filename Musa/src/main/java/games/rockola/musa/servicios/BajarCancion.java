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
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author lalo
 */
public class BajarCancion {

    Socket server = null;

    public void bajar(String ruta, String nombre, String autor, String album) {
        
        InputStream in = null;
        PrintWriter pw = null;
        
        try {

            server = new Socket(InetAddress.getByName("127.0.0.1"), 7171);
            
            pw = new PrintWriter(server.getOutputStream());
            
            pw.print(ruta);
            
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
