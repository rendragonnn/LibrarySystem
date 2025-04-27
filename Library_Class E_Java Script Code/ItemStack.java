/**
 * Node for a stack to manage returned library items.
 */
class StackNode {
    LibraryItem item;
    StackNode next;

    /**
     * Constructor for StackNode.
     * @param item The LibraryItem being returned and added to the stack.
     */
    StackNode(LibraryItem item) {
        this.item = item;
        next = null;
    }
}

/**
 * Represents a stack of returned library items.
 * Uses a linked list implementation for push and pop operations.
 * Has a fixed maximum size to prevent overflow.
 */
class ItemStack {
    private StackNode top;
    private int size;
    private final int maxSize;

    /**
     * Constructor for ItemStack.
     * @param maxSize The maximum number of items the stack can hold.
     */
    ItemStack(int maxSize) {
        this.maxSize = maxSize;
        top = null;
        size = 0;
    }

    /**
     * Checks if the stack is empty.
     * @return True if the stack is empty, false otherwise.
     */
    boolean isEmpty() {
        return top == null;
    }

    /**
     * Checks if the stack is full.
     * @return True if the stack is full, false otherwise.
     */
    boolean isFull() {
        return size == maxSize;
    }

    /**
     * Pushes a LibraryItem onto the top of the stack.
     * @param item The LibraryItem to be added to the stack.
     */
    void push(LibraryItem item) {
        if (isFull()) {
            System.out.println("Error: Stack is full. Cannot push more items.");
            return;
        }

        StackNode newNode = new StackNode(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    /**
     * Removes and returns the LibraryItem at the top of the stack.
     * @return The LibraryItem that was popped from the stack, or null if the stack is empty.
     */
    LibraryItem pop() {
        if (isEmpty()) {
            System.out.println("Error: Stack is empty. Cannot pop items.");
            return null;
        }

        LibraryItem poppedItem = top.item;
        top = top.next;
        size--;
        return poppedItem;
    }

    /**
     * Returns the LibraryItem at the top of the stack without removing it.
     * @return The LibraryItem at the top of the stack, or null if the stack is empty.
     */
    LibraryItem peek() {
        if (isEmpty()) {
            System.out.println("Error: Stack is empty.");
            return null;
        }
        return top.item;
    }

    /**
     * @return The number of items currently in the stack.
     */
    int size() {
        return size;
    }

    /**
     * Displays all items in the stack.
     */
    void displayStack() {
        if (isEmpty()) {
            System.out.println("Stack is empty.");
            return;
        }

        StackNode current = top;
        System.out.println("Stack items:");
        while (current != null) {
            current.item.displayInfo();
            current = current.next;
        }
    }
}