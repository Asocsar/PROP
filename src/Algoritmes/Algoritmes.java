package Algoritmes;

import java.io.IOException;

public abstract class Algoritmes {
    protected double time;
    protected double grade;
    //public abstract double getTime();
    //public abstract double getRate();
    public abstract byte[] compress (byte[] b) throws JPEG.JPEGException;
    public abstract byte[] descompress (byte[] b);
}
