import java.util.Scanner;

public class PartTimeStaff extends Staff {
    private final int hoursWorked;

    private static final double RATE_PER_HOUR = 10.00;
    private static final double TAX_RATE = 0.30;
    
    public PartTimeStaff(String staffNo, String staffName, int staffAge,
                         int hoursWorked) {
        super(staffNo, staffName, staffAge);
        if (hoursWorked < 0) {
            throw new IllegalArgumentException("Hours worked cannot be negative.");
        }
        this.hoursWorked = hoursWorked;
    }
    
    public double calculateGrossPay() {
        return hoursWorked * RATE_PER_HOUR;
    }

    public double calculateTax() {
        return calculateGrossPay() * TAX_RATE;
    }

    public double calculateNetSalary() {
        return calculateGrossPay() - calculateTax();
    }

    @Override
    public void printDetails() {
        System.out.println("\n--- PART-TIME STAFF DETAILS ---");
        super.printDetails();
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.printf("Hourly Rate: USD %.2f%n", RATE_PER_HOUR);
        System.out.printf("Gross Monthly Earnings: USD %.2f%n", calculateGrossPay());
        System.out.printf("Tax (30%%): USD %.2f%n", calculateTax());
        System.out.printf("Net Monthly Salary: USD %.2f%n", calculateNetSalary());
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("PART-TIME STAFF SALARY CALCULATOR");
            String number = readNonBlank(input, "Enter staff number: ");
            String name = readNonBlank(input, "Enter staff name: ");
            int age = readPositiveInt(input, "Enter staff age: ");
            int hours = readNonNegativeInt(input, "Enter hours worked this month: ");
            PartTimeStaff staff = new PartTimeStaff(number, name, age, hours);
            staff.printDetails();
        } catch (IllegalArgumentException ex) {
            System.out.println("Input error: " + ex.getMessage());
        }
    }

    private static String readNonBlank(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("This field cannot be blank. Try again.");
        }
    }

    private static int readPositiveInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value > 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a positive whole number.");
        }
    }

    private static int readNonNegativeInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value >= 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter zero or a positive whole number.");
        }
    }
}
