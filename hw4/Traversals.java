import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of the pre-order, in-order, and post-order
 * traversals of a tree.
 */
public class Traversals<T extends Comparable<? super T>> {

    /**
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */

    /**
     * Given the root of a binary search tree, generate a
     * pre-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the pre-order traversal of the tree.
     */
    public List<T> preorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> output = new ArrayList<>();
        recursePreorder(output, root);
        return output;
    }

    /**
     * Given the root of a binary search tree, generate an
     * in-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the in-order traversal of the tree.
     */
    public List<T> inorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> output = new ArrayList<>();
        recurseInorder(output, root);
        return output;
    }

    /**
     * Given the root of a binary search tree, generate a
     * post-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the post-order traversal of the tree.
     */
    public List<T> postorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> output = new ArrayList<>();
        recursePostorder(output, root);
        return output;
    }

    private void recursePreorder(List<T> list, TreeNode<T> node) {
        if (node == null) {
            return;
        }
        list.add(node.getData());
        recursePreorder(list, node.getLeft());
        recursePreorder(list, node.getRight());
        return;
    }

    private void recursePostorder(List<T> list, TreeNode<T> node) {
        if (node == null) {
            return;
        }
        recursePostorder(list, node.getLeft());
        recursePostorder(list, node.getRight());
        list.add(node.getData());
        return;
    }

    private void recurseInorder(List<T> list, TreeNode<T> node) {
        if (node == null) {
            return;
        }
        recurseInorder(list, node.getLeft());
        list.add(node.getData());
        recurseInorder(list, node.getRight());
        return;
    }
}