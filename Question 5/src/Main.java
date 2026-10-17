import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Path DATA_FILE = Paths.get("data", "students.csv");
    private static final String TEACHER_USERNAME = "Ibrahim";
    private static final String TEACHER_PASSWORD = "Fahim 21";
    private static final List<Student> STUDENTS = new ArrayList<>();

    public static void main(String[] args) {
        prepareStorage();
        loadStudents();
        System.out.println("--------------------------------------");
        System.out.println(" PRIME HIGH SCHOOL STUDENT MANAGEMENT SYSTEM ");
        System.out.println("--------------------------------------");

        if (login()) {
            int choice;
            do {
                showMenu();
                choice = readInt("Choose an option: ", 1, 5);
                switch (choice) {
                    case 1 -> registerStudents();
                    case 2 -> recordMarks();
                    case 3 -> viewStudents();
                    case 4 -> searchStudent();
                    case 5 -> System.out.println("Thank you. Your records have been saved.");
                    default -> System.out.println("Please choose a valid option.");
                }
            } while (choice != 5);
        }
        INPUT.close();
    }

    private static boolean login() {
        System.out.println("\nTEACHER LOGIN");
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Username: ");
            String username = INPUT.nextLine().trim();
            System.out.print("Password: ");
            String password = INPUT.nextLine();

            if (TEACHER_USERNAME.equals(username) && TEACHER_PASSWORD.equals(password)) {
                System.out.println("Login successful. Welcome, teacher!");
                return true;
            }
            System.out.println("Incorrect username or password. Attempts remaining: " + (3 - attempt));
        }
        System.out.println("Too many failed attempts. Exiting the system.");
        return false;
    }

    private static void showMenu() {
        System.out.println("\n--------------- MAIN MENU ---------------");
        System.out.println("1. Register students");
        System.out.println("2. Record or update a student's marks");
        System.out.println("3. View all students and marks");
        System.out.println("4. Search for a student by registration number");
        System.out.println("5. Save and exit");
        System.out.println("------------------------------------------");
    }

    private static void registerStudents() {
        System.out.println("\nSTUDENT REGISTRATION");
        int count = readInt("How many students do you want to register? ", 1, 10000);
        int added = 0;

        for (int i = 1; i <= count; i++) {
            System.out.println("\nStudent " + i + " of " + count);
            String regNo;
            while (true) {
                regNo = readNonBlank("Registration number: ");
                if (findByRegNo(regNo) != null) {
                    System.out.println("That registration number already exists. Enter a different one.");
                } else {
                    break;
                }
            }
            String name = readNonBlank("Student name: ");
            String subject = readNonBlank("Subject: ");
            String className = readNonBlank("Class: ");

            Student student = new Student(regNo, name, subject, className);
            STUDENTS.add(student);
            added++;
        }
        saveStudents();
        System.out.println(added + " student(s) registered and saved successfully.");
    }

    private static void recordMarks() {
        System.out.println("\nRECORD STUDENT MARKS");
        if (STUDENTS.isEmpty()) {
            System.out.println("No students are registered yet. Register a student first.");
            return;
        }

        String regNo = readNonBlank("Enter the student's registration number: ");
        Student student = findByRegNo(regNo);
        if (student == null) {
            System.out.println("No student found with registration number " + regNo + ".");
            return;
        }

        double marks = readDouble("Enter marks out of 100: ", 0.0, 100.0);
        student.setMarks(marks);
        saveStudents();
        System.out.printf("Marks %.2f recorded for %s and saved successfully.%n", marks, student.getName());
    }

    private static void viewStudents() {
        System.out.println("\nALL REGISTERED STUDENTS AND MARKS");
        if (STUDENTS.isEmpty()) {
            System.out.println("No students have been registered yet.");
            return;
        }

        System.out.printf("%-16s %-24s %-18s %-12s %-12s%n",
                "Registration No.", "Student Name", "Subject", "Class", "Marks / 100");
        System.out.println("---------------------------------------------------------");
        for (Student student : STUDENTS) {
            String marks = student.hasMarks() ? String.format("%.2f", student.getMarks()) : "Not recorded";
            System.out.printf("%-16s %-24s %-18s %-12s %-12s%n",
                    student.getRegistrationNumber(), shorten(student.getName(), 23),
                    shorten(student.getSubject(), 17), shorten(student.getClassName(), 11), marks);
        }
    }

    private static void searchStudent() {
        System.out.println("\nSEARCH STUDENT");
        String regNo = readNonBlank("Enter registration number: ");
        Student student = findByRegNo(regNo);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Registration number: " + student.getRegistrationNumber());
        System.out.println("Name: " + student.getName());
        System.out.println("Subject: " + student.getSubject());
        System.out.println("Class: " + student.getClassName());
        System.out.println(
                "Marks: " + (student.hasMarks() ? String.format("%.2f / 100", student.getMarks()) : "Not recorded"));
    }

    private static Student findByRegNo(String regNo) {
        for (Student student : STUDENTS) {
            if (student.getRegistrationNumber().equalsIgnoreCase(regNo.trim())) {
                return student;
            }
        }
        return null;
    }

    private static String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            try {
                int number = Integer.parseInt(value);
                if (number >= min && number <= max)
                    return number;
            } catch (NumberFormatException ignored) {
                // Show a friendly message below and ask again.
            }
            System.out.println("Enter a whole number from " + min + " to " + max + ".");
        }
    }

    private static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String value = INPUT.nextLine().trim();
            try {
                double number = Double.parseDouble(value);
                if (Double.isFinite(number) && number >= min && number <= max)
                    return number;
            } catch (NumberFormatException ignored) {
                // Show a friendly message below and ask again.
            }
            System.out.println("Enter a number from " + min + " to " + max + ".");
        }
    }

    private static String shorten(String value, int maxLength) {
        return value.length() <= maxLength ? value : value.substring(0, maxLength - 3) + "...";
    }

    private static void prepareStorage() {
        try {
            Files.createDirectories(DATA_FILE.getParent());
            if (Files.notExists(DATA_FILE))
                Files.createFile(DATA_FILE);
        } catch (IOException e) {
            System.out.println("Could not prepare data storage: " + e.getMessage());
        }
    }

    private static void loadStudents() {
        if (Files.notExists(DATA_FILE))
            return;
        try (BufferedReader reader = Files.newBufferedReader(DATA_FILE, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank())
                    continue;
                try {
                    String[] fields = line.split("\\t", -1);
                    if (fields.length != 5)
                        continue;
                    String regNo = decode(fields[0]);
                    String name = decode(fields[1]);
                    String subject = decode(fields[2]);
                    String className = decode(fields[3]);
                    Student student = new Student(regNo, name, subject, className);
                    if (!fields[4].isEmpty())
                        student.setMarks(Double.parseDouble(fields[4]));
                    if (findByRegNo(regNo) == null)
                        STUDENTS.add(student);
                } catch (IllegalArgumentException ignored) {
                    // Ignore a damaged row so the remaining records can still load.
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load saved students: " + e.getMessage());
        }
    }

    private static void saveStudents() {
        try (BufferedWriter writer = Files.newBufferedWriter(DATA_FILE, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            for (Student student : STUDENTS) {
                writer.write(encode(student.getRegistrationNumber()) + "\t"
                        + encode(student.getName()) + "\t"
                        + encode(student.getSubject()) + "\t"
                        + encode(student.getClassName()) + "\t"
                        + (student.hasMarks() ? Double.toString(student.getMarks()) : ""));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: records could not be saved: " + e.getMessage());
        }
    }

    private static String encode(String value) {
        return value.replace("\\", "\\\\").replace("\t", "\\t").replace("\n", "\\n").replace("\r", "\\r");
    }

    private static String decode(String value) {
        StringBuilder result = new StringBuilder();
        boolean escaped = false;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (escaped) {
                if (c == 't')
                    result.append('\t');
                else if (c == 'n')
                    result.append('\n');
                else if (c == 'r')
                    result.append('\r');
                else
                    result.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else {
                result.append(c);
            }
        }
        if (escaped)
            result.append('\\');
        return result.toString();
    }

    private static class Student {
        private final String registrationNumber;
        private final String name;
        private final String subject;
        private final String className;
        private Double marks;

        Student(String registrationNumber, String name, String subject, String className) {
            if (registrationNumber == null || registrationNumber.isBlank()
                    || name == null || name.isBlank()
                    || subject == null || subject.isBlank()
                    || className == null || className.isBlank()) {
                throw new IllegalArgumentException("Student details cannot be blank.");
            }
            this.registrationNumber = registrationNumber.trim();
            this.name = name.trim();
            this.subject = subject.trim();
            this.className = className.trim();
        }

        String getRegistrationNumber() {
            return registrationNumber;
        }

        String getName() {
            return name;
        }

        String getSubject() {
            return subject;
        }

        String getClassName() {
            return className;
        }

        boolean hasMarks() {
            return marks != null;
        }

        double getMarks() {
            return marks == null ? 0.0 : marks;
        }

        void setMarks(double marks) {
            if (!Double.isFinite(marks) || marks < 0 || marks > 100) {
                throw new IllegalArgumentException("Marks must be between 0 and 100.");
            }
            this.marks = marks;
        }
    }
}
