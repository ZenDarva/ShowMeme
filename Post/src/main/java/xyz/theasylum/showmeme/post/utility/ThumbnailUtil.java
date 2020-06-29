package xyz.theasylum.showmeme.post.utility;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ThumbnailUtil {

    public byte[] generateImageThumbnail(byte[] sourceImage) throws IOException, ImageReadException {
        BufferedImage image = Imaging.getBufferedImage(sourceImage);

        //Imaging.writeImageToBytes()

        return null;
    }
}
