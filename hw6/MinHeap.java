import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null) {
            throw new IllegalArgumentException("Input data is null.");
        }
        if (size == backingArray.length - 1) {
            resizeBackingArray();
        }
        backingArray[size + 1] = data;
        size++;
        if (size > 1) {
            upHeap(size);
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item. The heap is empty.");
        }

        // Save root for later
        T root = backingArray[1];
        // Copy last element to root
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downHeap(1);
        return root;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Perform an up-heap operation.
     * @param index The index of the node to up-heap.
     */
    private void upHeap(int index) {
        T data = backingArray[index];
        T parent = getParent(index);
        if (parent == null || data.compareTo(parent) > 0) {
            return;
        }
        else {
            // Swap with parent
            backingArray[index / 2] = data;
            backingArray[index] = parent;
            upHeap(index / 2);
        }
    }

    /**
     * Perform a down-heap operation.
     * @param index The index of the node to down-heap.
     */
    private void downHeap(int index) {
        T parent = backingArray[index];
        T leftChild = getLeftChild(index);
        T rightChild = getRightChild(index);

        // Determine number of children
        int numChildren = 0;
        if (leftChild != null && rightChild == null) {
            numChildren = 1;
        }
        else if (leftChild != null && rightChild != null) {
            numChildren = 2;
        }

        // Base case
        if (numChildren == 0) {
            return;
        }
        else {
            int comparison = 0;
            int smallestIndex = 0;
            if (numChildren == 1) {
                comparison = parent.compareTo(leftChild);
                smallestIndex = 2 * index;
            }
            else if (numChildren == 2) {
                // Determine higher priority child.
                // Start by assuming it's the left child.
                int priorityIndex = 2 * index;
                // If left is larger than right, change assignment to right.
                if (leftChild.compareTo(rightChild) > 0) {
                    priorityIndex = 2 * index + 1;
                }
                T smallestChild = backingArray[priorityIndex];
                comparison = parent.compareTo(smallestChild);
                smallestIndex = priorityIndex;
            }
            // Swap parent and child if necessary
            if (comparison > 0) {
                backingArray[index] = backingArray[smallestIndex];
                backingArray[smallestIndex] = parent;
            }
            downHeap(2 * index);
        }
    }

    /**
     * Gets the left child of a node at an index.
     * @param index The index of the node.
     * @return The data in the left child node.
     */
    private T getLeftChild(int index) {
        if (2 * index > size) {
            return null;
        }
        return backingArray[2 * index];
    }

    /**
     * Gets the right child of a node at an index.
     * @param index The index of the node.
     * @return The data in the right child node.
     */
    private T getRightChild(int index) {
        if (2 * index + 1 > size) {
            return null;
        }
        return backingArray[2 * index + 1];
    }

    /**
     * Gets the parent of a node at an index.
     * @param index The index of the node.
     * @return The data in the parent node.
     */
    private T getParent(int index) {
        if (index / 2 == 0) {
            return null;
        }
        return backingArray[index / 2];
    }

    /**
     * Resizes an array to twice its original size.
     */
    private void resizeBackingArray() {
        int newSize = backingArray.length * 2;
        T[] newBackingArray = (T[]) new Comparable[newSize];
        for (int i = 1; i < backingArray.length; i++)
            newBackingArray[i] = backingArray[i];
        backingArray = newBackingArray;
    }

    /**
     * Checks if an array is empty.
     */
    private boolean isEmpty() {
        boolean isEmpty = true;
        for (T element : backingArray) {
            if (element != null) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }
}