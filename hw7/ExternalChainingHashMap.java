import java.util.NoSuchElementException;

/**
 * Your implementation of a ExternalChainingHashMap.
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap with an initial capacity of INITIAL_CAPACITY.
     */
    public ExternalChainingHashMap() {
        //DO NOT MODIFY THIS METHOD!
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the table would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is
     * okay if the load factor is equal to max LF). For example, let's say
     * the table is of length 5 and the current size is 3 (LF = 0.6). For
     * this example, assume that no elements are removed in between steps.
     * If another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You should use the resizeBackingTable method to do so.
     *
     * @param key   The key to add.
     * @param value The value to add.
     * @return null if the key was not already in the map. If it was in the
     *         map, return the old value associated with it.
     * @throws java.lang.IllegalArgumentException If key or value is null.
     */
    public V put(K key, V value) {
        // Error checking
        if (key == null) {
            throw new IllegalArgumentException("Key is null.");
        }

        if (value == null) {
            throw new IllegalArgumentException("Value is null.");
        }

        // Resize if max load factor is strictly exceeded
        double loadFactor = (1.0 * size + 1) / table.length;
        if (loadFactor > MAX_LOAD_FACTOR) {
            resizeBackingTable((2 * table.length) + 1);
        }

        int index = getIndexAtKey(key);
        int hashCode = key.hashCode();
        ExternalChainingMapEntry<K, V> head = table[index];

        // Check if key already exists
        while (head != null) {
            if (head.getKey().equals(key) && head.getKey().hashCode() == hashCode) {
                V oldValue = head.getValue();
                head.setValue(value);
                return oldValue;
            }
            head = head.getNext();
        }
        // Add key to the head of the chain
        head = table[index];
        ExternalChainingMapEntry<K, V> newNode = new ExternalChainingMapEntry<K, V>(key, value, head);
        table[index] = newNode;
        size++;
        return null;
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key The key to remove.
     * @return The value associated with the key.
     * @throws java.lang.IllegalArgumentException If key is null.
     * @throws java.util.NoSuchElementException   If the key is not in the map.
     */
    public V remove(K key) {
        // Error checking
        if (key == null) {
            throw new IllegalArgumentException("Key isn null");
        }

        int index = getIndexAtKey(key);
        int hashCode = key.hashCode();

        // Get the head of the chain
        ExternalChainingMapEntry<K, V> head = table[index];

        // Search for the key in the chain
        ExternalChainingMapEntry<K, V> prev = null;
        while (head != null) {
            // If the key is found...
            if (head.getKey().equals(key) && head.getKey().hashCode() == hashCode) {
                break;
            }
            // ...Otherwise keep moving down the chain
            prev = head;
            head = head.getNext();
        }

        // If the key was not found...
        if (head == null) {
            throw new NoSuchElementException("Key not found.");
        }

        // Decrement size
        size--;

        // Remove the key
        if (prev != null) {
            prev.setNext(head.getNext());
        }
        else {
            table[index] = head.getNext();
        }

        return head.getValue();
    }

    /**
     * Helper method stub for resizing the backing table to length.
     *
     * This method should be called in put() if the backing table needs to
     * be resized.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you won't need to explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new table.
     *
     * @param length The new length of the backing table.
     */
    private void resizeBackingTable(int length) {
        ExternalChainingMapEntry<K, V>[] oldTable = table;
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[length];
        size = 0;
        for (int i = 0; i < length; i++) {
            table[i] = null;
        }
        for (ExternalChainingMapEntry<K, V> headNode : oldTable) {
            while (headNode != null) {
                put(headNode.getKey(), headNode.getValue());
                headNode = headNode.getNext();
            }
        }
    }

    /**
     * Gets the index at key.
     * @param key The key.
     * @return The index at the key.
     */
    private int getIndexAtKey(K key) {
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode % table.length);
        return index;
    }

    /**
     * Gets the value at a key.
     * @param key The key.
     * @return The value at the key.
     */
    private V getValueAtKey(K key)
    {
        int index = getIndexAtKey(key);
        int hashCode = key.hashCode();

        ExternalChainingMapEntry<K, V> head = table[index];

        // Search for key in chain
        while (head != null) {
            if (head.getKey().equals(key) && head.getKey().hashCode() == hashCode)
                return head.getValue();
            head = head.getNext();
        }
        // If key not found
        return null;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The table of the map.
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the map.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}