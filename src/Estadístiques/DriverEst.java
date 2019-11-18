package Estadístiques;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class DriverEst {

    private static double n1, n2, n3, n4;
    private static int n5;
    private static  String last;
    //Scanner S = new Scanner(System.in);

    @Before
    public void antes () {
        Random n = new Random();
        n1 = Math.abs(n.nextDouble()) % 100;
        n2 = Math.abs(n.nextDouble()) % 100;
        n3 = Math.abs(n.nextDouble()) % 100;
        n4 = Math.abs(n.nextDouble()) % 100;
        n5 = Math.abs(n.nextInt()) % 100;

    }

    @Test
    public void establecerLZW () {
        //System.out.println("Introduzca los siguientes parametros para LZW");
        //System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        /*n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();*/
        Estadistiques.setLZW(n1,n2,n3,n4,n5);
        assertEquals("Incorrect!",n1,Estadistiques.getTimeLZW(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n2/n5 : 0,Estadistiques.getGlobTimeLZW(), 0);
        assertEquals("Incorrect!",n3,Estadistiques.getRatioLZW(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n4/n5 : 0,Estadistiques.getGlobRatioLZW(), 0);
        assertEquals("Incorrect!",n5,Estadistiques.getQuantLZW(), 0);
    }

    @Test
    public void establecerLZSS () {
        //System.out.println("Introduzca los siguientes parametros para LZSS");
        //System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        /*n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();*/
        Estadistiques.setLZSS(n1,n2,n3,n4,n5);
        assertEquals("Incorrect!",n1,Estadistiques.getTimeLZSS(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n2/n5 : 0,Estadistiques.getGlobTimeLZSS(), 0);
        assertEquals("Incorrect!",n3,Estadistiques.getRatioLZSS(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n4/n5 : 0,Estadistiques.getGlobRatioLZSS(), 0);
        assertEquals("Incorrect!",n5,Estadistiques.getQuantLZSS(), 0);
    }

    @Test
    public void establecerLZ78 () {
        //System.out.println("Introduzca los siguientes parametros para LZ78");
       // System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        /*n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();*/
        Estadistiques.setLZ78(n1,n2,n3,n4,n5);
        assertEquals("Incorrect!",n1,Estadistiques.getTimeLZ78(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n2/n5 : 0,Estadistiques.getGlobTimeLZ78(), 0);
        assertEquals("Incorrect!",n3,Estadistiques.getRatioLZ78(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n4/n5 : 0,Estadistiques.getGlobRatioLZ78(), 0);
        assertEquals("Incorrect!",n5,Estadistiques.getQuantLZ78(), 0);

    }

    @Test
    public void establecerJPEG () {
        //System.out.println("Introduzca los siguientes parametros para JPEG");
        //System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        /*n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();*/
        Estadistiques.setJPEG(n1,n2,n3,n4,n5);
        assertEquals("Incorrect!",n1,Estadistiques.getTimeJPEG(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n2/n5 : 0,Estadistiques.getGlobTimeJPEG(), 0);
        assertEquals("Incorrect!",n3,Estadistiques.getRatioJPEG(), 0);
        assertEquals("Incorrect!",(n5 != 0) ? n4/n5 : 0,Estadistiques.getGlobRatioJPEG(), 0);
        assertEquals("Incorrect!",n5,Estadistiques.getQuantJPEG(), 0);
    }

    @Test
    public void establecerAlg () {
        //System.out.println("Introduzca ultimo algoritmo utilizado");
        //String s = S.next();
        Random n = new Random();
        String [] C = new String[] {"LZ78", "LZW", "LZSS", "JPEG"};
        int i = Math.abs(n.nextInt()%C.length);
        last = C[i];
        Estadistiques.setLastAlg(last);
        assertEquals("Incorrect!", last, Estadistiques.getLastAlg());
    }

    @AfterClass
    public static void main() {
        Estadistiques.visualglob();
    }
}
