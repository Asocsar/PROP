

package Algoritmes.JPEG;
import Algoritmes.JPEG.Huffman.HuffmanTables;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class JPEG {

    //Matriu de quantificació de la luminància, forma part de la part lossy.
    private final int[][] Q = {{16, 11, 12, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 110, 103, 99}};

    //Matriu de quantificació de la crominància, forma part de la part lossy.
    private static final int[][] QC = {{17, 18, 24, 47, 99, 99, 99, 99},
            {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 26, 56, 99, 99, 99, 99, 99},
            {47, 66, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}};

    private int[][] Q2 = new int[8][8];

    //Matriu per fer el reccoregut en ZigZag sobre cada blocde 8x8.
    private static final int[][] ZigZag = {
            {0, 0},
            {0, 1}, {1, 0},
            {2, 0}, {1, 1}, {0, 2},
            {0, 3}, {1, 2}, {2, 1}, {3, 0},
            {4, 0}, {3, 1}, {2, 2}, {1, 3}, {0, 4},
            {0, 5}, {1, 4}, {2, 3}, {3, 2}, {4, 1}, {5, 0},
            {6, 0}, {5, 1}, {4, 2}, {3, 3}, {2, 4}, {1, 5}, {0, 6},
            {0, 7}, {1, 6}, {2, 5}, {3, 4}, {4, 3}, {5, 2}, {6, 1}, {7, 0},
            {7, 1}, {6, 2}, {5, 3}, {4, 4}, {3, 5}, {2, 6}, {1, 7},
            {2, 7}, {3, 6}, {4, 5}, {5, 4}, {6, 3}, {7, 2},
            {7, 3}, {6, 4}, {5, 5}, {4, 6}, {3, 7},
            {4, 7}, {5, 6}, {6, 5}, {7, 4},
            {7, 5}, {6, 6}, {5, 7},
            {6, 7}, {7, 6},
            {7, 7}};

    private HashMap<String,String> HuffmanTables = new HashMap<String,String>() {{
        put("0,0", "1010");
        put("0,1", "00");put("0,2", "01");put("0,3", "100");put("0,4", "1011");put("0,5", "11010");
        put("0,6", "1111000");put("0,7", "11111000");put("0,8", "1111110110");put("0,9", "1111111110000010");put("0,10", "1111111110000011");
        put("1,1", "1100");put("1,2", "11011");put("1,3", "1111001");put("1,4", "111110110");put("1,5", "11111110110");
        put("1,6", "1111111110000100");put("1,7", "1111111110000101");put("1,8", "1111111110000110");put("1,9", "1111111110000111");put("1,10","1111111110001000");
        put("2,1", "11100");put("2,2", "11111001");put("2,3", "1111110111");put("2,4", "111111110100");put("2,5", "1111111110001001");
        put("2,6", "1111111110001010");put("2,7", "1111111110001011");put("2,8", "1111111110001100");put("2,9", "1111111110001101");put("2,10","1111111110001110");
        put("3,1", "111010");put("3,2", "111110111");put("3,3", "111111110101");put("3,4", "1111111110001111");put("3,5", "1111111110010000");
        put("3,6", "1111111110010001");put("3,7", "1111111110010010");put("3,8", "1111111110010011");put("3,9", "1111111110010100");put("3,10","1111111110010101");
        put("4,1", "111011");put("4,2", "1111111000");put("4,3", "1111111110010110");put("4,4", "1111111110010111");put("4,5", "1111111110011000");
        put("4,6", "1111111110011001");put("4,7", "1111111110011010");put("4,8", "1111111110011011");put("4,9", "1111111110011100");put("4,10","1111111110011101");
        put("5,1", "1111010");put("5,2", "11111110111");put("5,3", "1111111110011110");put("5,4", "1111111110011111");put("5,5", "1111111110100000");
        put("5,6", "1111111110100001");put("5,7", "1111111110100010");put("5,8", "1111111110100011");put("5,9", "1111111110100100");put("5,10","1111111110100101");
        put("6,1", "1111011");put("6,2", "111111110110");put("6,3", "1111111110100110");put("6,4", "1111111110100111");put("6,5", "1111111110101000");
        put("6,6", "1111111110101001");put("6,7", "1111111110101010");put("6,8", "1111111110101011");put("6,9", "1111111110101100");put("6,10","1111111110101101");
        put("7,1", "11111010");put("7,2", "111111110111");put("7,3", "1111111110101110");put("7,4", "1111111110101111");put("7,5", "1111111110110000");
        put("7,6", "1111111110110001");put("7,7", "1111111110110010");put("7,8", "1111111110110011");put("7,9", "1111111110110100");put("7,10","1111111110110101");
        put("8,1", "111111000");put("8,2", "111111111000000");put("8,3", "1111111110110110");put("8,4", "1111111110110111");put("8,5", "1111111110111000");
        put("8,6", "1111111110111001");put("8,7", "1111111110111010");put("8,8", "1111111110111011");put("8,9", "1111111110111100");put("8,10","1111111110111101");
        put("9,1", "111111001");put("9,2", "1111111110111110");put("9,3", "1111111110111111");put("9,4", "1111111111000000");put("9,5", "1111111111000001");
        put("9,6", "1111111111000010");put("9,7", "1111111111000011");put("9,8", "1111111111000100");put("9,9", "1111111111000101");put("9,10", "1111111111000110");
        put("10,1", "111111010");
    }};

    private HashMap<String, String> mapInversed = new HashMap<String, String>(){{
        for (HashMap.Entry<String, String> entry : HuffmanTables.entrySet()) put(entry.getValue(),entry.getKey());
    }};

    //Qualitat de la compressió
    private int quality;

    //temps de l'ultima compressió / descompressió
    private double time;


    //rati de compressió assolit en la última compressió
    private double rate;


    //PRE: Cert
    //POST: Inicialitza la classe JPEG
    // Descripció: Crea una instància de classe JPEG i inicialitza variable time i rate
    public JPEG( int quality ){
        this.quality = quality;
        this.time = 0.0;
        this.rate = 0.0;
    }

    // Pre : Cert
    // Post: Retorna l'últim temps de compressió.
    public double getTime () {return  this.time;}

    // Pre : Certs
    // Post: Retorna del ratio de compressió
    public double getRate () {return  this.rate;}

    //Funció que aplica la transformada discreta del cosinus
    // Es l'aplicació per definició, és una sub-funció de compress8.
    private double[][] dct(int[][] m) {

        double[][] dct = new double[8][8];
        double au, av, sum;
        int u, v, x, y, val;

        for (u = 0; u < 8; ++u) {
            for (v = 0; v < 8; ++v) {

                au = (u == 0) ? 1 / Math.sqrt(2) : 1;
                av = (v == 0) ? 1 / Math.sqrt(2) : 1;

                sum = 0;
                for (x = 0; x < 8; ++x) {
                    for (y = 0; y < 8; ++y) {
                        val = m[x][y] - 128;
                        sum += val * Math.cos(((2 * x + 1) * u * Math.PI) / 16) * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                    }
                }
                dct[u][v] = 0.25 * au * av * sum;
            }
        }
        return dct;
    }


    //Funció que aplica la transforma discreta del cosinus de Tipus -II
    //O també anomenada inversa, subfunció de decompress8
    private int[][] idct(int[][] m) {

        int[][] idct = new int[8][8];
        double au, av, sum;
        int u, v, x, y;

        for (x = 0; x < 8; ++x) {
            for (y = 0; y < 8; ++y) {

                sum = 0;

                for (u = 0; u < 8; ++u) {
                    for (v = 0; v < 8; ++v) {
                        au = (u == 0) ? 1 / Math.sqrt(2) : 1;
                        av = (v == 0) ? 1 / Math.sqrt(2) : 1;
                        sum += m[u][v] * au * av * Math.cos(((2 * x + 1) * u * Math.PI) / 16) * Math.cos(((2 * y + 1) * v * Math.PI) / 16);
                    }
                }
                idct[x][y] = (int) Math.round(0.25 * sum);
            }
        }


        for (x = 0; x < 8; x++) {
            for (y = 0; y < 8; y++) {
                if (idct[x][y] > 127) idct[x][y] = 255;
                else idct[x][y] += 128;
            }
        }

        return idct;
    }

    private void computeQ2(int quality, boolean chroma){

        double s;
        if (quality < 50) s = 5000.0/quality;
        else s = 200 - 2*quality;

        for(int x = 0; x < 8; ++x){
            for(int y = 0; y < 8; ++y){
                if(chroma) Q2[x][y] = (int) Math.floor((s * QC[x][y] + 50) / 100);
                else Q2[x][y] = (int) Math.floor((s * Q[x][y] + 50) / 100);
                if(Q2[x][y] == 0) Q2[x][y] = 1; //Per evitar dividir entre 0
            }
        }
    }

    //Excepció que salta quan un fitxer introduit per a comprimir no és de tipus correcte
    public  class JPEGException extends Exception {
        public JPEGException (String message) {
            super(message);
        }
    }


    // Pre : m conté una matriu de 8x8 corresponent a un bloc a la imatge.
    // El contigut de m són enters.
    // Post: Retorna una llista de ints que representa la codificació amb RLE.
    // Descripció: La compressió bloc a bloc està feta per aquesta funció, aplica la
    //transformació DCT, la quantització i l'encoding fet amb RLE.
    // Per aconseguir l'encoding de RLE, ho guardem en una llista
    //i després ho passem a un array de ints
    public byte[] compress8(int[][] m) {


        //DCT Transform
        double[][] D = this.dct(m);

        //Quantitzation
        int[][] B = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                B[i][j] = (int) Math.min(Math.max(-127, Math.round(D[i][j] / (double) Q2[i][j])), 128);
            }
        }

        //Encoding ZigZag (RLE)
        ArrayList<String> list = new ArrayList<>();
        String s;
        StringBuilder sb = new StringBuilder();
        int count = 0, curr, num, mask;
        String st;
        //curr = B[0][0]; //Tractament DCT

        for (int i = 0; i < 64; i++) { //Canviar 0 per 1 per a tractar different DC de AC coefficients
            curr = B[ZigZag[i][0]][ZigZag[i][1]];
            //System.out.println("Curr: " + curr);
            if (curr == 0 && count < 9) count++;
            else {

                st = Integer.toBinaryString(Math.abs(curr));
                mask = st.length();
                if(curr < 0) st = Integer.toBinaryString(--curr).substring(32 -mask);
                if(HuffmanTables.get(count+ "," + mask) == null) System.out.println("curr: " +curr +", count: " + count + " ,mask: " + mask);
                sb.append(HuffmanTables.get(count+ "," + mask)).append(st);
                count = 0;
            }
        }

        //We have to add the DC coefficient and the 63 other values
        //With RLE and Hufmann (RUNLENTH, SIZE) (AMPLITUDE)

        return new BigInteger(sb.toString(), 2).toByteArray();
    }

    // Pre : Cert
    // Post: Retorna una matriu de mida 8x8 que representa el sub-bloc original.
    // Descripció: Es fa la conversió de l'array d'enters a un bloc mitjançant l'aplicació
    //del decodint del RLE, la desquantització i la DCT inversa.
    // A RLE c es el Nint [0..63] inici i count són les repeticions del caràcter curr
    public int[][] decompress8(byte[] b) {

        //Decoding ZigZag (RLE)
        String[] RS;
        int[][] B = new int[8][8];
        int c = 0, nbytes, count, z;
        long l;
        String numbin, se, mask = "11111111111111111111111111111111";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            String runsize = mapInversed.get(sb.append(s.charAt(i)).toString());
            if(runsize != null){
                //System.out.println(sb.toString() + " " + runsize);
                RS = runsize.split(",");
                count = Integer.parseInt(RS[0]);
                nbytes = Integer.parseInt(RS[1]);

                for(int j= 0; j < count; ++j)  B[ZigZag[c + j][0]][ZigZag[c + j][1]] = 0;
                c+= count;


                numbin = s.substring(i+1, i+1 + nbytes);
                //System.out.println(numbin);
                if(numbin.charAt(0) == '1') z = Integer.parseInt(numbin, 2);
                else{
                    se = mask.substring(numbin.length()) + numbin;
                    l = Long.parseLong(se, 2) + 1;
                    z = (int) l;
                }

                //System.out.println( "Posició c: " +c + " " + z);
                B[ZigZag[c][0]][ZigZag[c][1]] = z;
                sb = new StringBuilder();
                i += nbytes;
                ++c;
            }

        }


        int[][] D = new int[8][8];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                D[i][j] = B[i][j] * Q2[i][j];
            }
        }

        //DCT Transform
        return idct(D);


    }



    // Pre : Cert
    // Post: Retorna una matriu de Integers que representa el fitxer comprimit.
    // Descripció: Hi han 3 canals, Y, Cb i Cr. Per cada canal guardem una array amb
    //tantes posicions com NBlocs hi han. I per cada NBlock guardem el
    //resultat codificat, que té longitud variable segons la compressió que
    //aconseguim. De padding utilitzem repetició el caràcter si posx o posy són mes grans que la imatge
    public byte[] compress(byte[] b) throws JPEGException, IOException {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        StringBuilder sb = new StringBuilder();
        byte[] bs;
        int height = 0, width = 0, Bheight, Bwidth, length, posx, posy, it;

        if((char)b[0] != 'P' || (char)b[1] != '6') throw new JPEGException("Format de fitxer no suportat ");
        it = 3;
        char c = (char) b[it];
        StringBuilder str = new StringBuilder();
        while(c == '#'){
            while(c != '\n') c = (char) b[++it]; // Skip comentaris
            c = (char) b[++it];
        }


        while( c != '\n'){
            if(c != ' ') str.append(c);
            else {
                width = Integer.parseInt(str.toString());
                str = new StringBuilder();
            }
            c = (char) b[++it];
        }

        height = Integer.parseInt(str.toString());
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);

        sb.append(width).append(' ').append(height).append('\n');
        output.write(sb.toString().getBytes());
        sb = new StringBuilder();
        sb.append(this.quality).append('\n');
        output.write(sb.toString().getBytes());


        int[][][] YCbCr = new int[3][height][width];
        //Read ints
        int red, green, blue;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                red = b[++it];
                green = b[++it];
                blue = b[++it];
                YCbCr[0][i][j] = (int) (0.299 * red + 0.587 * green + 0.114 * blue);
                YCbCr[1][i][j] = (int) (128 - 0.1687 * red - 0.3313 * green + 0.5 * blue);
                YCbCr[2][i][j] = (int) (128 + 0.5 * red - 0.4187 * green - 0.0813 * blue);
            }
        }


        long start = System.currentTimeMillis(); //Comença a comptar el temps
        System.out.println("Comença la compressió");

        long mida = 0;
        int[][] m = new int[8][8];
        String s;

        Bheight = (height % 8 == 0) ? height / 8 : height / 8 + 1;
        Bwidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;

        for (int a = 0; a < 3; ++a) {
            if(a < 2) computeQ2(quality, a != 0);

            for (int i = 0; i < Bheight; ++i) {
                for (int j = 0; j < Bwidth; ++j) {
                    /* int[][] m = new int[8][8]; Línia treta per estalviar reiniciar matriu cada cop */
                    for (int y = 0; y < 8; ++y) {
                        for (int x = 0; x < 8; ++x) {

                            posx = j * 8 + x;
                            posy = i * 8 + y;
                            if (posx >= width) m[y][x] = m[y][x - 1]; //Repeteix últim pixel de padding
                            else if (posy >= height) m[y][x] = m[y - 1][x]; //Repeteix últim pixel de padding
                            else m[y][x] = YCbCr[a][posy][posx];

                        }
                    }


                    bs = compress8(m);
                    mida += bs.length;
                    //System.out.println("String: " + s);
                    output.write(bs);

                }
            }

        }

        long end = System.currentTimeMillis();
        this.time = (end - start) / 1000F;
        this.rate = 1 - (double) mida /(double) (height * width + 1);

        return output.toByteArray();
    }

    //Pre: Cert
    //Post: Retorna una matriu de ints que representa la imatge original.
    //Descripció: Fa el procés de decompressió general, és a dir que reconstrueix la
    //imatge bloc a bloc, llegint els buffers de longitud variable i
    //descomprimint-los obté la submatriu que afegeix q la matriu
    //general.
    public byte[] descompress(byte[] b) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int height, width = 0, quality, it = 0;
        //Decodificar height, width i quality a partir del buffer de bytes
        StringBuilder sb = new StringBuilder();
        char c = (char) b[it];
        System.out.println(c);
        while( c != '\n'){
            if(c != ' ') sb.append(c);
            else {
                width = Integer.parseInt(sb.toString());
                sb = new StringBuilder();
            }
            c = (char) b[++it];
        }
        height = Integer.parseInt(sb.toString());
        sb = new StringBuilder();
        c = (char) b[++it];
        while( c != '\n') {
            sb.append(c);
            c = (char) b[++it];
        }
        quality = Integer.parseInt(sb.toString());

        System.out.println("Comença la descompressió");


        int[][][] YCbCr = new int[3][height][width];
        int Bheight = (height % 8 == 0) ? height / 8 : height / 8 + 1;
        int Bwidth = (width % 8 == 0) ? width / 8 : width / 8 + 1;
        int posx, posy;
        int[][] m;

        for(int a = 0; a < 3; a++) {
            if(a < 2) computeQ2(quality, a != 0);

            for (int i = 0; i < height; i+=8) {
                for(int j = 0; j < width; j+=8) {
                    //System.out.println("Nou bloc");

                    while( Diferent de EOB){
                        out.write(b[++it]);
                    }
                    m = decompress8(out.toByteArray());

                    for (int y = 0; y < 8; ++y) {
                        for (int x = 0; x < 8; ++x) {
                            posx = j + x;
                            posy = i + y;
                            //System.out.println("posy: " + posy + " " + "posx: " + posx);
                            if (posx < width && posy < height) YCbCr[a][posy][posx] = m[y][x];
                        }
                    }
                }
            }
        }

        sb  = new StringBuilder();
        out = new ByteArrayOutputStream();
        sb.append("P6\n");
        sb.append(width).append(" ").append(height).append("\n");
        sb.append("255\n");
        out.write(sb.toString().getBytes());

        //YCbCr to RGB and write
        int red,green,blue;
        double y, cb, cr;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {

                y = (YCbCr[0][i][j] * 298.082) / 256;
                cb = YCbCr[1][i][j];
                cr = YCbCr[2][i][j];

                red = (int) (y + 1.40200 * (cr - 0x80));
                green = (int) (y - 0.34414 * (cb - 0x80) - 0.71414 * (cr - 0x80));
                blue = (int) (y + 1.77200 * (cb - 0x80));

                red = Math.max(Math.min(red, 255), 0);
                green = Math.max(Math.min(green, 255), 0);
                blue = Math.max(Math.min(blue, 255), 0);

                out.write(red);
                out.write(green);
                out.write(blue);

            }
        }

        return out.toByteArray();
    }

}