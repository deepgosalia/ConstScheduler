public class HeapNode {

    int bldg_no, executed_time, total_time;
    RedBlackNode ptr;

    HeapNode(int bldg_no, int executed_time, int total_time) {
        this.bldg_no = bldg_no;
        this.total_time = total_time;
        this.executed_time = executed_time;
        this.ptr = null;
    }

    @Override
    public String toString(){
        return this.bldg_no + ","+this.executed_time + "," + this.total_time;
    }
}
