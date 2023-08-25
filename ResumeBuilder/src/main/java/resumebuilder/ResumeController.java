package resumebuilder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class ResumeController {
    private ResumeView view;
    private Workbook workbook;

    public ResumeController() {
        view = new ResumeView();
        workbook = new XSSFWorkbook();
    }

    public void createResume() {
        PersonInfo personInfo = view.inputPersonInfo();
        List<Education> educationList = view.inputEducation();
        List<Career> careerList = view.inputCareer();
        String selfIntroduction = view.inputSelfIntroduction();

        createResumeSheet(personInfo, educationList, careerList);
        createSelfIntroductionSheet(selfIntroduction);
        saveWorkbookToFile();

        System.out.println("Finished generating resume file");
    }

    private void createResumeSheet(PersonInfo personInfo, List<Education> educationList, List<Career> careerList) {
        Sheet sheet = workbook.createSheet("resume");

        // Generate header
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Photo");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Address");
        headerRow.createCell(4).setCellValue("Phone number");
        headerRow.createCell(5).setCellValue("Birthdate");

        // Input data
        Row dataRow = sheet.createRow(1);
        String photoFileName = personInfo.getPhoto();
        try (InputStream photoInputStream = new FileInputStream(photoFileName)) {
            // Read photo file
            BufferedImage originalImage = ImageIO.read(photoInputStream);

            // Resize image to 35mm * 45mm
            int newWidth = (int) (35 * 2.83465); // mm to pixel (1mm = 2.83465px).
            int newHeight = (int) (45 * 2.83465); // mm to pixel

            Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedBufferedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = resizedBufferedImage.createGraphics();
            g2d.drawImage(resizedImage, 0, 0, null);
            g2d.dispose();

            // Transform resized image to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedBufferedImage, "jpeg", baos);
            byte[] imageBytes = baos.toByteArray();
            int imageIndex = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);

            // Generate Drawing object and insert image;
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, 0, 1, 1, 2);
            drawing.createPicture(anchor, imageIndex);

            // Adjust the width and height of the image cell
            // 96 = DPI of the display
            // Measurement unit of the height of a cell in Excel = point (1 point = 1/72 in)
            dataRow.setHeightInPoints(newHeight*72/96); // change pixel to point

            // 8 = default value of the String in excel
            // In excel, calculate the width of one letter by 1/256
            int columnWidth = (int) Math.floor(((float) newWidth / (float) 8) * 256);
            sheet.setColumnWidth(0, columnWidth);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set data
        dataRow.createCell(1).setCellValue(personInfo.getName());
        dataRow.createCell(2).setCellValue(personInfo.getEmail());
        dataRow.createCell(3).setCellValue(personInfo.getAddress());
        dataRow.createCell(4).setCellValue(personInfo.getPhoneNumber());
        dataRow.createCell(5).setCellValue(personInfo.getBirthDate());

        // Generate education header
        int educationStartRow = 3;
        Row educationHeaderRow = sheet.createRow(educationStartRow - 1);
        educationHeaderRow.createCell(0).setCellValue("Graduate year");
        educationHeaderRow.createCell(1).setCellValue("School name");
        educationHeaderRow.createCell(2).setCellValue("Major");
        educationHeaderRow.createCell(3).setCellValue("Graduation status");

        // Insert education data
        int educationRowNum = educationStartRow;
        for (Education education : educationList) {
            Row educationDataRow = sheet.createRow(educationRowNum++);
            educationDataRow.createCell(0).setCellValue(education.getGraduateYear());
            educationDataRow.createCell(1).setCellValue(education.getSchoolName());
            educationDataRow.createCell(2).setCellValue(education.getMajor());
            educationDataRow.createCell(3).setCellValue(education.getGraduationStatus());
        }

        // Generate career header
        int careerStartRow = educationRowNum + 1;
        Row careerHeaderRow = sheet.createRow(careerStartRow - 1);
        careerHeaderRow.createCell(0).setCellValue("Work period");
        careerHeaderRow.createCell(1).setCellValue("Company name");
        careerHeaderRow.createCell(2).setCellValue("Position title");
        careerHeaderRow.createCell(3).setCellValue("Years employed");

        // Insert career data
        int careerRowNum = careerStartRow;
        for (Career career : careerList) {
            Row careerDataRow = sheet.createRow(careerRowNum++);
            careerDataRow.createCell(0).setCellValue(career.getWorkPeriod());
            careerDataRow.createCell(1).setCellValue(career.getCompanyName());
            careerDataRow.createCell(2).setCellValue(career.getJobTitle());
            careerDataRow.createCell(3).setCellValue(career.getEmploymentYears());

        }
    }

    private void createSelfIntroductionSheet(String selfIntroduction) {
        Sheet sheet = workbook.createSheet("Cover letter");

        // Insert data
        Row dataRow = sheet.createRow(0);
        Cell selfIntroductionCell = dataRow.createCell(0);
        selfIntroductionCell.setCellStyle(getWrapCellStyle());
        selfIntroductionCell.setCellValue(new XSSFRichTextString(selfIntroduction.replaceAll("\n", String.valueOf((char) 10))));
    }

    private XSSFCellStyle getWrapCellStyle() {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    private void saveWorkbookToFile() {
        try (FileOutputStream fileOut = new FileOutputStream("resume.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ResumeController controller = new ResumeController();
        controller.createResume();
    }
}
