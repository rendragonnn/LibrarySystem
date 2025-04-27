/**
 * Node for a queue to manage borrowers waiting for a specific item.
 */
class BorrowerQueueNode {
    String name;
    String itemTitle;
    String enqueueTime;
    BorrowerQueueNode next;

    /**
     * Constructor for BorrowerQueueNode.
     * @param name The name of the borrower waiting in the queue.
     * @param itemTitle The title of the item the borrower is waiting for.
     * @param enqueueTime The timestamp when the borrower joined the queue.
     */
    BorrowerQueueNode(String name, String itemTitle, String enqueueTime) {
        this.name = name;
        this.itemTitle = itemTitle;
        this.enqueueTime = enqueueTime;
        next = null;
    }
}

/**
 * Represents a queue of borrowers waiting for specific library items.
 * Uses a linked list implementation for enqueue and dequeue operations.
 */
class BorrowerQueue {
    BorrowerQueueNode front;
    BorrowerQueueNode rear;

    /**
     * Constructor for BorrowerQueue.
     * Initializes the queue as empty.
     */
    BorrowerQueue() {
        front = null;
        rear = null;
    }

    /**
     * Adds a borrower to the rear of the queue.
     * @param name The name of the borrower.
     * @param itemTitle The title of the item they are waiting for.
     * @param enqueueTime The timestamp when they joined the queue.
     */
    void enqueue(String name, String itemTitle, String enqueueTime) {
        BorrowerQueueNode newNode = new BorrowerQueueNode(name, itemTitle, enqueueTime);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    /**
     * Removes and returns the borrower at the front of the queue who is waiting for a specific item.
     * @param itemTitle The title of the item to dequeue a borrower for.
     * @return The BorrowerQueueNode representing the borrower who was waiting, null if no match is found.
     */
    BorrowerQueueNode dequeue(String itemTitle) {
        if (front == null) {
            return null;
        }

        BorrowerQueueNode temp = null;
        if (front.itemTitle.equals(itemTitle)) {
            temp = front;
            front = front.next;
            if (front == null) {
                rear = null;
            }
        } else {
            BorrowerQueueNode current = front;
            while (current.next != null) {
                if (current.next.itemTitle.equals(itemTitle)) {
                    temp = current.next;
                    current.next = current.next.next;
                    if (current.next == null) {
                        rear = current;
                    }
                    break;
                }
                current = current.next;
            }
        }
        return temp;
    }

    /**
     * Adds a borrower to the rear of the queue (same as enqueue).
     * @param borrowerName The name of the borrower.
     * @param itemTitle The title of the item they are waiting for.
     * @param enqueueTime The timestamp when they joined the queue.
     */
    void enqueueBorrower(String borrowerName, String itemTitle, String enqueueTime) {
        BorrowerQueueNode newNode = new BorrowerQueueNode(borrowerName, itemTitle, enqueueTime);
        if (rear == null) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    /**
     * Displays all borrowers in the queue along with the item they are waiting for and their enqueue time.
     */
    void displayQueue() {
        if (front == null) {
            System.out.println("No borrowers in the queue.");
            return;
        }

        BorrowerQueueNode current = front;
        System.out.println("Queue elements:");
        while (current != null) {
            System.out.println("Name: " + current.name + ", Item Title: " + current.itemTitle + ", Enqueue Time: " + current.enqueueTime);
            current = current.next;
        }
    }

    /**
     * Checks if there is a borrower in the queue waiting for a specific item.
     * @param itemTitle The title of the item to check for.
     * @return True if a borrower is waiting for the item, false otherwise.
     */
    boolean hasBorrowerInQueue(String itemTitle) {
        BorrowerQueueNode current = front;
        while (current != null) {
            if (current.itemTitle.equals(itemTitle)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /**
     * Returns the number of borrowers in the queue.
     * @return The size of the queue.
     */
    int size() {
        int count = 0;
        BorrowerQueueNode current = front;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}