package Estadístiques;
import org.junit.Test;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;


public class DriverEst {

    @Test
    public static void main(String[] args) {
        //Estadistiques E = new Estadistiques();
        Scanner S = new Scanner(System.in);
        double n1, n2, n3, n4;
        int n5;
        System.out.println("Introduzca los siguientes parametros para LZW");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();
        Estadistiques.setLZW(n1,n2,n3,n4,n5);
        assertEquals("Correct!",n1,Estadistiques.getTimeLZW(), 0);
        assertEquals("Correct!",n2/n5,Estadistiques.getGlobTimeLZW(), 0);
        assertEquals("Correct!",n3,Estadistiques.getRatioLZW(), 0);
        assertEquals("Correct!",n4/n5,Estadistiques.getGlobRatioLZW(), 0);
        assertEquals("Correct!",n5,Estadistiques.getQuantLZW(), 0);
        System.out.println("Introduzca los siguientes parametros para LZSS");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();
        Estadistiques.setLZSS(n1,n2,n3,n4,n5);
        assertEquals("Correct!",n1,Estadistiques.getTimeLZW(), 0);
        assertEquals("Correct!",n2/n5,Estadistiques.getGlobTimeLZW(), 0);
        assertEquals("Correct!",n3,Estadistiques.getRatioLZW(), 0);
        assertEquals("Correct!",n4/n5,Estadistiques.getGlobRatioLZW(), 0);
        assertEquals("Correct!",n5,Estadistiques.getQuantLZW(), 0);
        System.out.println("Introduzca los siguientes parametros para LZ78");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();
        Estadistiques.setLZ78(n1,n2,n3,n4,n5);
        assertEquals("Correct!",n1,Estadistiques.getTimeLZW(), 0);
        assertEquals("Correct!",n2/n5,Estadistiques.getGlobTimeLZW(), 0);
        assertEquals("Correct!",n3,Estadistiques.getRatioLZW(), 0);
        assertEquals("Correct!",n4/n5,Estadistiques.getGlobRatioLZW(), 0);
        assertEquals("Correct!",n5,Estadistiques.getQuantLZW(), 0);
        System.out.println("Introduzca los siguientes parametros para JPEG");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        n1 = S.nextDouble();
        n2 = S.nextDouble();
        n3 = S.nextDouble();
        n4 = S.nextDouble();
        n5 = S.nextInt();
        Estadistiques.setJPEG(n1,n2,n3,n4,n5);
        assertEquals("Correct!",n1,Estadistiques.getTimeLZW(), 0);
        assertEquals("Correct!",n2,Estadistiques.getGlobTimeLZW(), 0);
        assertEquals("Correct!",n3,Estadistiques.getRatioLZW(), 0);
        assertEquals("Correct!",n4,Estadistiques.getGlobRatioLZW(), 0);
        assertEquals("Correct!",n5,Estadistiques.getQuantLZW(), 0);
        System.out.println("Introduzca ultimo algoritmo utilizado");
        String s = S.next();
        Estadistiques.setLastAlg(s);
        assertEquals("Correct!", s, Estadistiques.getLastAlg());
        Estadistiques.visualglob();
    }
}
