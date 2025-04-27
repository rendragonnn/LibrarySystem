/**
 * Node for a Double Circular Linked List representing a list of Borrowers.
 * Each node holds a Borrower and a reference to the BorrowedItemNode associated with them.
 */
class BorrowerNode {
    Borrower borrower;
    BorrowedItemNode borrowedItemNode; // Reference to the BorrowedItemNode 
    BorrowerNode next;
    BorrowerNode prev;

    /**
     * Constructor for BorrowerNode.
     * @param borrower The Borrower object to be stored in the node.
     * @param borrowedItemNode The BorrowedItemNode associated with the borrower.
     */
    BorrowerNode(Borrower borrower, BorrowedItemNode borrowedItemNode) {
        this.borrower = borrower;
        this.borrowedItemNode = borrowedItemNode; 
        next = null;
        prev = null;
    }
}

/**
 * Represents a list of borrowers using a Double Circular Linked List. 
 * Provides methods for adding, removing, finding, and displaying borrowers.
 */
class BorrowerList {
    private BorrowerNode head;

    /**
     * Constructor for BorrowerList.
     * Initializes the list with an empty head.
     */
    BorrowerList() {
        head = null;
    }

    /**
     * Finds a borrower in the list by name.
     * @param name The name of the borrower to search for.
     * @return The Borrower object if found, null otherwise.
     */
    Borrower findBorrower(String name) {
        BorrowerNode current = head;
        if (head == null) {
            return null;
        }
        do {
            if (current.borrower.getName().equals(name)) {
                return current.borrower;
            }
            current = current.next;
        } while (current != head);
        return null;
    }

    /**
     * Adds a borrower to the list.
     * If the list is empty, the new borrower becomes the head. 
     * Otherwise, the borrower is added to the end of the list, maintaining the circular structure.
     * @param borrower The Borrower object to add to the list.
     * @param borrowedItemNode The BorrowedItemNode associated with the borrower.
     */
    void addBorrower(Borrower borrower, BorrowedItemNode borrowedItemNode) {
        BorrowerNode newNode = new BorrowerNode(borrower, borrowedItemNode);

        if (head == null) {
            head = newNode;
            newNode.next = head;
            newNode.prev = head;
        } else {
            BorrowerNode last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
    }

    /**
     * Removes a borrower from the list by name.
     * @param name The name of the borrower to be removed.
     */
    void removeBorrower(String name) {
        if (head == null) {
            return;
        }

        BorrowerNode current = head;

        do {
            if (current.borrower.getName().equals(name)) {
                if (current == head) {
                    if (head.next == head) { // Only one node in the list
                        head = null; 
                    } else {
                        head = head.next;
                        head.prev = current.prev;
                        current.prev.next = head;
                    }
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                return;
            }
            current = current.next;
        } while (current != head);
    }

    /**
     * Displays all borrowers in the list along with their borrowed item (if any).
     */
    void displayBorrowers() {
        if (head == null) {
            System.out.println("No borrowers.");
            return;
        }

        BorrowerNode current = head;

        do {
            Borrower borrower = current.borrower;
            String borrowedItemTitle = (current.borrowedItemNode == null) ? "None" : current.borrowedItemNode.item.getTitle();
            System.out.println("Name: " + borrower.getName() + ", Borrowed Item: " + borrowedItemTitle);
            current = current.next;
        } while (current != head);
    }
}