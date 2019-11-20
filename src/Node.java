public class Node {
    Color color;
    int bldg_no, executed_time, total_time;
    Node leftChild;
    Node rightChild;
    Node parent; // pointer to parent  easier for re balancing as we go up
    Node ptr;

    Node(Color c, int bldg_no, int executed_time, int total_time, Node initializer) {
        this.color = c;
        this.bldg_no = bldg_no;
        this.leftChild = initializer;
        this.rightChild = initializer;
        this.parent = initializer;
        this.total_time = total_time;
        this.executed_time = executed_time;
        this.ptr = null;
    }
}
