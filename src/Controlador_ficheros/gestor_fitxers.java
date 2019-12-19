package Controlador_ficheros;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;





public class gestor_fitxers {
    //VARIABLES DE LA CLASSE
    private String nom_fitxer;
    private String extensio;
    private String Path_original;
    private String []extensiones = new String[]{".txt",".ppm",".f8",".fW",".fS",".fG",".FW",".FS",".F8"};
    private List<String> paths_no_valids = new ArrayList<>();
    private Integer num_fitxers;
    private Integer bytes_llegits;
    private String path_carpeta_og;

    /** \brief Creadora
     \pre Cert
     \post Es crea una instància de la classe gestor_fitxers
     */

    public gestor_fitxers(){ }

    //COMPRESSIÓ CARPETES

    /** \brief Número de fitxers
     \pre Cert
     \post el número de fitxers de la carpeta a comprimir és igual a i
     */

    public void act_num_f( Integer i){
        num_fitxers= i;
    }

    /** \brief Path actual
     \pre p és un path vàlid
     \post S'ha assignat el path actual del directori font
     */

    public void act_path_carpeta_og(String p){path_carpeta_og=p;}


    /** \brief Obtenció paths directori
     \pre Path_o és un path vàlid
     \post S'han guardat els paths dels elements continguts dins de la carpeta a Path_o
     */


    public List<String> get_paths_folder(String Path_o, List<String> paths_fitxers){
        File[] files = new File(Path_o).listFiles();
        for(File a: files) {
            if (a.isDirectory()) {
                get_paths_folder(a.getPath(),paths_fitxers);
            } else{
                String aux= a.getPath().substring(a.getPath().length()-3);
                if(!aux.equals("ppm") && !aux.equals("txt")){
                    paths_no_valids.add(a.getPath());
                }
                else {
                    paths_fitxers.add(a.getPath());
                    num_fitxers= num_fitxers +1;
                }
            }
        }
        return paths_fitxers;
    }


    /** \brief Crea directori
     \pre Cert
     \post S'ha creat un directori en el path path_dir. Retorna aquest path.
     */


    public String create_dir_comp(String Path_dir) throws IOException {
        File file = new File(Path_dir);
        file.createNewFile();
        FileOutputStream fop = new FileOutputStream(file);
        byte[] path= path_carpeta_og.getBytes();
        fop.write(path);
        fop.write((byte)10);
        List<Byte> char_seq= int_to_byte(num_fitxers);
        for(byte c : char_seq) {
            fop.write(c);
        }
        fop.write((byte)10);
        fop.flush();
        fop.close();
        return Path_dir;
    }

    /** \brief Compressió de carpetes
     \pre path_original és un path vàlid
     \post S'ha escrit en un fitxer tota la codificació dels fitxers dins del directori situat a path_original
     */

    public void write_compressed_folder(byte[] aux, String path_original, String fitxer_carpeta) throws IOException {
        File file= new File(fitxer_carpeta);
        FileOutputStream fop = new FileOutputStream(file, true);
        byte[] path= path_original.getBytes();
        fop.write(path);
        fop.write((byte)10);
        List<Byte> char_seq= new ArrayList<>();
        if(aux.length== 0) char_seq.add((byte)0);
        else char_seq= int_to_byte(aux.length);
        for(byte c : char_seq) {
            fop.write(c);
        }
        fop.write((byte)10);
        fop.write(aux);
        fop.write((byte)10);
        fop.flush();
        fop.close();
    }

    //DESCOMPRESIO DE CARPETES:

    /** \brief Número de fiters
     \pre path_fitxer_carptea és un path vàlid.
     \post Es retorna el número de bytes al fitxer/directori situat a path_fitxer_carpeta
     */


