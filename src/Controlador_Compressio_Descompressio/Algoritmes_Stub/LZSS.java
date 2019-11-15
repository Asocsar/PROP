package Controlador_Compressio_Descompressio.Algoritmes_Stub;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

public class LZSS {

    public double rate = 2.0;
    public double time = 100;

    public LZSS () {
    }
    public double getTime() {
        return time;
    }

    public double getRate() {
        return rate;
    }

    public List<Integer> compress (BufferedReader file) {
        List<Integer> L = Arrays.asList(76,111,114,101);
        return L;
    }

    public String descomprimir (List<Integer> s) {
        String S = "Hola";
        return S;
    }
}
