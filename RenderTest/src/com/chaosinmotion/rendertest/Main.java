/*  Main.java
 *
 *  Created on Sep 5, 2010 by William Edward Woody
 */

package com.chaosinmotion.rendertest;

import java.io.IOException;

public class Main
{
    /**
     * @param args
     */
    public static void main(String[] args)
    {
    	System.out.println("starting...");
        Matrix m = Matrix.perspective1(0.8, 1, 1);
        renderTest(m,"image_err.png");
        
        m = Matrix.perspective2(0.8, 1, 1);
        renderTest(m,"image_ok.png");
    	System.out.println("done.");
    }

    private static void renderTest(Matrix m, String fname)
    {
        ImageBuffer buf = new ImageBuffer(450,450);
        m = m.multiply(Matrix.scale(225,225,1));
        m = m.multiply(Matrix.translate(225, 225, 0));
        
        Sphere sp = new Sphere(0,0,-5000000000000d,4000000000000d,0x0080FF);
        sp.render(m, buf);
        
        sp = new Sphere(700000000000d,100000000000d,-1300000000000d,300000000000d,0xFF0000);
        sp.render(m, buf);
        
        Polygon p = new Polygon();
        p.color = 0xFF00FF00;
        p.poly.add(new Vector(-10,-3,-20));
        p.poly.add(new Vector(-10,-1,-19));
        p.poly.add(new Vector(0,0.5,-22));
        p = p.transform(m);
        p.render(buf);
        
        try {
            buf.writeJPEGFile(fname);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}


