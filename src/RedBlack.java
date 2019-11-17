enum Color {
    RED, BLACK;
}

class Node {
    Color color;
    int value;
    Node leftChild;
    Node rightChild;
    Node parent; // pointer to parent  easier for re balancing as we go up

    Node(Color c, int value) {
        this.color = c;
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }
}

public class RedBlack {
    static Node root;

    public static void main(String[] args) {
        //RL
        insert(1);
        insert(4);
        insert(2);


        //LR
//        insert(3);
//        insert(1);
//        insert(2);
        //RR
//        insert(1);
//        insert(2);
//        insert(3);
        // LL
//        insert(5);
//        insert(2);
//        insert(1);
//        insert(0);
//        insert(-1);
//        insert(4);
//        insert(3);

        inorder_traversal(root);
        System.out.println(root.value);

    }

    // for testing
    private static void inorder_traversal(Node root) {
        if (root == null) {
            return;
        }
        inorder_traversal(root.leftChild);
        System.out.println(root.value + " -> " + root.color);
        inorder_traversal(root.rightChild);
    }

    private static void insert(int value) {

        /**
         * 1. Root is always black
         * 2. New Node is given RED
         */

        if (root == null) {
            root = new Node(Color.BLACK, value);
            return;
        }
        // find correct position of new node
        Node iterator = root;
        Node p, pp, gp;
        gp = null; // when inserted as the child of root
        while (true) {
            if (iterator.value < value) {
                if (iterator.rightChild == null) {
                    iterator.rightChild = new Node(Color.RED, value);
                    p = iterator.rightChild; //pointer to new node
                    p.parent = iterator;
                    break;
                } else {
                    iterator = iterator.rightChild;
                }

            } else if (iterator.value > value) {     //TODO add = for duplicate
                if (iterator.leftChild == null) {
                    iterator.leftChild = new Node(Color.RED, value);
                    p = iterator.leftChild; //pointer to new node
                    p.parent = iterator;
                    break;
                } else {
                    iterator = iterator.leftChild;
                }

            }
        }
        pp = p.parent;
        gp = pp.parent;  // gp could be null if insertion was at the child of root

        insert_balancing(p, pp, gp);
    }

    private static void insert_balancing(Node p, Node pp, Node gp) {

        if (checkIfConsecutiveRed(p, pp, gp)) {
            //perform XYz operation
            // check child of color of gp i.e. color of z
            Node d = getChildOfGrandParent(pp, gp);  // this will return our 'd' node
            Color c_d;
            if (d == null) {
                c_d = Color.BLACK;  // external node
            } else {
                c_d = d.color;
            }

            // XYr case:  // TODO is there a case of flip occuring, when we have only p pp
            if (c_d == Color.RED) {
                operationXYr(pp, gp, d);
                // now we test again for any possibility of unbalancing

            } else {
                //XYb case-> which has 4 possibility
                //LLb
                int relation1 = identifyRelation(gp, pp);
                int relation2 = identifyRelation(pp, p);
                System.out.println(relation1 + " " + relation2);

                if (relation1 == 1 && relation2 == 1) { //LLb

                    operationLLb(p, pp, gp);
                } else if (relation1 == 1 && relation2 == 0) { // LRb
                    operationLRb(p, pp, gp);
                } else if (relation1 == 0 && relation2 == 0) { //RRb
                    operationRRb(p, pp, gp);
                } else if (relation1 == 0 && relation2 == 1) { //RLb
                    operationRLb(p, pp, gp);
                }

            }
            // move 2 level up
            p = p.parent;
            if (p != null && p.parent != null) {
                p = p.parent;
            }

            if (p != null && !p.equals(root)) {
                pp = p.parent;
                if (pp != null && !pp.equals(root)) {
                    gp = p.parent;
                    insert_balancing(p, pp, gp);
                }
            }

        }
    }

