/**
 * Node for a single linked list representing a list of Library Items.
 */
class LibraryItemNode {
    LibraryItem libraryItem;
    LibraryItemNode next;

    /**
     * Constructor for LibraryItemNode.
     * @param libraryItem The LibraryItem to be stored in the node.
     */
    LibraryItemNode(LibraryItem libraryItem) {
        this.libraryItem = libraryItem;
        next = null;
    }
}

/**
 * Represents a list of library items using a single linked list. 
 * Provides methods to add, remove, display, and find items in the list. 
 */
class LibraryItemList {
    LibraryItemNode head;

    /**
     * Constructor for LibraryItemList.
     * Initializes the list as empty.
     */
    LibraryItemList() {
        head = null;
    }

    /**
     * Adds a library item to the end of the list.
     * @param libraryItem The LibraryItem to be added to the list.
     */
    void addItem(LibraryItem libraryItem) {
        LibraryItemNode newNode = new LibraryItemNode(libraryItem);
        if (head == null) {
            head = newNode;
        } else {
            LibraryItemNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    /**
     * Removes a library item from the list by its title. 
     * If the item has multiple copies, only the stock is decremented. 
     * If it's the last copy, the node is removed from the list. 
     * @param title The title of the item to be removed.
     */
    void removeItem(String title) {
        if (head == null) {
            return;
        }
        if (head.libraryItem.getTitle().equals(title)) {
            if (head.libraryItem.getStock() > 1) {
                head.libraryItem.setStock(head.libraryItem.getStock() - 1);
            } else {
                head = head.next; 
            }
            return;
        }
        LibraryItemNode current = head;
        while (current.next != null) {
            if (current.next.libraryItem.getTitle().equals(title)) {
                if (current.next.libraryItem.getStock() > 1) {
                    current.next.libraryItem.setStock(current.next.libraryItem.getStock() - 1);
                } else {
                    current.next = current.next.next; 
                }
                return;
            }
            current = current.next;
        }
    }

    /**
     * Displays all library items in the list.
     */
    void displayItems() {
        LibraryItemNode current = head;
        if (head == null) {
            System.out.println("No items in the list.");
            return;
        }
        while (current != null) {
            current.libraryItem.displayInfo();
            current = current.next;
        }
    }

    /**
     * Finds a library item node in the list by its title.
     * @param title The title of the item to search for.
     * @return The LibraryItemNode if the item is found, null otherwise.
     */
    LibraryItemNode findItem(String title) {
        LibraryItemNode current = head;
        while (current != null) {
            if (current.libraryItem.getTitle().equals(title)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Decrements the stock of a library item in the list by its title.
     * @param title The title of the item to decrement the stock for.
     */
    void decrementStock(String title) {
        LibraryItemNode current = head;
        while (current != null) {
            if (current.libraryItem.getTitle().equals(title)) {
                current.libraryItem.setStock(current.libraryItem.getStock() - 1);
                return;
            }
            current = current.next;
        }
    }

    /**
     * Increments the stock of a library item in the list by its title.
     * @param title The title of the item to increment the stock for.
     */
    void incrementStock(String title) {
        LibraryItemNode current = head;
        while (current != null) {
            if (current.libraryItem.getTitle().equals(title)) {
                current.libraryItem.setStock(current.libraryItem.getStock() + 1);
                return;
            }
            current = current.next;
        }
    }
}