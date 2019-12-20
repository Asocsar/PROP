package Controlador_ficheros;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
    private static String path_absoluto;


    /** \brief Creadora
     \pre Cert
     \post Es crea una instància de la classe gestor_fitxers
     */
    public gestor_fitxers() {


    }

    /*************************
     *COMPRESSIÓ DE CARPETES**
     *************************/

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
     \post Si s'ha creat el directori en el path path_dir, retorna aquest path. Si no l'ha creat retorna null.
     */
    public String create_dir_comp(String Path_dir, boolean force) throws IOException {
        File file = new File(Path_dir);
        if(file.createNewFile() || force) {
            if(force){
                file.delete();
                file.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file);
            byte[] path = path_carpeta_og.getBytes();
            fop.write(path);
            fop.write((byte) 10);
            List<Byte> char_seq = int_to_byte(num_fitxers);
            for (byte c : char_seq) {
                fop.write(c);
            }
            fop.write((byte) 10);
            fop.flush();
            fop.close();
            return Path_dir;
        }
        else return null;
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


    /***************************
     *DESCOMPRESSIÓ DE CARPETES*
     ***************************/



    /** \brief Obtenció d'un path contingut en un arxiu comprimit
     \pre path_fitxer_carpeta és un path vàlid
     \post es llegeixen bytes fins arribar a un byte de control del fitxer amb path igual a "path_fitxer_carpeta", els quals representen el path d'un fitxer de la carpeta a descomprimir. Retorna el path en forma de String.
     */
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



    /** \brief Lectura d'un fitxer contingut en un arxiu comprimit.
     \pre path_fitxer_carpeta és vàlid
     \post es llegeixen de l'arxiu comprimit en el path "paht_fitxer_carpeta" un número de bytes igual a tam_fitxer, els quals representen un dels arxius continguts en la carpeta. Retorna aquest conjunt de bytes en forma de String.
     */
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


    /** \brief Lectura d'un enter contingut en un arxiu comprimit.
     \pre path_fitxer_carptea és un path vàlid.
     \post es llegeixen bytes fins arribar a un byte de control del fitxer amb path igual a "path_fitxer_carpeta", els quals representen un enter. Retorna l'enter.
     */
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

    /** \brief Obtenció del path de destí on es crearà el directori al ser descomprimit
     \pre path_carpeta_comprimida i path_destino són paths vàlids.
     \post retorna o bé el path de destí de la carpeta a descomprimir, o bé null depenent del resultat de "crea_dir_desc"
     */
    public String path_dest_carpeta(String path_carpeta_comprimida, String path_destino, boolean force){
        if(path_destino.equals("")) {
            String aux= path_carpeta_comprimida.substring(0,path_carpeta_comprimida.lastIndexOf("."));
            boolean b=  crea_dir_desc(aux,force);
            if(!b) return null;
            return aux;
        }
        else{
            String aux= get_nom_carpeta(path_carpeta_comprimida);
            Path dest= Paths.get(path_destino,aux);
            boolean b= crea_dir_desc(dest.toString(), force);
            if(!b) return null;
            return dest.toString();
        }
    }


    /** \brief Escriptura d'un fitxer
     \pre path_c_og, path_dest_c i path_fichero són paths vàlids.
     \post escriu un vector de bytes[] en un fitxer previament creat.
     */
    public boolean  write_fitxer_carpeta_desc(String path_c_og,String path_dest_c, String path_fichero, byte[] fdescomprimit) throws IOException {
        String dest_final=  cc_directori(path_fichero,path_c_og,path_dest_c);
        Path p = Paths.get(path_fichero);
        String nom_f= path_fichero.substring(path_fichero.lastIndexOf(p.getFileSystem().getSeparator()));
        Path direccio = Paths.get(dest_final,nom_f);
        File file= new File(direccio.toString());
        file.createNewFile();
        FileOutputStream fop= new FileOutputStream(file);
        fop.write(fdescomprimit);
        fop.flush();
        fop.close();
        ++bytes_llegits;
        return true;
    }



    /*************************
     **COMPRESSIÓ DE FITXERS**
     *************************/

    /**\brief Obtenció del fitxer a comprimir
     \pre path_og és vàlid
     \post Retorna l’estructura de dades necessària per la compressió de l’arxiu demanat.
     */

    public byte[] get_f_compressio(String path_og) throws IOException {
        return buscar_leer_archivo(path_og);
    }

    /**\brief Creació del fitxer comprimit
     \pre path_og i path_desti són vàlids. L'id de l'algorisme ha de ser vàlid també.
     \post Crea un fitxer on s'escriura la compressió de l'arxiu amb path igual a "path_og". Si es crea, retorna el path on ha estat creat, si no, retorna -1.
     */

    public String c_fichero_comp (String path_og, String path_desti, boolean force, String id_a) throws IOException {
        Path p = Paths.get(path_og);
        int pos= path_og.lastIndexOf(p.getFileSystem().getSeparator());
        Path_original= path_og.substring(0,pos);
        nombre_fichero(path_og);
        ex_comp(id_a);
        if (path_desti.equals("")) path_desti=Path_original;
        Path path_dest= Paths.get(path_desti,nom_fitxer);
        String nom_ex= path_dest + extensio;
        path_absoluto= nom_ex;
        File file = new File(nom_ex);
        if (file.createNewFile() || force) {
            if (force) {
                file.delete();
                file.createNewFile();
            }
            return nom_ex;
        }
        return "-1";
    }

    /** \brief Escriptura compressió
     \pre path_desti ha de ser vàlid.
     \post Escriu el resultat d'una compressió en el fitxer amb path igual a "path_desti".
     */
    public void e_fichero_comp (String path_desti, Object aux) throws IOException {
        File file = new File(path_desti);
        FileOutputStream fop= new FileOutputStream(file);
        fop.write((byte[])aux);
        fop.flush();
        fop.close();
    }

    /**************************
     *DESCOMPRESSIÓ DE FITXERS*
     **************************/

    /**\brief Obtenció del fitxer a descomprimir
     \pre path_og és vàlid
     \post Retorna  l’estructura de dades necessària per la descompressió de l’arxiu a path_og.
     */
    public byte[] conversio_fitxer_desc(String path_og) throws IOException {
        return buscar_leer_archivo(path_og);
    }

    /**\brief Creació del fitxer descomprimit
     \pre path_og i path_desti són vàlids.
     \post Crea un fitxer on s'escriura la descompressió de l'arxiu amb path igual a "path_og". Si es crea, retorna el path on ha estat creat, si no, retorna -1.
     */
    public String c_fichero_descomp (String path_og, String path_desti, boolean force) throws IOException {
        Path p = Paths.get(path_og);
        int pos= path_og.lastIndexOf(p.getFileSystem().getSeparator());
        Path_original= path_og.substring(0,pos);
        id_ex_desc(path_og);
        nombre_fichero(path_og);
        if (path_desti.equals("")) path_desti=Path_original;
        Path path_dest= Paths.get(path_desti,nom_fitxer);
        String nom_ex= path_dest + extensio;
        path_absoluto= nom_ex;
        File file = new File(nom_ex);
        if (file.createNewFile() || force) {
            if (force) {
                file.delete();
                file.createNewFile();
            }
            return nom_ex;
        }
        return "-1";
    }


    /** \brief Creadora del fitxer destí
     \pre path_og és vàlid
     \post Si pot crear un fitxer en el path_desti, hi escriu el resultat de la descompressió i retorna true. Si no, retorna false;
     */
    public void e_fichero_descomp ( String path_desti, Object encoded) throws IOException {
        File file = new File(String.valueOf(path_desti));
        FileOutputStream fop = new FileOutputStream(file);
        fop.write((byte[])encoded);
        fop.flush();
        fop.close();
    }



    /*************************
     *FUNCIONALITATS DIVERSES*
     **************************/


    /** \brief Transformació d'imatge
     \pre path és vàlid
     \post agafa una imatge ppm i la transforma en un arxiu png.
     */
    public void create_img_aux1 (String name, String path) throws IOException {
        byte [] b = Files.readAllBytes(Paths.get(path));
        int i = 0;
        boolean formato = true;
        boolean first = true;
        boolean end = false;
        boolean maximo = true;
        String max = "";
        byte[] contenido = new byte[1];
        String width = "";
        String hight = "";
        int aux = 0;
        while (i < b.length) {
            if (!formato) {
                if (b[i] == 35 && maximo) {
                    formato = true;
                }
                else if (!end && maximo){
                    if (b[i] == 32 && maximo) {
                        first = false;
                    }
                    else if (b[i] == 10) {
                        end = true;
                    }
                    else {
                        if (first)
                            width += Character.toString((char) b[i]);
                        else
                            hight += Character.toString((char) b[i]);
                    }
                }
                else {
                    if (!maximo)
                        contenido[aux++] = (b[i] != 0) ? b[i] : 32;
                    else {
                        if (b[i] != 10)
                            max += Character.toString((char) b[i]);
                        else {
                            maximo = false;
                            contenido = new byte[b.length-i-1];
                        }
                    }
                }
            }
            if (formato && b[i] == 10) {
                formato = false;
            }
            ++i;
        }
        BufferedImage im = ppm(Integer.parseInt(width), Integer.parseInt(hight),Integer.parseInt(max) ,contenido);
        ImageIO.write(im, "jpg", new File(name + ".png"));
    }


    /** \brief Eliminador de fitxers
     \pre Existeix un fitxer amb path igual a "path"
     \post el fitxer amb path igual a "path" és eliminat.
     */
    public void delete_file (String path){
        File file = new File(path);
        file.delete();
    }

    /** \brief Comprovació d'extensions
     \pre path és un path vàlid
     \post es comprova l'extensió del fitxer amb path igual a "path" i retorna true si l'extensió d'aquest és o .ppm o .txt.
     */
    public boolean a_comprimir (String path) {
        String ex = path.substring(path.length() - 4);
        return (ex.equals(".txt") || ex.equals(".ppm"));
    }

    /** \brief Obtenció d'extensions.
     \pre Existeix un fitxer amb path igual a "path"
     \post retorna l'extensió del fitxer.
     */
    public  String get_ext_file (String p) {
        String ex = p.substring(p.length() - 4);
        if (ex.equals(".txt") || ex.equals(".ppm")) {
            return p.substring(p.length() - 4);
        }
        else
            return p.substring(p.length() - 3);
    }


    /**\brief Lectura fitxer
     \pre Existeix un fitxer amb path igual a "path"
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


    /**\brief Comparació de fitxers
     \pre cert
     \post retorna en forma de String el vector de bytes rebut.
     */
    public String compare_g(byte[] aux) throws IOException {
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


    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada extensio.
     */
    public String get_extensio(){return extensio;}

    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada extensiones.
     */
    public String[] extensiones_validas(){
        return extensiones;
    }


    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada Path_original.
     */
    public String path_if_null(){
        return Path_original;
    }


    /**\brief Comprovació
     \pre path és vàlid
     \post retorna true si el path és un fitxer, o bé false si és un directori.
     */
    public boolean dir_or_arch(String path){
        File file = new File(path);
        return !file.isDirectory();
    }


    /**\brief Consultora
     \pre path és vàlid.
     \post retorna true si l'extensió del fitxer és .F. Si no retorna false.
     */
    public boolean carpeta_des(String path){
        return path.substring(path.lastIndexOf("."), path.length() - 1).equals(".F");
    }

    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada paths_no_valids.
     */
    public List<String> getPaths_no_valids(){
        List<String> aux = paths_no_valids;
        paths_no_valids= new ArrayList<>();
        return aux;
    }

    /**\brief Obtenció de noms.
     \pre path_o és un path vàlid
     \post retorna el nom de la carpeta amb path igual a "path_o".
     */
    public String get_nom_carpeta2(String Path_o){
        Path p = Paths.get(Path_o);
        int pos = Path_o.lastIndexOf(p.getFileSystem().getSeparator());
        String aux= Path_o.substring(pos);
        return aux;
    }



    /**\brief Actualitzadora
     \pre cert
     \post fica la variable global num_fitxers a 0.
     */
    public void reset_num(){
        num_fitxers=0;
    }

    /**\brief Actualitzadora
     \pre cert
     \post fica la variable global bytes_llegits a 0.
     */
    public void reset_bytes_llegits(){
        bytes_llegits=0;
    }

    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada nom_fitxer.
     */
    public String getNom_fitxer(){
        return nom_fitxer;
    }

    /** \brief Número de fitxers
     \pre Cert
     \post el número de fitxers de la carpeta a comprimir és igual a i
     */
    public void act_num_f( Integer i){
        num_fitxers= i;
    }

    /** \brief Actualitzadora
     \pre p és un path vàlid
     \post S'ha assignat el path actual del directori font
     */
    public void act_path_carpeta_og(String p){path_carpeta_og=p;}

    /**\brief Consultora
     \pre cert
     \post retorna la variable global privada path_absoluto.
     */
    public String getPath_absoluto(){ return path_absoluto; }



    /*************************
     *FUNCIONALITATS PRIVADES*
     *************************/

    /**\brief Comprovació de directori
     \pre path_fitxer i path_desti són paths vàlids
     \post retorna la variable global privada nom_fitxer.
     */
    private String cc_directori(String path_fitxer, String path_c_original, String path_desti){
        Path p = Paths.get(path_fitxer);
        int pos= path_fitxer.lastIndexOf(p.getFileSystem().getSeparator());
        String dir= path_fitxer.substring(0,pos);
        String new_dir=path_desti;
        List<String> auxiliar= new ArrayList<>();
        if(!dir.equals(path_c_original)) {
            while (!dir.equals(path_c_original)) {
                auxiliar.add(dir.substring(dir.lastIndexOf(p.getFileSystem().getSeparator())));
                dir = path_fitxer.substring(0, dir.lastIndexOf(p.getFileSystem().getSeparator()));
            }
            for (int i = auxiliar.size()-1; i >=0; --i) {
                new_dir = new_dir + auxiliar.get(i);
                File dire = new File(new_dir);
                dire.mkdir();
            }
        }
        else new_dir= path_desti;
        return new_dir;
    }

    /**\brief Crea Directori
     \pre Cert
     \post Crea un directori siutat al path path_carpeta. Retorna false si no s'ha pogut crear o true si s'ha completat la creació.
     */
    private boolean crea_dir_desc(String path_carpeta, boolean force){
        File dire = new File(path_carpeta);
        boolean b= dire.mkdir();
        if(!b && !force) return false;
        else if(!b && force){
            dire.delete();
            dire.mkdir();
            return true;
        }
        return b;
    }

    /** \brief Update extensio
     \pre id ha de ser un id valid (LZ78, LZSS, LZW, JPEG)
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

    /** \brief Buscar i llegir arxiu
     \pre Cert
     \post Retorna un objecte que representa la estructura de dades necessària per a la compressió o descompressió d'un arxiu.
     */
    private byte[] buscar_leer_archivo(String path_og) throws IOException {
        File file = new File (path_og);
        return Files.readAllBytes(file.toPath());
    }

    /**\brief Obtenció de noms.
     \pre path_o és un path vàlid
     \post retorna el nom del fitxer amb path igual a "path_o".
     */
    private void nombre_fichero(String Path_og){
        Path p = Paths.get(Path_og);
        String aux = p.getFileName().toString();
        int pos = aux.lastIndexOf(".");
        nom_fitxer=aux.substring(0, pos);
    }

    /**\brief Transformació de dades.
     \pre cert
     \post transforma el enter que entra per parametre en una llista de bytes.
     */
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

    /**\brief Obtenció d'extensió
     \pre path_og és un path vàlid
     \post actualitza la variable global extensio depenent de l'extensio de path_og.
     */
    private void id_ex_desc(String path_og){
        String aux= path_og.substring(path_og.length()-2);
        if (aux.equals("fG")) extensio = ".ppm";
        else extensio = ".txt";

    }
    /**\brief Transformació de dades.
     \pre cert
     \post transforma la llista de bytes que entra per parametre en un enter.
     */
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


    /**\brief Transformació d'imatges.
     \pre cert
     \post transforma el vector de bytes passat per paràmetre en un arxiu jpeg.
     */
    private BufferedImage ppm(int width, int height, int maxcolval, byte[] data){
        if(maxcolval<256){
            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            int r,g,b,k=0,pixel;
            if(maxcolval==255){                                      // don't scale
                for(int y=0;y<height;y++){
                    for(int x=0;(x<width)&&((k+3)<data.length);x++){
                        r=data[k++] & 0xFF;
                        g=data[k++] & 0xFF;
                        b=data[k++] & 0xFF;
                        pixel=0xFF000000+(r<<16)+(g<<8)+b;
                        image.setRGB(x,y,pixel);
                    }
                }
            }
            else{
                for(int y=0;y<height;y++){
                    for(int x=0;(x<width)&&((k+3)<data.length);x++){
                        r=data[k++] & 0xFF;r=((r*255)+(maxcolval>>1))/maxcolval;  // scale to 0..255 range
                        g=data[k++] & 0xFF;g=((g*255)+(maxcolval>>1))/maxcolval;
                        b=data[k++] & 0xFF;b=((b*255)+(maxcolval>>1))/maxcolval;
                        pixel=0xFF000000+(r<<16)+(g<<8)+b;
                        image.setRGB(x,y,pixel);
                    }
                }
            }
            return image;
        }
        else{
            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            int r,g,b,k=0,pixel;
            for(int y=0;y<height;y++){
                for(int x=0;(x<width)&&((k+6)<data.length);x++){
                    r=(data[k++] & 0xFF)|((data[k++] & 0xFF)<<8);r=((r*255)+(maxcolval>>1))/maxcolval;  // scale to 0..255 range
                    g=(data[k++] & 0xFF)|((data[k++] & 0xFF)<<8);g=((g*255)+(maxcolval>>1))/maxcolval;
                    b=(data[k++] & 0xFF)|((data[k++] & 0xFF)<<8);b=((b*255)+(maxcolval>>1))/maxcolval;
                    pixel=0xFF000000+(r<<16)+(g<<8)+b;
                    image.setRGB(x,y,pixel);
                }
            }
            return image;
        }
    }

    /**\brief Comprovació de path.
     \pre cert
     \post comprova que el path pasat existeixi.
     */
    public boolean path_valid(String path){
        File file= new File(path);
        return file.exists();
    }

    /**\brief Obtenció de noms.
     \pre path_o és un path vàlid
     \post retorna el nom de la carpeta amb path igual a "path_o".
     */
    private String get_nom_carpeta(String Path_o){
        Path p = Paths.get(Path_o);
        int pos = Path_o.lastIndexOf(p.getFileSystem().getSeparator());
        String aux= Path_o.substring(pos,Path_o.lastIndexOf("."));
        return aux;
    }

    /**\brief Actualització carpeta descomprimida.
     \pre path és un path vàlid
     \post esborra el fitxer amb path igual a "path" i crea un fitxer igual amb l'extensió .FG.
     */
    public String all_jpeg(String path, List<String> l_aux) throws IOException {
        for(int i=0; i < l_aux.size(); ++i){
            if(!(l_aux.get(i).substring(l_aux.get(i).lastIndexOf(".")).equals(".ppm"))) return path;
        }
        String aux= path.substring(0,path.lastIndexOf("."));
        aux = aux + ".FG";
        path_absoluto= aux;
        return aux;

    }

    /**\brief Obtenció d'algoritme.
     \pre path_o és un path vàlid
     \post retorna el id de l'algorisme detectat.
     */
    public String getAlgoritme(String path_o, Map<String,List<String>> m_aux) {
        String aux = path_o.substring(path_o.length() - 2);
        if(m_aux.get("LZW").contains(aux)) return "LZW";
        else if(m_aux.get("LZSS").contains(aux)) return "LZSS";
        else if(m_aux.get("LZ78").contains(aux)) return "LZ78";
        else if(m_aux.get("JPEG").contains(aux)) return "JPEG";
        return null;
    }

    /**\brief Obtenció de la extensió.
     \pre id_s és un id vàlid.
     \post retorna el id de l'algorisme detectat.
     */
    public String get_Ex(String id_s) {
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

}