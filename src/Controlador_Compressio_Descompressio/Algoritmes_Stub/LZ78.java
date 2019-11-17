package Controlador_Compressio_Descompressio.Algoritmes_Stub;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

public class LZ78 {

    public double rate = 2.0;
    public double time = 100;

    public LZ78 () {}

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

    public String descomprimir (Object o) {
        String S = "Contingut de descomprimir amb LZ78";
        return S;
    }
}
