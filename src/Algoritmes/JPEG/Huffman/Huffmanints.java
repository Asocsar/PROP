package Algoritmes.JPEG.Huffman;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;



class Node
{
    int num;
    int frequencia;
    Node left, right;

    public Node(int num, int frequencia, Node left, Node right) {
        this.num = num;
        this.frequencia = frequencia;
        this.left = left;
        this.right = right;
    }
}

public class Huffmanints {

    // traverse the Huffman Tree and store Huffman Code in a map.
    public static void encode(Node root, String str,
                              Map<Integer, String> huffmanCode) {
        if (root == null)
            return;

        // found a leaf node
        if (root.left == null && root.right == null) {
            huffmanCode.put(root.num, str);
        }
        encode(root.left, str + "0", huffmanCode);
        encode(root.right, str + "1", huffmanCode);
    }

    // traverse the Huffman Tree and decode the encoded string
    public static int decode(Node root, int index, StringBuilder sb) {
        if (root == null) return index;

        // found a leaf node
        if (root.left == null && root.right == null) {
            System.out.print(root.num + " ");
            return index;
        }

        index++;
        return (sb.charAt(index) == '0') ? decode(root.left, index, sb) : decode(root.right, index, sb);

    }


    // Builds Huffman Tree and huffmanCode and decode given input text
    public static void buildHuffmanTree(int[] buf) {
        // count frequency of appearance of each character
        // and store it in a map
        Map<Integer, Integer> Mfrequencia = new HashMap<>();
        for (int item : buf) Mfrequencia.merge(item, 1, Integer::sum);

        //Highest priority item has lowest frequency
        PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingInt(l -> l.frequencia)
        );

        // Create a leaf node for each character and add it
        // to the priority queue.
        for (Map.Entry<Integer, Integer> entry : Mfrequencia.entrySet())
            pq.add(new Node(entry.getKey(), entry.getValue(), null, null));


        // do till there is more than one node in the queue
        while (pq.size() != 1) {
            // Remove the two nodes of highest priority
            // (lowest frequency) from the queue
            Node left = pq.poll();
            Node right = pq.poll();

            // Create a new internal node with these two nodes as children
            // and with frequency equal to the sum of the two nodes
            // frequencies. Add the new node to the priority queue.
            int sum = left.frequencia + right.frequencia;
            pq.add(new Node('\0', sum, left, right));
        }

        // root stores pointer to root of Huffman Tree
        Node root = pq.peek();

        // traverse the Huffman tree and store the Huffman codes in a map
        Map<Integer, String> huffmanCode = new HashMap<>();
        encode(root, "", huffmanCode);

        // print the Huffman codes
        System.out.println("Huffman Codes are :\n");
        for (Map.Entry<Integer, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("\nOriginal string was : ");
        for (int i : buf) System.out.printf("%d ", i);
        System.out.println();

        // print encoded string
        StringBuilder sb = new StringBuilder();
        for (int value : buf) sb.append(huffmanCode.get(value));


        System.out.println("\nEncoded string is :\n" + sb);

        // traverse the Huffman Tree again and this time
        // decode the encoded string
        int index = -1;
        System.out.println("\nDecoded string is: \n");
        while (index < sb.length() - 2) {
            index = decode(root, index, sb);
            //Append index to byte array[]
        }

    }


    public static void main(String[] args) {
        int[] buf = {1, 13, 1, -1, 1, 1, 1, -2, 1, 0, 1, 1, 1, 0, 1, -1, 1, 1, 1, -1, 2, 0, 3, -1};

        buildHuffmanTree(buf);
    }
}
