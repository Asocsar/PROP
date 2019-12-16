/**
 * /file LZ78.java
 * /author Pau Frederic Bujons
 * /title Algorisme LZ78
 */

package Algoritmes;

import Algoritmes.Algoritmes;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class LZ78 extends Algoritmes {
    

    /** \brief Creadora
        \pre Cert
        \post Es crea una instància de la classe LZ78
    */
    public LZ78(){
        super.time = 0.0;
        super.grade = 0.0;
    }

    /** \brief Obtenció del temps de compressió
        \pre Cert
        \post Es retorna el temps de l'última compressió.
    */
    public double get_Time() {

        return super.time;
    }
    
    /** \brief Obtenció del ratio de compressió
        \pre Cert
        \post Es retorna el ratio de l'última compressió.
    */
    public double get_Rate() {
        return super.grade;
    }
    
    
    /** \brief Transformacio de llista a byte[]
        \pre Cert
        \post Retorna un byte[] amb els valors de la llista paràmetre
    */
    
    public  byte[] transform(List<Byte>a){
        byte[] n = new byte[a.size()];
        for(int i=0; i < a.size(); ++i){
            n[i]= a.get(i);
        }
        return n;
    }
    
    /** \brief Transformacio de llista a byte[] 2
        \pre Cert
        \post Retorna un byte[] amb els valors de la llista paràmetre
    */
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
    
    /** \brief Transformacio de llista a byte[] 2
        \pre Cert
        \post Retorna un byte indicant la poscio de la llista b en a, o -1 si no existeix
    */    
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
    
    /** \brief Codificació de l'arxiu font en byte[] amb l'algorisme_LZ78
        \pre Cert
        \post Retorna un byte[] representant el fitxer comprimt
    */
    
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
            super.time = endtime - starttime;
            super.grade = fole.length/result.size();
            return transform(result);
    }

    

    /** \brief Descompressió LZ78
        \pre Cert
        \post Retorna un byte[] corresponent a la decodificació de l'arxiu comprimit
    */

    public  byte[] descompress(byte[] aux) {
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
                super.time = endtime - starttime;
                return transform2(Caracters_aux,tam);
            }


}
