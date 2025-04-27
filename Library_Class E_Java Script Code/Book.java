/**
 * Class representing a book, which is a type of LibraryItem.
 */
class Book extends LibraryItem {
    private String publisher;

    /**
     * Constructor for Book.
     * @param title The title of the book.
     * @param author The author of the book.
     * @param stock The number of copies in stock.
     * @param publisher The publisher of the book.
     */
    public Book(String title, String author, int stock, String publisher) {
        super(title, author, stock);
        this.publisher = publisher;
    }

    /**
     * Displays the book's information including title, author, stock, and publisher.
     */
    @Override
    public void displayInfo() {
        System.out.println("Type: Book");
        System.out.println("Title: " + getTitle() + ", Author: " + getAuthor() + ", Stock: " + getStock() + ", Publisher: " + publisher);
    }
}
