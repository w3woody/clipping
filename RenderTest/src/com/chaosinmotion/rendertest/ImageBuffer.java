/*  ImageBuffer.java
 *
 *  Created on Sep 5, 2010 by William Edward Woody
 */

package com.chaosinmotion.rendertest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBuffer
{
    BufferedImage image;
    private float[] depth;
    private int width;
    private int height;
    
    /**
     * Create the image buffer
     * @param w
     * @param h
     */
    public ImageBuffer(int w, int h)
    {
        width = w;
        height = h;
        
        image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        g.dispose();
        
        depth = new float[w*h];
        for (int i = w*h-1; i >= 0; --i) depth[i] = 1;
    }
    
    /**
     * Set the color at this point and depth
     * @param x
     * @param y
     * @param c
     * @param d
     */
    public void setColor(int x, int y, int c, double d)
    {
        if (x < 0) return;
        if (y < 0) return;
        if (x >= width) return;
        if (y >= height) return;
        
        int index = x + y * width;
        if (depth[index] > d) {
            depth[index] = (float)d;
            image.setRGB(x, y, c);
        }
    }
    
    public void writeJPEGFile(String name) throws IOException
    {
        ImageIO.write(image, "png", new File(name));
    }

    public int getHeight()
    {
        return height;
    }
    
    public int getWidth()
    {
        return width;
    }
}


