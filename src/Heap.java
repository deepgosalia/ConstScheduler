import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Heap {
    private static List<Integer> list;   //use linkedList for faster operation??
    public static void main(String[] args) {
        list = new ArrayList<>();
        insert(5);
        print_heap();
        insert(4);
        print_heap();
        insert(6);
        print_heap();
        insert(10);
        print_heap();
        insert(1);
        print_heap();
        removeMin();
        print_heap();

    }
    private static void insert(int nodeValue){
        list.add(nodeValue);
        heapify_up();  //check if insert takes place from the bottom

    }
    private static void removeMin(){
        if(list.size()==0){
            System.out.println("Min Cannot be removed");
            return;
        }
        heapify_down();
    }

    private static void heapify_down() {
        if(isEmpty() || list.size()==1){
            return;
        }
        // replace with the last node
        list.set(0,list.get(list.size()-1));
        list.remove(list.size()-1);
        /* now start checking with children till we reach the last node
        Leaf nodes wont have any children so they will return -1
        * */
        int parentIndex = 0;  // 0-> start from the root
        int leftChild = getLeftChild(parentIndex);
        int rightChild = getRightChild(parentIndex);
        while(leftChild != -1 || rightChild != -1){
            System.out.println("P" + parentIndex + " L" +leftChild + " R" +rightChild);
            if(leftChild!= -1 && list.get(leftChild) < list.get(parentIndex)){
                System.out.println(leftChild);
                Collections.swap(list,leftChild,parentIndex);
                parentIndex = leftChild;
            }else if(rightChild!= -1 && list.get(rightChild) < list.get(parentIndex)){
                System.out.println(rightChild);
                Collections.swap(list,rightChild,parentIndex);
                parentIndex = rightChild;
            } else{
                //means it is at correct position and no need to go ahead
                break;
            }

            leftChild = getLeftChild(parentIndex);
            rightChild = getRightChild(parentIndex);
        }
    }


    private static void heapify_up(){
        if(list.size()==1){
            return;   //nothing to do, we can simply return
        }
        int insert_index = list.size() - 1;
        int parent_index = getParent(insert_index);
        while(parent_index !=-1 && list.get(insert_index)<list.get(parent_index)){
            // swap the values
            Collections.swap(list,insert_index, parent_index);
            // for next iteration
            System.out.println(insert_index + " " + parent_index);
            insert_index = parent_index;
            parent_index = getParent(insert_index);  // return -1 if it is at the root
        }
    }


    private static void print_heap(){
        System.out.println(list);
    }
    private static int getLeftChild(int parentIndex){
        int childIndex = 2 * parentIndex + 1;
        if(childIndex>list.size()-1){
            return -1;
        }else{
            return childIndex;
        }
    }
    private static int getRightChild(int parentIndex){
        int childIndex = 2 * parentIndex + 2;
        if(childIndex>list.size()-1){
            return -1;
        }else{
            return childIndex;
        }
    }
    private static int getParent(int childIndex){
        if(childIndex<=0){
            return -1;
        }

        return (int)Math.ceil(childIndex/2.0)-1;
    }
    private static int getMin(){
        if(list.size()>0){
            return list.get(0);
        }
        return -1;
    }


    private static boolean isEmpty() {
        return list.size() == 0;
    }

}
