package Algoritmes;

public abstract class Algoritmes {


    protected double time;
    protected double grade;

    /** \brief Abstract Compress
     \pre Cert
     \post S'ha codificat l'arxiu
     */

    public abstract byte[] compress (byte[] b);

    /** \brief Abstract Decompress
     \pre Cert
     \post S'ha descodificat l'arxiu
     */


    public abstract byte[] descompress (byte[] b);
}
