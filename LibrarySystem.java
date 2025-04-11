import java.util.*;

public class LibrarySystem {
    private static final Scanner scanner = new Scanner(System.in);
private static final List<Map<String, String>> bookDatabase = new ArrayList<>();

    public static void addBook(String title, String author, String ISBN) {
        if (title.isEmpty() || author.isEmpty() || ISBN.isEmpty()) {
            System.out.println("Error: Please enter all book details.");
            return;
        }

        if (!validateBookInfo(title, author, ISBN)) {
            System.out.println("Error: Invalid book information. ISBN must be numeric.");
            return;
        }

        saveBookToDatabase(title, author, ISBN);
        System.out.println("Book added successfully!");
    }

    public static void searchBook() {
        System.out.print("Enter Book Title, Author, or ISBN: ");
        String searchQuery = scanner.nextLine().toLowerCase();

        List<String> searchResults = searchLibraryDatabase(searchQuery);

        if (!searchResults.isEmpty()) {
            System.out.println("Search Results:");
            for (String result : searchResults) {
                System.out.println(result);
            }
        } else {
            System.out.println("No matching books found.");
        }
    }

    public static void deleteBook(String ISBN_or_Title) {
        if (ISBN_or_Title.isEmpty()) {
            System.out.println("Error: Please enter book title or ISBN to delete.");
            return;
        }

        Map<String, String> book = findBookInDatabase(ISBN_or_Title);
        if (book == null) {
            System.out.println("Error: Book not found.");
            return;
        }

        System.out.println("Book found: " + formatBook(book));
        System.out.print("Are you sure you want to delete this book? (Yes/No): ");
        String userConfirmation = scanner.nextLine();

        if (!userConfirmation.equalsIgnoreCase("Yes")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        deleteBookFromDatabase(book);
        System.out.println("Book deleted successfully!");
    }

    public static void viewAllBooks() {
        List<String> libraryCatalog = getAllBooksFromDatabase();

        if (!libraryCatalog.isEmpty()) {
            System.out.println("Library Catalog:");
            for (String book : libraryCatalog) {
                System.out.println(book);
            }
        } else {
            System.out.println("No books available in the library.");
        }
    }

    private static boolean validateBookInfo(String title, String author, String ISBN) {
        return ISBN.matches("\\d+");
    }

    private static void saveBookToDatabase(String title, String author, String ISBN) {
        Map<String, String> book = new HashMap<>();
        book.put("title", title);
        book.put("author", author);
        book.put("ISBN", ISBN);
        bookDatabase.add(book);
    }

    private static List<String> searchLibraryDatabase(String query) {
        List<String> results = new ArrayList<>();
        for (Map<String, String> book : bookDatabase) {
            if (book.get("title").toLowerCase().contains(query) ||
                book.get("author").toLowerCase().contains(query) ||
                book.get("ISBN").contains(query)) {
                results.add(formatBook(book));
            }
        }
        return results;
    }

    private static Map<String, String> findBookInDatabase(String ISBN_or_Title) {
        for (Map<String, String> book : bookDatabase) {
            if (book.get("ISBN").equalsIgnoreCase(ISBN_or_Title) ||
                book.get("title").equalsIgnoreCase(ISBN_or_Title)) {
                return book;
            }
        }
        return null;
    }

    private static void deleteBookFromDatabase(Map<String, String> book) {
        bookDatabase.remove(book);
    }

    private static List<String> getAllBooksFromDatabase() {
        List<String> books = new ArrayList<>();
        for (Map<String, String> book : bookDatabase) {
            books.add(formatBook(book));
        }
        return books;
    }

    private static String formatBook(Map<String, String> book) {
        return "Title: " + book.get("title") + ", Author: " + book.get("author") + ", ISBN: " + book.get("ISBN");
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Library System Menu ===");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Books");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    addBook(title, author, isbn);
                }
                case "2" -> searchBook();
                case "3" -> {
                    System.out.print("Enter ISBN or Title of the book to delete: ");
                    String input = scanner.nextLine();
                    deleteBook(input);
                }
                case "4" -> viewAllBooks();
                case "5" -> {
                    System.out.println("Exiting Library System. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
