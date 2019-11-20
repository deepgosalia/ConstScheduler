import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//class HeapNode {
//
//    int bldg_no, executed_time, total_time;
//    RedBlack ptr;
//
//    HeapNode(int bldg_no, int executed_time, int total_time) {
//        this.bldg_no = bldg_no;
//        this.total_time = total_time;
//        this.executed_time = executed_time;
//        this.ptr = null;
//    }
//
//}

public class Heap {


    private List<Node> list;   //use linkedList for faster operation??

    public static void main(String[] args) {
        Heap heap = new Heap();
        heap.execute();

    }

    public void execute() {
        list = new ArrayList<>();
//        insert(1, 5, 10);
//        insert(2, 4, 10);
//        insert(3, 4, 10);
//        insert(4, 6, 10);
//        insert(5, 3, 10);
//        removeMin();
       // print_heap();
        //removeMin();
        //print_heap();
    }

    private void insert(Node heapNode) {
        //HeapNode heapNode = new HeapNode(bldg_no, exec_time, total_time);
        list.add(heapNode);
        heapify_up();  //check if insert takes place from the bottom

    }


    private void removeMin() {
        if (list.size() == 0) {
            System.out.println("Min Cannot be removed");
            return;
        }
        heapify_down();
    }

    // below function will handle both the duplicate as well as unique elements
    private boolean check_duplicate(int child, int parentIndex) {
        if (list.get(child).executed_time == list.get(parentIndex).executed_time) {
            return list.get(child).bldg_no < list.get(parentIndex).bldg_no;
        } else return list.get(child).executed_time < list.get(parentIndex).executed_time;
    }

    private void heapify_down() {
        if (isEmpty() || list.size() == 1) {
            return;
        }
        // replace with the last node
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        /* now start checking with children till we reach the last node
        Leaf nodes wont have any children so they will return -1
        * */
        int parentIndex = 0;  // 0-> start from the root
        int leftChild = getLeftChild(parentIndex);
        int rightChild = getRightChild(parentIndex);
        while (leftChild != -1 || rightChild != -1) {
            if (leftChild != -1 && check_duplicate(leftChild, parentIndex)) {
                Collections.swap(list, leftChild, parentIndex);
                parentIndex = leftChild;
            } else if (rightChild != -1 && check_duplicate(rightChild, parentIndex)) {
                Collections.swap(list, rightChild, parentIndex);
                parentIndex = rightChild;
            } else {
                //means it is at correct position and no need to go ahead
                break;
            }

            leftChild = getLeftChild(parentIndex);
            rightChild = getRightChild(parentIndex);
        }
    }


    private void heapify_up() {
        if (list.size() == 1) {
            return;   //nothing to do, we can simply return
        }
        int insert_index = list.size() - 1;
        int parent_index = getParent(insert_index);
        while (parent_index != -1 && check_duplicate(insert_index, parent_index)) {
            // swap the values
            Collections.swap(list, insert_index, parent_index);
            // for next iteration
            insert_index = parent_index;
            parent_index = getParent(insert_index);  // return -1 if it is at the root
        }
    }


    private void print_heap() {
        for (Node heapNode : list) {
            System.out.println(heapNode.bldg_no + "->" + heapNode.executed_time);
        }

    }

    private int getLeftChild(int parentIndex) {
        int childIndex = 2 * parentIndex + 1;
        if (childIndex > list.size() - 1) {
            return -1;
        } else {
            return childIndex;
        }
    }

    private int getRightChild(int parentIndex) {
        int childIndex = 2 * parentIndex + 2;
        if (childIndex > list.size() - 1) {
            return -1;
        } else {
            return childIndex;
        }
    }

    private int getParent(int childIndex) {
        if (childIndex <= 0) {
            return -1;
        }

        return (int) Math.ceil(childIndex / 2.0) - 1;
    }

    private int getMin() {
        if (list.size() > 0) {
            return list.get(0).executed_time;
        }
        return -1;
    }


    private boolean isEmpty() {
        return list.size() == 0;
    }

}
