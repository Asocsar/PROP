package Controlador_Compressio_Descompressio;


import java.io.IOException;
import java.util.Scanner;

public class Driver_CD {
    public static void main(String[] args) throws IOException {
        Cont_CD C = new Cont_CD();
        String path_o;
        String path_d;
        int id;
        boolean compress;
        Scanner S = new Scanner(System.in);
        boolean first = true;
        System.out.println("Introduzca la funcionalidad a probar");
        System.out.println("0 : Sortir");
        System.out.println("1 : Comprimir");
        System.out.println("2 : Descomprimir");
        System.out.println("3 : Comparar");
        int act = 1;
        while (act != 0) {
            System.out.println();
            System.out.println();
            if (!first) {
                System.out.println("Introduzca la funcionalidad a probar");
                System.out.println("0 : Sortir");
                System.out.println("1 : Comprimir");
                System.out.println("2 : Descomprimir");
                System.out.println("3 : Comparar");
            }
            act = S.nextInt();
            first = false;
            if (act == 1 || act == 2) {
                System.out.println("Introduzca el path del fichero a comprimir");
                path_o = S.next();
                System.out.println("Introduzca donde quiere descomprimir el fihero");
                path_d = S.next();
                System.out.println("introduzca que algoritmo desea utilizar");
                System.out.println("LZ78 : 1");
                System.out.println("LZSS : 2");
                System.out.println("LZW : 3");
                System.out.println("JPEG : 4");
                id = S.nextInt();
                while (id != 1 && id != 2 && id != 3 && id != 4) {
                    System.out.println("Introduzca un valor correcto");
                    id = S.nextInt();
                }
                compress = act == 1;
                C.compressio_descompressio(path_o, path_d, id, compress);
            }
            else if (act == 3) {
                System.out.println();
                System.out.println("Procediendo a comparar ultima compression");
                C.comparar();
            }
        }
    }
}
