package Algoritmes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class LZ78 {
    public static long elapsed_time;
    public static long length_n;
    public static long length_c;

    /*public long get_time() {

        return elapsed_time;
    }*/

    public static byte[] transform(List<Byte>a){
        byte[] n = new byte[a.size()];
        for(int i=0; i < a.size(); ++i){
            n[i]= a.get(i);
        }
        return n;
    }

    public static byte[] compresio(byte[] fole) throws IOException {
        long startTime = System.currentTimeMillis();
        int x=0;
        Byte[] file = new Byte[fole.length];
        for(byte a : fole){
            file[x++] = a;
        }
        length_n = fole.length;
        List<String> Caracters = new ArrayList<>();
        Caracters.add(null);
        List<Byte> aux_byte = new ArrayList<>();
        Byte lletra = file[0];
        byte index = 0;
        int i = 1;
        String aux_s= "";
        while (i < file.length) {

            if (Caracters.size()-1 < 127) {
                aux_s = aux_s + (char)(lletra.byteValue());
                if (!Caracters.contains(aux_s)) {
                    Caracters.add(aux_s);
                    aux_byte.add(index);
                    aux_byte.add(lletra);
                    index = 0;
                    aux_s="";
                } else {
                    index = (byte)Caracters.indexOf(aux_s);
                }
                ++length_n;
                lletra = file[i];
                ++i;

            } else {
                Caracters.clear();
                Caracters.add(null);
            }
        }
        long endTime = System.currentTimeMillis();
        elapsed_time = (endTime - startTime);
        length_c = aux_byte.size();

        return transform(aux_byte);
    }

    public static String listtostring(List<String> a) {
        int n = a.size();
        String aux = "";
        for (int i = 0; i < n; ++i) {
            aux = aux + a.get(i);

        }
        return aux;
    }

    public static String descompresio(byte[] aux) throws IOException {
        long startTime = System.currentTimeMillis();
        List<String> Caracters = new ArrayList<>();
        List<String> Caracters_aux = new ArrayList<>();
        Caracters.add(null);
        String aux_c = "";
        int index=0;
        for(int i = 0; i < aux.length; ++i){
            if(Caracters.size() != 128) {
                if (i % 2 == 0 | i == 0) index = aux[i];

                else {
                    if (index == 0) {
                        aux_c = "";
                        aux_c = aux_c + (char) aux[i];
                        Caracters.add(aux_c);
                        aux_c = "";
                    }
                    else {
                        aux_c = aux_c + Caracters.get(index) + (char) aux[i];
                        Caracters.add(aux_c);
                        aux_c = "";
                    }
                }
            }
           else {
                Caracters.remove(0);
                Caracters_aux.addAll(Caracters);
                Caracters.clear();
                Caracters.add(null);
                index= 0;

             }

        }
        Caracters.remove(0);
        Caracters_aux.addAll(Caracters);
        long endTime = System.currentTimeMillis();
        elapsed_time = (endTime - startTime);
        return listtostring(Caracters_aux);
    }

    public static Long get_ratio_c() {
        return length_n / length_c;
    }

}

