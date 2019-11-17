package Controlador_Compressio_Descompressio;


import Controlador_Compressio_Descompressio.Controlador_fitxers_stub.controlador_gestor_fitxer;

import java.io.IOException;

public class Driver_CD {
    public static void main(String[] args) throws IOException {
        controlador_gestor_fitxer I = new controlador_gestor_fitxer("Origen","destino",4,true);
        Cont_CD C = new Cont_CD();
        C.compressio_descompressio(I);
        C.comparar();
    }
}
