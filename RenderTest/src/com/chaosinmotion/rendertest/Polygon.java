/*  Polygon.java
 *
 *  Created on Sep 5, 2010 by William Edward Woody
 */

package com.chaosinmotion.rendertest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Define a 3 point 3D polygon
 *	Comment
 */
public class Polygon
{
    public ArrayList<Vector> poly;
    public int color;
    private static int count = 0;
    private int id = ++count;
    
    Polygon()
    {
        poly = new ArrayList<Vector>();
    }
    
    public Polygon transform(Matrix m)
    {
        Polygon p = new Polygon();
        for (Vector v: poly) {
            p.poly.add(m.transform(v));
        }
        p.color = color;
        return p;
    }
    
    private static class Line implements Comparable<Line>
    {
        double x,dx;
        double yt, yb;
        double z,dz;
        
        Line(Vector t, Vector b)
        {
            double y1 = t.a[1]/t.a[3];
            double y2 = b.a[1]/b.a[3];

            if (y1 > y2) {
                double tmp = y2;
                y2 = y1;
                y1 = tmp;
                
                Vector tmpv = t;
                t = b;
                b = tmpv;
            }
            
            double x1 = t.a[0]/t.a[3];
            double z1 = t.a[2]/t.a[3];

            double x2 = b.a[0]/b.a[3];
            double z2 = b.a[2]/b.a[3];
            
            yt = y1;
            yb = y2;
            
            x = x1;
            z = z1;
            dx = (x2 - x1) / (y2 - y1);
            dz = (z2 - z1) / (y2 - y1);
        }
        
        int in()
        {
            return (int)Math.ceil(yt);
        }
        
        boolean out(int y)
        {
            return (y >= yb);
        }

        public void initialize(int index)
        {
            x += dx * (index - yt);
            z += dz * (index - yt);
        }

        @Override
        public int compareTo(Line o)
        {
            if (x < o.x) return -1;
            if (x > o.x) return 1;
            return 0;
        }
    }
    
    /**
     * Render this polygon in the image buffer. This does the conversion to x,y,z, and
     * renders the polygon
     * @param buf
     */
    public void render(ImageBuffer buf)
    {
        if (id == 264) {
            System.out.println("...");
        }
        /*
         * Create an array of line starts and populate with my lines
         */
        @SuppressWarnings("unchecked")
        ArrayList<Line>[] list = new ArrayList[buf.getHeight()];
        Vector end = poly.get(poly.size()-1);
        for (Vector start: poly) {
            Line l = new Line(start,end);
            end = start;
            int index = l.in();
            if (index < 0) index = 0;
            if (l.out(index)) continue;     // horizontal line or above top
            if (index >= list.length) continue; // past bottom
            l.initialize(index);
            if (list[index] == null) list[index] = new ArrayList<Line>();
            list[index].add(l);
        }
        
        /*
         * Render the runs
         */
        
        HashSet<Line> lines = new HashSet<Line>();
        
        for (int y = 0; y < buf.getHeight(); ++y) {
            if (list[y] != null) {
                for (Line line: list[y]) {
                    lines.add(line);
                }
            }
            Iterator<Line> iter = lines.iterator();
            while (iter.hasNext()) {
                if (iter.next().out(y)) iter.remove();
            }
            if (lines.isEmpty()) continue;
            
            Line[] lrow = (Line[])lines.toArray(new Line[lines.size()]);
            Arrays.sort(lrow);
            
            int i;
            for (i = 0; i < lrow.length; i += 2) {
                int j = i+1;
                if (j < lrow.length) {
                    /*
                     * Draw the line from lrow[i] to lrow[j]
                     */
                    
                    int xleft = (int)Math.ceil(lrow[i].x);
                    int xright = 1+(int)lrow[j].x;
                    if (xleft < 0) xleft = 0;
                    if (xright > buf.getWidth()) xright = buf.getWidth();
                    if (xright <= xleft) continue;
                    
                    /*
                     * Render on row y from xleft to xright
                     */
                    
                    double zj = lrow[j].z;
                    double zi = lrow[i].z;
                    double dx = lrow[j].x - lrow[i].x;
                    double dz;
                    if (dx == 0) {
                        dz = 0;
                    } else {
                        dz = (zj - zi)/dx;
                    }

                    double z = zi + dz * (xleft - lrow[i].x);
                    for (int x = xleft; x < xright; ++x) {
                        buf.setColor(x, y, color, z);
                        z += dz;
                    }
                } else {
                    System.out.println(id + "?");
                }
            }
            
            for (Line line: lines) {
                line.x += line.dx;
                line.z += line.dz;
            }
        }
    }
}


