import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Student implements Serializable{
    private String name;
    private int rollNumber;
    private int age;
    private String course;
    private String grade;
    public Student(String name, int rollNumber, int age, String grade, String course) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.age = age;
        this.grade = grade;
        this.course = course;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getRollNumber() {
        return rollNumber;
    }
    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }
    public int getage() {
        return age;
    }
    public void setage(int age) {
        this.age = age;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Age: " + age + ", Grade: " + grade + ", Course:" + course;
    }
}
class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";
    public StudentManagementSystem() {
        if (!loadDataFromFile()) {
            students = new ArrayList<>();
        }
    }
    public void addStudent(Student student) {
        students.add(student);
        saveDataToFile();
    }
    public boolean removeStudent(int rollNumber) {
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                studentToRemove = student;
                break;
            }
        }
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            saveDataToFile();
            return true;
        }
        return false;
    }
    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }
    public void editStudentDetails(int rollNumber, String name, int age, String grade, String course) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                student.setName(name);
                student.setage(age);
                student.setGrade(grade);
                student.setCourse(course);
                saveDataToFile();
                break;
            }
        }
    }
    public List<Student> getAllStudents() {
        return students;
    }
    public void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
            System.out.println("Student data saved to file: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error while saving student data: " + e.getMessage());
        }
    }
    private boolean loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                students = (List<Student>) ois.readObject();
                System.out.println("Student data loaded from file: " + FILE_NAME);
                return true;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error while loading student data: " + e.getMessage());
            }
        }
        return false;
    }
}
public class StudentManagementSystemApp {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentManagementSystem system = new StudentManagementSystem();
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("======= Student Management System =======");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Edit Student Details");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Save Data to File");
            System.out.println("7. Load Data from File");
            System.out.println("8. Exit");
            System.out.print("Enter your choice (1-8): ");
            int choice = validateIntInput(1, 8);
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    editStudentDetails();
                    break;
                case 4:
                    searchStudent();
                    break;
                case 5:
                    displayAllStudents();
                    break;
                case 6:
                    system.saveDataToFile();
                    break;
                case 7:
                    system = new StudentManagementSystem();
                    break;
                case 8:
                    system.saveDataToFile();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        }
    private static void addStudent() {
        System.out.print("Enter the name of the student: ");
        String name = validateStringInput();
        System.out.print("Enter the roll number: ");
        int rollNumber = validateIntInput();
        System.out.print("Enter the age: ");
        int age = validateIntInput();
        System.out.print("Enter the grade: ");
        String grade = validateStringInput();
        System.out.print("Enter the course: ");
        String course = validateStringInput();
        Student student = new Student(name, rollNumber, age, grade, course);
        system.addStudent(student);
        System.out.println("Student added successfully!");
    }
    private static void removeStudent() {
        System.out.print("Enter the roll number of the student to remove: ");
        int rollNumber = validateIntInput();
        if (system.removeStudent(rollNumber)) {
            System.out.println("Student removed successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }
    private static void editStudentDetails() {
        System.out.print("Enter the roll number of the student to edit: ");
        int rollNumber = validateIntInput();
        Student student = system.searchStudent(rollNumber);
        if (student != null) {
            System.out.print("Enter the new name: ");
            String name = validateStringInput();
            System.out.print("Enter the new age: ");
            int age = validateIntInput();
            System.out.print("Enter the new grade: ");
            String grade = validateStringInput();
            System.out.print("Enter the new course: ");
            String course = validateStringInput();
            system.editStudentDetails(rollNumber, name, age, grade, course);
            System.out.println("Student details updated successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }
    private static void searchStudent() {
        System.out.print("Enter the roll number of the student to search: ");
        int rollNumber = validateIntInput();
        Student student = system.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found:");
            System.out.println(student);
        } else {
            System.out.println("Student not found!");
        }
    }
    private static void displayAllStudents() {
        List<Student> students = system.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("======= All Students =======");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }
    private static String validateStringInput() {
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("This field cannot be left empty. Please try again: ");
            input = scanner.nextLine().trim();
        }
        return input;
    }
    private static int validateIntInput() {
        return validateIntInput(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    private static int validateIntInput(int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value < min || value > max) {
                    throw new NumberFormatException();
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer between " + min + " and " + max + ": ");
            }
        }
    }
}