    //LLEGEIX ELS BYTES QUE REPRESENTEN UN TAMANY (NOMBRE DE FITXERS/ NOMBRE DE BYTES D'UN FITXER)
    public Integer read_tamany(String path_fitxer_carpeta) throws IOException {
        File file= new File(path_fitxer_carpeta);
        FileInputStream fip= new FileInputStream(file);
        if(bytes_llegits!= null) fip.skip(bytes_llegits);
        List<Byte> num_fitxers_b= new ArrayList<>();
        boolean fin= false;
        while (!fin){
            byte aux= (byte) fip.read();
            if(aux== 10)  fin = true;
            else  num_fitxers_b.add(aux);
            bytes_llegits = bytes_llegits + 1;
        }
        return list_b_to_i(num_fitxers_b);
    }

    //LLEGEIX ELS BYTES QUE REPRESENTEN EL PATH D'UN FITXER I HO TRANSFORMA A STRING
    public String read_path(String path_fitxer_carpeta) throws IOException {
        File file= new File(path_fitxer_carpeta);
        FileInputStream fip= new FileInputStream(file);
        List<Byte> path_en_bytes= new ArrayList<>();
        fip.skip(bytes_llegits);
        boolean fin= false;
        while (!fin){
            byte aux= (byte) fip.read();
            if(aux== 10) fin = true;
            else path_en_bytes.add(aux);
            bytes_llegits = bytes_llegits + 1;
        }
        return p_bytes_to_string(path_en_bytes);
    }

    //A PARTIR D'EL PATH D'UN FITXER, COMPROVA SI EL DIRECTORI ON ESTA EXISTEIX, I EN CAS DE QUE NO, EL CREA
    private String cc_directori(String path_fitxer, String path_c_original, String path_desti){
        int pos= path_fitxer.lastIndexOf("/");
        String dir= path_fitxer.substring(0,pos);
        String new_dir;
        if (!dir.equals(path_c_original)){
            new_dir= path_desti + path_fitxer.substring(dir.lastIndexOf('/'), path_fitxer.lastIndexOf('/'));
            File dire = new File(new_dir);
            dire.mkdir();
        }
        else new_dir= path_desti;
        return new_dir;
    }

    /**\brief Crea Directori
     \pre Cert
     \post Crea un directori siutat al path path_carpeta
     */

    private void crea_dir_desc(String path_carpeta){
        File dire = new File(path_carpeta);
        dire.mkdir();
    }


    //LLEGEIX TAM_FITXER BYTES DEL ARCHIU DE LA CARPETA COMPRIMIDA I HO TRANSFORMA A A BYTE[] PER A QUE SIGUI COMPRIMIT
    public byte[] read_file_compressed(Integer tam_fitxer, String path_fitxer_carpeta) throws IOException {
        File file= new File(path_fitxer_carpeta);
        FileInputStream fip= new FileInputStream(file);
        List<Byte> file_readed= new ArrayList<>();
        fip.skip(bytes_llegits);
        for(int i=0; i < tam_fitxer; ++i){
            byte ax=(byte)fip.read();
            file_readed.add(ax);
            ++bytes_llegits;
        }
        return list_b_to_ab(file_readed);
    }


    /****************
     ***COMPRESSIÓ***
     ****************/

    /**\brief Gestio fitxer a comprimir
       \pre path_og és vàlid, 0 < id < 4
       \post Retorna l’estructura de dades necessària per la compressió de l’arxiu demanat per l’algorisme seleccionat.
     */

    public byte[] get_f_compressio(String path_og, String id_a) throws IOException {
        int pos= path_og.lastIndexOf('/');
        Path_original= path_og.substring(0,pos);
        nombre_fichero(path_og);
        ex_comp(id_a);
        return buscar_leer_archivo(path_og);
    }

    /** \brief Creadora
     \pre 0 < id < 4
     \post Crea un fitxer i hi escriu el resultat de la compressió.
     */


    public void c_e_fichero_comp (String path_desti, Object aux) throws IOException {
        if (path_desti.equals("")) path_desti=Path_original;
        Path path_dest= Paths.get(path_desti,nom_fitxer);
        String nom_ex= path_dest + extensio;

        File file = new File(nom_ex);
        if (file.createNewFile()) {
            FileOutputStream fop= new FileOutputStream(file);
            fop.write((byte[])aux);
            fop.flush();
            fop.close();
        }
    }

