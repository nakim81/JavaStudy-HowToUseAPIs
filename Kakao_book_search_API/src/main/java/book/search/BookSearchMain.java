package book.search;

import javax.imageio.IIOException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BookSearchMain {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the book title: ");
            String bookTitle = scanner.nextLine();
            List<Book> books = KakaoBookApi.searchBooks(bookTitle);

            if (books.isEmpty()) {
                String str = String.format("A book %s does not exist", bookTitle);
                System.out.println(str);
            } else {
                String fileName = "booklist.pdf";
                PdfGenerator.generateBookListPdf(books, fileName);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
