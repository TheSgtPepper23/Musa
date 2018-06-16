package games.rockola.musa.servicios;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

public class Imagen {
//    

    public static String codificarImagen(File imagen) throws IOException {
        Base64 base = new Base64();
        BufferedImage originalImage = ImageIO.read(imagen);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        baos.close();
        return base.encodeToString(baos.toByteArray());
    }

    public static Image decodificarImagen(String imagenCodificada) throws Exception {
        Base64 base = new Base64();
        imagenCodificada = imagenCodificada.replaceAll(" ", "+");
        InputStream in = new ByteArrayInputStream(base.decode(imagenCodificada));
        BufferedImage image = ImageIO.read(in);

        return SwingFXUtils.toFXImage(image, null);
    }

    public static Image archivoAImagen(File archivoImagen) throws Exception {
        BufferedImage bimage = ImageIO.read(archivoImagen);
        return SwingFXUtils.toFXImage(bimage, null);
    }

    public static String imagenAString(Image imagen) throws Exception {
        File archivoImagen = new File("src/main/resources/images/fotoTemp.jpg");
        BufferedImage bImage = SwingFXUtils.fromFXImage(imagen, null);
        try {
            ImageIO.write(bImage, "jpg", archivoImagen);
        } catch (IOException e) {
            System.out.println("Aqui");
        }
        return codificarImagen(archivoImagen);
    }
}
