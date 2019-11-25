public class RedBlackNode {
    Color color;
    int bldg_no, executed_time, total_time;
    RedBlackNode leftChild;
    RedBlackNode rightChild;
    RedBlackNode parent; // pointer to parent  easier for re balancing as we go up
    HeapNode ptr;

    RedBlackNode(Color c, int bldg_no, int executed_time, int total_time, RedBlackNode initializer) {
        this.color = c;
        this.bldg_no = bldg_no;
        this.leftChild = initializer;
        this.rightChild = initializer;
        this.parent = initializer;
        this.total_time = total_time;
        this.executed_time = executed_time;
        this.ptr = null;
    }
    @Override
    public String toString(){
        return "("+this.bldg_no + ","+this.executed_time + "," + this.total_time + ")";
    }
}