    // this will tell what kind of relation it is L or R  1->L and 0 ->
    private static int identifyRelation(Node parent, Node child) {
        if (parent.leftChild != null && parent.leftChild.equals(child)) {
            return 1;
        }
        return 0;

    }

    private static void operationRLb(Node p, Node pp, Node gp) {
        //Here first perform right rotation and then left  LR = RR + LL
        pp.leftChild = p.rightChild; //b
        if (pp.leftChild != null) {
            pp.leftChild.parent = pp;
        }
        p.rightChild = pp;
        pp.parent = p;

        gp.rightChild = p;
        p.parent = gp;

        //Note: above p will act like pp and vice versa
        operationRRb(pp, p, gp);

    }

    private static void operationRRb(Node p, Node pp, Node gp) {
        gp.rightChild = pp.leftChild;  // c
        if (pp.leftChild != null) {
            pp.leftChild.parent = gp;
        }
        pp.leftChild = gp;
        if (gp.equals(root)) {
            root = pp;
            pp.parent = null;
        } else {
            // we need pointer which connects gp to its parent
            int relation = getParentChildPointer(gp.parent, gp); // L->1, R->0
            if (relation == 1) {
                gp.parent.leftChild = pp;
                pp.parent = gp.parent;
            } else {
                gp.parent.rightChild = pp;
                pp.parent = gp.parent;
            }

        }
        gp.parent = pp;

        flipColor(pp);
        flipColor(gp);
    }

    private static void operationLRb(Node p, Node pp, Node gp) {
        //Here first perform right rotation and then left  LR = RR + LL
        pp.rightChild = p.leftChild; //b
        if (pp.rightChild != null) {
            pp.rightChild.parent = pp;
        }
        p.leftChild = pp;
        pp.parent = p;

        gp.leftChild = p;
        p.parent = gp;

        //Note: above p will act like pp and vice versa
        operationLLb(pp, p, gp);

    }

    private static void operationLLb(Node p, Node pp, Node gp) {
        gp.leftChild = pp.rightChild;  // c
        if (pp.rightChild != null) {
            pp.rightChild.parent = gp;
        }
        pp.rightChild = gp;
        if (gp.equals(root)) {
            root = pp;
            pp.parent = null;
        } else {
            // we need pointer which connects gp to its parent
            int relation = getParentChildPointer(gp.parent, gp); // L->1, R->0
            if (relation == 1) {
                gp.parent.leftChild = pp;
                pp.parent = gp.parent;
            } else {
                gp.parent.rightChild = pp;
                pp.parent = gp.parent;
            }

        }
        gp.parent = pp;

        flipColor(pp);
        flipColor(gp);
    }

    private static int getParentChildPointer(Node parent, Node child) {
        if (parent.leftChild != null && parent.leftChild.equals(child)) {
            return 1;
        }
        return 0;
    }


    private static void operationXYr(Node pp, Node gp, Node d) {
//        if (!gp.equals(root)) {
//            flipColor(gp);   // we don't flip
//        }
        flipColor(gp);
        flipColor(d);
        flipColor(pp);
    }

    private static void flipColor(Node node) {
        if (node.equals(root)) {
            node.color = Color.BLACK;
            return;
        }
        if (node.color == Color.RED) {
            node.color = Color.BLACK;
            return;
        }
        node.color = Color.RED;
    }

    private static Node getChildOfGrandParent(Node pp, Node gp) {
        if (gp.leftChild == null || gp.rightChild == null) {
            return null;  //external node
        }
        if (gp.leftChild.equals(pp)) {
            return gp.rightChild;
        }
        return gp.leftChild;
    }

    private static boolean checkIfConsecutiveRed(Node p, Node pp, Node gp) {
        if (gp == null) {
            // it means that it is inserted as child of root and pp has to be black
            return false;
        }
        if (p.color == Color.RED && pp.color == Color.RED) {
            return true;
        }
        return false;
    }

    private static Color getColor(Node node) {
        return node.color;
    }

    private static void changeColor(Node node, Color c) {
        node.color = c;
    }


}
