package Controlador_ficheros;


import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class controlador_gestor_fitxer {

    private String path_fitxer_carpeta;
    private  Boolean C_P;
    private String id_a;
    private gestor_fitxers gestor;
    private List<String> path_no_valid = new ArrayList<>();
    private List<String> paths_fitxers = new ArrayList<>();


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

    /** \brief Creadora
     \pre Cert
     \post Es crea una instància de la classe gestor_fitxers
     */
    public controlador_gestor_fitxer() {
        gestor = new gestor_fitxers();
    }

    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada extensio.
     */
    public String get_extensio(){return gestor.get_extensio();}

    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada Path_original.
     */
    public  String path_if_null(){
        return gestor.path_if_null();
    }

    /**\brief Comprovació
     \pre path és vàlid
     \post retorna true si el path és un fitxer, o bé false si és un directori.
     */
    public boolean dir_or_arch(String path){ return gestor.dir_or_arch(path);}


    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada paths_no_valids.
     */
    public List<String> getPaths_no_valids(){ return this.path_no_valid;}


    /**\brief Obtenció dels paths d'una carpeta
     \pre path_o i path_desti són paths vàlids. El id ha de ser vàlid.
     \post crea el fitxer on s'escriurà el contingut comprimit de la carpeta amb path igual a path_o. Si pot retorna la llista de paths existents en aquesta carpeta, si no pot, retorna una llista amb un -1 a la primera posició.
     */
    public List<String> get_paths_carpeta(String Path_o, String path_desti, String id, boolean force) throws IOException {
        paths_fitxers= new ArrayList<>();
        String exten =getExtensio(id);
        gestor.act_path_carpeta_og(Path_o);
        String path_dest;
        if(path_desti.equals("")) path_dest = Path_o + exten;
        else{
            String s_aux= gestor.get_nom_carpeta2(Path_o) + exten;
            Path p_aux= Paths.get(path_desti,s_aux);
            path_dest = p_aux.toString();
        }
        gestor.reset_num();
        paths_fitxers = gestor.get_paths_folder(Path_o, paths_fitxers);
        path_dest= gestor.all_jpeg(path_dest,paths_fitxers);
        path_fitxer_carpeta=  gestor.create_dir_comp(path_dest, force);
        if(path_fitxer_carpeta== null) {
            paths_fitxers = new ArrayList<>();
            paths_fitxers.add("-1");
            return paths_fitxers;
        }

        path_no_valid = gestor.getPaths_no_valids();
        gestor.act_num_f( paths_fitxers.size());
        return paths_fitxers;
    }

    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada nom_fitxer.
     */
    public String getNom_fitxer(){
        return gestor.getNom_fitxer();
    }

    /** \brief Compressió de carpetes
     \pre path_original és un path vàlid
     \post S'ha escrit en un fitxer tota la codificació dels fitxers dins del directori situat a path_original
     */
    public void write_c_folder(String Path_o, byte[] encoded) throws IOException { gestor.write_compressed_folder(encoded, Path_o,path_fitxer_carpeta); }

    /**\brief Actualitzadora
     \pre cert
     \post fica la variable global bytes_llegits a 0.
     */
    public void reset_bytes_llegits(){gestor.reset_bytes_llegits();}

    /** \brief Eliminador de fitxers
     \pre Existeix un fitxer amb path igual a "path"
     \post el fitxer amb path igual a "path" és eliminat.
     */
    public void delete_file(String path){ gestor.delete_file(path);}


    /** \brief Lectura d'un enter contingut en un arxiu comprimit.
     \pre path_fitxer_carptea és un path vàlid.
     \post es llegeixen bytes fins arribar a un byte de control del fitxer amb path igual a "path_fitxer_carpeta", els quals representen un enter. Retorna l'enter.
     */
    public Integer read_tamany(String path_fitxer_carpeta) throws IOException { return gestor.read_tamany(path_fitxer_carpeta); }


    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada extensiones.
     */
    public String[] extensiones_validas(){
        return gestor.extensiones_validas();
    }

    /**\brief Creació del fitxer comprimit
     \pre path_og i path_desti són vàlids. L'id de l'algorisme ha de ser vàlid també.
     \post Crea un fitxer on s'escriura la compressió de l'arxiu amb path igual a "path_og". Si es crea, retorna el path on ha estat creat, si no, retorna -1.
     */
    public String c_fichero_comp (String path_og, String path_desti, boolean force, String id_a) throws IOException {
        return gestor.c_fichero_comp(path_og,path_desti,force,id_a);
    }

    /**\brief Creació del fitxer descomprimit
     \pre path_og i path_desti són vàlids.
     \post Crea un fitxer on s'escriura la descompressió de l'arxiu amb path igual a "path_og". Si es crea, retorna el path on ha estat creat, si no, retorna -1.
     */
    public String c_fichero_descomp (String path_og, String path_desti, boolean force) throws IOException { return gestor.c_fichero_descomp(path_og,path_desti,force); }

    /**\brief Comprovació de path.
     \pre cert
     \post comprova que el path pasat existeixi.
     */
    public boolean path_valid(String path){ return gestor.path_valid(path);}


    /**\brief Obtenció fitxer.
     \pre Path_original és un path vàlid. Id es correspon amb algun dels algorismes implementats.
     \post retorna un fitxer transformat en l'estructura de dades necessaria per a la compressió o descompressió.
     */
    public byte[] get_buffer(String Path_original, Boolean c_p, String id) throws IOException, FicheroCompressionNoValido, FicheroDescompressionNoValido {
        C_P = c_p;
        id_a = id;
        byte b[] = null;
        if (C_P) {
            String aux = Path_original.substring(Path_original.length() - 3);
            if (!aux.equals("txt") & !aux.equals("ppm")) {
                throw new FicheroCompressionNoValido("El fichero seleccionado no es valido");
            } else {
                if ((aux.equals("txt") && id_a.equals("JPEG")) || (aux.equals("ppm") && !id_a.equals("JPEG"))) {
                    throw new FicheroCompressionNoValido("El fichero seleccionado no es comaptible con el algorismo");
                } else {
                    b = gestor.get_f_compressio(Path_original);
                }
            }
        }
        else {
            String aux = Path_original.substring(Path_original.length() - 2);
            if ((!aux.equals("f8") && !aux.equals("fS") && !aux.equals("fW") && !aux.equals("fG"))) {
                throw new FicheroDescompressionNoValido("El fichero no se puede descomprimir.");
            } else
                b = gestor.conversio_fitxer_desc(Path_original);

        }
        return b;
    }

    /** \brief Escriptura en un fitxer.
     \pre path_desti ha de ser vàlid.
     \post Escriu el resultat d'una compressió o descompressió en el fitxer amb path igual a "path_desti".
     */
    public void writeFile(Object write, String Path_desti) throws IOException {
        if (!C_P) {
            gestor.e_fichero_descomp(Path_desti, write);
        } else gestor.e_fichero_comp(Path_desti, write);
    }

    /**\brief Lectura fitxer
     \pre Existeix un fitxer amb path igual a "path"
     \post S'ha guardat en un string el fitxer del path "path"
     */
    public String obtenir_fitxer(String path) throws IOException { return gestor.read_file(path); }


    public String getAlgoritme(String path_o, Map<String,List<String>> m_aux) {
       return gestor.getAlgoritme(path_o);}

    public String getExtensio(String id_s) { return gestor.get_Ex(id_s); }

    public String getAlgoritme(String path_o) { return gestor.getAlgoritme(path_o);}

    public boolean is_jpeg(String path_fitxer){
        if(path_fitxer.substring(path_fitxer.length() - 3).equals("ppm")) return true;
        return false;
    }

    public String compare(byte[] aux, String id) throws IOException { return gestor.compare_g(aux); }

    public String path_dest_carpeta(String path_carpeta_comprimida, String path_destino, boolean force){ return gestor.path_dest_carpeta(path_carpeta_comprimida,path_destino, force); }

    //SI DEVUELVE FALSO NO SE HA CREADO EL FICHERO
    public boolean  write_fitxer_carpeta_desc(String path_c_og,String path_dest_c, String path_fichero, byte[] fdescomprimit) throws IOException {
        return gestor.write_fitxer_carpeta_desc(path_c_og,path_dest_c,path_fichero,fdescomprimit);
    }

    public String getPath_absoluto(){
        return gestor.getPath_absoluto();
    }

    public String crea_dir_desc(String path_destino_carpeta, boolean force) throws IOException { return gestor.create_dir_comp(path_destino_carpeta,force); }

    public boolean carpeta_des(String path){ return gestor.carpeta_des(path); }

    public String read_path(String path_fitxer_carpeta) throws IOException { return gestor.read_path(path_fitxer_carpeta); }

    public byte[] read_file_compressed(Integer bytesfichero, String path_fitxer_carpeta) throws IOException { return gestor.read_file_compressed(bytesfichero,path_fitxer_carpeta); }

    public void create_img_aux1 (String name, String path) throws IOException { gestor.create_img_aux1(name, path); }

    public boolean a_comprimir (String path) {return gestor.a_comprimir(path);}

    public String get_ext_file (String path) {return gestor.get_ext_file(path);}


}
