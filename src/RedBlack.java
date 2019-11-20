//
//enum Color {
//    RED, BLACK;
//}
//
//class Node {
//
//}

public class RedBlack {


    private Node nil = new Node(Color.BLACK, -999, -99, -9, null);
    private Node root = nil;

    public static void main(String[] args) {

        RedBlack redBlack = new RedBlack();
        redBlack.execute();

    }

    public void execute() {
        //delete
//        this.insert(10);
//        this.insert(2);
//        this.insert(3);
//        this.insert(4);
//        this.insert(5);
//        this.insert(6);


    }

    public Node getNil(){
        return nil;
    }
    // for testing
    public void inorder_traversal(Node root) {
        if (root == nil) {
            return;
        }
        inorder_traversal(root.leftChild);
        System.out.println(root.parent.bldg_no + " " + root.bldg_no + " -> " + root.color);
        inorder_traversal(root.rightChild);
    }

    public Node getRoot(){
        return root;
    }

    public void rotateLeft(Node x) {
        Node y = x.rightChild;
        x.rightChild = y.leftChild;
        //move beta to x
        if (y.leftChild != nil) {
            //change beta parent to x
            y.leftChild.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == nil) {
            root = y;
            // means it is the root node
        } else {
            if (x == x.parent.leftChild) {
                // means it is the leftChild
                x.parent.leftChild = y;

            } else if (x == x.parent.rightChild) {
                // means it is rightChild
                x.parent.rightChild = y;

            }

        }
        // update y
        y.leftChild = x;
        x.parent = y;

    }


    public void rotateRight(Node x) {
        Node y = x.leftChild;
        x.leftChild = y.rightChild;
        //move beta to x
        if (y.rightChild != nil) {
            //change beta parent to x
            y.rightChild.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == nil) {
            root = y;
            // means it is the root node
        } else {
            if (x == x.parent.rightChild) {
                // means it is the leftChild
                x.parent.rightChild = y;

            }
            if (x == x.parent.leftChild) {
                // means it is rightChild
                x.parent.leftChild = y;

            }

        }
        // update y
        y.rightChild = x;
        x.parent = y;


    }

    private void deleteNode(int val) {
        Node deleted_node = findNode(val, root);
        if (deleted_node == null) {
            System.out.println("Node does not exist");
            return;
        }
        Node y = deleted_node;
        Color y_color = y.color;
        Node toBeFixed, replacement;
        // case 1-> when we have degree one node to be deleted
        if (deleted_node.leftChild == nil) {
            // even if we were at leaf then we will linking to null
            toBeFixed = deleted_node.rightChild;
            reLinkNodes(deleted_node, deleted_node.rightChild);
        } else if (deleted_node.rightChild == nil) {
            toBeFixed = deleted_node.leftChild;
            reLinkNodes(deleted_node, deleted_node.leftChild);
        } else {
            replacement = findInorderSuccessor(deleted_node.rightChild);
            y_color = replacement.color;
            // now we relink it with the parent of deletedNode
            toBeFixed = replacement.rightChild;
            if (replacement.parent == deleted_node) {
                toBeFixed.parent = replacement; // simply link it
            } else {
                // then we link the replacement's right child since left will be definitely null
                // this below procedure will link whatever subtree replacement had before deleting it
                reLinkNodes(replacement, replacement.rightChild);
                replacement.rightChild = deleted_node.rightChild;  // replacement will inherit whatever deleted node had
                replacement.rightChild.parent = replacement;  // it is like linking deleted node's right child
            }
            reLinkNodes(deleted_node, replacement); // final replacement
            replacement.leftChild = deleted_node.leftChild;
            deleted_node.leftChild.parent = replacement;
            replacement.color = deleted_node.color;
        }
        if (y_color == Color.BLACK) {
            deleteFix(toBeFixed);
        }
    }

