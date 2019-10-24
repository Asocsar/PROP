package Controlador_Compressio_Descompressio;

import java.io.*;
import java.util.*;


public class Cont_CD {

    private static String path1 = "/home/asocar/Desktop/Ejemplo.txt";
    private static String path2 = "/home/asocar/Desktop/Ejemplo_salida.LZW";

    public Cont_CD (){
    }

    public static class Item {
        private String path;
        private int best_metod;
        private List<Integer> Cod;

        public Item () {
            Cod = Arrays.asList(76,111,114,101,109,32,73,112,115,117,259,101,115,32,115,105,109,112,108,258,101,110,116,101,32,101,108,32,277,120,116,111,32,100,278,257,108,273,110,286,288,32,108,97,267,270,112,257,276,298,32,121,32,97,114,99,104,105,118,111,267,295,283,285,46,32,10,255,257,259,261,263,259,104,97,268,105,100,286,280,282,101,284,294,289,280,292,333,115,116,159,110,100,308,287,278,297,299,346,117,343,114,105,304,288,115,295,334,97,163,286,49,53,48,48,44,320,99,117,97,346,286,117,110,32,300,257,115,256,32,40,78,319,288,281,84,319,112,101,114,382,110,329,113,117,278,115,278,288,100,105,99,329,329,297,379,271,302,116,97,41,349,115,99,111,293,99,331,376,115,161,32,377,329,103,97,273,114,160,329,295,10,317,314,305,296,438,109,101,122,99,108,425,316,430,32,109,374,393,397,399,440,103,114,425,328,99,393,426,378,108,105,98,114,338,335,337,267,266,392,421,442,110,319,10,78,286,424,108,481,111,467,101,118,312,105,425,367,48,307,364,115,370,269,293,32,398,278,413,109,98,105,275,379,110,457,266,425,418,109,286,437,349,32,290,341,279,378,10,332,372,476,285,472,273,99,116,458,110,405,314,370,501,347,375,279,401,110,421,430,526,278,105,103,373,281,449,256,547,105,396,108,478,70,455,112,111,112,117,297,356,122,97,332,521,440,267,54,48,267,418,464,329,99,257,97,421,161,378,295,351,32,104,111,106,304,34,76,101,531,298,593,34,370,10,585,372,430,266,32,575,277,533,374,32,112,298,97,106,603,295,322,258,260,262,264,370,306,109,159,267,257,421,275,277,545,604,419,320,382,102,116,119,308,402,278,97,117,285,101,404,581,110,370,513,286,560,114,279,613,271,483,32,65,108,100,354,32,80,97,103,101,77,97,107,393,370,334,601,281,554,445,117,121,278,118,393,269,419,614,278,616,324,619,109,46,10);
        }

        public void setPath(String p) {this.path = p;}

        public void analize() {
            this.best_metod = 3;
        }

        public int gethmetod() { return this.best_metod; }

        public List<Integer> getcode() { return this.Cod; }
    }

    public static class LZW {

        public double rate = 2.0;
        public double time = 100;

        public LZW () {
        }

        public double getTime() {
            return time;
        }

        public double getRate() {
            return rate;
        }

        public List<Integer> compress (BufferedReader file) {
            Item I = new Item();
            List<Integer> L = I.getcode();
            return L;
        }

        public String descomprimir (List<Integer> s) {
            String S = "Hola";
            return S;
        }
    }

    public static class LZSS {

        public double rate = 2.0;
        public double time = 100;

        public LZSS () {
        }
        public double getTime() {
            return time;
        }

        public double getRate() {
            return rate;
        }

        public List<Integer> compress (BufferedReader file) {
            Item I = new Item();
            List<Integer> L = I.getcode();
            return L;
        }

        public String descomprimir (List<Integer> s) {
            String S = "Hola";
            return S;
        }
    }

    public static class LZ78 {

        public double rate = 2.0;
        public double time = 100;

        public LZ78 () {}

        public double getTime() {
            return time;
        }

        public double getRate() {
            return rate;
        }

        public List<Integer> compress (BufferedReader file) {
            Item I = new Item();
            List<Integer> L = I.getcode();
            return L;
        }

        public String descomprimir (List<Integer> s) {
            String S = "Hola";
            return S;
        }
    }

    public static class JPEG {

        public double rate = 2.0;
        public double time = 100;

        public JPEG () {}

        public double getTime() {
            return time;
        }

        public double getRate() {
            return rate;
        }

        public List<Integer> compress (BufferedReader file) {
            Item I = new Item();
            List<Integer> L = I.getcode();
            return L;
        }

        public String descomprimir (List<Integer> s) {
            String S = "Hola";
            return S;
        }
    }

    public static  void compressio_descompressio (String path, int id, String path_dest, boolean comprimir) throws IOException {
        Item I = new Item();
        if (path_dest == "") path_dest = path;
        if (id == 0) {
            I.setPath(path);
            I.analize();
            id = I.best_metod;
        }
        List<Integer> L = new ArrayList<>();
        String S = "";
        double time = 0;
        double rate = 0;
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        switch (id) {
            case 1:
                LZ78 L8 = new LZ78();
                if (comprimir) {
                    L = L8.compress(br);
                    time = L8.getTime();
                    rate = L8.getRate();
                }
                else {
                    S = L8.descomprimir(I.getcode());
                    time = L8.getTime();
                    rate = L8.getRate();
                }

                break;
            case 2:
                LZSS LS = new LZSS();
                if (comprimir) {
                    L = LS.compress(br);
                    time = LS.getTime();
                    rate = LS.getRate();
                }
                else {
                    S = LS.descomprimir(I.getcode());
                    time = LS.getTime();
                    rate = LS.getRate();
                }
                break;
            case 3:
                LZW LW = new LZW();
                if (comprimir) {
                    L = LW.compress(br);
                    time = LW.getTime();
                    rate = LW.getRate();

                }
                else {
                    S = LW.descomprimir(I.getcode());
                    time = LW.getTime();
                    rate = LW.getRate();

                }
                break;
            case 4:
                JPEG JG = new JPEG();
                if (comprimir) {
                    L = JG.compress(br);
                    time = JG.getTime();
                    rate = JG.getRate();
                }
                else {
                    S = JG.descomprimir(I.getcode());
                    time = JG.getTime();
                    rate = JG.getRate();
                }
                break;
            }

        BufferedWriter F = new BufferedWriter(new FileWriter(path_dest));
        if (comprimir) {
            for (int i = 0; i < L.size(); ++i) {
                F.write(L.get(i) + ",");
            }
        }
        else
            F.write(S);

        F.flush();
        F.close();

        if (comprimir) {
            path1 = path;
            path2 = path_dest;
        }

        System.out.println("Tiempo " + time);
        System.out.println("Ratio " + rate);

    }

    public static void comparar () throws IOException {
        System.out.println(path1);
        File file1 = new File(path1);
        BufferedReader br = new BufferedReader(new FileReader(file1));
        int n = br.read();
        String S = "";
        boolean one_more = true;
        while (n != -1) {
            S = S + (char)n;
            n = br.read();
        }
        System.out.println(S);
        System.out.println();
        File file2 = new File(path2);
        BufferedReader gr = new BufferedReader(new FileReader(file2));
        n = gr.read();
        S = "";
        while (n != -1) {
            S = S + (char)n;
            n = gr.read();
        }
        System.out.println(S);
    }

    public static void main(String[] args) throws IOException {
        compressio_descompressio("/home/asocar/Desktop/Ejemplo.txt", 3,"/home/asocar/Desktop/Ejemplo_salida.LZW", true);
        comparar();
    }
}