    /** \brief Update extensio
     \pre 0 < id < 4
     \post La variable extensió ha estat actualitzada
     */

    private void ex_comp(String id_s){
        switch(id_s) {
            case"LZ78":
                extensio=".f8";
                break;
            case"LZSS":
                extensio=".fS";
                break;
            case"LZW":
                extensio=".fW";
                break;

            case"JPEG":
                extensio=".fG";
                break;

            default:
        }
    }


    /*****************
     **DESCOMPRESSIÓ**
     *****************/

    /**\brief Gestio fitxer a descomprimir
        \pre path_og és vàlid
        \post Retorna  l’estructura de dades necessària per la descompressió de l’arxiu a path_og per l’algorisme seleccionat.
     */

    public byte[] conversio_fitxer_desc(String path_og) throws IOException {
        int pos= path_og.lastIndexOf('/');
        Path_original= path_og.substring(0,pos);
        id_ex_desc(path_og);
        nombre_fichero(path_og);
        return buscar_leer_archivo(path_og);
    }

    /** \brief Creadora del fitxer destí
     \pre path_og és vàlid
     \post Crea un fitxer en el path_desti i hi escriu el resultat de la descompressió.
     */

    public void c_e_fichero_descomp ( String path_desti, Object encoded) throws IOException {
        if (path_desti.equals("")) path_desti=Path_original;
        String nom_ex= nom_fitxer + extensio;
        Path path_dest= Paths.get(path_desti,nom_ex);
        File file = new File(String.valueOf(path_dest));
        if(file.createNewFile()) {
            FileOutputStream fop = new FileOutputStream(file);
            fop.write((byte[])encoded);
            fop.flush();
            fop.close();
        }

    }

    /*************************
     *FUNCIONALITATS DIVERSES*
     *************************/


    /**\brief Lectura fitxer
     \pre cert
     \post S'ha guardat en un string el fitxer del path "path"
     */

    public String read_file(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String aux = "";
        String aux2;
        while ((aux2 = br.readLine()) != null) {
            aux = aux + aux2 + "\n";
        }
        return aux;
    }


    //CREA UN FITXER TEMPORAL PER A LA COMPARACIO DE FITXERS
    public String compare_g(byte[] aux, String id) throws IOException {
        File file = new File("temp.txt");
        if(file.createNewFile()) {
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(aux);
            fop.flush();
            fop.close();
            byte[] encoded = Files.readAllBytes(Paths.get("temp.txt"));
            file.delete();
            return new String(encoded, Charset.defaultCharset());
        }
        return null;
    }


    //RETORNA LA VARIABLE GLOBAL EXTENSIO
    public String get_extensio(){return extensio;}

    //RETORNA LES EXTENSIONS D'ARXIUS ACCEPTADES PER EL PROGRAMA
    public String[] extensiones_validas(){
        return extensiones;
    }

    //RETORNA VARIABLE GLOBAL PATH_ORIGINAL
    public String path_if_null(){
        return Path_original;
    }


    //BOOL--> FALSE SI PATH ES DIRECTORI/ TRUE SI PATH ES FITXER
    public boolean dir_or_arch(String path){
        File file = new File(path);
        if (file.isDirectory()) return false;
        else return true;
    }

    //RETORNA LA LLISTA DE PATHS NO VALIDS D'UNA CARPETA PER A COMPRIMIR I LA RESETEJA
    public List<String> getPaths_no_valids(){
        List<String> aux = paths_no_valids;
        paths_no_valids= new ArrayList<>();
        return aux;
    }

    //OBTÉ EL NOM D'UNA CARPETA
    public String get_nom_carpeta2(String Path_o){
        int pos = Path_o.lastIndexOf("/");
        String aux= Path_o.substring(pos);
        return aux;
    }

    //OBTÉ EL NOM D'UNA CARPETA
    public String get_nom_carpeta(String Path_o){
        int pos = Path_o.lastIndexOf("/");
        String aux= Path_o.substring(pos,Path_o.lastIndexOf("."));
        return aux;
    }

