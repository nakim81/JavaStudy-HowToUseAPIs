package petTreatmentManagementSystem.mvc;

import java.util.List;
import java.util.Scanner;

public class PetMain {
    public static void main(String[] args) {
        MedicalRecordController recordController = new MedicalRecordController();
        CustomerController customerController = new CustomerController(recordController);
        ConsoleView view = new ConsoleView();

        while(true){
            System.out.println("===Pet Treatment Management System===");
            System.out.println("1. Enter new customer info");
            System.out.println("2. Save medical record");
            System.out.println("3. View medical records");
            System.out.println("4. Remove medical record");
            System.out.println("5. Quit");
            System.out.print("Select menu:");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt(); // 1~5
            switch (choice) {
                case 1:
                    Customer newCustomer = view.getCustomerInfo();
                    String phoneNumber = newCustomer.getPhoneNumber();
                    if (customerController.isPhoneNumberExist(phoneNumber)) {
                        view.printMessage("Phone number already exist.");
                        continue;
                    }
                    customerController.addCustomer(newCustomer);
                    view.printMessage("Customer info saved.");
                    break;

                case 2:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber) == null) {
                        view.printMessage("Customer with the phone number does not exist.");
                        break;
                    }
                    Customer customer = customerController.findCustomer(phoneNumber);
                    MedicalRecord newRecord = view.getMedicalRecordInfo();
                    newRecord.setPhoneNumber(phoneNumber);
                    recordController.addMedicalRecord(newRecord);
                    //customer.addMedicalRecords(newRecord);
                    view.printMessage("Medical record saved.");
                    break;

                case 3:
                    phoneNumber = view.getPhoneNumber();
                    List<MedicalRecord> records = recordController.findMedicalRecords(phoneNumber);
                    if (records.isEmpty()) {
                        view.printMessage("Medical record with the phone number does not exist.");
                        break;
                    }
                    customer = customerController.findCustomer(phoneNumber);
                    view.printMedicalRecordInfo(customer, records); // editing
                    break;

                case 4:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber) == null) {
                        view.printMessage("Customer with the phone number does not exist.");
                        break;
                    }
                    recordController.removeMedicalRecord(phoneNumber);
                    view.printMessage("Medical record removed.");
                    break;

                case 5:
                    System.out.println("Terminating the program.");
                    return;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }
}
