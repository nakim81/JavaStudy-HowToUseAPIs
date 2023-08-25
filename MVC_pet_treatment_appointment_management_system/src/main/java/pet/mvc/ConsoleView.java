package petTreatmentManagementSystem.mvc;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner = new Scanner(System.in);

    public String getPhoneNumber() {
        System.out.print("Please enter your phone number: ");
        return scanner.nextLine();
    }

    public Customer getCustomerInfo() {
        System.out.println("New customer info");
        System.out.print("Phone number:");
        String phoneNumber = scanner.nextLine();
        System.out.print("Pet owner name:");
        String ownerName = scanner.nextLine();
        System.out.print("Pet name:");
        String petName = scanner.nextLine();
        System.out.print("Address:");
        String address = scanner.nextLine();
        System.out.print("Pet type:");
        String species = scanner.nextLine();
        System.out.print("Birth year(yyyy):");
        int birthYear = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character left by nextInt()
        return new Customer(phoneNumber, ownerName, petName, address, species, birthYear);
    }

    public MedicalRecord getMedicalRecordInfo() {
        System.out.print("Appointment date: ");
        String date = scanner.nextLine();

        System.out.print("Diagnosis: ");
        String content = scanner.nextLine();

        return new MedicalRecord(null, date, content);
    }

    public void printMedicalRecordInfo(Customer customer, List<MedicalRecord> records) {
        //List<MedicalRecord> records = customer.getMedicalRecords(); // 수정
        System.out.println("[" + customer.getPetName() + "]'s medical record");
        for (MedicalRecord record : records) {
            System.out.println("- appointment date: " + record.getDate());
            System.out.println("  diagnosis: " + record.getContent());
            System.out.println("  pet owner: " + customer.getOwnerName());
            System.out.println("  pet name: " + customer.getPetName());
            System.out.println("  address: " + customer.getAddress());
            System.out.println("  pet type: " + customer.getSpecies());
            System.out.println("  birth year: " + customer.getBirthYear());
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
