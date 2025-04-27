/**
 * Node for a doubly linked list representing a list of Borrowed Items.
 */
class BorrowedItemNode {
    LibraryItem item;
    String borrowerName;
    String borrowDate;
    BorrowedItemNode next;
    BorrowedItemNode prev;

    /**
     * Constructor for BorrowedItemNode.
     * @param item The LibraryItem that has been borrowed.
     * @param borrowerName The name of the borrower.
     * @param borrowDate The date the item was borrowed.
     */
    BorrowedItemNode(LibraryItem item, String borrowerName, String borrowDate) {
        this.item = item;
        this.borrowerName = borrowerName;
        this.borrowDate = borrowDate;
        next = null;
        prev = null;
    }
}

/**
 * Represents a list of borrowed items using a doubly linked list. 
 * Provides methods for borrowing, returning, and displaying borrowed items.
 */
class BorrowedItemList {
    BorrowedItemNode head;
    BorrowedItemNode tail;

    /**
     * Constructor for BorrowedItemList. 
     * Initializes the list as empty.
     */
    BorrowedItemList() {
        head = null;
        tail = null;
    }

    /**
     * Adds a new BorrowedItemNode to the end of the list, representing a borrowed item.
     * @param item The LibraryItem that has been borrowed.
     * @param borrowerName The name of the borrower.
     * @param borrowDate The date the item was borrowed.
     * @return The newly created BorrowedItemNode.
     */
    BorrowedItemNode borrowItem(LibraryItem item, String borrowerName, String borrowDate) {
        BorrowedItemNode newNode = new BorrowedItemNode(item, borrowerName, borrowDate);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        return newNode; 
    }
    
    /**
     * Returns a borrowed item from the list.
     * 
     * This method searches for the item by title. If found, it removes the item 
     * from the borrowed list and returns the corresponding BorrowedItemNode. 
     * If the item is not found, it returns null.
     * 
     * The method handles cases where the removed item is the head or tail of the list,
     * ensuring proper updates to the list pointers.
     *
     * @param title The title of the item to be returned.
     * @return The BorrowedItemNode of the returned item, or null if not found.
     */
    BorrowedItemNode returnItem(String title) {
        if (head == null) {
            return null;
        }
        if (head.item.getTitle().equals(title)) {
            BorrowedItemNode returnedItem = head;
            head = head.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
            return returnedItem;
        }
        BorrowedItemNode current = head;
        while (current.next != null) {
            if (current.next.item.getTitle().equals(title)) {
                BorrowedItemNode returnedItem = current.next;
                if (current.next == tail) {
                    tail = current;
                } else {
                    current.next.next.prev = current;
                }
                current.next = current.next.next;
                return returnedItem;
            }
            current = current.next;
        }
        return null;
    }
    
    /**
     * Removes a specific BorrowedItemNode from the list. 
     * Handles cases where the node is the head or tail, 
     * and ensures the list remains properly connected.
     * @param node The BorrowedItemNode to be removed.
     */
    void removeByNode(BorrowedItemNode node) {
        if (node == null) {
            return;
        }
    
        if (node == head) {
            if (head == tail) { 
                head = null;
                tail = null;
            } else {
                head = head.next;
                if (head != null) {
                    head.prev = null;
                } else {
                    tail = null; 
                }
            }
        } else if (node == tail) {
            tail = tail.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null; 
            }
        } else {
            BorrowedItemNode prevNode = node.prev;
            if (prevNode != null) { 
                prevNode.next = node.next;
            }
            if (node.next != null) {
                node.next.prev = prevNode;
            }
        }
    }
    

    /**
     * Displays all borrowed items in the list.
     */
    void displayBorrowedItems() {
        BorrowedItemNode current = head;
        if (head == null) {
            System.out.println("No borrowed items.");
            return;
        }
        while (current != null) {
            System.out.println("Borrowed Item:");
            current.item.displayInfo();
            System.out.println("Borrowed By: " + current.borrowerName + ", Borrow Date: " + current.borrowDate);
            current = current.next;
        }
    }
}