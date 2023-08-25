package excel.example;

import excel.entity.MemberVO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExcelWriter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<MemberVO> members = new ArrayList<>();

        while (true) {
            System.out.print("이름을 입력하세요: "); //Please enter your name
            String name = scanner.nextLine();
            if (name.equals("quit")) {
                break;
            }

            System.out.print("나이를 입력하세요: "); //Please enter your age
            int age = scanner.nextInt();
            scanner.nextLine();

            System.out.print("생년월일을 입력하세요: "); //Please enter your birthdate
            String birthdate = scanner.nextLine();

            System.out.print("전화번호를 입력하세요: "); //Please enter your phone number
            String phone = scanner.nextLine();

            System.out.print("주소를 입력하세요: "); //Please enter your address
            String address = scanner.nextLine();

            System.out.print("결혼여부를 입력하세요: "); //Please enter if you are married or not
            boolean isMarried = scanner.nextBoolean();
            scanner.nextLine();

            MemberVO member = new MemberVO(name, age, birthdate, phone, address, isMarried);
            members.add(member);
        }
        scanner.close();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Member Info");
            //Create header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("name");
            headerRow.createCell(1).setCellValue("age");
            headerRow.createCell(2).setCellValue("birthdate");
            headerRow.createCell(3).setCellValue("phone number");
            headerRow.createCell(4).setCellValue("address");
            headerRow.createCell(5).setCellValue("isMarried");

            for (int i = 0; i < members.size(); i++) {
                MemberVO member = members.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(member.getName());
                row.createCell(1).setCellValue(member.getAge());
                row.createCell(2).setCellValue(member.getBirthdate());
                row.createCell(3).setCellValue(member.getPhone());
                row.createCell(4).setCellValue(member.getAddress());
                row.createCell(5).setCellValue(member.isMarried());
            }
            String filename = "members.xlsx";
            FileOutputStream outputStream = new FileOutputStream(new File(filename));
            workbook.write(outputStream);
            workbook.close();

            System.out.println("Succefully saved " + filename);
        } catch (IOException e) {
            System.out.println("Error occurred while trying to save the Excel file");
            e.printStackTrace();
        }
    }
}
