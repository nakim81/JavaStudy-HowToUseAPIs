package covidStatusWebCrawler;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {
    public static void exportToExcel(String date, List<CovidStatus> covidStatusList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Covid Status");

        // Header
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Region", "Total", "Domestic", "Abroad", "Confirmed", "Deaths", "Rate"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // add data rows
        for (int i = 0; i < covidStatusList.size(); i++) {
            CovidStatus covidStatus = covidStatusList.get(i);
            Row row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(covidStatus.getRegion());
            row.createCell(1).setCellValue(covidStatus.getTotal());
            row.createCell(2).setCellValue(covidStatus.getDomestic());
            row.createCell(3).setCellValue(covidStatus.getAbroad());
            row.createCell(4).setCellValue(covidStatus.getConfirmed());
            row.createCell(5).setCellValue(covidStatus.getDeaths());
            row.createCell(6).setCellValue(covidStatus.getRate());
        }

        // save as Excel file
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }
}
