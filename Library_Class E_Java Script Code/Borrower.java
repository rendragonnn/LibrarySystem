/**
 * Represents a borrower in the library system. 
 * Can borrow multiple library items (up to a defined maximum).
 */
class Borrower implements Displayable {
    private String name;
    private LibraryItem[] borrowedItems;
    private int numBorrowedItems;
    private static final int MAX_BORROWED_ITEMS = 10;

    /**
     * Constructor for the Borrower class.
     * Initializes the borrower with a name and an empty list of borrowed items.
     * @param name The name of the borrower.
     */
    public Borrower(String name) {
        this.name = name;
        this.borrowedItems = new LibraryItem[MAX_BORROWED_ITEMS];
        this.numBorrowedItems = 0;
    }

    /**
     * Adds a library item to the borrower's list of borrowed items.
     * Checks if the maximum number of borrowed items has been reached. 
     * @param item The LibraryItem to be added to the borrowed items list.
     */
    public void addBorrowedItem(LibraryItem item) {
        if (numBorrowedItems < MAX_BORROWED_ITEMS) {
            borrowedItems[numBorrowedItems++] = item;
        } else {
            System.out.println("Error: Maximum borrowed items reached for " + name);
        }
    }

    /**
     * Removes a library item from the borrower's list of borrowed items.
     * @param item The LibraryItem to be removed from the borrowed items list.
     */
    public void removeBorrowedItem(LibraryItem item) {
        for (int i = 0; i < numBorrowedItems; i++) {
            if (borrowedItems[i] == item) {
                // Shift items to the left to fill the gap
                for (int j = i; j < numBorrowedItems - 1; j++) {
                    borrowedItems[j] = borrowedItems[j + 1];
                }
                numBorrowedItems--;
                return;
            }
        }
        System.out.println("Error: Item " + item.getTitle() + " not found in borrowed items for " + name);
    }

    /**
     * @return The name of the borrower.
     */
    public String getName() {
        return name;
    }

    /**
     * Displays the borrower's information, including their name and the list of borrowed items.
     */
    @Override
    public void displayInfo() {
        System.out.println("Name: " + name);
        if (numBorrowedItems > 0) {
            System.out.println("Borrowed Items:");
            for (LibraryItem item : borrowedItems) {
                if (item != null) {
                    System.out.println("- " + item.getTitle() + " (Not Returned Yet)");
                }
            }
        } else {
            System.out.println("No borrowed items.");
        }
    }

    /**
     * @return The name of the borrower (used as the title for display purposes).
     */
    @Override
    public String getTitle() {
        return name;
    }
}