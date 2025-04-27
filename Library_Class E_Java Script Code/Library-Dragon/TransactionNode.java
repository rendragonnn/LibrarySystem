/**
 * Node for a doubly circular linked list representing a list of transactions.
 */
class TransactionNode {
    Displayable item;
    Borrower borrower;
    String borrowDate;
    String returnDate;
    TransactionNode prev;
    TransactionNode next;

    /**
     * Constructor for TransactionNode.
     * @param item The item involved in the transaction (can be LibraryItem or Borrower for display).
     * @param borrower The borrower involved in the transaction.
     * @param borrowDate The date the item was borrowed.
     * @param returnDate The date the item was returned (can be empty if not returned yet).
     */
    TransactionNode(Displayable item, Borrower borrower, String borrowDate, String returnDate) {
        this.item = item;
        this.borrower = borrower;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        prev = null;
        next = null;
    }
}

/**
 * Represents a list of library transactions using a doubly circular linked list. 
 * Each transaction records the item, borrower, borrow date, and return date.
 */
class TransactionList {
    private TransactionNode head;
    private TransactionNode tail;

    /**
     * Constructor for TransactionList.
     * Initializes the list as empty.
     */
    TransactionList() {
        head = null;
        tail = null;
    }

    /**
     * Adds a transaction to the end of the list. 
     * Maintains the circular structure of the list.
     * @param item The item involved in the transaction.
     * @param borrower The borrower involved in the transaction.
     * @param borrowDate The date the item was borrowed.
     * @param returnDate The date the item was returned (can be empty if not returned yet).
     */
    void addTransaction(Displayable item, Borrower borrower, String borrowDate, String returnDate) {
        if (item == null || borrower == null) {
            System.out.println("Error: Cannot add transaction with null item or borrower.");
            return;
        }
    
        TransactionNode newNode = new TransactionNode(item, borrower, borrowDate, returnDate);
        if (head == null) { // If the list is empty
            head = newNode;
            tail = newNode;
            newNode.next = head; // Make it circular
            newNode.prev = tail; 
        } else { // If the list is not empty
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head; // Connect to the head
            head.prev = newNode;
            tail = newNode; // Update tail
        }
    }

    /**
     * Removes a specific transaction node from the list. 
     * Handles cases for removing the head or tail and 
     * maintains the circular structure of the list.
     * @param node The TransactionNode to be removed.
     */
    void removeTransaction(TransactionNode node) {
        if (node == null) {
            return;
        }
        if (node == head) {
            if (head == tail) { 
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = tail;
                tail.next = head;
            }
        } else if (node == tail) {
            tail = tail.prev;
            tail.next = head;
            head.prev = tail;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    /**
     * Displays all transactions in the list.
     */
    void displayTransactions() {
        if (head == null) {
            System.out.println("No transactions.");
            return;
        }

        TransactionNode current = head;
        int transactionCount = 0;
        do {
            System.out.println("Transaction " + (++transactionCount) + ":");
            System.out.println("Title: " + current.item.getTitle());
            current.borrower.displayInfo();
            System.out.print("Borrow Date: " + current.borrowDate);
            if (current.returnDate.isEmpty()) {
                System.out.println(", Return Date: Not Returned Yet");
            } else {
                System.out.println(", Return Date: " + current.returnDate);
            }
            System.out.println();
            current = current.next;
        } while (current != head);
    }
}