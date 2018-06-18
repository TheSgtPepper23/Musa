package games.rockola.musa.servicios;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SubirCancion {

    private static InetAddress host;
    private static final int PUERTO = 55055;

    public void subir(File archivo) {
        try {
            host = InetAddress.getByName("206.189.124.168");
        } catch (UnknownHostException ex) {
            System.out.println("ID del host no encontrado");
            System.exit(1);
        }

        Socket socket = null;
        byte[] buffer = new byte[8192];
        try {
            socket = new Socket(host, PUERTO);
            OutputStream out = socket.getOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));

            int count;
            while ((count = in.read(buffer)) >= 0) {
                out.write(buffer, 0, count);
                out.flush();
            }
            out.close();
            in.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
