package Controlador_ficheros;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class controlador_gestor_fitxer {

    private String path_fitxer_carpeta;
    private  Boolean C_P;
    private String id_a;
    private static gestor_fitxers gestor;
    private List<String> no_val = new ArrayList<>();




    //EXCEPCIONS

    //Excepció que salta quan un fitxer introduit per a comprimir no és vàlid o no és compatible amb l'algorisme
    public class FicheroCompressionNoValido extends Exception {

        public FicheroCompressionNoValido(String message) {
            super(message);
        }
    }

    //Excepció que salta quan un fitxer introduit per a comprimir no és vàlid
    public class FicheroDescompressionNoValido extends Exception {

        public FicheroDescompressionNoValido(String message) {
            super(message);
        }
    }

    //PRE: Cert
    //POST: Crea una instancia de la classe controlador_gestor_fitxer
    public controlador_gestor_fitxer() {
        gestor = new gestor_fitxers();
    }

    public String get_extensio(){return gestor.get_extensio();}

    public  String path_if_null(){
        return gestor.path_if_null();
    }

    public boolean dir_or_arch(String path){ return gestor.dir_or_arch(path);}

    public List<String> getPaths_no_valids(){ return no_val;}


    public void create_img_aux1 (String name, String path) throws IOException {
        gestor.create_img_aux1(name, path);
    }

    public boolean a_comprimir (String path) {return gestor.a_comprimir(path);}

    public String get_ext_file (String path) {return gestor.get_ext_file(path);}

    //COMPRESIO DE CARPETES:
    public List<String> get_paths_carpeta(String Path_o, String path_desti, String id) throws IOException {
        List<String> paths_fitxers;
        String exten =getExtensio(id);
        gestor.act_path_carpeta_og(Path_o);
        String path_dest;
        if(path_desti.equals("")) path_dest = Path_o + exten;
        else{
            String s_aux= gestor.get_nom_carpeta2(Path_o) + exten;
            Path p_aux= Paths.get(path_desti,s_aux);
            path_dest = p_aux.toString();
        }
        paths_fitxers = new ArrayList<>();
        gestor.reset_num();
        paths_fitxers = gestor.get_paths_folder(Path_o, paths_fitxers);
        no_val = gestor.getPaths_no_valids();
        gestor.act_num_f( paths_fitxers.size());
        path_fitxer_carpeta=  gestor.create_dir_comp(path_dest);
        return paths_fitxers;
    }

    public String getNom_fitxer(){
        return gestor.getNom_fitxer();
    }

    public void write_c_folder(String Path_o, byte[] encoded) throws IOException {
        gestor.write_compressed_folder(encoded, Path_o,path_fitxer_carpeta);

    }

    //DESCOMPRESIO DE CARPETES:

    public void reset_bytes_llegits(){gestor.reset_bytes_llegits();}

   /* public String find_path(String a){
        return gestor.find_path(a);
    }*/

    public Integer read_tamany(String path_fitxer_carpeta) throws IOException {
        return gestor.read_tamany(path_fitxer_carpeta);
    }



    public String[] extensiones_validas(){
        return gestor.extensiones_validas();
    }


    //PRE: Path_original ha de ser vàlid
    //POST: Retorna l’estructura de dades necessària obtinguda del fitxer del path_original, depenent de si és una compressió o descompressió i de l’algorisme que s’utilitzarà.
    public byte[] get_buffer(String Path_original, Boolean c_p, String id) throws IOException, FicheroCompressionNoValido, FicheroDescompressionNoValido {
        C_P = c_p;
        id_a = id;
        byte b[] = null;
        //IF COMPRESIO
        if (C_P) {
            String aux = Path_original.substring(Path_original.length() - 3);
            if (!aux.equals("txt") & !aux.equals("ppm")) {
                throw new FicheroCompressionNoValido("El fichero seleccionado no es valido");
            } else {
                if ((aux.equals("txt") && id_a.equals("JPEG")) || (aux.equals("ppm") && !id_a.equals("JPEG"))) {
                    throw new FicheroCompressionNoValido("El fichero seleccionado no es comaptible con el algorismo");
                } else {
                    b = gestor.get_f_compressio(Path_original, id_a);
                }
            }
        }

        //PART DESCOMPRESIO
        else {
            String aux = Path_original.substring(Path_original.length() - 2);
            if ((!aux.equals("f8") && !aux.equals("fS") && !aux.equals("fW") && !aux.equals("fG"))) {
                throw new FicheroDescompressionNoValido("El fichero no se puede descomprimir.");
            } else
                b = gestor.conversio_fitxer_desc(Path_original);

        }
        return b;
    }

    //PRE: path_og ha de ser vàlid
    //POST: Crea un fitxer amb el resultat de la compressió o descompressió passat per paràmetre.
    public void writeFile(Object write, String Path_desti) throws IOException {
        if (!C_P) {
            gestor.c_e_fichero_descomp(Path_desti, write);
        } else gestor.c_e_fichero_comp(Path_desti, write);
    }

    //PRE: Cert
    //POST: Retorna el contingut del fitxer en el path "path" en un string
    public String obtenir_fitxer(String path) throws IOException {
        return gestor.read_file(path);
    }

    public String getAlgoritme(String path_o) {
        String aux = path_o.substring(path_o.length() - 2);
        switch (aux) {
            case "f8" :

            case "F8" :
                return "LZ78";

            case "fS":

            case "FS":
                return "LZSS";

            case "fW":

            case "FW":
                return "LZW";

            case "fG":

            case "FG":
                return "JPEG";


            default:
        }
        return null;
    }

    private String getExtensio(String id_s) {
        switch (id_s) {
            case "LZ78":
                return ".F8";

            case "LZSS":
                return ".FS";

            case "LZW":
                return ".FW";

            case "JPEG":
                return ".FG";

            default:
        }
        return null;
    }

    public boolean is_jpeg(String path_fitxer){
        if(path_fitxer.substring(path_fitxer.length() - 3).equals("ppm")) return true;
        return false;
    }

    public String compare(byte[] aux, String id) throws IOException {
        return gestor.compare_g(aux, id);
    }

    public String path_dest_carpeta(String path_carpeta_comprimida, String path_destino){
        return gestor.path_dest_carpeta(path_carpeta_comprimida,path_destino);
    }

    public void  write_fitxer_carpeta_desc(String path_c_og,String path_dest_c, String path_fichero, byte[] fdescomprimit) throws IOException {
        gestor.write_fitxer_carpeta_desc(path_c_og,path_dest_c,path_fichero,fdescomprimit);
    }

    public void crea_dir_desc(String path_destino_carpeta) throws IOException {
        gestor.create_dir_comp(path_destino_carpeta);
    }

    public String read_path(String path_fitxer_carpeta) throws IOException {
        return gestor.read_path(path_fitxer_carpeta);
    }

    public byte[] read_file_compressed(Integer bytesfichero, String path_fitxer_carpeta) throws IOException {
        return gestor.read_file_compressed(bytesfichero,path_fitxer_carpeta);
    }

}