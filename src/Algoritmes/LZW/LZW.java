package Algoritmes.LZW;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class LZW {


    private static Map<List<Byte>, Integer> Alfabet = new HashMap<List<Byte>, Integer>();
    private static Map<Integer, List<Byte>> Alfabet_inv = new HashMap<Integer, List<Byte>>();

    private double time;
    private double rate;

    public double getTime () {return  this.time;}
    public double getRate () {return  this.rate;}

    public LZW () {
        create_alfa();
        this.time = 0;
        this.rate = 0;
    }

    private static char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };

    private void create_alfa() {
        int aux = 0;
        Byte [] j = new Byte[1];
        byte n = 0;
        for (int i = 0; i < 256; ++i) {
            j[0] = n;
            Alfabet.put(Collections.unmodifiableList(Arrays.asList(j)), aux);
            Alfabet_inv.put(aux, Collections.unmodifiableList(Arrays.asList(j)));
            ++aux;
            ++n;
        }

    }


    private double log (int x, int base ) {
        return (double) Math.round(Math.log(x) / Math.log(base));
    }

    public List<Integer> compress(byte [] file) throws IOException {
        Map<List<Byte>, Integer> Alf_aux = new HashMap<List<Byte>, Integer>(Alfabet);
        int cantidad = 0;
        List<Integer> result = new ArrayList<>();
        Byte  [] w = new Byte[0];
        for (byte b : file) {
            ++cantidad;
            Byte[] k = new Byte[1];
            k[0] = b;
            Byte[] aux = new Byte[k.length + w.length];
            System.arraycopy(w, 0, aux, 0, w.length);
            System.arraycopy(k, 0, aux, w.length, k.length);
            if (Alf_aux.containsKey(Arrays.asList(aux))) {
                w = new Byte[aux.length];
                w = aux;
            } else {
                Alf_aux.put(Collections.unmodifiableList(Arrays.asList(aux)), Alf_aux.size());
                result.add(Alf_aux.get(Arrays.asList(w)));
                w = new Byte[k.length];
                w = k;
            }
        }
        double n1 = cantidad*8;
        double n2 =  log(Alf_aux.size(), 2)*result.size();

        return result;
    }





    //System.out.println(n1/n2);
        /*if (match) {
            String k = Integer.toBinaryString(max);
            int base = 1;
            List<Integer> numero = new ArrayList<>();
            int aux = 0;
            boolean zero = false;
            for (int i = k.length() - 1; i >= 0; --i) {
                double s = Integer.parseInt(k.charAt(i) + "") * Math.pow(2, base);
                zero = (s == 0.0);
                if (aux + s > Integer.MAX_VALUE) {
                    numero.add(aux);
                    aux = 0;
                    base = 0;
                }
                aux += s;
                ++base;
                if (aux + Math.pow(2, base) > Integer.MAX_VALUE) {
                    numero.add(aux);
                    aux = 0;
                    base = 0;
                    if (zero) base = 1;
                }
                aux += Math.pow(2, base);
                ++base;
                zero = false;
            }
            numero.add(aux);
            for (int i = 0; i < numero.size(); ++i) {
                result.add(0, numero.get(i));
            }
            for (int i = 0; i < result.size(); ++i) {
                if (result.get(i) < Math.pow(2, log(max, 2))) {
                    result.set(i, (int) (result.get(i) + Math.pow(2, log(max, 2))));
                }
            }
        }*/
       /* System.out.println(result);
        for (Integer lee: result
             ) {
            System.out.println(Integer.toBinaryString(lee));
        }*/

    public byte[] descomprimir (List<Integer> s) {
        Map<Integer, List<Byte>> Alf_aux = new HashMap<Integer, List<Byte>>(Alfabet_inv);
        int i = 0;
        int cod_viejo = s.get(i);
        List<Byte> caracter = Alf_aux.get(cod_viejo);
        int cod_nuevo;
        List<Byte> cadena;
        List<Byte> result = caracter;
        ++i;
        while (i < s.size()) {
            cod_nuevo = s.get(i); //Integer.parseInt(s.get(i));
            if (Alf_aux.containsKey(cod_nuevo)) {
                cadena = Alf_aux.get(cod_nuevo);
            }
            else {
                cadena = Alf_aux.get(cod_viejo);
                cadena.addAll(caracter);
                //cadena += caracter;
            }
            result.addAll(cadena);
            caracter = Collections.singletonList(cadena.get(0));
            List<Byte> aderir = Alf_aux.get(cod_viejo);
            aderir.addAll(caracter);
            Alf_aux.put(Alf_aux.size(), aderir);
            cod_viejo = cod_nuevo;
            ++i;
        }
        byte [] fin = new byte[result.size()];
        int k = 0;
        for (Byte b: result) {
            fin[k++] = b;
        }
        return fin;
    }
}
