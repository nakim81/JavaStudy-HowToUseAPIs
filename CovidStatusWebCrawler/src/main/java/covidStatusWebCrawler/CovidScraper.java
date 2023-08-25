package covidStatusWebCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CovidScraper {
    public static void main(String[] args) {
        String url = "https://ncov.kdca.go.kr/bdBoardList_Real.do?brdId=1&brdGubun=13&ncvContSeq=&contSeq=&board_id=&gubun=";

        try {
            // Get HTML document from the web
            Document doc = Jsoup.connect(url).get();

            String date = doc.select("div.timetable > p").first().text();

            Element table = doc.select("table.num").first();
            Elements rows = table.select("tbody > tr");

            List<CovidStatus> covidStatusList = new ArrayList<>();

            for (Element row : rows) {
                // Extract data from each cell
                String region = row.select("th").text();
                int total = Integer.parseInt(row.select("td:nth-child(2)").text().replaceAll(",", ""));
                int domestic = Integer.parseInt(row.select("td:nth-child(3)").text().replaceAll(",", ""));
                int abroad = Integer.parseInt(row.select("td:nth-child(4)").text().replaceAll(",", ""));
                int confirmed = Integer.parseInt(row.select("td:nth-child(5)").text().replaceAll(",", ""));
                int deaths = Integer.parseInt(row.select("td:nth-child(6)").text().replaceAll(",", ""));
                String rateStr = row.select("td:nth-child(7)").text().replaceAll(",", "");
                double rate = rateStr.equals("-") ? 0.0 : Double.parseDouble(rateStr);

                covidStatusList.add(new CovidStatus(region, total, domestic, abroad, confirmed, deaths, rate));
            }

            System.out.println("Daily Covid status (" + date + ")");
            System.out.println("Region | Total | Domestic | Abroad | Confirmed | Deaths | Rate");

            for (CovidStatus covidStatus : covidStatusList) {
                System.out.println(covidStatus);
            }

            // 엑셀 파일로 저장
            String excelFileName = "covid_status_" + date.replace(" ", "_").replace(":", "_") + ".xlsx";
            ExcelExporter.exportToExcel(date, covidStatusList, excelFileName);
            System.out.println("Finished exporting as Excel file: " + excelFileName);

            // PDF 파일로 저장
            String pdfFileName = "covid_status_" + date.replace(" ", "_").replace(":", "_") + ".pdf";
            PdfExporter.exportToPdf(date, covidStatusList, pdfFileName);
            System.out.println("Finished exporting as PDF File: " + pdfFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
