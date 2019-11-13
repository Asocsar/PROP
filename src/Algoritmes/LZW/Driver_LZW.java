package Algoritmes.LZW;

import java.nio.file.Files;
import java.lang.Object;
import java.io.File;
import java.util.List;

public class Driver_LZW {
    public static void main(String[] args) throws Exception {
        /*COMPRESSIÓN */
        File file = new File("C:\\Users\\Asocs\\Desktop\\mgemgmeg.txt");
        byte [] b = Files.readAllBytes(file.toPath());
        LZW A = new LZW();
        List<Integer> s = A.compress(b);

        /*

        System.out.println(s.subList(40,100));
        FileWriter file_o = new FileWriter("C:\\Users\\Asocs\\Desktop\\test.LZW");
        String SE = "";
         n = 0;
        for (int i = 0; i < s.size(); ++i) {
            if (s.get(i) != null)
                file_o.write(s.get(i));

        }
        file_o.close();
        FileReader fr = new FileReader("C:\\Users\\Asocs\\Desktop\\test.LZW");
        int num;
        List<Integer> L = new ArrayList<>();
        while ((num = fr.read()) != -1) {
            L.add(num);
        }
        System.out.println(num);
        System.out.println(L.subList(40,100));

        /*-----------------------*/
        /*String Ex = A.descomprimir(L);
        FileWriter fw2 = new FileWriter("C:\\Users\\Asocs\\Desktop\\salida.txt");
        fw2.write(Ex);
        fw2.close();
        //assertEquals ("Mensaje ?", Ex, Ej);

    */}
}

