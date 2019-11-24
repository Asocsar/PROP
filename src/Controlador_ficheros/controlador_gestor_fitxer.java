package Controlador_ficheros;

import java.io.IOException;



public class controlador_gestor_fitxer {

    private Boolean C_P;
    private Integer id_a;

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

    //PRE: Path_original ha de ser vàlid
    //POST: Retorna l’estructura de dades necessària obtinguda del fitxer del path_original, depenent de si és una compressió o descompressió i de l’algorisme que s’utilitzarà.
    public Object get_buffer(String Path_original, Boolean c_p,Integer id_algorisme) throws IOException, FicheroCompressionNoValido, FicheroDescompressionNoValido {
        gestor_fitxers gestor = new gestor_fitxers();
        C_P= c_p;
        id_a= id_algorisme;
        if (C_P) {
            String aux = Path_original.substring(Path_original.length() - 3);
            if (!aux.equals("txt") & !aux.equals("ppm")) {
                throw new FicheroCompressionNoValido("El fichero seleccionado no es valido");
            }

            else {
                if ((aux.equals("txt") & id_algorisme == 4) | (aux.equals("ppm") & id_algorisme != 4)) {
                    throw new FicheroCompressionNoValido("El fichero seleccionado no es comaptible con el algorismo");
                } else gestor.get_f_compressio(Path_original, id_algorisme);
            }
        }

        else {
            String aux = Path_original.substring(Path_original.length() - 2);
            if (((aux.equals("f8")|aux.equals("fS") | aux.equals("fW"))& id_algorisme == 4) | (aux.equals("fJ") & id_algorisme != 4)) {
                throw new FicheroDescompressionNoValido("El fichero no se puede descomprimir.");
            }

            else gestor.conversio_fitxer_desc(Path_original);

        }
        return null;
    }

    //PRE: path_og ha de ser vàlid
    //POST: Crea un fitxer amb el resultat de la compressió o descompressió passat per paràmetre.
    public void writeFile (Object write, String Path_desti,String path_og) throws IOException{
        gestor_fitxers gestor = new gestor_fitxers();
        if(!C_P){
            gestor.c_e_fichero_descomp(path_og,Path_desti,write);
        }

        else gestor.c_e_fichero_comp(Path_desti,write,id_a);
    }

    //PRE: Cert
    //POST: Retorna el contingut del fitxer en el path "path" en un string
    public String obtenir_fitxer(String path) throws IOException {
        gestor_fitxers gestor= new gestor_fitxers();
        String a=  gestor.read_file(path);
        return a;

    }

}