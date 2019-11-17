package Estadístiques;

import java.util.Scanner;

public class DriverEst {
    public static void main(String[] args) {
        //Estadistiques E = new Estadistiques();
        Scanner S = new Scanner(System.in);
        System.out.println("Introduzca los siguientes parametros para LZW");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        Estadistiques.setLZW(S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextInt());
        System.out.println("Introduzca los siguientes parametros para LZSS");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        Estadistiques.setLZSS(S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextInt());
        System.out.println("Introduzca los siguientes parametros para LZ78");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        Estadistiques.setLZ78(S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextInt());
        System.out.println("Introduzca los siguientes parametros para JPEG");
        System.out.println("tiempo local - tiempo global - ratio local - ratio global - numero de veces de ejecución");
        Estadistiques.setJPEG(S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextDouble(), S.nextInt());
        System.out.println("Introduzca ultimo algoritmo utilizado");
        Estadistiques.setLastAlg(S.next());
        Estadistiques.visualglob();
    }
}
