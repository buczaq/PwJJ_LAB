/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugins;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.imageio.ImageIO;

/**
 *
 * @author buczak
 */
public class RotateImage {
    
public static void rotate(BufferedImage img, File f) throws IOException
{
    int width  = img.getWidth();
    int height = img.getHeight();
    BufferedImage newImage = new BufferedImage( height, width, img.getType() );
    System.out.println("sciezka: " + f.getAbsolutePath());
    for( int i=0 ; i < width ; i++ )
        for( int j=0 ; j < height ; j++ )
            newImage.setRGB( height-1-j, i, img.getRGB(i,j) );
    Files.deleteIfExists(f.toPath()); 
    ImageIO.write(newImage, "jpg", f);
}
}
