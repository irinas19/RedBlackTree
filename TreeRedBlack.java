public class TreeRedBlack {
    private Node root;
    private class Node {
        private int value;
        private Color color;
        private Node leftChild;
        private Node rightChild;
    }

    private enum Color {
        BLACK,
        RED
    }

    private void colorChange(Node node) {
        node.leftChild.color = Color.BLACK;
        node.rightChild.color = Color.BLACK;
        node.color = Color.RED;
    }

    private Node leftTurn(Node node) {
        Node leftChild = node.leftChild;
        Node leftChild_rightChild = leftChild.rightChild;
        leftChild.rightChild = node;
        node.leftChild = leftChild_rightChild;
        leftChild.color = node.color;
        node.color = Color.RED;
        return leftChild;
    }

    private Node rightTurn(Node node) {
        Node rightChild = node.rightChild;
        Node rightChild_leftChild = rightChild.leftChild;
        rightChild.leftChild = node;
        node.rightChild = rightChild_leftChild;
        rightChild.color = node.color;
        node.color = Color.RED;
        return rightChild;
    }

    private Node balancing(Node node) {
        Node result = node;
        boolean needBalancing;
        do {
            needBalancing = false;
            if (result.rightChild != null && result.rightChild.color == Color.RED &&
                    (result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                needBalancing = true;
                result = rightTurn(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.leftChild.leftChild != null && result.leftChild.leftChild.color == Color.RED) {
                needBalancing = true;
                result = leftTurn(result);
            }
            if (result.leftChild != null && result.leftChild.color == Color.RED &&
                    result.rightChild != null && result.rightChild.color == Color.RED) {
                needBalancing = true;
                colorChange(result);
            }
        } while(needBalancing);
        return result;
    }

    private boolean addNode(Node node, int value) {
        if (node.value == value) {
            return false;
        } else {
            if (node.value > value) {
                if (node.leftChild != null) {
                    boolean result = addNode(node.leftChild, value);
                    node.leftChild = balancing(node.leftChild);
                    return result;
                } else {
                    node.leftChild = new Node();
                    node.leftChild.color = Color.RED;
                    node.leftChild.value = value;
                    return true;
                }
            } else {
                if (node.rightChild != null) {
                    boolean result = addNode(node.rightChild,value);
                    node.rightChild = balancing(node.rightChild);
                    return result;
                } else {
                    node.rightChild = new Node();
                    node.rightChild.color = Color.RED;
                    node.rightChild.value = value;
                    return true;
                }
            }
        }
    }

    public boolean add(int value) {
        if (root != null) {
            boolean result = addNode(root,value);
            root = balancing(root);
            root.color = Color.BLACK;
            return result;
        } else {
            root = new Node();
            root.color = Color.BLACK;
            root.value = value;
            return true;
        }
    }
}
