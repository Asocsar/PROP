/**
 * /file LZW.java
 * /author Daniel Cano
 * /title Algorisme LZW
 */

package Algoritmes;



import java.nio.ByteBuffer;
import java.util.*;

/** \class LZW */

public class LZW extends Algoritmes {

    //diccionaris que contenen la assosiació entre una seqüència de bytes i un enter
    private Map<List<Byte>, Integer> Alfabet = new HashMap<List<Byte>, Integer>();
    //diccionari que té associat a cada identificador una seqüència de Bytes
    private Map<Integer, List<Byte>> Alfabet_inv = new HashMap<Integer, List<Byte>>();



    /** \brief Obtenció del temps de compressió
     \pre Cert
     \post Es retorna el temps de l'última compressió.
     */
    public double getTime () {return  super.time;}

    /** \brief Obtenció del ratio de compressió
     \pre Cert
     \post Es retorna el ratio de l'última compressió.
     */
    public double getRate () {return  super.grade;}

    /** \brief Creadora
     \pre Cert
     \post Es crea una instància de la classe LZW
     */
    public LZW () {
        create_alfa();
        super.time = 0;
        super.grade = 0;
    }

    /** \brief Alfabets
     \pre Cert
     \post S'inicialitzen les variables Alfabet i Alfabet_inv
     */
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


    /** \brief Codificació de l'arxiu font en byte[] amb l'algorisme_LZW
     \pre Cert
     \post Retorna un byte[] representant el fitxer comprimt
     */

    public byte[] compress(byte [] file)  {
        long start = System.nanoTime();
        // Creem el diccionari de forma local a la funció per un acces més ràpid
        Map<List<Byte>, Integer> Alf_aux = new HashMap<List<Byte>, Integer>(Alfabet);
        List<Integer> result = new ArrayList<>();
        // cadena de bytes que es troben al diccionari
        Byte  [] w = new Byte[0];
        // següent cadena a llegir
        Byte[] k = new Byte[1];
        // cadena que contindrà concatenació de w + k
        Byte[] aux = new Byte[k.length + w.length];
        double n2 = 0;
        for (byte b : file) {
            k = new Byte[1];
            k[0] = b;
            aux = new Byte[k.length + w.length];
            System.arraycopy(w, 0, aux, 0, w.length);
            System.arraycopy(k, 0, aux, w.length, k.length);
            // si ja existeix aux al diccionari llavors fem w = aux, per mes tard poder probar aux + k
            if (Alf_aux.containsKey(Arrays.asList(aux))) {
                w = new Byte[aux.length];
                w = aux;
            } else {
                // si el identificador supera el nombre 53248 llavors no s'incrementa el tamany per
                // evitar perdues en la lectura i escritura del fitxer
                if (Alf_aux.size() < 53248) Alf_aux.put(Arrays.asList(aux), Alf_aux.size());
                int l = Alf_aux.get(Arrays.asList(w));
                n2 += 2;
                result.add(l);
                // avancem al següent caràcter
                w = new Byte[k.length];
                w = k;
            }
        }
        // en la ultima iteració fem w = k, es a dir que el caràcter k, o la seqüència
        // de caràcters aux no han sigut afegits al resultat
        result.add(Alf_aux.get(Arrays.asList(w)));
        long end = System.nanoTime();
        super.time = (end - start) / 1e6;

        byte [] resul = new byte[result.size()*4];
        int it = 0;

        for (Integer i : result) {
            byte [] bytes = ByteBuffer.allocate(4).putInt(i).array();
            for (byte p : bytes) {
                resul[it] = p;
                ++it;
            }
        }
        if (file.length > 0)
            super.grade = file.length/resul.length;
        else
            super.grade = 0;
        return resul;
    }


    /** \brief Descompressió LZ78
     \pre Cert
     \post Retorna un byte[] corresponent a la decodificació de l'arxiu comprimit.
     */
    public byte[] descompress (byte[] bytes) {
        if (bytes.length == 0) {
            return new byte[0];
        }
        long start_time = System.nanoTime();
        Map<Integer, List<Byte>> Alf_aux = new HashMap<Integer, List<Byte>>(Alfabet_inv);
        int i = 0;
        ByteBuffer s = ByteBuffer.wrap(bytes);
        // comencem amb el primer codi
        int cod_viejo = s.getInt(i);
        List<Byte> caracter = Alf_aux.get(cod_viejo);
        int cod_nuevo;
        List<Byte> cadena = null;
        List<Byte> result =  new ArrayList<>();
        result.addAll(caracter);
        i += 4;
        while (i < bytes.length) {
            cod_nuevo = s.getInt(i);
            // si el següent codi esta al diccionari el decodifiquem obtenint la seqüència
            // de caràcters associada
            if (Alf_aux.containsKey(cod_nuevo)) {
                cadena = Alf_aux.get(cod_nuevo);
            }
            // de no ser així cadena serà igual a la concatenació del codi vell
            // més el següent caràcter
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
            //afegim la concatenació trobada al diccionari
            Alf_aux.put(Alf_aux.size(), aderir);
            cod_viejo = cod_nuevo;
            i += 4;
        }
        byte [] fin = new byte[result.size()];
        int k = 0;
        // pasem de llistes de Bytes a array de bytes
        for (Byte b: result) {
            fin[k++] = b;
        }
        long end_time = System.nanoTime();
        super.time = (end_time - start_time) / 1e6;
        return fin;
    }
}