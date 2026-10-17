import java.util.Scanner;

public class FullTimeStaff extends Staff {
    private final double basicSalary;
    private final double statutoryDeductions;

        public FullTimeStaff(String staffNo, String staffName, int staffAge,
                         double basicSalary, double statutoryDeductions) {
        super(staffNo, staffName, staffAge);
        if (!Double.isFinite(basicSalary) || basicSalary < 0) {
            throw new IllegalArgumentException("Basic salary must be zero or more.");
        }
        if (!Double.isFinite(statutoryDeductions) || statutoryDeductions < 0) {
            throw new IllegalArgumentException("Deductions must be zero or more.");
        }
        if (statutoryDeductions > basicSalary) {
            throw new IllegalArgumentException("Deductions cannot exceed basic salary.");
        }
        this.basicSalary = basicSalary;
        this.statutoryDeductions = statutoryDeductions;
    }

        public double calculateNetSalary() {
        return basicSalary - statutoryDeductions;
    }

    @Override
    public void printDetails() {
        System.out.println("\n--- FULL-TIME STAFF DETAILS ---");
        super.printDetails();
        System.out.printf("Basic Monthly Salary: USD %.2f%n", basicSalary);
        System.out.printf("Statutory Deductions: USD %.2f%n", statutoryDeductions);
        System.out.printf("Net Monthly Salary: USD %.2f%n", calculateNetSalary());
    }

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            System.out.println("FULL-TIME STAFF SALARY CALCULATOR");
            String number = readNonBlank(input, "Enter staff number: ");
            String name = readNonBlank(input, "Enter staff name: ");
            int age = readPositiveInt(input, "Enter staff age: ");
            double salary = readNonNegativeDouble(input, "Enter basic monthly salary (USD): ");
            double deductions = readNonNegativeDouble(input, "Enter statutory deductions (USD): ");
            FullTimeStaff staff = new FullTimeStaff(number, name, age, salary, deductions);
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

    private static double readNonNegativeDouble(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(input.nextLine().trim());
                if (Double.isFinite(value) && value >= 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a valid amount that is zero or more.");
        }
    }
}