    //RESETEJA LA VARIABLE NUM_FITXERS AL ACABAR UNA COMPRESSIO DE CARPETA
    public void reset_num(){
        num_fitxers=0;
    }

    //RESETEJA LA VARIABLE BYTES LLEGITS AL ACABAR UNA DESCOMPRESIO DE CARPETA
    public void reset_bytes_llegits(){
        bytes_llegits=0;
    }

    public String getNom_fitxer(){
        return nom_fitxer;
    }


    //PRIVATES

    /** \brief Buscar i llegir arxiu
     \pre Cert
     \post Retorna un objecte que representa la estructura de dades necessària depenent de l’algorisme utilitzat i de si es fa una compressió o descompressió.
     */
    private byte[] buscar_leer_archivo(String path_og) throws IOException {
        File file = new File (path_og);
        byte[] aux1 = Files.readAllBytes(file.toPath());
        return aux1;
    }

    //AGAFA EL NOM DEL FITXER ORIGINAL
    private void nombre_fichero(String Path_og){
        Path p = Paths.get(Path_og);
        String aux = p.getFileName().toString();
        int pos = aux.lastIndexOf(".");
        nom_fitxer=aux.substring(0, pos);
    }

    //INTEGER --> LLISTA DE BYTES
    private  List<Byte> int_to_byte(int i){
        int aux= i;
        List<Byte> char_seq= new ArrayList<>();
        while (aux > 0) {
            int punt = aux % 10;
            aux = aux / 10;
            char_seq.add(0,(byte)punt);

        }
        return char_seq;
    }

    //PRE: path_og és un path vàlid
    //POST: Actualitza les variables id_desc i extensió depenent del path_og
    private void id_ex_desc(String path_og){
        String aux= path_og.substring(path_og.length()-2);
        if (aux.equals("fG")) extensio = ".ppm";
        else extensio = ".txt";

    }

    //LLISTA DE BYTES --> INTEGER
    private Integer list_b_to_i(List<Byte> aux){
        int i_aux=0;
        for(int i=0; i < aux.size(); ++i){
            i_aux = i_aux + (int) aux.get(i);
            if(!(i == aux.size()-1)) i_aux=i_aux *10;
        }
        return i_aux;
    }

    /**\brief Conversió de llista a array
     \pre Cert
     \post Retorna un byte[] amb elements coincidents als de la llista 'a'
     */

    private byte[] list_b_to_ab(List<Byte> a){
        byte[] file= new byte[a.size()];
        for(int i=0; i < a.size(); ++i){
            file[i]= a.get(i);
        }
        return file;
    }

    /**\brief Conversió de llista a String
     \pre Cert
     \post Retorna un String amb elements coincidents als de la llista 'path_en_bytes'
     */

    private String p_bytes_to_string(List<Byte>path_en_bytes){
        byte[] aux= new byte[path_en_bytes.size()];
        for (int i=0; i < aux.length; ++i){
            aux[i]= path_en_bytes.get(i);
        }
        return new String(aux, Charset.defaultCharset());
    }

    public String path_dest_carpeta(String path_carpeta_comprimida, String path_destino){
        if(path_destino.equals("")) {
            String aux= path_carpeta_comprimida.substring(0,path_carpeta_comprimida.lastIndexOf("."));
            crea_dir_desc(aux);
            return aux;
        }
        else{
            String aux= get_nom_carpeta(path_carpeta_comprimida);
            Path dest= Paths.get(path_destino,aux);
            crea_dir_desc(dest.toString());
            return dest.toString();
        }
    }

    public void  write_fitxer_carpeta_desc(String path_c_og,String path_dest_c, String path_fichero, byte[] fdescomprimit) throws IOException {
        String dest_final=  cc_directori(path_fichero,path_c_og,path_dest_c);
        String nom_f= path_fichero.substring(path_fichero.lastIndexOf("/"));
        Path direccio = Paths.get(dest_final,nom_f);
        File file= new File(direccio.toString());
        file.createNewFile();
        FileOutputStream fop= new FileOutputStream(file);
        fop.write(fdescomprimit);
        fop.flush();
        fop.close();
        ++bytes_llegits;
    }



}