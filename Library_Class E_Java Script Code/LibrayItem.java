
/**
 * Abstract class representing a generic library item. 
 * Provides common attributes and methods for books and magazines.
 */
abstract class LibraryItem implements Displayable {
    private String title;
    private String author;
    private int stock;

    /**
     * Constructor for LibraryItem.
     * @param title The title of the item.
     * @param author The author of the item.
     * @param stock The number of copies in stock.
     */
    public LibraryItem(String title, String author, int stock) {
        this.title = title;
        this.author = author;
        this.stock = stock;
    }

    /**
     * @return The title of the library item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The author of the library item.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return The number of copies in stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the number of copies in stock.
     * @param stock The new stock quantity.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Abstract method to display information about the specific library item.
     */
    public abstract void displayInfo(); 
}