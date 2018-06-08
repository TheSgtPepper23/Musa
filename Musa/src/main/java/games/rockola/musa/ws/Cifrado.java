package games.rockola.musa.ws;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author lalo
 */
public class Cifrado {
    
    public static String cifrarCadena(String cadena) {
        String cifrada = null;
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(cadena.getBytes("UTF-8"));
            return DatatypeConverter.printHexBinary(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            return cifrada;
        }
    }
}
