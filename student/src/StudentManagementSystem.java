import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentDAO studentDAO = new StudentDAO();
    
    public static void main(String[] args) {
        System.out.println("=== Student Management System ===");
        System.out.println("Welcome to the Student Database Application!");
        
        if (DatabaseConnection.getInstance().getConnection() == null) {
            System.err.println("Failed to connect to database. Please check your configuration.");
            return;
        }
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addNewStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    viewStudentById();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 6:
                    searchStudentsByName();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        DatabaseConnection.getInstance().closeConnection();
        System.out.println("Thank you for using Student Management System!");
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("STUDENT MANAGEMENT SYSTEM - MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. View Student by ID");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Search Students by Name");
        System.out.println("7. Exit");
        System.out.println("=".repeat(50));
    }
    
    private static void addNewStudent() {
        System.out.println("\n--- Add New Student ---");
        
        String firstName = getValidatedStringInput("Enter first name: ", InputValidator::isValidName, "Invalid name. Please enter a valid first name.");
        String lastName = getValidatedStringInput("Enter last name: ", InputValidator::isValidName, "Invalid name. Please enter a valid last name.");
        String email = getValidatedStringInput("Enter email: ", InputValidator::isValidEmail, "Invalid email format. Please enter a valid email.");
        String phoneNumber = getValidatedStringInput("Enter phone number: ", InputValidator::isValidPhone, "Invalid phone number. Please enter a valid phone number.");
        String major = getValidatedStringInput("Enter major: ", InputValidator::isValidMajor, "Invalid major. Please enter a valid major.");
        
        double gpa;
        do {
            gpa = getDoubleInput("Enter GPA (0.0 - 4.0): ");
            if (!InputValidator.isValidGPA(gpa)) {
                System.out.println("Invalid GPA. Please enter a value between 0.0 and 4.0.");
            }
        } while (!InputValidator.isValidGPA(gpa));
        
        Student student = new Student(firstName, lastName, email, phoneNumber, major, gpa);
        
        if (studentDAO.insertStudent(student)) {
            System.out.println("Student added successfully!");
            System.out.println("Student Details: " + student);
        } else {
            System.out.println("Failed to add student.");
        }
    }
    
    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found in the database.");
        } else {
            System.out.printf("%-5s %-15s %-15s %-30s %-15s %-20s %-6s%n", 
                            "ID", "First Name", "Last Name", "Email", "Phone", "Major", "GPA");
            System.out.println("=".repeat(110));
            
            for (Student student : students) {
                System.out.printf("%-5d %-15s %-15s %-30s %-15s %-20s %-6.2f%n",
                    student.getStudentId(), student.getFirstName(), student.getLastName(),
                    student.getEmail(), student.getPhoneNumber(), student.getMajor(), student.getGpa());
            }
            System.out.println("\nTotal students: " + students.size());
        }
    }
    
    private static void viewStudentById() {
        System.out.println("\n--- View Student by ID ---");
        int studentId = getIntInput("Enter student ID: ");
        
        Student student = studentDAO.getStudentById(studentId);
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("No student found with ID: " + studentId);
        }
    }
    
    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        int studentId = getIntInput("Enter student ID to update: ");
        
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("No student found with ID: " + studentId);
            return;
        }
        
        System.out.println("Current student details:");
        System.out.println(student);
        System.out.println("\nEnter new information (press Enter to keep current value):");
        
        String firstName = getOptionalStringInput("Enter new first name [" + student.getFirstName() + "]: ");
        if (!firstName.isEmpty() && InputValidator.isValidName(firstName)) {
            student.setFirstName(firstName);
        }
        
        String lastName = getOptionalStringInput("Enter new last name [" + student.getLastName() + "]: ");
        if (!lastName.isEmpty() && InputValidator.isValidName(lastName)) {
            student.setLastName(lastName);
        }
        
        String email = getOptionalStringInput("Enter new email [" + student.getEmail() + "]: ");
        if (!email.isEmpty() && InputValidator.isValidEmail(email)) {
            student.setEmail(email);
        }
        
        String phoneNumber = getOptionalStringInput("Enter new phone number [" + student.getPhoneNumber() + "]: ");
        if (!phoneNumber.isEmpty() && InputValidator.isValidPhone(phoneNumber)) {
            student.setPhoneNumber(phoneNumber);
        }
        
        String major = getOptionalStringInput("Enter new major [" + student.getMajor() + "]: ");
        if (!major.isEmpty() && InputValidator.isValidMajor(major)) {
            student.setMajor(major);
        }
        
        String gpaInput = getOptionalStringInput("Enter new GPA [" + student.getGpa() + "]: ");
        if (!gpaInput.isEmpty()) {
            try {
                double gpa = Double.parseDouble(gpaInput);
                if (InputValidator.isValidGPA(gpa)) {
                    student.setGpa(gpa);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid GPA format. Keeping current value.");
            }
        }
        
        if (studentDAO.updateStudent(student)) {
            System.out.println("Student updated successfully!");
            System.out.println("Updated details: " + student);
        } else {
            System.out.println("Failed to update student.");
        }
    }
    
    private static void deleteStudent() {
        System.out.println("\n--- Delete Student ---");
        int studentId = getIntInput("Enter student ID to delete: ");
        
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            System.out.println("No student found with ID: " + studentId);
            return;
        }
        
        System.out.println("Student to be deleted:");
        System.out.println(student);
        
        String confirmation = getStringInput("Are you sure you want to delete this student? (yes/no): ");
        if (confirmation.equalsIgnoreCase("yes") || confirmation.equalsIgnoreCase("y")) {
            if (studentDAO.deleteStudent(studentId)) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Failed to delete student.");
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }
    
    private static void searchStudentsByName() {
        System.out.println("\n--- Search Students by Name ---");
        String name = getStringInput("Enter name to search: ");
        
        List<Student> students = studentDAO.searchStudentsByName(name);
        
        if (students.isEmpty()) {
            System.out.println("No students found matching: " + name);
        } else {
            System.out.println("Students found matching '" + name + "':");
            System.out.printf("%-5s %-15s %-15s %-30s %-15s %-20s %-6s%n", 
                            "ID", "First Name", "Last Name", "Email", "Phone", "Major", "GPA");
            System.out.println("=".repeat(110));
            
            for (Student student : students) {
                System.out.printf("%-5d %-15s %-15s %-30s %-15s %-20s %-6.2f%n",
                    student.getStudentId(), student.getFirstName(), student.getLastName(),
                    student.getEmail(), student.getPhoneNumber(), student.getMajor(), student.getGpa());
            }
            System.out.println("\nTotal matches: " + students.size());
        }
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static String getOptionalStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
    
    private static String getValidatedStringInput(String prompt, java.util.function.Predicate<String> validator, String errorMessage) {
        String input;
        do {
            input = getStringInput(prompt);
            if (!validator.test(input)) {
                System.out.println(errorMessage);
            }
        } while (!validator.test(input));
        return input;
    }
}