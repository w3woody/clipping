/*  Vector.java
 *
 *  Created on Sep 5, 2010 by William Edward Woody
 */

package com.chaosinmotion.rendertest;

public class Vector
{
    double[] a;
    
    public Vector()
    {
        a = new double[4];
    }
    
    public Vector(double x, double y, double z)
    {
        this();
        a[0] = x;
        a[1] = y;
        a[2] = z;
        a[3] = 1;
    }
    
    public double get(int x)
    {
        return a[x];
    }
    
    public void set(int x, double val)
    {
        a[x] = val;
    }
    
    public String toString()
    {
        double x = a[0]/a[3];
        double y = a[1]/a[3];
        double z = a[2]/a[3];
        
        return "[" + x + ',' + y + ',' + z + ']';
    }
}


