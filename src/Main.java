/*import Controlador_ficheros.controlador_gestor_fitxer;
import Controlador_Compressio_Descompressio.Cont_CD;
import Estadístiques.Estadistiques;
import Controlador_Estadistiques.Cont_Est;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, controlador_gestor_fitxer.FicheroDescompressionNoValido, controlador_gestor_fitxer.FicheroCompressionNoValido {
        Cont_Est CE = new Cont_Est();
        CE.GetStats();
        Scanner S = new Scanner(System.in);
        int inten = -1;
        while (inten != 4) {
            System.out.println("1:comprimir/descomprimir\n2:Estadisticas globales\n3:Comparar ultima compresión\n4:Salir");
            inten = S.nextInt();
            if (inten == 1) {
                System.out.println("Introduzca dirección del fichero a comprimir");
                String path = S.next();
                System.out.println("Introduzca destino del fichero a descomprimir");
                String path2 = S.next();
                System.out.println("1 para Comprimir \n0 para Descomprimir");
                boolean n = (S.nextInt() == 1);
                System.out.println("Introduzca algoritmo deseado para comprimir/descomprimir");
                System.out.println("\t1: LZ78\n\t2:LZSS\n\t3:LZW\n\t4:JPEG");
                int id = S.nextInt();
                Cont_CD C = new Cont_CD();
                C.compressio_descompressio(path, path2, id, n);
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
        CE.Stats_Update();
    }
}
*/