import Gestor_fitxeros.gestor_fitxers;

import java.io.IOException;


public class controlador_gestor_fitxer {

    private static Boolean C_P;
    private static String Path_original = "";
    private static String Path_desti = "";
    private static Integer id_algorisme;


    //EXCEPCIONS
    public  class FicheroCompressionNoValido extends Exception {

        public FicheroCompressionNoValido(String message) {
            super(message);
        }
    }

    public  class FicheroDescompressionNoValido extends Exception {

        public FicheroDescompressionNoValido(String message) {
            super(message);
        }
    }

    public controlador_gestor_fitxer(String path_og, String path_dest, Integer id_alg, Boolean cd) {
        C_P = cd;
        Path_original = path_og;
        Path_desti = path_dest;
        id_algorisme = id_alg;
    }

    public Object get_buffer() throws IOException, FicheroCompressionNoValido, FicheroDescompressionNoValido {
        gestor_fitxers gestor = new gestor_fitxers();
        if (!C_P) {
            String aux = Path_original.substring(Path_original.length() - 3);
            if ((aux.equals("txt") & id_algorisme == 4) | (aux.equals("ppm") & id_algorisme != 4)) {
                throw new FicheroCompressionNoValido("El fichero seleccionado no es comaptible con el algorismo");
            }

            else return gestor.get_f_compressio(Path_original, id_algorisme);
        }

        else {
            String aux = Path_original.substring(Path_original.length() - 2);
            if (((aux.equals("f8")|aux.equals("fS") | aux.contentEquals("fW"))& id_algorisme == 4) | (aux.equals("fJ") & id_algorisme != 4)) {
                throw new FicheroDescompressionNoValido("El fichero no se puede descomprimir.");
            }

            else return gestor.conversio_fitxer_desc(Path_original);

        }
    }

    public void writeFile (Object write) throws IOException{
        gestor_fitxers gestor = new gestor_fitxers();
        if(!C_P){
            gestor.c_e_fichero_descomp(Path_desti,write,id_algorisme);
        }

        else gestor.c_e_fichero_comp(Path_desti,write,id_algorisme);
    }

}
