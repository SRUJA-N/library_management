import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/* ===================== BOOK CLASS ===================== */
class Book {
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public Book(String title, String author, int totalCopies) {
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public boolean issueBook() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    public boolean returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Title: " + title +
               " | Author: " + author +
               " | Total Copies: " + totalCopies +
               " | Available: " + availableCopies;
    }
}

/* ===================== MAIN MANAGER CLASS ===================== */
public class LibraryBookManager {

    private Map<String, Book> bookInventory;

    public LibraryBookManager() {
        bookInventory = new HashMap<>();
    }

    private String generateBookId(String title, String author) {
        return title.trim().toLowerCase().replaceAll("\\s+", " ")
                + "_" +
               author.trim().toLowerCase().replaceAll("\\s+", " ");
    }

    public void addBook(String title, String author, int copies) {
        if (copies <= 0) {
            System.out.println("Copies must be greater than zero.");
            return;
        }

        String bookId = generateBookId(title, author);

        if (bookInventory.containsKey(bookId)) {
            System.out.println("Book already exists!");
            return;
        }

        bookInventory.put(bookId, new Book(title, author, copies));
        System.out.println("Book added successfully.");
    }

    /* ===== CHECK AVAILABILITY ===== */
    public void checkAvailability(String title, String author) {
        String bookId = generateBookId(title, author);
        Book book = bookInventory.get(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println(book);
        if (book.getAvailableCopies() > 0) {
            System.out.println("Status: Available");
        } else {
            System.out.println("Status: Currently Unavailable");
        }
    }

    public void issueBook(String title, String author) {
        String bookId = generateBookId(title, author);
        Book book = bookInventory.get(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.issueBook()) {
            System.out.println("Book issued successfully.");
        } else {
            System.out.println("No copies available.");
        }
    }

    public void returnBook(String title, String author) {
        String bookId = generateBookId(title, author);
        Book book = bookInventory.get(bookId);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.returnBook()) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("All copies are already in library.");
        }
    }

    public void displayAllBooks() {
        if (bookInventory.isEmpty()) {
            System.out.println("Library is empty.");
            return;
        }

        System.out.println("\n===== LIBRARY BOOKS =====");
        for (Book book : bookInventory.values()) {
            System.out.println(book);
        }
        System.out.println("========================");
    }

    /* ===================== MAIN METHOD ===================== */
    public static void main(String[] args) {

        LibraryBookManager library = new LibraryBookManager();

        // ===== Default Books (Demo Data) =====
        library.addBook("Clean Code", "Robert C. Martin", 5);
        library.addBook("Effective Java", "Joshua Bloch", 4);
        library.addBook("Introduction to Algorithms", "Cormen", 6);
        library.addBook("Head First Java", "Kathy Sierra", 3);
        library.addBook("Design Patterns", "Erich Gamma", 2);
        // ====================================

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n1. Add Book");
            System.out.println("2. Check Availability");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display All Books");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter numbers only.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Copies: ");
                    int copies = sc.nextInt();
                    sc.nextLine();
                    library.addBook(title, author, copies);
                    break;

                case 2:
                    System.out.print("Title: ");
                    title = sc.nextLine();
                    System.out.print("Author: ");
                    author = sc.nextLine();
                    library.checkAvailability(title, author);
                    break;

                case 3:
                    System.out.print("Title: ");
                    title = sc.nextLine();
                    System.out.print("Author: ");
                    author = sc.nextLine();
                    library.issueBook(title, author);
                    break;

                case 4:
                    System.out.print("Title: ");
                    title = sc.nextLine();
                    System.out.print("Author: ");
                    author = sc.nextLine();
                    library.returnBook(title, author);
                    break;

                case 5:
                    library.displayAllBooks();
                    break;

                case 6:
                    System.out.println("Thank you. Exiting program.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}
