import java.io.*;
import java.util.*;

public class StudentManagementApp {
    static class Student implements Serializable {
        private String name;
        private int rollNumber;
        private String grade;
        private String email;

        public Student(String name, int rollNumber, String grade, String email) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.grade = grade;
            this.email = email;
        }
        public int getRollNumber() { return rollNumber; }
        public String getName() { return name; }
        public String getGrade() { return grade; }
        public String getEmail() { return email; }

        public void setName(String name) { this.name = name; }
        public void setGrade(String grade) { this.grade = grade; }
        public void setEmail(String email) { this.email = email; }

        @Override
        public String toString() {
            return "Roll: " + rollNumber + ", Name: " + name + ", Grade: " + grade + ", Email: " + email;
        }
    }
    static class StudentManagementSystem {
        private List<Student> students;
        private static final String FILE_NAME = "students.dat";

        public StudentManagementSystem() {
            students = new ArrayList<>();
            loadStudents();
        }

        public void addStudent(Student student) {
            students.add(student);
            saveStudents();
        }

        public boolean removeStudent(int rollNumber) {
            boolean removed = students.removeIf(s -> s.getRollNumber() == rollNumber);
            if (removed) saveStudents();
            return removed;
        }

        public Student searchStudent(int rollNumber) {
            for (Student s : students) {
                if (s.getRollNumber() == rollNumber) return s;
            }
            return null;
        }

        public List<Student> getAllStudents() {
            return students;
        }

        public void saveStudents() {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                oos.writeObject(students);
            } catch (IOException e) {
                System.out.println("Error saving student data.");
            }
        }

        public void loadStudents() {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                students = (List<Student>) ois.readObject();
            } catch (Exception e) {
                students = new ArrayList<>();
            }
        }
    }
    static Scanner scanner = new Scanner(System.in);
    static StudentManagementSystem sms = new StudentManagementSystem();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n====== Student Management System ======");
            System.out.println("1. Add Student");
            System.out.println("2. Edit Student");
            System.out.println("3. Search Student");
            System.out.println("4. Remove Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addStudent(); break;
                case "2": editStudent(); break;
                case "3": searchStudent(); break;
                case "4": removeStudent(); break;
                case "5": displayAll(); break;
                case "6": System.out.println("Exiting..."); System.exit(0); break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

    static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter roll number: ");
        int roll = readInt();
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine().trim();
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        if (name.isEmpty() || grade.isEmpty() || email.isEmpty()) {
            System.out.println("Error: All fields must be filled.");
            return;
        }

        if (sms.searchStudent(roll) != null) {
            System.out.println("Error: Roll number already exists.");
            return;
        }

        sms.addStudent(new Student(name, roll, grade, email));
        System.out.println("Student added successfully.");
    }

    static void editStudent() {
        System.out.print("Enter roll number of student to edit: ");
        int roll = readInt();
        Student s = sms.searchStudent(roll);
        if (s == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.println("Leave input empty to keep current value.");
        System.out.print("New name (current: " + s.getName() + "): ");
        String name = scanner.nextLine().trim();
        System.out.print("New grade (current: " + s.getGrade() + "): ");
        String grade = scanner.nextLine().trim();
        System.out.print("New email (current: " + s.getEmail() + "): ");
        String email = scanner.nextLine().trim();

        if (!name.isEmpty()) s.setName(name);
        if (!grade.isEmpty()) s.setGrade(grade);
        if (!email.isEmpty()) s.setEmail(email);

        sms.saveStudents();
        System.out.println("Student updated.");
    }

    static void searchStudent() {
        System.out.print("Enter roll number to search: ");
        int roll = readInt();
        Student s = sms.searchStudent(roll);
        if (s == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(s);
        }
    }

    static void removeStudent() {
        System.out.print("Enter roll number to remove: ");
        int roll = readInt();
        if (sms.removeStudent(roll)) {
            System.out.println("Student removed.");
        } else {
            System.out.println("Student not found.");
        }
    }

    static void displayAll() {
        List<Student> list = sms.getAllStudents();
        if (list.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            System.out.println("\n--- Student List ---");
            for (Student s : list) {
                System.out.println(s);
            }
        }
    }

    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }
}
