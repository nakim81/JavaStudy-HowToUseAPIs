package resumebuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ResumeView {
    private Scanner scanner;
    public ResumeView() {
        scanner = new Scanner(System.in);
    }

    public PersonInfo inputPersonInfo() {
        System.out.print("Enter the name of your picture file: ");
        String photo = scanner.nextLine();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Enter your birthdate(ex: 1999-01-01): ");
        String birthDate = scanner.nextLine();

        return new PersonInfo(photo, name, email, address, phoneNumber, birthDate);
    }

    public List<Education> inputEducation() {
        List<Education> educationList = new ArrayList<>();

        while (true) {
            System.out.println("Please enter your education info (press q to quit)");
            System.out.println("Graduate-year School-name Major Graduation status");

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }

            String[] tokens = input.split(" ");
            if (tokens.length != 4) {
                System.out.println("Invalid input");
                continue;
            }

            String graduateYear = tokens[0];
            String schoolName = tokens[1];
            String major = tokens[2];
            String graduationStatus = tokens[3];

            educationList.add(new Education(graduateYear, schoolName, major, graduationStatus));
        }

        return educationList;
    }

    public List<Career> inputCareer() {
        List<Career> careerList = new ArrayList<>();

        while (true) {
            System.out.println("Please enter your career info (press q to quit");
            System.out.println("Work-period Company-name Position-title Work-years");

            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                break;
            }

            String[] tokens = input.split(" ");
            if (tokens.length != 4) {
                System.out.println("Invalid input");
                continue;
            }

            String workPeriod = tokens[0];
            String companyname = tokens[1];
            String jobTitle = tokens[2];
            String yearsEmployed = tokens[3];

            careerList.add(new Career(workPeriod, companyname, jobTitle, yearsEmployed));
        }

        return careerList;
    }

    public String inputSelfIntroduction() {
        System.out.println("Enter your self introduction. Enter blank line to quit");
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = scanner.nextLine()).trim().length() > 0) {
            sb.append(line).append("\n");
        }

        return sb.toString().trim();
    }
}
