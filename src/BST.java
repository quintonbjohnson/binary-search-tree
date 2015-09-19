import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
* A class that implements the BSTInterface in order
 * to create a usable Binary Search Tree.
* @author Quinton Johnson
* @version 1.0
*/
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element
     *                                            in data is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null.");
        }
        for (T item : data) {
            if (item != null) {
                this.add(item);
            } else {
                throw new IllegalArgumentException("Null value found.");
            }

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed in is null.");
        }
        if (root == null) {
            root = new BSTNode<T>(data);
            size++;
        } else {
            root = add(root, data);
        }
    }

    /**
     * Adds a node in to the BST.
     * @param node the node passed in
     * @param data the data to give the new node
     * @return the node to add
     */
    private BSTNode<T> add(BSTNode<T> node, T data) {
        if (node == null) {
            node = new BSTNode<T>(data);
        }
        if (data.compareTo(node.getData()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<T>(data));
                size++;
            } else {
                add(node.getLeft(), data);
            }
        } else if (data.compareTo(node.getData()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<T>(data));
                size++;
            } else {
                add(node.getRight(), data);
            }
        } else {
            return node;
        }
        return node;
    }

    /**
     * {@inheritDoc}
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = remove(root, data, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * Removes the node with the given starting node and data.
     * @param node the node to start with
     * @param data the data of the node to remove
     * @param dummy the dummy node to store the data in
     * @return the node removed
     */
    private BSTNode<T> remove(BSTNode<T> node, T data, BSTNode<T> dummy) {
        if (node == null) {
            throw new NoSuchElementException("No such element found.");
        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(remove(node.getRight(), data, dummy));
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(remove(node.getLeft(), data, dummy));
        } else {
            dummy.setData(node.getData());
            if (node.getLeft() == null && node.getRight() == null) { //Leaf
                return null;
            } else if (node.getLeft() == null) { //One child on the right
                return node.getRight();
            } else if (node.getRight() == null) { //One child on the left
                return node.getLeft();
            } else { //Two children
                T predecessor = lowest(node.getLeft());
                node.setData(predecessor);
                BSTNode<T> newDummy = new BSTNode<T>(null);
                node.setLeft(remove(node.getLeft(), predecessor, newDummy));
            }
        }
        return node;
    }

    /**
     * Used to find the lowest position of the right subtree
     * @param node the node passed in
     * @return the data of the node found
     */
    private T lowest(BSTNode<T> node) {
        if (node == null) {
            return null;
        }
        BSTNode<T> right = node.getRight();
        if (right == null) {
            return node.getData();
        } else {
            return lowest(right);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(T data) {
        BSTNode<T> temp = get(root, data);
        if (temp == null) {
            throw new NoSuchElementException("No element found "
                    + "with given search data.");
        }
        return temp.getData();
    }

    /**
     * Retrieves a node in the tree
     * @param node the node passed in
     * @param data the data of the node to find
     * @return the node to add
     */
    private BSTNode<T> get(BSTNode<T> node, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data passed is null.");
        }
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.getData()) < 0) {
            return get(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return get(node.getRight(), data);
        } else {
            return node;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is null.");
        }
        return contains(root, data);
    }

    /**
     * Checks to see if the tree contains a node with the passed in data.
     * @param node the node passed in
     * @param data the data of the node to find
     * @return a boolean of whether or not the node was found
     */
    private boolean contains(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (data.compareTo(node.getData()) < 0) {
            return contains(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return contains(node.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> preorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        preOrderHelper(root, list);
        return list;
    }

    /**
     * Traverses through the nodes using preorder.
     * @param node the node passed in
     * @param list the list used to put the nodes in preOrder
     */
    private void preOrderHelper(BSTNode<T> node, ArrayList<T> list) {
        if (node != null) {
            list.add(node.getData());
            preOrderHelper(node.getLeft(), list);
            preOrderHelper(node.getRight(), list);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> postorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        postOrderHelper(root, list);
        return list;
    }

    /**
     * Traverses through the nodes using postorder.
     * @param node the node passed in
     * @param list the list used to put the nodes in postOrder
     */
    private void postOrderHelper(BSTNode<T> node, ArrayList<T> list) {
        if (node != null) {
            postOrderHelper(node.getLeft(), list);
            postOrderHelper(node.getRight(), list);
            list.add(node.getData());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> inorder() {
        ArrayList<T> list = new ArrayList<T>();
        if (root == null) {
            return list;
        }
        inOrderHelper(root, list);
        return list;
    }

    /**
     * Traverses through the nodes using inorder.
     * @param node the node passed in
     * @param list the list used to put the nodes in inOrder
     */
    private void inOrderHelper(BSTNode<T> node, ArrayList<T> list) {
        if (node != null) {
            inOrderHelper(node.getLeft(), list);
            list.add(node.getData());
            inOrderHelper(node.getRight(), list);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
        ArrayList<T> list = new ArrayList<T>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> temp = queue.poll();
            if (temp != null) {
                list.add(temp.getData());
                if (temp.getLeft() != null) {
                    queue.add(temp.getLeft());
                }
                if (temp.getRight() != null) {
                    queue.add(temp.getRight());
                }
            }
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int height() {
        return height(root);
    }

    /**
     * Finds the height of the tree.
     * @param node the node passed in
     * @return the height of the tree
     */
    private int height(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(height(node.getLeft()),
                    height(node.getRight()));
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}
