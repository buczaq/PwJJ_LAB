/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugins;

import java.awt.image.BufferedImage;

/**
 *
 * @author buczak
 */
public class RotateImage {
    
public static BufferedImage rotateCw(BufferedImage img)
{
    int width  = img.getWidth();
    int height = img.getHeight();
    BufferedImage newImage = new BufferedImage( height, width, img.getType() );

    for( int i=0 ; i < width ; i++ )
        for( int j=0 ; j < height ; j++ )
            newImage.setRGB( height-1-j, i, img.getRGB(i,j) );

    return newImage;
}
}
