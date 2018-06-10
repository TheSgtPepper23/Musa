package games.rockola.musa;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

public class Imagenes {
    
    public static byte [] codificarImagen(File imagen) throws IOException{
        BufferedImage bufferedImage = ImageIO.read(imagen);
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return data.getData();
    }
    
    public static Image decodificarImagen (byte[] imagenCodificada) throws Exception{
        return new Image(new ByteArrayInputStream(imagenCodificada));
    }
    
    public static String bytes2string(byte[] bytes) throws Exception {
        return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
    }
    
    public static byte[] string2bytes(String string) throws Exception {
        return Base64.getDecoder().decode(string.getBytes(StandardCharsets.UTF_8));
    }
 }
