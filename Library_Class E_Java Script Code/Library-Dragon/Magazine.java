/**
 * Class representing a magazine, which is a type of LibraryItem.
 */
class Magazine extends LibraryItem {
    private String issue;

    /**
     * Constructor for Magazine.
     * @param title The title of the magazine.
     * @param author The author of the magazine.
     * @param stock The number of copies in stock.
     * @param issue The issue number or date of the magazine.
     */
    public Magazine(String title, String author, int stock, String issue) {
        super(title, author, stock);
        this.issue = issue;
    }

    /**
     * Displays the magazine's information, including title, author, stock, and issue.
     */
    @Override
    public void displayInfo() {
        System.out.println("Type: Magazine");
        System.out.println("Title: " + getTitle() + ", Author: " + getAuthor() + ", Stock: " + getStock() + ", Issue: " + issue);
    }
}