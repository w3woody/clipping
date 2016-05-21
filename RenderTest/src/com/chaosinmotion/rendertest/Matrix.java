/*  Matrix.java
 *
 *  Created on Sep 5, 2010 by William Edward Woody
 */

package com.chaosinmotion.rendertest;

/**
 * Represents a 4x4 matrix for transformations
 */
public class Matrix
{
    private double[] a;
    
    public Matrix()
    {
        a = new double[16];
    }
    
    public Matrix clone()
    {
        Matrix m = new Matrix();
        for (int i = 0; i < 16; ++i) m.a[i] = a[i];
        return m;
    }
    
    public Matrix multiply(Matrix m)
    {
        int i,j,k;
        Matrix ret = new Matrix();
        
        for (i = 0; i < 4; ++i) {
            for (j = 0; j < 4; ++j) {
                double n = 0;
                for (k = 0; k < 4; ++k) {
                    n += a[k+j*4] * m.a[i+k*4];
                }
                ret.a[i+j*4] = n;
            }
        }
        return ret;
    }
    
    public static Matrix identity()
    {
        Matrix m = new Matrix();
        
        for (int i = 0; i < 4; ++i) {
            m.a[i*5] = 1;
        }
        
        return m;
    }
    
    public static Matrix translate(double x, double y, double z)
    {
        Matrix m = identity();
        
        m.a[12] = x;
        m.a[13] = y;
        m.a[14] = z;
        
        return m;
    }
    
    public static Matrix scale(double x, double y, double z)
    {
        Matrix m = identity();
        
        m.a[0] = x;
        m.a[5] = y;
        m.a[10] = z;
        
        return m;
    }
    
    public static Matrix scale(double x)
    {
        return scale(x,x,x);
    }
    
    public static Matrix rotate(double angle, int axis)
    {
        Matrix m = identity();
        
        double c = Math.cos(angle);
        double s = Math.sin(angle);
        
        switch (axis) {
            case 0:     // x axis
                m.a[5] = c;
                m.a[10] = c;
                m.a[6] = s;
                m.a[9] = -s;
                break;
            case 1:     // y axis
                break;
            case 2:     // z axis
                break;
            default:
                throw new IllegalArgumentException("Expected axis 0, 1 or 2");
        }
        
        return m;
    }
    
    public static Matrix perspective1(double fov, double aspect, double n)
    {
        Matrix m = identity();
        
        m.a[0] = fov/aspect;
        m.a[5] = fov;
        m.a[10] = -1;
        m.a[14] = -2*n;
        m.a[11] = -1;
        m.a[15] = 0;
        
        return m;
    }
    
    public static Matrix perspective2(double fov, double aspect, double n)
    {
        Matrix m = identity();
        
        m.a[0] = fov/aspect;
        m.a[5] = fov;
        m.a[10] = 0;
        m.a[11] = -1;
        m.a[14] = -n;
        m.a[15] = 0;
        
        return m;
    }
    
    public Vector transform(Vector v)
    {
        Vector ret = new Vector();
        
        for (int i = 0; i < 4; ++i) {
            double d = 0;
            for (int j = 0; j < 4; ++j) {
                d += v.a[j] * a[i+j*4];
            }
            ret.a[i] = d;
        }
        
        return ret;
    }
    
    public Vector transformLine(Vector v)
    {
        Vector ret = new Vector();
        
        for (int i = 0; i < 4; ++i) {
            double d = 0;
            for (int j = 0; j < 4; ++j) {
                d += v.a[j] * a[j+i*4];
            }
            ret.a[i] = d;
        }
        
        return ret;
    }
    
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < 16; ++i) {
            if (0 == (i % 4)) buf.append("| ");
            buf.append(a[i]).append(' ');
            if (3 == (i % 4)) buf.append("|\n");
        }
        
        return buf.toString();
    }
}


