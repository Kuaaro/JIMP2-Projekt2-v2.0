public class BinaryTree {
    private BinaryTree left = null, right = null;
    private int value = -1;
    public BinaryTree addLeaf(boolean is_right) {
        if (is_right) {
            right = new BinaryTree();
            return right;
        } else {
            left = new BinaryTree();
            return left;
        }
    }

    public void setValue(int input) {
        value = input;
    }

    public boolean hasValue() {
        return value != -1;
    }

    public int getValue() {
        return value;
    }

    public BinaryTree getTree(boolean is_right) {
        if (is_right) {
            return right;
        } else {
            return left;
        }
    }
}
