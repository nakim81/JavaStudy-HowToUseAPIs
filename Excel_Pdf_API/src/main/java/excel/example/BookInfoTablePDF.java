package excel.example;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

public class BookInfoTablePDF {
    public static void main(String[] args) throws MalformedURLException, IOException {
        String fileName = "book_table.pdf";
        new BookInfoTablePDF().createPdf(fileName);
    }

    //Create Pdf file
    private void createPdf(String fileName) throws MalformedURLException, IOException {
        List<Map<String, String >> books = createDummyData();

        //Initialize Pdf Writer and Pdf Document
        PdfWriter writer = new PdfWriter(fileName);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        // Initialize fonts
        PdfFont font = PdfFontFactory.createFont("Arial.ttf", "Identity-H", true);

        // Initialize table
        float[] columnWidths = {1, 2, 2, 2, 2, 2};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));
        // Initialize table header cells
        Cell headerCell1 = new Cell().add(new Paragraph("Number")).setFont(font);
        Cell headerCell2 = new Cell().add(new Paragraph("Title")).setFont(font);
        Cell headerCell3 = new Cell().add(new Paragraph("Author")).setFont(font);
        Cell headerCell4 = new Cell().add(new Paragraph("Publisher")).setFont(font);
        Cell headerCell5 = new Cell().add(new Paragraph("Published Date")).setFont(font);
        Cell headerCell6 = new Cell().add(new Paragraph("Book Cover")).setFont(font);
        table.addHeaderCell(headerCell1);
        table.addHeaderCell(headerCell2);
        table.addHeaderCell(headerCell3);
        table.addHeaderCell(headerCell4);
        table.addHeaderCell(headerCell5);
        table.addHeaderCell(headerCell6);

        // Add table body cells
        int rowNum = 1;
        for (Map<String, String> book : books) {
            String title = book.get("title");
            String authors = book.get("authors");
            String publisher = book.get("publisher");
            String publishedDate = book.get("publishedDate");
            String thumbnail= book.get("thumbnail");
            Cell rowNumCell = new Cell().add(new Paragraph(String.valueOf(rowNum))).setFont(font);
            table.addCell(rowNumCell);
            Cell titleCell = new Cell().add(new Paragraph(title)).setFont(font);
            table.addCell(titleCell);
            Cell authorsCell = new Cell().add(new Paragraph(authors)).setFont(font);
            table.addCell(authorsCell);
            Cell publisherCell = new Cell().add(new Paragraph(publisher)).setFont(font);
            table.addCell(publisherCell);
            Cell publishedDateCell = new Cell().add(new Paragraph(publishedDate)).setFont(font);
            table.addCell(publishedDateCell);
            ImageData imageData = ImageDataFactory.create(new File(thumbnail).toURI().toURL());
            Image img = new Image(imageData);
            Cell imageCell = new Cell().add(img.setAutoScale(true));
            table.addCell(imageCell);
            rowNum++;
        }
        document.add(table);
        document.close();
    }

    //Get necessary info inputs from the user
    private static List<Map<String, String>> createDummyData() {
        List<Map<String, String>> books = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the number of books:");
        int bookCount = scanner.nextInt();
        scanner.nextLine();
        for (int i = 1; i <= bookCount; i++) {
            Map<String, String> book = new HashMap<>();

            System.out.printf("\n[ Entering the info of book number %d ]\n", i);
            System.out.print("Title:");
            book.put("title", scanner.nextLine());

            System.out.print("Author:");
            book.put("authors", scanner.nextLine());

            System.out.print("Publisher:");
            book.put("publisher", scanner.nextLine());

            System.out.print("Published Date(YYYY-MM-DD):");
            book.put("publishedDate", scanner.nextLine());

            System.out.print("Book image URL:");
            book.put("thumbnail", scanner.nextLine());

            books.add(book);
        }
        scanner.close();
        return books;
    }
}
