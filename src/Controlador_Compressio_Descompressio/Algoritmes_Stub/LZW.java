package Controlador_Compressio_Descompressio.Algoritmes_Stub;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

public class LZW {

    public double rate = 2.0;
    public double time = 100;

    public LZW () {
    }

    public double getTime() {
        return time;
    }

    public double getRate() {
        return rate;
    }

    public List<Integer> compress (Object o) {

        List<Integer> L = Arrays.asList(76,111,114,101);
        return L;
    }

    public String descomprimir (List<Integer> s) {
        String S = "Hola Mundo!";
        return S;
    }
}
