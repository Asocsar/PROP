package Algoritmes.LZW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

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
        List<String> s = A.compress(br);
        FileWriter file_o = new FileWriter("C:\\Users\\Asocs\\Desktop\\test_o");
        System.out.println(s.size());
        String SE = "";
        for (int i = 0; i < s.size(); ++i) {
            file_o.write(s.get(i));
            //SE += s.get(i);
            //SE +=',';
        }
        //file_o.write((SE));
        file_o.close();
        /*-----------------------*/
        //String Ex = A.descomprimir(s);
        //System.out.println(Ex);
        //assertEquals ("Mensaje ?", Ex, Ej);

    }
}
