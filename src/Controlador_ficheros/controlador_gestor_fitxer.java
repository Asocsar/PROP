package Controlador_Gestor_Fitxer;


import Gestor_fitxeros.gestor_fitxers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class controlador_gestor_fitxer {

    private Boolean C_P;
    private String id_a;
    private gestor_fitxers gestor;
    private Boolean restante;

    //EXCEPCIONS

    //Excepció que salta quan un fitxer introduit per a comprimir no és vàlid o no és compatible amb l'algorisme
    public  class FicheroCompressionNoValido extends Exception {

        public FicheroCompressionNoValido(String message) {
            super(message);
        }
    }

    //Excepció que salta quan un fitxer introduit per a comprimir no és vàlid
    public  class FicheroDescompressionNoValido extends Exception {

        public FicheroDescompressionNoValido(String message) {
            super(message);
        }
    }

    //PRE: Cert
    //POST: Crea una instancia de la classe controlador_gestor_fitxer
    public controlador_gestor_fitxer() {

    }

    public void folder(String Path_o){

    }

    public boolean resten(){
        return restante;
    }

    //PRE: Path_original ha de ser vàlid
    //POST: Retorna l’estructura de dades necessària obtinguda del fitxer del path_original, depenent de si és una compressió o descompressió i de l’algorisme que s’utilitzarà.
    public Object get_buffer(String Path_original, Boolean c_p,String id) throws IOException, FicheroCompressionNoValido, FicheroDescompressionNoValido {
        C_P= c_p;
        id_a= id;
        //IF COMPRESIO
        if (C_P) {
            String aux = Path_original.substring(Path_original.length() - 3);
            if (!aux.equals("txt") & !aux.equals("ppm")) {
                throw new FicheroCompressionNoValido("El fichero seleccionado no es valido");
            }

            else {
                if ((aux.equals("txt") & id_a.equals("JPEG")) | (aux.equals("ppm") & !id_a.equals("JPEG") )) {
                    throw new FicheroCompressionNoValido("El fichero seleccionado no es comaptible con el algorismo");
                } else gestor.get_f_compressio(Path_original, id);
            }
        }

        //PART DESCOMPRESIO
        else {
            String aux = Path_original.substring(Path_original.length() - 2);
            if((!aux.equals("f8")& !aux.equals("fS") & !aux.equals("fW") & !aux.equals("fG"))){
                throw new FicheroDescompressionNoValido("El fichero no se puede descomprimir.");
            }
            else gestor.conversio_fitxer_desc(Path_original);

        }
        return null;
    }

    //PRE: path_og ha de ser vàlid
    //POST: Crea un fitxer amb el resultat de la compressió o descompressió passat per paràmetre.
    public void writeFile (Object write, String Path_desti,String Path_og) throws IOException{
        Path path= Paths.get(Path_desti);
        if(!C_P){
            gestor.c_e_fichero_descomp(Path_og,Path_desti,write);
        }

        else gestor.c_e_fichero_comp(Path_desti,write); 
    }

    //PRE: Cert
    //POST: Retorna el contingut del fitxer en el path "path" en un string
    public String obtenir_fitxer(String path) throws IOException {
        String a=  gestor.read_file(path);
        return a;

    }

    public String getAlgoritme (String path_o){
        String aux= path_o.substring(path_o.length()-2);
        switch(aux) {
            case"f8": return "LZ78";

            case"fS": return "LZSS";

            case"fW": return "LZW";

            case"fG": return "JPEG";

            default:
        }
        return null;
    }
     public String compare(byte[] aux, String id) throws IOException {
        return gestor.compare_g(aux,id);
    }


}
