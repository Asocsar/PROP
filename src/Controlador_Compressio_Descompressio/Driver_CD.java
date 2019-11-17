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
        System.out.println("Introduzca 1 para comprimir u otro valor cualquiera para descomprimir");
        compress = S.nextInt() == 1;
        C.compressio_descompressio(path_o, path_d, id, compress);
        if (compress) {
            System.out.println();
            System.out.println("Procediendo a comparar");
            C.comparar();
        }
    }
}
