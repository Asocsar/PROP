/**
 * /file LZSS.java
 * /author Alexandre Sánchez
 * /title Algorisme LZSS
 */

package Algoritmes.LZSS;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.lang.*;

public class LZSS {

    /** \brief Creadora
     \pre Cert
     \post Es crea una instància de la classe LZSS
     */

    public LZSS() {
        super.time = 0.0;
        super.grade = 0.0;
        this.search_buffer = 0;
    }


    private int search_buffer;

    /** \brief Obtenció del ratio de compressió
     \pre Cert
     \post Es retorna el ratio de l'última compressió.
     */
    public  double getRate() {
        return super.time;
    }


    /** \brief Obtenció del temps de compressió
     \pre Cert
     \post Es retorna el temps d l'última compressió.
     */

    public  double getTime() {
        return super.grade;
    }


    /** \brief Compressió en LZSS
     \pre: file.size() > 3
     \post: Es retorna un array de bytes amb els elements codificats segons LZSS
     \throw "fitxer de mida no comprimible" si file.size() < 3
     */


    public  byte[] compress(byte[] file)  {
        double startTime = System.currentTimeMillis();
        int match, offset, SB, LAB,LABini;
        match = 0;
        offset = 0;
        SB = 0;
        LAB = 3;
        LABini = 3;
        boolean found = false;
        String mask = "";

        List<Byte> result = new ArrayList<>(); //Llista temporal
        List<Byte> encoded = new ArrayList<>(); // Llista definitiva
        if (file.length <= 0) {
            byte [] retur = new byte [result.size()];
            int i = 0;
            for (Byte b : result) {
                retur[i] = b.byteValue();
                ++i;
            }
            return retur;
        }
        else {

            if (file.length < 4) {
                for (byte f : file) result.add(f);
                byte [] retur = new byte [result.size()];
                int i = 0;
                for (Byte b : result) {
                    retur[i] = b.byteValue();
                    ++i;
                }
                return retur;

            }

            if (file.length <= 4095) search_buffer = 0;
            else search_buffer = 4095;
            //Inicialitzem la codificació ( els primers bytes mai seran match)
            result.add(file[0]);
            result.add(file[1]);
            result.add(file[2]);
            mask += "000";
            //Recorrem l'arxiu
            while (LAB < file.length) {
                //Si la mascara te mida 7, o hem reccorregut tota la sequûència codifiquem en la llista definitiva mask+llista temporal
                if (mask.length() == 7) {
                    encoded.addAll((encode(mask, result)));
                    mask = "";
                    result.clear();
                }
                //Generem una parella <match,offset> si : hem trobat el match màxim, o els elements actuals ja no coincideixen i el match > 3
                if (found && (match == 127 || ((SB == LABini || file[LAB] != file[SB]) && match > 3))) {

                    byte binmatch = (byte) (match);
                    byte binoffsetl = (byte) (offset);  // 3<offset<4096 --> s'ha de codificar en 2 bytes
                    byte binoffseth = (byte) (offset >> 8);

                    result.add(binmatch);
                    result.add(binoffseth);
                    result.add(binoffsetl);

                    mask += '1'; // Com hem generat una parella, l'element següent de la màscara és 1

                    match = 0;
                    offset = 0;
                    found = false;

                    if (LAB >= 4095) SB = LAB - search_buffer;
                    else SB = 0;

                }
                //Si anem trobant elements coincidents augmentem el match
                else if (file[LAB] == file[SB] && SB < LABini) {
                    if (!found) {
                        offset = LAB - SB; // Si és el primer element coincident (trobat == false), guardem la diferència entre ambdós (offset)
                        found = true;
                    }
                    match++;
                    SB++;
                    LAB++;
                }
                //Si no hem trobat cap element coincident, o bé hem trobat el primer que no ho és i el match actual és < 4, codifiquem com a caràcter
                else if ((found && match < 4 && (file[LAB] != file[SB] || SB == LABini)) || (!found && SB == LAB - 1)) {
                    byte notmatched;
                    if (found) {
                        notmatched = file[LAB - match]; //Si hi havia coincidència hem de restar match de l'element que ens trobem per situar-nos en l'actual
                        found = false;
                        LAB -= match;
                    } else notmatched = file[LAB]; //Sino, l'actual és el que toca codificar

                    result.add(notmatched);

                    mask += '0'; //Hem afegit un caràcter, per tant el següent bit de la mascara és 0.

                    LAB++;
                    LABini = LAB;
                    offset = 0;
                    match = 0;

                    if (LAB >= 4095) SB = LAB - search_buffer;
                    else SB = 0;
                } else ++SB;
                //Controlem que l'última màscara es codifiqui per molt que no hagi arribat a 7 bits, així com els possibles últims elements restants
                if (LAB == file.length) {
                    if (match > 3) {
                        byte binmatch = (byte) (match);
                        byte binoffsetl = (byte) (offset);  // 3<offset<4096 --> s'ha de codificar en 2 bytes
                        byte binoffseth = (byte) (offset >> 8);

                        result.add(binmatch);
                        result.add(binoffseth);
                        result.add(binoffsetl);

                        mask += '1';
                    }
                    else {
                        while (match > 0) {
                            result.add(file[LAB - match]);
                            match--;
                            mask += "0";
                        }
                    }
                    while (mask.length() < 7) mask = mask + "0";
                    encoded.addAll((encode(mask, result)));
                }
            }
        }

        double endTime = System.currentTimeMillis();
        super.time = (endTime - startTime);                                               //Càlcul del temps de compressió i assignació a variable de la superclasse
        (file.length > 0) ? super.grade = (double)encoded.size()/(double)file.length : 0; //Càlcul del ratio de compressió i assignació a variable de la superclasse

        // Conversió de la llista a un byte[]
        byte [] retur = new byte [result.size()];
        int i = 0;
        for (Byte b : encoded) {
            retur[i] = b.byteValue();
            ++i;
        }
        return retur;
    }


