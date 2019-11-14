package Algoritmes.LZW;

import javafx.util.converter.ByteStringConverter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class prueba {
    public static void main(String[] args) throws IOException {
        /*List<Integer> L = new ArrayList<>();
        L.add(1);
        L.add(2);
        L.add(3);
        L.add(4);
        System.out.println(L.subList(0,1));*/
        Map<List<Byte>, Integer> Alfabet = new HashMap<List<Byte>, Integer>();
        Byte [] b = new Byte[1];
        b[0] = (byte)34;
        System.out.println(b[0]);
        List<Byte> L = Arrays.asList(b);
        Alfabet.put(L, 10);
        Byte [] l = new Byte[1];
        l[0] = (byte)34;
        System.out.println(l[0]);

        for (Map.Entry<List<Byte>, Integer> entry : Alfabet.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            //System.out.println(Arrays.toString(Arrays.toString(b).replaceAll("[\\[\\]\"]", "").split(", ")));
            System.out.println(Arrays.toString(l));
            System.out.println(Alfabet.get(Arrays.asList(l)));
            System.out.println(Alfabet.get(Arrays.asList(b)));
        }

        /*FileWriter fe = new FileWriter("testeo.txt");
        FileReader fr = new FileReader("testeo.txt");

        fe.write(67);
        fe.write(20);
        fe.write(700);
        fe.write(0);
        fe.write(210);
        fe.write(40);
        fe.close();
        System.out.println(fr.read());
        System.out.println(fr.read());
        System.out.println(fr.read());
        System.out.println(fr.read());
        System.out.println(fr.read());
        System.out.println(fr.read());*/
    }
}
