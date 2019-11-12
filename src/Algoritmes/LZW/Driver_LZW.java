package Algoritmes.LZW;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import java.io.File;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        /*ESCRIBIR ARCHIVO DE PRUEBA*/
        Scanner S = new Scanner(System.in);
        LZW A = new LZW();
        String Ej = "";
        while (S.hasNext()) Ej += S.nextLine() + '\n';
        System.out.println(Ej);
        FileWriter fw = new FileWriter("C:\\Users\\Asocs\\Desktop\\test.txt");
        fw.write(Ej);
        fw.close();
        /*-------------------------*/

        /*COMPRESSIÓN */
        File file = new File ("C:\\Users\\Asocs\\Desktop\\test.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<Integer> s = A.compress(br);
       /* FileWriter file_o = new FileWriter("C:\\Users\\Asocs\\Desktop\\test.LZW");
        OutputStream os = new FileOutputStream("C:\\Users\\Asocs\\Desktop\\test.LZW");*/
        FileOutputStream stream = new FileOutputStream("C:\\Users\\Asocs\\Desktop\\test.LZW");
        String SE = "";
        int n = 0;
        for (int i = 0; i < s.size(); ++i) {
            //System.out.println("iter " + i + " " + s.get(i));
            stream.write(s.get(i));

        }
        stream.close();
        File file2 = new File ("C:\\Users\\Asocs\\Desktop\\test.LZW");
        BufferedReader br2 = new BufferedReader(new FileReader(file2));
        FileInputStream filee = new FileInputStream("C:\\Users\\Asocs\\Desktop\\test.LZW");
        int x = -1;
        String fe = "";
        while (filee.available() != 0) {
            x = filee.read();
            int b = 0b100000000;
            int sh = 0;
            while (b > 0) {
                fe +=((x&b)/b);
                b = b/2;
            }
            fe += " ";
        }
        System.out.println(fe);

        /*-----------------------*/
        //String Ex = A.descomprimir(LS);
        //System.out.println(Ex);
        //assertEquals ("Mensaje ?", Ex, Ej);

    }
}

