package Algoritmes;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class LZ78 extends Algoritmes {
    private double time;
    private double grade;

    //PRE: Cert
    //POST: Crea una instància de la classe Algorisme_LZ78.LZ78
    public LZ78(){

    }

    //PRE: Cert
    //POST: Retorna el temps trigat en la ultima compressió / descompressió
    public double get_Time() {
        return time;
    }
    //PRE: Cert
    //POST: Retorna un byte[] amb tots els valors de a ajuntats
    private byte[] transform(List<Byte>a){
        byte[] n = new byte[a.size()];
        for(int i=0; i < a.size(); ++i){
            n[i]= a.get(i);
        }
        return n;
    }

    //PRE: Cert
    //POST: Retorna un byte[] amb tots els valors de a ajuntats
    private byte[] transform2(List<List<Byte>>a, int tam){
        byte[] n = new byte[tam];
        int x=0;
        for(int i=0; i < a.size(); ++i){
            for(int j=0; j < a.get(i).size(); ++j){
                n[x] = a.get(i).get(j);
                ++x;
            }
        }
        return n;
    }

    //PRE: Cert
    //POST: Retorna un byte indicant la poscio de la llista b en a, o -1 si no existeix
    private byte found (List<List<Byte>> a, List<Byte> b){
        for(int i= 1; i < a.size(); ++i){
            if (a.get(i).size() == b.size()) {
                List<Byte> aux = a.get(i);
                boolean diff = false;
                for (int j = 0; j < aux.size() & !diff; ++j) {
                    if (aux.get(j) != b.get(j)) diff = true;
                }
                if (!diff) return (byte) i;
            }
        }
        return (byte)-1;
    }

    //PRE: Cert
    //POST: Retorna una llista de Integers que representa el fitxer comprimit

    //PROBANT CARACTERS ESPECIALS
    public byte[] compress(byte[] fole)  {
        double starttime = System.currentTimeMillis();
        int x=0;
        Byte[] file = new Byte[fole.length];
        for(byte a : fole){
            file[x++] = a;
        }
        List<List<Byte>> Caracters = new ArrayList<>();
        Caracters.add(null);
        List<Byte> result = new ArrayList<>();
        List<Byte> aux_c = new ArrayList<>();
        byte index = 0;
        int i = 0;
        byte index_aux= -1;
        while (i < file.length) {
            if (Caracters.size()-1 < 127) {
                aux_c.add(file[i]);
                if(file[i]< 0){
                    ++i;
                    aux_c.add(file[i]);
                }
                index_aux= found(Caracters,aux_c);
                if (index_aux == -1) {
                    Caracters.add(aux_c);
                    result.add(index);
                    if(file[i] < 0 ) result.add(file[i-1]);
                    result.add(file[i]);
                    index = 0;
                    aux_c= new ArrayList<>();
                }
                else index = index_aux;
                ++i;

            } else {
                Caracters.clear();
                Caracters.add(null);
            }
        }
        double endtime = System.currentTimeMillis();
        time = endtime - starttime;
        if(fole.length==0) grade= 0;
        else grade = fole.length/result.size();
        return transform(result);
    }

    //PRE: Cert
    //POST: Retorna un String amb el text original
    public byte[] descompress(byte[] aux) {
        double starttime = System.currentTimeMillis();
        int x=0;
        Byte[] file = new Byte[aux.length];
        for(byte a : aux){
            file[x++] = a;
        }
        List<List<Byte>> Caracters = new ArrayList<>();
        List<List<Byte>> Caracters_aux = new ArrayList<>();
        Caracters.add(null);
        List<Byte> aux_c = new ArrayList<>();
        int index = 0;
        int i = 0;
        boolean par_impar =true; //TRUE: par FALSE: impar
        int tam=0;
        while (i < aux.length) {
            if (Caracters.size() != 128) {
                if ((i % 2 == 0 &par_impar) | (i%2 !=0 & !par_impar)  | i == 0) {
                    index = file[i];
                } else {
                    if (index == 0) {
                        aux_c= new ArrayList<>();
                        aux_c.add(file[i]);
                        ++tam;
                        if(file[i] < 0){
                            ++i;
                            aux_c.add(file[i]);
                            ++tam;
                            par_impar = !par_impar;
                        }
                        Caracters.add(aux_c);
                        aux_c= new ArrayList<>();
                    } else {
                        for(Byte a : Caracters.get(index)){
                            aux_c.add(a);
                            ++tam;
                        }
                        aux_c.add(file[i]);
                        ++tam;
                        if(file[i] < 0){
                            ++i;
                            aux_c.add(file[i]);
                            ++tam;
                            par_impar = !par_impar;
                        }
                        Caracters.add(aux_c);
                        aux_c= new ArrayList<>();
                    }
                }
                ++i;
            } else {
                Caracters.remove(0);
                Caracters_aux.addAll(Caracters);
                Caracters= new ArrayList<>();
                Caracters.add(null);
                index = 0;

            }

        }
        Caracters.remove(0);
        Caracters_aux.addAll(Caracters);
        double endtime = System.currentTimeMillis();
        this.time = endtime - starttime;
        return transform2(Caracters_aux,tam);
    }


    //PRE: Cert
    //POST: Retorna el rati assolit en la ultima compressió
    public double get_Rate() {
        return grade;
    }

}
