/*  Sphere.java
 *
 *  Created on Sep 5, 2010 by William Edward Woody
 */

package com.chaosinmotion.rendertest;

/**
 * Code for handling a sphere
 */
public class Sphere
{
    double x,y,z,r;
    private static final double delta = Math.PI/64;
    int color;
    
    public Sphere(double xx, double yy, double zz, double rr, int c)
    {
        x = xx;
        y = yy;
        z = zz;
        r = rr;
        color = c;
    }
    
    private Vector findPoint(double lat, double lon)
    {
        double xx,yy,zz;
        
        yy = Math.sin(lat) * r + y;
        
        double c = Math.cos(lat);
        xx = Math.cos(lon) * r * c + x;
        zz = Math.sin(lon) * r * c + z;
        
        return new Vector(xx,yy,zz);
    }
    
    private int color(double lat, double lon)
    {
        double yy = Math.sin(lat);
        double cc = Math.cos(lat);
        double xx = Math.cos(lon) * cc;
        double zz = Math.sin(lon) * cc;
        
        double i = - xx*0.75 - yy*0.75 + zz*0.5;
        if (i < 0) i = 0;
        if (i > 1) i = 1;
        
        int r = (int)(i * (0x00FF & (color >> 16)));
        int g = (int)(i * (0x00FF & (color >> 8)));
        int b = (int)(i * (0x00FF & color));
        
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
    
    public void render(Matrix m, ImageBuffer buf)
    {
        for (double lat = -Math.PI/2; lat <= Math.PI/2; lat += delta) {
            for (double lon = 0; lon < Math.PI*2; lon += delta) {
                renderSquare(m, buf, lat, lon);
            }
        }
    }

    private void renderSquare(Matrix m, ImageBuffer buf, double lat, double lon)
    {
        Polygon p;
        double lat2 = lat + delta;
        double lon2 = lon + delta;
        
        p = new Polygon();
        p.color = color(lat,lon);
        p.poly.add(findPoint(lat,lon));
        p.poly.add(findPoint(lat2,lon));
        p.poly.add(findPoint(lat2,lon2));
        
        p = p.transform(m);
        p.render(buf);
        
        p = new Polygon();
        p.color = color(lat,lon);
        p.poly.add(findPoint(lat,lon));
        p.poly.add(findPoint(lat,lon2));
        p.poly.add(findPoint(lat2,lon2));
        
        p = p.transform(m);
        p.render(buf);
    }
}


