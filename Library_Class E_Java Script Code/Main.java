// Main.java
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Main class for the Library Management System.
 * Provides the user interface and manages the interaction between different components of the system. 
 */
public class Main {
    private static LibraryItemList itemList = new LibraryItemList();
    private static BorrowedItemList
    borrowedItemList = new BorrowedItemList();
    private static BorrowerList borrowerList = new BorrowerList(); 
    private static TransactionList transactionList = new TransactionList();
    private static ItemStack returnedItemStack = new ItemStack(10);
    private static BorrowerQueue borrowerQueue = new BorrowerQueue();
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Gets the current time in the format "yyyy-MM-dd HH:mm:ss".
     * @return The current time as a formatted string.
     */
    private static String getCurrentTime() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentDate);
    }
 
    /**
     * Adds a new book or magazine to the library's item list.
     * Takes user input for the item details.
     */
    private static void addItem() {
        int choice = 0;
        boolean validChoice = false;
    
        while (!validChoice) {
            System.out.println("Enter item type (1. Book, 2. Magazine): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
    
                if (choice == 1 || choice == 2) {
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice! Please enter 1 for Book or 2 for Magazine.");
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); 
            }
        }
    
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
    
        int stock = 0;
        boolean validStock = false;
    
        while (!validStock) {
            System.out.print("Enter stock: ");
            if (scanner.hasNextInt()) {
                stock = scanner.nextInt();
                scanner.nextLine(); 
                validStock = true;
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); 
            }
        }
    
        if (choice == 1) {
            System.out.print("Enter publisher: ");
            String publisher = scanner.nextLine();
            Book book = new Book(title, author, stock, publisher);
            itemList.addItem(book);
            System.out.println("Book added successfully.");
        } else if (choice == 2) {
            System.out.print("Enter issue: ");
            String issue = scanner.nextLine();
            Magazine magazine = new Magazine(title, author, stock, issue);
            itemList.addItem(magazine);
            System.out.println("Magazine added successfully.");
        }
    }
    

    
    /**
     * Removes an item from the library's item list. 
     * Takes user input for the item title to be removed.
     */
    private static void removeItem() {
        System.out.println("Available items:");
        itemList.displayItems();
        System.out.print("Enter the title of the item to be removed: ");
        String title = scanner.nextLine();
        itemList.removeItem(title);
        System.out.println("Item removed successfully.");
    }

    /**
     * Handles the process of borrowing a library item.
     * Takes user input for the item title, borrower name, and borrow date. 
     * Checks for availability and manages the waiting queue if needed.
     */
    private static void borrowItem() {
        System.out.println("Available books:");
        itemList.displayItems();
        System.out.print("Enter the title of the item to borrow: ");
        String title = scanner.nextLine();
        System.out.print("Enter borrower name: ");
        String borrowerName = scanner.nextLine();
        System.out.print("Enter borrow date (yyyy-mm-dd): ");
        String borrowDate = scanner.nextLine();

        LibraryItemNode itemNode = itemList.findItem(title);
        if (itemNode != null && itemNode.libraryItem.getStock() > 0) {
            Borrower borrower = borrowerList.findBorrower(borrowerName);
            if (borrower == null) {
                borrower = new Borrower(borrowerName);
                borrowerList.addBorrower(borrower, null); 
            }
            BorrowedItemNode borrowedItemNode = borrowedItemList.borrowItem(itemNode.libraryItem, borrowerName, borrowDate);
            borrower.addBorrowedItem(itemNode.libraryItem);
            borrowerList.addBorrower(borrower, borrowedItemNode);
            transactionList.addTransaction(itemNode.libraryItem, borrower, borrowDate, "");
            itemList.decrementStock(title);
            System.out.println("Item borrowed successfully.");
        } else if (borrowerQueue.hasBorrowerInQueue(title)) {
            System.out.println("There are borrowers in the queue for this item. Please try again later.");
        } else {
            borrowerQueue.enqueueBorrower(borrowerName, title, getCurrentTime());
            System.out.println("No available stock. Borrower enqueued for the requested item.");
        }
    }

    /**
     * Adds a borrower to the queue for a specific library item. 
     * Takes user input for the borrower name and item title.
     */
    private static void enqueueBorrower() {
        System.out.println("Available items:");
        itemList.displayItems();
        System.out.print("Enter borrower name: ");
        String borrowerName = scanner.nextLine();
        System.out.print("Enter item title: ");
        String itemTitle = scanner.nextLine();
    
        LibraryItemNode item = itemList.findItem(itemTitle);
        if (item != null && item.libraryItem.getStock() > 0) {
            borrowerQueue.enqueue(borrowerName, itemTitle, getCurrentTime());
            System.out.println("Borrower enqueued successfully.");
        } else {
            System.out.println("Item not available or not found.");
        }
    }



    /**
     * Handles the process of returning a borrowed library item. 
     * Takes user input for the item title and return date. 
     * Updates the item stock, borrower's list, transaction history, and 
     * checks the queue to process waiting borrowers.
     */
    private static void returnItem() {
        System.out.println("Borrowed items:");
        borrowedItemList.displayBorrowedItems();
        System.out.print("Enter the title of the item to return: ");
        String title = scanner.nextLine();

        BorrowedItemNode returnedItem = borrowedItemList.returnItem(title);
        if (returnedItem != null) {
            returnedItemStack.push(returnedItem.item);
            System.out.print("Enter return date (yyyy-mm-dd): ");
            String returnDate = scanner.nextLine();

            Borrower borrower = borrowerList.findBorrower(returnedItem.borrowerName);
            borrower.removeBorrowedItem(returnedItem.item);
            borrowerList.removeBorrower(borrower.getName());
            transactionList.addTransaction(returnedItem.item, borrower, returnedItem.borrowDate, returnDate);
            itemList.incrementStock(title);
            borrowedItemList.removeByNode(returnedItem);
            System.out.println("Item returned successfully.");

            // Check if there are borrowers in the queue for the returned item
     if (borrowerQueue.hasBorrowerInQueue(title)) {
        BorrowerQueueNode nextBorrower = borrowerQueue.dequeue(title);
        System.out.println("Borrowing item to the next borrower: " + nextBorrower.name);

        // Automatically borrow the item to the next borrower
        LibraryItem borrowedItem = returnedItemStack.pop();
        BorrowedItemNode newBorrowedItemNode = borrowedItemList.borrowItem(borrowedItem, nextBorrower.name, getCurrentTime());
        Borrower newBorrower = borrowerList.findBorrower(nextBorrower.name);
        if (newBorrower == null) {
            newBorrower = new Borrower(nextBorrower.name);
            borrowerList.addBorrower(newBorrower, newBorrowedItemNode);
        } else {
            newBorrower.addBorrowedItem(borrowedItem);
            borrowerList.addBorrower(newBorrower, newBorrowedItemNode);
        }
        transactionList.addTransaction(borrowedItem, newBorrower, getCurrentTime(), "");
        itemList.decrementStock(title); 
    }}}
    
    
    /**
     * The main method of the application. 
     * Starts the Library Management System and presents the main menu.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to the Library System");
            System.out.println("1. Admin");
            System.out.println("2. Borrower");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("Wrong option! Please enter a number.");
                scanner.nextLine(); 
                continue;
            }

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    borrowerMenu();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays the admin menu and handles admin operations.
     */
     private static void adminMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. View Item List");
            System.out.println("4. View Borrowed Item List");
            System.out.println("5. View Borrower List");
            System.out.println("6. View Transaction History");
            System.out.println("7. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Wrong option! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    removeItem();
                    break;
                case 3:
                    itemList.displayItems();
                    break;
                case 4:
                    borrowedItemList.displayBorrowedItems();
                    break;
                case 5:
                    borrowerList.displayBorrowers();
                    break;
                case 6:
                    transactionList.displayTransactions();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    /**
     * Displays the borrower menu and handles borrower operations.
     */
    private static void borrowerMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nBorrower Menu");
            System.out.println("1. Borrow Item");
            System.out.println("2. Return Item");
            System.out.println("3. View Borrowed Item List");
            System.out.println("4. View Borrower Queue");
            System.out.println("5. Enqueue Borrower"); 
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option: ");
        

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Wrong option! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    borrowItem();
                    break;
                case 2:
                    returnItem();
                    break;
                case 3:
                    borrowedItemList.displayBorrowedItems();
                    break;
                case 4:
                    borrowerQueue.displayQueue();
                    break;
                case 5:
                    enqueueBorrower();
                    break;
                case 6: 
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}