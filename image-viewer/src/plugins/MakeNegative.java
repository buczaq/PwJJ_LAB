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
public class MakeNegative {
    public static void negative(BufferedImage img, File f) throws IOException
    {
        int width  = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImage = new BufferedImage(width, height, img.getType() );
        System.out.println("sciezka: " + f.getAbsolutePath());
        for(int i = 0 ; i < width ; i++)
            for(int j = 0 ; j < height ; j++) {
                int p = img.getRGB(i, j);
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                p = (a<<24) | (r<<16) | (g<<8) | b;
                //System.out.println(i + " " + j);
                newImage.setRGB(i, j, p);
            }
        Files.deleteIfExists(f.toPath()); 
        ImageIO.write(newImage, "jpg", f);
    }
}
