package excel.example;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Year;
import java.util.HashMap;

public class BoodInfoToPDF {
    public static void main(String[] args) {
        HashMap<String, String> bookInfo = new HashMap<>();
        bookInfo.put("title", "Korean Java");
        bookInfo.put("author", "Programmer");
        bookInfo.put("publisher", "Korean Publisher");
        bookInfo.put("year", String.valueOf(Year.now().getValue()));
        bookInfo.put("price", "25000");
        bookInfo.put("pages", "400");

        try {
            //Generate PdfWriter object to generate PDF
            PdfWriter writer = new PdfWriter(new FileOutputStream("book_information.pdf"));

            //Generate PdfDocument object from PdfWriter
            PdfDocument pdf = new PdfDocument(writer);

            //Generate Document object
            Document document = new Document(pdf);

            //Generate and add font
            PdfFont font = PdfFontFactory.createFont("Arial.ttf", PdfEncodings.IDENTITY_H, true);

            document.setFont(font);

            //Add info of a book to the document as a paragraph
            for (String key : bookInfo.keySet()) {
                Paragraph paragraph = new Paragraph(key + ": " + bookInfo.get(key));
                document.add(paragraph);
            }

            //Close the document
            document.close();

            System.out.println("book_information.pdf file generated");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
