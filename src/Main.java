import java.io.*;

public class Main {
    private static String fileName = "input.txt";
    private static int day = 0;

    private static int input_count = 0;   // timer taken from the input  i.e 0, 2 ,5...
    private static String input_action;   //action-> print, insert, ...
    private static int[] input_parameter = new int[]{-1, -1}; // size is limited to 2 because for any given function there can be the at most 2 parameter, else we can use arraylist

    static boolean isConstructingBuilding = false;
    private static Heap heap;
    private static RedBlack redBlack;

    private static HeapNode currentTask;

    public static void main(String[] args) throws IOException {
        File file = new File("input.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file)); // TODO Handle exception
        String st = null;
        String[] inputLine = null;
        // initialize data structure
        heap = new Heap();
        redBlack = new RedBlack();

        while (true) {
            System.out.println("On Day " + day);
            if (st == null && (st = bufferedReader.readLine()) != null) {
                st = st.trim();// if there are ending whitespace then it will removed
                if (!st.equals("")) { //..if it is true then it   means that it will be at the blank line
                    inputLine = st.split(":|\\(|,|\\)"); // this is read as : OR ( OR.....  It will split to multiple delimiter
                    input_count = Integer.parseInt(inputLine[0].trim());
                }
//                else {
//                    heap.print_heap();
//                    redBlack.inorder_traversal(redBlack.getRoot());
//                    break;  // means EOF that is line after last line which is blank
//                }
            }

            if (st != null && day == input_count) { // if not null then we have some work to do and we are waiting for our count to reach that state
                // it is day when we need to perform action
                if (inputLine != null) {
                    performAction(inputLine);
                    st = null;  // mean ready to take next input
                } else {
                    // some building are yet to complete so let them perform
                }

            }

            if (currentTask != null) {
                currentTask.executed_time++;
                currentTask.ptr.executed_time++;  // for red black tree
                if (currentTask.executed_time == currentTask.total_time) {  // TODO can total time be zero??
                    // should we push it back and then print or simply print i
                    // remove from rbt
                    System.out.println(currentTask.bldg_no + " completed on " + day);
                    redBlack.delete(currentTask.ptr);
                    currentTask = null;
                    isConstructingBuilding = false;
                    // should the next construction start today or next day
                }
                //TODO should we update at every second
            }


            /**
             * Below we only pick the building and then from next day we start the construction
             * */
            if(!isConstructingBuilding && day!=0){

                currentTask = pickNextBuilding();      // pick next building and update current
                heap.print_heap();
                if (currentTask == null) {
                    //Means there is nothing to construct and heap is empty
                    break;
                }else {
                    System.out.println("Now starting: " + currentTask);
                }
            }

            if (day % 5 == 0) {  // either fifth day or previous task was completed
                if (day == 0) { // here we start working on the first construction
                    System.out.println("heap before");
                    heap.print_heap();
                    currentTask = startConstruction();
                    System.out.println("heap after");
                    if (currentTask == null) {
                        break;
                    }else{
                        heap.print_heap();
                        System.out.println("Now starting: " + currentTask);
                    }
                } else {
                    if (isConstructingBuilding) {  // if building is still constructing
                        System.out.println(currentTask.bldg_no + " was partially completed on day " + day);
                        System.out.println("heap before");
                        heap.print_heap();
                        currentTask = pickNextBuilding();      // pick next building and update current
                        System.out.println("heap after");
                        if (currentTask == null) {
                            //Means there is nothing to construct and heap is empty
                            break;
                        }else {
                            heap.print_heap();
                            System.out.println("Now starting: " + currentTask);
                        }
                    }
                }
            }
            day++;
        }
    }

    private static HeapNode pickNextBuilding() {
        //
        if (currentTask != null) {
            heap.insert(currentTask);  // push current building
        }
        return startConstruction();
    }

    private static HeapNode startConstruction() {
        currentTask = heap.removeMin();
        if (currentTask != null) {
            isConstructingBuilding = true;
            return currentTask;
        } else {
            //TODO
            return null;
        }
    }

    private static void performAction(String[] inputLine) {
        input_action = inputLine[1].trim().toLowerCase();  // to prevent conflict between Insert and insert
        input_parameter[0] = Integer.parseInt(inputLine[2].trim());
        if (inputLine.length > 3) {
            input_parameter[1] = Integer.parseInt(inputLine[3].trim());  // if there is a second parameter, will happen for insert and print range function
        }

        if (input_action.equals("insert")) {
            performInsert(input_parameter[0], input_parameter[1]);// bldg_no-->[0], total_time-->[1]
        } else if (input_action.equals("print") && inputLine.length > 3) {
            performPrintRange(input_parameter[0], input_parameter[1]);

        } else if (input_action.equals("print")) {
            performPrint(input_parameter[0]);
        }
    }

    private static void performInsert(int bldg_no, int total_time) {
        HeapNode heapNode = new HeapNode(bldg_no, 0, total_time);// executed time is 0 initially
        heap.insert(heapNode);

        RedBlackNode redBlackNode = new RedBlackNode(Color.RED, bldg_no, 0, total_time, redBlack.getNil()); // getNil for the external node which is taken as nil
        redBlack.insert(redBlackNode);

        // link both side
        heapNode.ptr = redBlackNode;
        redBlackNode.ptr = heapNode;
    }

    private static void performPrint(int bldg_no) {
        RedBlackNode redBlackNode = redBlack.findNode(bldg_no, redBlack.getRoot());
        if (redBlackNode == null) {
            System.out.println("No such element");
        } else {
            System.out.println("Print-> " + redBlackNode);
        }

    }


    private static void performPrintRange(int bldg_no1, int bldg_no2) {
        boolean result = redBlack.printRange(redBlack.getRoot(), bldg_no1, bldg_no2);   // check if false is given when nothing in range
        if (!result) {
            System.out.println("No elements exist in range");
        }
    }

}