    /** \brief Codificació en una sola llista d'una màscara i els elements corresponents als bits d'aquesta
     \pre Cert
     \post Es retorna una llista composta per un byte de màscara i els elements als qui fa referència
     */

    private  List<Byte> encode(String mask, List<Byte> current) {
        //Codifiquem en una sola llista la màscara actual i els elements corresponents a aquesta màscara
        List<Byte> encoded = new ArrayList<>();

        Byte Bmask = Byte.parseByte(mask,2);
        encoded.add(Bmask);
        for (byte bin : current) {

            encoded.add(bin);
        }
        return encoded;
    }




    /** \brief Descompressió en LZSS
     \pre encoded és un arxiu codificat segons LZSS
     \post Retorna una List<Byte> corresponent a la descodificació d' encoded
     */

    public byte[] descompress(byte[] entrada)  {
        List<Byte> encoded = new ArrayList<Byte>(entrada.length);
        for (byte b : entrada) {
            encoded.add(b);
        }
        List<Byte> result = new ArrayList<>();
        int resultindex = 0;
        while (encoded.size() < 4 && resultindex < encoded.size()) {
            result.add(encoded.get(resultindex++));
            if (resultindex == encoded.size()) {
                byte [] retur = new byte [result.size()];
                int i = 0;
                for (Byte b : result) {
                    retur[i] = b.byteValue();
                    ++i;
                }
                return retur;
            }
        }
        //Recorrem els bytes codificats
        for (int i = 0; i < encoded.size();) {

            //Obtenim la màscara i la posem en 7 bits

            String binmask = Integer.toBinaryString(((int) encoded.get(i++)) & 0xFF);
            while (binmask.length() < 7 ) binmask = "0" + binmask;


            //Recorrem la màscara de bits per identificar què són caràcters (0) i què són parelles <match,offset> (1)

            for (int maskind = 0; maskind < binmask.length() && i < encoded.size(); ++maskind) {
                //Obtenim caràcters
                if (binmask.charAt(maskind) == '0') {
                    result.add(encoded.get(i++));
                    resultindex++;
                }
                //Decsodifiquem parelles
                else if (i+2 < encoded.size()){
                    String offset,offsetl,offseth;

                    offsetl = Integer.toBinaryString(encoded.get(i+2).intValue()&0xFF);
                    while (offsetl.length() < 8) offsetl = "0"+offsetl;
                    offseth = Integer.toBinaryString(encoded.get(i+1).intValue()&0x0F);
                    offset = offseth+offsetl;

                    int offsetint = Integer.parseInt(offset,2);

                    for (int match = 0; match < encoded.get(i).intValue(); ++match) {
                        result.add((result.get(resultindex - offsetint)));
                        resultindex++;
                    }
                    i += 3;
                }
            }
        }
        byte [] retur = new byte [result.size()];
        int i = 0;
        for (Byte b : result) {
            retur[i] = b.byteValue();
            ++i;
        }
        return retur;
    }


}