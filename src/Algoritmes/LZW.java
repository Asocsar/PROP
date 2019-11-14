package Algoritmes;

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

        /*for (Map.Entry<List<Byte>, Integer> entry : Alfabet.entrySet()) {
            //entry.getValue().toString()
            System.out.println(entry.getKey() + ":" + entry.getKey());
        }*/

    }


    private double log (int x, int base ) {
        return (double) Math.round(Math.log(x) / Math.log(base));
    }

    public int bytesCount(int n) {
        return n < 0 ? 4 : (32 - Integer.numberOfLeadingZeros(n)) / 8 + 1;
    }

    public List<Integer> compress(Object o) throws IOException {
        byte [] file = (byte []) o;
        long start = System.currentTimeMillis();
        Map<List<Byte>, Integer> Alf_aux = new HashMap<List<Byte>, Integer>(Alfabet);
        int cantidad = 0;
        List<Integer> result = new ArrayList<>();
        Byte  [] w = new Byte[0];
        boolean add = false;
        double n2 = 0;
        for (byte b : file) {
            if (Alf_aux.size() >= 0xFFFE) {
                Alf_aux = new HashMap<List<Byte>, Integer>(Alfabet);
                /*if (add) {
                    result.add(Alf_aux.get(Arrays.asList(w)));
                    w[0] = b;
                }*/
            }
            ++cantidad;
            Byte[] k = new Byte[1];
            k[0] = b;
            Byte[] aux = new Byte[k.length + w.length];
            System.arraycopy(w, 0, aux, 0, w.length);
            System.arraycopy(k, 0, aux, w.length, k.length);
            if (Alf_aux.containsKey(Arrays.asList(aux))) {
                w = new Byte[aux.length];
                w = aux;
                add = true;
            } else {
                add = false;
                Alf_aux.put(Arrays.asList(aux), Alf_aux.size());
                Integer l = Alf_aux.get(Arrays.asList(w));
                if (l < 0x10000) {
                    if (l < 0x100)
                        n2 += 8;// 8 bit
                    else
                        n2 += 2;// 16 bit
                } else {
                    if (l < 0x100000000L)
                        n2 += 4;// 32 bit
                    else
                        n2 += 8;// 64 bit
                }
                //n2 += bytesCount(l);
                result.add(l);
                w = new Byte[k.length];
                w = k;
            }
        }
        long end = System.currentTimeMillis();
        this.time = (end - start) / 1000F;
        this.rate = cantidad/n2;
        //System.out.println(this.time);
        //System.out.println(this.rate);
        return result;
    }


    public byte[] descomprimir (Object o) {
        List<Integer> s = (List<Integer>) o;
        long start = System.currentTimeMillis();
        Map<Integer, List<Byte>> Alf_aux = new HashMap<Integer, List<Byte>>(Alfabet_inv);
        int i = 0;
        int cod_viejo = s.get(i);
        List<Byte> caracter = Alf_aux.get(cod_viejo);
        int cod_nuevo;
        List<Byte> cadena;
        List<Byte> result =  new ArrayList<>();
        result.addAll(caracter);
        ++i;
        while (i < s.size()) {
            if (Alf_aux.size() >= 0xFFFE) {
                Alf_aux = new HashMap<Integer, List<Byte>>(Alfabet_inv);
                /*cod_viejo = s.get(i);
                caracter = Alf_aux.get(cod_viejo);
                result.addAll(caracter);*/
            }
            cod_nuevo = s.get(i);
            if (Alf_aux.containsKey(cod_nuevo)) {
                cadena = Alf_aux.get(cod_nuevo);
            }
            else {
                cadena = new ArrayList<>();
                cadena.addAll(Alf_aux.get(cod_viejo));
                cadena.addAll(caracter);
                //cadena += caracter;
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