    private void deleteFix(Node toBeFixed) {

        while (toBeFixed != root && toBeFixed.color == Color.BLACK) {

            if (toBeFixed == toBeFixed.parent.leftChild) {

                Node w = toBeFixed.parent.rightChild;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    toBeFixed.parent.color = Color.RED;
                    rotateLeft(toBeFixed.parent);
                    w = toBeFixed.parent.rightChild; // go up
                }

                if (w.leftChild.color == Color.BLACK && w.rightChild.color == Color.BLACK) {
                    w.color = Color.RED;
                    toBeFixed = toBeFixed.parent;

                    continue;        // TODO change this
                } else if (w.rightChild.color == Color.BLACK) {
                    w.leftChild.color = Color.BLACK;
                    w.color = Color.RED;
                    rotateRight(w);
                    w = toBeFixed.parent.rightChild;
                }
                if (w.rightChild.color == Color.RED) {
                    w.color = toBeFixed.parent.color;
                    toBeFixed.parent.color = Color.BLACK;
                    w.rightChild.color = Color.BLACK;
                    rotateLeft(toBeFixed.parent);
                    toBeFixed = root;  // no need to go further
                }

            } else {

                Node w = toBeFixed.parent.leftChild;
                if (w.color == Color.RED) {
                    w.color = Color.BLACK;
                    toBeFixed.parent.color = Color.RED;
                    rotateRight(toBeFixed.parent);
                    w = toBeFixed.parent.leftChild; // go up
                }

                if (w.rightChild.color == Color.BLACK && w.leftChild.color == Color.BLACK) {
                    w.color = Color.RED;
                    toBeFixed = toBeFixed.parent;
                    continue;
                } else if (w.leftChild.color == Color.BLACK) {
                    w.rightChild.color = Color.BLACK;
                    w.color = Color.RED;
                    rotateLeft(w);
                    w = toBeFixed.parent.leftChild;
                }
                if (w.leftChild.color == Color.RED) {
                    w.color = toBeFixed.parent.color;
                    toBeFixed.parent.color = Color.BLACK;
                    w.leftChild.color = Color.BLACK;
                    rotateRight(toBeFixed.parent);
                    toBeFixed = root;  // no need to go further
                }
            }

        }
        toBeFixed.color = Color.BLACK;
        // inorder_traversal(root);
    }

    private void reLinkNodes(Node deleted_node, Node replacement) {
        // means we are deleting root
        if (deleted_node.parent == nil) {
            root = replacement;
        } else if (deleted_node == deleted_node.parent.leftChild) {
            deleted_node.parent.leftChild = replacement;
        } else {
            deleted_node.parent.rightChild = replacement;
        }

        replacement.parent = deleted_node.parent;  // if it was root then it would be linked to null


    }

    private Node findInorderSuccessor(Node node) {
        // here we need to find the replacement in right subtree of node
        // One more assumption is the right subtree cannot be null if we are having degree 2 node
        while (node.leftChild != nil) {
            node = node.leftChild;
        }
        return node;
    }


    public void delete(int val) {

        Node deleted_node = findNode(val, root);
        if (deleted_node == null) {
            System.out.println("Node does not exist");
            return;
        }
        rotateRight(deleted_node);
        // Check the degree

        // TODO delete root


        //Leaf Node
        if (deleted_node.leftChild == null && deleted_node.rightChild == null) {
            System.out.println("Degree 0");
            //deleteLeafNode(deleted_node);
            return;
        }


        // Node with one child

        if (deleted_node.rightChild == null || deleted_node.leftChild == null) {
            System.out.println("Degree 1");
            // deleteNodeWithDegreeOne(deleted_node);

        }


        if (deleted_node.rightChild != null && deleted_node.leftChild != null) {
            // System.out.println("Degree 2");

        }
    }

    // if RED node is deleted then no re-balancing


    private void deleteLeafNode(Node deleted_node) {
        if (deleted_node.equals(root)) {
            root = null;
        }
        int relation = getParentChildPointer(deleted_node.parent, deleted_node);
        if (relation == 1) {
            if (deleted_node.color == Color.RED) {  // if color is red then no operations needs to be performed further
                deleted_node.parent.leftChild = null;

            } else {
                // we have black leaf
                //here y is always black
                deleteBlackLeaf(deleted_node, relation);
            }
        } else {
            if (deleted_node.color == Color.RED) {
                deleted_node.parent.rightChild = null;
            } else {
                //we have black leaf
                //here y is always black, since it is null
                deleteBlackLeaf(deleted_node, relation);
            }
        }
    }

    private void deleteBlackLeaf(Node deleted_node, int relation) {
        // we first identify what our Xcn is
        /*if relation is 1 then X=L
        py's other child could be null therefore c = BLACK or whatever it is
        then we need number of red child of v
        if v is nul then 0 else count it
        * */
        Color c;
        Node v, py, y;
        int n = 0;
        if (relation == 1) {
            // get color of node in right side

            // number of red child of v
            if (deleted_node.parent.rightChild != null) {
                v = deleted_node.parent.rightChild;
                c = v.color;  //c
                if (v.leftChild != null && v.leftChild.color == Color.RED) {
                    n++;
                }
                if (v.rightChild != null && v.rightChild.color == Color.RED) {
                    n++;
                }
            } else {
                v = null;
                c = Color.BLACK;
                //here n will stay 0
            }
            py = deleted_node.parent;

            // we first check if deletedNode is leaf or not
            if (isLeaf(deleted_node)) {
                y = null;
                py.leftChild = null;
            } else {
                if (deleted_node.leftChild == null) {
                    //means y is at the right
                    py.leftChild = deleted_node.rightChild;
                    y = deleted_node.rightChild;
                    y.parent = py;
                } else {
                    py.leftChild = deleted_node.leftChild;
                    y = deleted_node.leftChild;
                    y.parent = py;
                }
            }
            // now we delete that node and link its parent to null
        } else {
            if (deleted_node.parent.leftChild != null) {
                v = deleted_node.parent.leftChild;
                c = v.color;
                if (v.leftChild != null && v.leftChild.color == Color.RED) {
                    n++;
                }
                if (v.rightChild != null && v.rightChild.color == Color.RED) {
                    n++;
                }
            } else {
                v = null;
                c = Color.BLACK;
            }
            py = deleted_node.parent;
            if (isLeaf(deleted_node)) {
                y = null;
                py.rightChild = null;
            } else {
                if (deleted_node.leftChild == null) {
                    //means y is at the right
                    py.rightChild = deleted_node.rightChild;
                    y = deleted_node.rightChild;
                    y.parent = py;
                } else {
                    py.rightChild = deleted_node.leftChild;
                    y = deleted_node.leftChild;
                    y.parent = py;
                }
            }
        }


        // case 0 -> n=0 and v is black Xb0
        if (n == 0 && (v == null || v.color == Color.BLACK)) {
            //X = R
            operationXb0(py, v);
        }


        // Case 1 -> Xb1
        int r1 = identifyRelation(py, v);
        if (n == 1 && v.color == Color.BLACK) { // v cannot be null
            // identify whether it is LL or LR or its symmetric

            int r2;
            // we need to get node whose color is red and parent in v
            Node a;
            if (v.leftChild != null) {
                a = v.leftChild;
                r2 = 1;
            } else {
                a = v.rightChild;
                r2 = 0;
            }

            operationXb1(r1, r2, a, v, py); // py,v,a -> p,pp,gp
        }

        // Case 2-> Xb2
        int r2;
        Node w;
        if (n == 2 && v.color == Color.BLACK) {
            if (r1 == 1) {
                r2 = 0;  // we select opposite since we need LR or RL
                w = v.rightChild;
            } else {
                r2 = 1;
                w = v.leftChild;
            }

            operationXb2(r1, r2, w, v, py);

        }

        // Case 3 -> Xrn

        if (v != null && v.color == Color.RED) {
            System.out.println("V is red");
        }


    }

    private void operationXb2(int relation1, int relation2, Node p, Node pp, Node gp) {
        if (relation1 == 1 && relation2 == 0) { // LRb
            operationLRb(p, pp, gp);
        } else if (relation1 == 0 && relation2 == 1) { //RLb
            operationRLb(p, pp, gp);
        }

        //negate the change done by rotation
        flipColor(gp);
        flipColor(pp);
        // we need to change color of child of v
        flipColor(p);
    }

    private boolean isLeaf(Node deleted_node) {
        return deleted_node.leftChild == null && deleted_node.rightChild == null;
    }


    private void operationXb1(int relation1, int relation2, Node p, Node pp, Node gp) {
        if (relation1 == 1 && relation2 == 1) { //LLb
            operationLLb(p, pp, gp);
        } else if (relation1 == 1 && relation2 == 0) { // LRb
            operationLRb(p, pp, gp);
        } else if (relation1 == 0 && relation2 == 0) { //RRb
            operationRRb(p, pp, gp);
        } else if (relation1 == 0 && relation2 == 1) { //RLb
            operationRLb(p, pp, gp);
        }
        //negate the change done by rotation
        flipColor(gp);
        flipColor(pp);
        // we need to change color of child of v
        flipColor(p);

    }

    private void operationXb0(Node py, Node v) {
        if (py.color == Color.BLACK) {
            flipColor(v); // TODO go up and re balance
        } else {
            flipColor(v);
            flipColor(py);
        }
    }

    // following function handles case when deleted node is black and
    private void deleteNodeWithDegreeOne(Node deleted_node) {
        int relation = identifyRelation(deleted_node.parent, deleted_node);
        if (deleted_node.color == Color.BLACK && deleted_node.leftChild != null && deleted_node.leftChild.color == Color.RED) {
            //y is deleted_node.leftChild
            // link y with leftChild of py
            linkDegreeOne(relation, deleted_node, deleted_node.leftChild);
            flipColor(deleted_node.leftChild);
        } else if (deleted_node.color == Color.BLACK && deleted_node.rightChild != null && deleted_node.rightChild.color == Color.RED) {
            //y is deleted_node.rightChild
            // link y with leftChild of py
            linkDegreeOne(relation, deleted_node, deleted_node.rightChild);
            flipColor(deleted_node.rightChild);  // make that node black
        } else if (deleted_node.color == Color.RED && deleted_node.leftChild != null) {
            linkDegreeOne(relation, deleted_node, deleted_node.leftChild);
            // here y has to be black because we cant have consecutive red

        } else if (deleted_node.color == Color.RED && deleted_node.rightChild != null) {
            linkDegreeOne(relation, deleted_node, deleted_node.rightChild);
        }


    }

    private void linkDegreeOne(int position, Node deleted_node, Node link) {
        if (position == 1) {
            deleted_node.parent.leftChild = link;
        } else {
            deleted_node.parent.rightChild = link;
        }
    }

    private Node findNode(int val, Node iterator) {
        if (root == nil) {
            return null;
        }

        if (iterator.bldg_no == val) {
            return iterator;
        } else if (iterator.bldg_no < val) {
            if (iterator.rightChild != nil) {
                return findNode(val, iterator.rightChild);
            } else {
                return null;
            }

        } else {
            if (iterator.leftChild != nil) {
                return findNode(val, iterator.leftChild);
            } else {
                return null;
            }

        }
    }

    public void insert(Node node) {

        /**
         * 1. Root is always black
         * 2. New Node is given RED
         */

        if (root == nil) {
            root = node;
            root.parent = nil;
            return;
        }
        // find correct position of new node
        Node iterator = root;
        Node p, pp, gp;
        //gp = null; // when inserted as the child of root
        while (true) {
            if (iterator.bldg_no < node.bldg_no) {
                if (iterator.rightChild == nil) {
                    iterator.rightChild = node;
                    p = iterator.rightChild; //pointer to new node
                    p.parent = iterator;
                    break;
                } else {
                    iterator = iterator.rightChild;
                }

            } else if (iterator.bldg_no > node.bldg_no) {     //TODO add = for duplicate
                if (iterator.leftChild == nil) {
                    iterator.leftChild = node;
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

    private void insert_balancing(Node p, Node pp, Node gp) {

        if (checkIfConsecutiveRed(p, pp, gp)) {
            //perform XYz operation
            // check child of color of gp i.e. color of z
            Node d = getChildOfGrandParent(pp, gp);  // this will return our 'd' node
            Color c_d;
            if (d == nil) {
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
            if (p != nil && p.parent != nil) {
                p = p.parent;
            }

            if (p != nil && !p.equals(root)) {
                pp = p.parent;
                if (pp != nil && !pp.equals(root)) {
                    gp = p.parent;
                    insert_balancing(p, pp, gp);
                }
            }

        }
    }

    // this will tell what kind of relation it is L or R  1->L and 0 ->R
    private int identifyRelation(Node parent, Node child) {
        if (parent.leftChild != nil && parent.leftChild.equals(child)) {
            return 1;
        }
        return 0;
    }

    private void operationRLb(Node p, Node pp, Node gp) {
        //Here first perform right rotation and then left i.e. LR = RR + LL
        pp.leftChild = p.rightChild; //b
        if (pp.leftChild != nil) {
            pp.leftChild.parent = pp;
        }
        p.rightChild = pp;
        pp.parent = p;

        gp.rightChild = p;
        p.parent = gp;

        //Note: above p will act like pp and vice versa
        operationRRb(pp, p, gp);

    }

    private void operationRRb(Node p, Node pp, Node gp) {
        gp.rightChild = pp.leftChild;  // c
        if (pp.leftChild != nil) {
            pp.leftChild.parent = gp;
        }
        pp.leftChild = gp;
        if (gp.equals(root)) {
            root = pp;
            pp.parent = nil;
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

    private void operationLRb(Node p, Node pp, Node gp) {
        //Here first perform right rotation and then left  LR = RR + LL
        pp.rightChild = p.leftChild; //b
        if (pp.rightChild != nil) {
            pp.rightChild.parent = pp;
        }
        p.leftChild = pp;
        pp.parent = p;

        gp.leftChild = p;
        p.parent = gp;

        //Note: above p will act like pp and vice versa
        operationLLb(pp, p, gp);

    }

    private void operationLLb(Node p, Node pp, Node gp) {
        gp.leftChild = pp.rightChild;  // c
        if (pp.rightChild != nil) {
            pp.rightChild.parent = gp;
        }
        pp.rightChild = gp;
        if (gp.equals(root)) {
            root = pp;
            pp.parent = nil;
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

    private int getParentChildPointer(Node parent, Node child) {
        if (parent.leftChild != nil && parent.leftChild.equals(child)) {
            return 1;
        }
        return 0;
    }


    private void operationXYr(Node pp, Node gp, Node d) {
//        if (!gp.equals(root)) {
//            flipColor(gp);   // we don't flip
//        }
        flipColor(gp);
        flipColor(d);
        flipColor(pp);
    }

    private void flipColor(Node node) {
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

    private Node getChildOfGrandParent(Node pp, Node gp) {
        if (gp.leftChild == nil || gp.rightChild == nil) {
            return nil;  //external node
        }
        if (gp.leftChild.equals(pp)) {
            return gp.rightChild;
        }
        return gp.leftChild;
    }

    private boolean checkIfConsecutiveRed(Node p, Node pp, Node gp) {
        if (gp == nil) {
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
