import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Heap {
    private List<HeapNode> list;   // TODO  doubt---use linkedList for faster operation?? or use array

    Heap() {
        list = new ArrayList<>();
    }


    public void insert(HeapNode heapNode) {
        list.add(heapNode);
        heapify_up();  //insert takes place from the bottom to top
    }

    public HeapNode removeMin() {

        if (list.size() == 0) {
            System.out.println("Min Cannot be removed");
            return null;
        }else{
            HeapNode minNode = getMin();
            heapify_down();
            //System.out.println(minNode);  // TODO check if references  change
            return minNode;
        }
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public void print_heap() {
        System.out.println("heap size is " + list.size());
        for (HeapNode heapNode : list) {

            System.out.print("heap is "+heapNode.bldg_no + "->" + heapNode.executed_time + ", ");
        }

    }
    // below function will handle both the duplicate as well as unique elements


    private boolean check_duplicate(int child, int parentIndex) {
        if (list.get(child).executed_time == list.get(parentIndex).executed_time) {
            return list.get(child).bldg_no < list.get(parentIndex).bldg_no;
        } else return list.get(child).executed_time < list.get(parentIndex).executed_time;
    }

    private void heapify_down() {
        if (isEmpty()){
            return;
        }
        if(list.size() == 1){
            list.remove(0);
            return;
        }
        // replace with the last node
        list.set(0, list.get(list.size() - 1));
        list.remove(list.size() - 1);
        System.out.println("At heapify_down " + list);
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

    private HeapNode getMin() {
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
