import Controlador_ficheros.classe_fichero;
import Controlador_Compressio_Descompressio.Cont_CD;
import Estadístiques.Estadistiques;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner S = new Scanner(System.in);
        while (S.hasNext()) {
            System.out.println("1:comprimir/descomprimir\n2:Estadisticas globales\n3:Comparar ultima compresión");
            int inten = S.nextInt();
            if (inten == 1) {
                System.out.println("Introduzca dirección del fichero a comprimir");
                String path = S.nextLine();
                System.out.println("Introduzca destino del fichero a comprimir");
                String path2 = S.nextLine();
                System.out.println("1 para Comprimir \n0 para Descomprimir");
                boolean n = (S.nextInt() == 1);
                System.out.println("Introduzca algoritmo deseado para comprimir/descomprimir");
                System.out.println("\t1: LZ78\n\t2:LZSS\n\t3:LZW\n\t4:JPEG");
                int id = S.nextInt();
                classe_fichero I = new classe_fichero(path, path2, id, n);
                Cont_CD C = new Cont_CD();
                C.compressio_descompressio(I);
                System.out.println("Si desea comparar introduzca 1\nAlternativamente 0");
                int com = S.nextInt();
                if (com == 1) C.comparar();
            }
            else  if (inten == 2) {
                Estadistiques E = new Estadistiques();
                E.visualglob();
            }
            else if (inten == 3) {
                Cont_CD C = new Cont_CD();
                C.comparar();
            }
        }
    }
}
