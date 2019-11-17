package Algorisme_LZ78;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class LZ78 {
    private long elapsed_time;
    private long length_n;
    private long length_c;

    //PRE: Cert
    //POST: Crea una instància de la classe Algorisme_LZ78.LZ78
    public LZ78(){

    }

    //PRE: Cert
    //POST: Retorna el temps trigat en la ultima compressió / descompressió
    public long get_time() {

        return elapsed_time;
    }

    //PRE: Cert
    //POST: Retorna un byte[] amb tots els valors de a ajuntats
    public  byte[] transform(List<Byte>a){
        byte[] n = new byte[a.size()];
        for(int i=0; i < a.size(); ++i){
            n[i]= a.get(i);
        }
        return n;
    }

    //PRE: Cert
    //POST: Retorna una llista de Integers que representa el fitxer comprimit
    public byte[] compresio(byte[] fole)  {
        long startTime = System.currentTimeMillis();
        length_n = fole.length;
        int x=0;
        Byte[] file = new Byte[fole.length];
        for(byte a : fole){
            file[x++] = a;
        }
        List<String> Caracters = new ArrayList<>();
        Caracters.add(null);
        List<Byte> aux_byte = new ArrayList<>();
        byte index = 0;
        int i = 0;
        String aux_s= "";
        while (i < file.length) {

            if (Caracters.size()-1 < 127) {
                aux_s = aux_s + (char)(file[i].byteValue());
                if (!Caracters.contains(aux_s)) {
                    Caracters.add(aux_s);
                    aux_byte.add(index);
                    aux_byte.add(file[i]);
                    index = 0;
                    aux_s="";
                } else {
                    index = (byte)Caracters.indexOf(aux_s);
                }
                ++length_n;
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

    //PRE: Cert
    //POST: Retorna un string amb tots els valors de a ajuntats
    public String listtostring(List<String> a) {
        int n = a.size();
        String aux = "";
        for (int i = 0; i < n; ++i) {
            aux = aux + a.get(i);

        }
        return aux;
    }

    //PRE: Cert
    //POST: Retorna un String amb el text original
    public String descompresio(byte[] aux){
        long startTime = System.currentTimeMillis();
        List<String> Caracters = new ArrayList<>();
        List<String> Caracters_aux = new ArrayList<>();
        Caracters.add(null);
        String aux_c = "";
        int index=0;
        int i = 0;
        while( i < aux.length){
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
                ++i;
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

    //PRE: Cert
    //POST: Retorna el rati assolit en la ultima compressió
    public Long get_ratio() {
        return length_n / length_c;
    }

}
