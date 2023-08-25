package covidStatusWebCrawler;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.element.Cell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PdfExporter {
    public static void exportToPdf(String date, List<CovidStatus> covidStatusList, String fileName) throws FileNotFoundException {
        try {
            PdfFont font = PdfFontFactory.createFont("AmericanTypewriter.ttc", PdfEncodings.IDENTITY_H, true);

            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
            Document doc = new Document(pdfDoc);

            Paragraph title = new Paragraph("Daily Covid status (" + date + ")");
            doc.add(title.setFont(font));

            float[] columnWidths = {100, 50, 50, 50, 50, 50, 50};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            String[] headers = {"Region", "Total", "Domestic", "Abroad", "Confirmed", "Deaths", "Rate"};

            for (String header : headers) {
                Cell cell = new Cell();
                cell.add(new Paragraph(header).setFont(font));
                cell.setTextAlignment(TextAlignment.CENTER);
                table.addHeaderCell(cell);
            }

            for (CovidStatus covidStatus : covidStatusList) {
                table.addCell(new Cell().add(new Paragraph(covidStatus.getRegion()).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getTotal())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getDomestic())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getAbroad())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getConfirmed())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(covidStatus.getDeaths())).setFont(font)));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", covidStatus.getRate())).setFont(font)));
            }

            doc.add(table);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
