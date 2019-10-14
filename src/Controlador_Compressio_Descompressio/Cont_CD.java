package Controlador_Compressio_Descompressio;

import java.io.*;
import java.util.List;
import Algoritmes.*;

public class Cont_CD {

    public class Item {
        private String path;
        private int best_metod;
        private List<Integer> Cod;

        public Item () {
            path = "/home/asocar/Desktop/Ejemplo.txt";
            best_metod = 3;
            Cod.add(3);
        }

        public String getpath() { return this.path; }

        public int gethmetod() { return this.best_metod; }

        public List<Integer> getcode() { return this.Cod; }
    }

    public void compressio_descompressio (Item I, int id, String path_dest, boolean comprimir) throws IOException {
        String path = I.getpath();
        if (path_dest == "") path_dest = path;
        if (id == 0) id = I.gethmetod();
        List<Integer> L;
        String S;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        switch (id) {
            case 1:
                LZ78 L8 = new LZ78();
                break;
            case 2:
                LZSS LS = new LZSS();
                break;
            case 3:
                LZW LW = new LZW();
                if (comprimir)
                    L = LW.compress(br);
                else
                    S = LW.descomprimir(I.getcode());
                break;
            case 4:
                JPEG JG = new JPEG();
                break;
            }

        int n = 4;


    }

    public static void main(String[] args) {

    }
}
