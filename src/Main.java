import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    private static String fileName = "input.txt";
    //private static Node nil = new Node(Color.BLACK, -999, -99, -9, null);
    public static void main(String[] args) throws IOException {

//        File file = new File("input.txt");
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); // TODO Handle exception
//        String st;
//        while((st=bufferedReader.readLine())!=null){
//            st = st.trim();   // if there are ending whitespace then it will removed
//            if(!st.equals("")){
//                String[] inp = st.split(":");
//                System.out.println(inp[0]);
//                String[] temp = inp[1].split("\\(");
//                System.out.println(temp[0]);
//            }
//        }

        RedBlack redBlack = new RedBlack();
        Heap heap = new Heap();
        Node nil = redBlack.getNil();
        Node n1 = new Node(Color.RED,10,10,34,nil);
        Node n2 = new Node(Color.RED,2,2,34,nil);
        Node n3 = new Node(Color.RED,3,3,34,nil);
        Node n4 = new Node(Color.RED,4,4,34,nil);
        Node n5 = new Node(Color.RED,5,5,34,nil);
        Node n6 = new Node(Color.RED,6,10,34,nil);

        redBlack.insert(n1);
        redBlack.insert(n2);
        redBlack.insert(n3);
        redBlack.insert(n4);
        redBlack.insert(n5);
        redBlack.insert(n6);

        redBlack.inorder_traversal(redBlack.getRoot());



    }
}
