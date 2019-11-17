package Algoritmes.LZW;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class LZW {


    private Map<List<Byte>, Integer> Alfabet = new HashMap<List<Byte>, Integer>();
    private Map<Integer, List<Byte>> Alfabet_inv = new HashMap<Integer, List<Byte>>();

    private double time;
    private double rate;

    public double getTime () {return  this.time;}
    public double getRate () {return  this.rate;}

    public LZW () {
        create_alfa();
        this.time = 0;
        this.rate = 0;
    }

    private void create_alfa() {
        int aux = 0;
        byte n = 0;
        for (int i = 0; i < 256; ++i) {
            Byte [] j = new Byte[1];
            j[0] = n;
            Alfabet.put(Arrays.asList(j), aux);
            Alfabet_inv.put(aux, Arrays.asList(j));
            ++aux;
            ++n;
        }

    }


    private double log (int x, int base ) {
        return (double) Math.round(Math.log(x) / Math.log(base));
    }

    public int bytesCount(int n) {
        return n < 0 ? 4 : (32 - Integer.numberOfLeadingZeros(n)) / 8 + 1;
    }

    public List<Integer> compress(byte [] file)  {
        long start = System.currentTimeMillis();
        Map<List<Byte>, Integer> Alf_aux = new HashMap<List<Byte>, Integer>(Alfabet);
        int cantidad = 0;
        List<Integer> result = new ArrayList<>();
        Byte  [] w = new Byte[0];
        Byte[] k = new Byte[1];
        Byte[] aux = new Byte[k.length + w.length];
        double n2 = 0;
        for (byte b : file) {
            ++cantidad;
            k = new Byte[1];
            k[0] = b;
            aux = new Byte[k.length + w.length];System.arraycopy(w, 0, aux, 0, w.length);
            System.arraycopy(k, 0, aux, w.length, k.length);
            if (Alf_aux.containsKey(Arrays.asList(aux))) {
                w = new Byte[aux.length];
                w = aux;
            } else {
                if (Alf_aux.size() < 53248) Alf_aux.put(Arrays.asList(aux), Alf_aux.size());
                int l = Alf_aux.get(Arrays.asList(w));
                n2 += 2;
                result.add(l);
                w = new Byte[k.length];
                w = k;
            }
        }
        result.add(Alf_aux.get(Arrays.asList(w)));
        long end = System.currentTimeMillis();
        this.time = (end - start) / 1000F;
        if (file.length > 0)
            this.rate = cantidad/n2;
        else
            this.rate = 0;
        return result;
    }


    public byte[] descomprimir (List<Integer> s) {
        if (s.size() == 0) {
            return new byte[0];
        }
        long start = System.currentTimeMillis();
        Map<Integer, List<Byte>> Alf_aux = new HashMap<Integer, List<Byte>>(Alfabet_inv);
        int i = 0;
        int cod_viejo = s.get(i);
        List<Byte> caracter = Alf_aux.get(cod_viejo);
        int cod_nuevo;
        List<Byte> cadena = null;
        List<Byte> result =  new ArrayList<>();
        result.addAll(caracter);
        ++i;
        while (i < s.size()) {
            cod_nuevo = s.get(i);
            if (Alf_aux.containsKey(cod_nuevo)) {
                cadena = Alf_aux.get(cod_nuevo);
            }
            else {
                cadena = new ArrayList<>();
                cadena.addAll(Alf_aux.get(cod_viejo));
                cadena.addAll(caracter);
            }
            result.addAll(cadena);
            caracter = Collections.singletonList(cadena.get(0));
            List<Byte> aderir = new ArrayList<>();
            aderir.addAll(Alf_aux.get(cod_viejo));
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
        long end = System.currentTimeMillis();
        this.time = (end - start) / 1000F;
        return fin;
    }
}
