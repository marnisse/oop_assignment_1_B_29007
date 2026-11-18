package com.auca.main;

import com.auca.abstracts.Person;
import com.auca.abstracts.AcademicMember;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.teacher.Teacher;
import com.auca.administrator.Administrator;
import com.auca.department.Department;
import com.auca.course.Course;
import com.auca.classroom.Classroom;
import com.auca.enrollment.Enrollment;
import com.auca.assessment.Assessment;
import com.auca.feepayment.FeePayment;
import com.auca.librarybook.LibraryBook;
import com.auca.bookloan.BookLoan;
import com.auca.exceptions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;


public class Main {

    private static final Scanner sc = new Scanner(System.in);

    // In-memory collections
    private static final List<Department> departments = new ArrayList<>();
    private static final List<Student> students = new ArrayList<>();
    private static final List<Teacher> teachers = new ArrayList<>();
    private static final List<Administrator> administrators = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<Classroom> classrooms = new ArrayList<>();
    private static final List<Enrollment> enrollments = new ArrayList<>();
    private static final List<Assessment> assessments = new ArrayList<>();
    private static final List<FeePayment> feePayments = new ArrayList<>();
    private static final List<LibraryBook> libraryBooks = new ArrayList<>();
    private static final List<BookLoan> bookLoans = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("   AUCA_ID SCHOOL MANAGEMENT SYSTEM");
        System.out.println("   Console OOP Practical Assignment");
        System.out.println("================================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = readLine("Select an option: ");
            try {
                switch (choice.trim()) {
                    case "1"  -> registerDepartment();
                    case "2"  -> registerStudent();
                    case "3"  -> registerTeacher();
                    case "4"  -> registerAdministrator();
                    case "5"  -> registerCourse();
                    case "6"  -> registerClassroom();
                    case "7"  -> enrollStudent();
                    case "8"  -> recordAssessment();
                    case "9"  -> recordFeePayment();
                    case "10" -> registerLibraryBook();
                    case "11" -> borrowBook();
                    case "12" -> returnBook();
                    case "13" -> viewAllRecords();
                    case "14" -> searchById();
                    case "15" -> updateRecord();
                    case "16" -> showStudentAcademicSummary();
                    case "17" -> showPaymentHistory();
                    case "18" -> showAvailableBooks();
                    case "19" -> demonstratePolymorphism();
                    case "0"  -> {
                        System.out.println("Thank you for using AUCA_ID. Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("! Invalid option. Please choose 0-19.");
                }
            } catch (Exception e) {
                System.out.println("! Error: " + e.getMessage());
                System.out.println("  Returning to main menu...");
            }
        }
        sc.close();
    }

    // ==================== MENU ====================
    private static void printMainMenu() {
        System.out.println();
        System.out.println("============== AUCA_ID SCHOOL MANAGEMENT ==============");
        System.out.println(" 1. Register Department       11. Borrow Library Book");
        System.out.println(" 2. Register Student          12. Return Library Book");
        System.out.println(" 3. Register Teacher          13. View All Records");
        System.out.println(" 4. Register Administrator    14. Search Record by ID");
        System.out.println(" 5. Register Course           15. Update Existing Record");
        System.out.println(" 6. Register Classroom        16. Student Academic Summary");
        System.out.println(" 7. Enroll Student            17. Student Payment History");
        System.out.println(" 8. Record Assessment         18. View Available Books");
        System.out.println(" 9. Record Fee Payment        19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book      0. Exit");
        System.out.println("=======================================================");
    }

    // ==================== HELPERS ====================
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static String readRequired(String prompt) {
        while (true) {
            String v = readLine(prompt);
            if (v.equalsIgnoreCase("CANCEL") || v.equals("0") && prompt.toLowerCase().contains("cancel")) {
                return null;
            }
            if (!v.isEmpty()) return v;
            System.out.println("! Value cannot be blank. Re-enter (or type CANCEL):");
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            String v = readLine(prompt);
            if (v.equalsIgnoreCase("CANCEL")) return null;
            try {
                return LocalDate.parse(v);
            } catch (DateTimeParseException e) {
                System.out.println("! Invalid date. Use YYYY-MM-DD (example: 2004-05-16). Re-enter:");
            }
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            String v = readLine(prompt);
            if (v.equalsIgnoreCase("CANCEL")) return -999;
            try {
                int n = Integer.parseInt(v);
                if (n >= min && n <= max) return n;
                System.out.println("! Enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("! Invalid number. Re-enter:");
            }
        }
    }

    private static BigDecimal readMoney(String prompt) {
        while (true) {
            String v = readLine(prompt);
            if (v.equalsIgnoreCase("CANCEL")) return null;
            try {
                BigDecimal bd = new BigDecimal(v);
                if (bd.compareTo(BigDecimal.ZERO) > 0) return bd;
                System.out.println("! Amount must be strictly positive.");
            } catch (NumberFormatException e) {
                System.out.println("! Invalid amount. Re-enter:");
            }
        }
    }

    private static boolean idExists(String id) {
        for (Department d : departments) if (d.getId().equalsIgnoreCase(id)) return true;
        for (Student s : students) if (s.getId().equalsIgnoreCase(id) || s.getStudentNumber().equalsIgnoreCase(id)) return true;
        for (Teacher t : teachers) if (t.getId().equalsIgnoreCase(id) || t.getEmployeeNumber().equalsIgnoreCase(id)) return true;
        for (Administrator a : administrators) if (a.getId().equalsIgnoreCase(id) || a.getStaffNumber().equalsIgnoreCase(id)) return true;
        for (Course c : courses) if (c.getId().equalsIgnoreCase(id) || c.getCourseCode().equalsIgnoreCase(id)) return true;
        for (Classroom cl : classrooms) if (cl.getId().equalsIgnoreCase(id)) return true;
        for (Enrollment e : enrollments) if (e.getId().equalsIgnoreCase(id)) return true;
        for (Assessment a : assessments) if (a.getId().equalsIgnoreCase(id)) return true;
        for (FeePayment f : feePayments) if (f.getId().equalsIgnoreCase(id) || f.getReceiptNumber().equalsIgnoreCase(id)) return true;
        for (LibraryBook b : libraryBooks) if (b.getId().equalsIgnoreCase(id)) return true;
        for (BookLoan l : bookLoans) if (l.getId().equalsIgnoreCase(id)) return true;
        return false;
    }

    // ==================== 1. DEPARTMENT ====================
    private static void registerDepartment() {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID  /  SCHOOL MANAGEMENT                              |");
        System.out.println("| DEPARTMENT REGISTRATION                                    |");
        System.out.println("+------------------------------------------------------------+");

        String id = readRequired("Department ID [String | unique]: ");
        if (id == null) return;
        if (idExists(id)) {
            System.out.println("! ID already exists. Cancelled.");
            return;
        }
        String name = readRequired("Department Name [2-60 chars]: ");
        if (name == null) return;
        String code = readRequired("Department Code [unique]: ");
        if (code == null) return;
        String loc = readRequired("Office Location: ");
        if (loc == null) return;
        String email = readRequired("Contact Email: ");
        if (email == null) return;

        Teacher head = null;
        if (!teachers.isEmpty()) {
            System.out.println("Head Teacher (optional):");
            for (int i = 0; i < teachers.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + teachers.get(i).getRegistrationSummary());
            }
            System.out.println("  0. Skip / None");
            int sel = readInt("Select: ", 0, teachers.size());
            if (sel > 0) head = teachers.get(sel - 1);
        }

        Department d = new Department(id, name, code, loc, email, head);
        try {
            d.validate();
            System.out.println("\n--- REVIEW ---");
            d.printDetails();
            String conf = readLine("[1] Save  [0] Cancel: ");
            if ("1".equals(conf)) {
                departments.add(d);
                System.out.println("+ Department " + d.getDepartmentCode() + " registered successfully.");
            } else {
                System.out.println("Cancelled. No record saved.");
            }
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    // ==================== 2. STUDENT (Smart multi-step form) ====================
    private static void registerStudent() {
        System.out.println("\n+------------------------------------------------------------+");
        System.out.println("| AUCA_ID  /  SCHOOL MANAGEMENT                              |");
        System.out.println("| STUDENT REGISTRATION                     Step 1 of 4       |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| [1] Personal details    [2] Academic details               |");
        System.out.println("| [3] Student details     [4] Review & confirm               |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| Type CANCEL at any prompt to discard.                      |");
        System.out.println("+------------------------------------------------------------+");

        // ---- STEP 1: Person ----
        System.out.println("\n[STEP 1/4] PERSONAL DETAILS");
        String personId = readRequired("01  Person ID          [String | unique | 4-30]: ");
        if (personId == null) return;
        if (idExists(personId)) { System.out.println("! Person ID already exists."); return; }

        String firstName = readRequired("02  First name         [String | 2-60]: ");
        if (firstName == null) return;
        String lastName = readRequired("03  Last name          [String | 2-60]: ");
        if (lastName == null) return;
        String email = readRequired("04  Email              [String | unique | email]: ");
        if (email == null) return;
        String phone = readRequired("05  Phone              [String | 10-15 digits]: ");
        if (phone == null) return;
        LocalDate dob = readDate("06  Date of birth      [LocalDate | YYYY-MM-DD]: ");
        if (dob == null) return;

        // ---- STEP 2: Academic ----
        System.out.println("\n[STEP 2/4] ACADEMIC DETAILS");
        String memberNum = readRequired("07  Member number      [String | unique | 4-30]: ");
        if (memberNum == null) return;

        Department dept = selectOrCreateDepartment();
        if (dept == null) {
            System.out.println("Department required. Cancelled.");
            return;
        }

        LocalDate joinDate = readDate("09  Join date          [LocalDate | YYYY-MM-DD]: ");
        if (joinDate == null) return;
        System.out.println("10  Academic status    [1 ACTIVE / 2 INACTIVE]");
        int st = readInt("Select: ", 1, 2);
        if (st == -999) return;
        String academicStatus = (st == 1) ? "ACTIVE" : "INACTIVE";
        String instEmail = readRequired("11  Institutional email[String | unique | email]: ");
        if (instEmail == null) return;
        String advisor = readRequired("12  Advisor name       [String | 2-120]: ");
        if (advisor == null) return;

        // ---- STEP 3: Student ----
        System.out.println("\n[STEP 3/4] STUDENT DETAILS");
        String stuNum = readRequired("13  Student number     [String | unique | 4-30]: ");
        if (stuNum == null) return;
        if (idExists(stuNum)) { System.out.println("! Student number already exists."); return; }
        String program = readRequired("14  Program name       [String | 2-120]: ");
        if (program == null) return;
        LocalDate admDate = readDate("15  Admission date     [LocalDate | YYYY-MM-DD]: ");
        if (admDate == null) return;
        int year = readInt("16  Study year         [int | 1-6]: ", 1, 6);
        if (year == -999) return;
        String guardPhone = readRequired("17  Guardian phone     [String | 10-15 digits]: ");
        if (guardPhone == null) return;
        System.out.println("18  Scholarship        [1 YES / 2 NO]");
        int sch = readInt("Select: ", 1, 2);
        if (sch == -999) return;
        boolean scholarship = (sch == 1);

        // ---- STEP 4: Review ----
        Student s = new Student(personId, firstName, lastName, email, phone, dob,
                memberNum, dept, joinDate, academicStatus, instEmail, advisor,
                stuNum, program, admDate, year, guardPhone, scholarship);

        try {
            s.validate();
            System.out.println("\n+---------------- REVIEW STUDENT ---------------------------+");
            System.out.println("| PERSON                                                    |");
            System.out.println("| 01 Person ID          : " + personId);
            System.out.println("| 02 First name         : " + firstName);
            System.out.println("| 03 Last name          : " + lastName);
            System.out.println("| 04 Email              : " + email);
            System.out.println("| 05 Phone              : " + phone);
            System.out.println("| 06 Date of birth      : " + dob);
            System.out.println("| ACADEMIC MEMBER                                           |");
            System.out.println("| 07 Member number      : " + memberNum);
            System.out.println("| 08 Department         : " + dept.getDisplayName());
            System.out.println("| 09 Join date          : " + joinDate);
            System.out.println("| 10 Academic status    : " + academicStatus);
            System.out.println("| 11 Institutional email: " + instEmail);
            System.out.println("| 12 Advisor name       : " + advisor);
            System.out.println("| STUDENT                                                   |");
            System.out.println("| 13 Student number     : " + stuNum);
            System.out.println("| 14 Program name       : " + program);
            System.out.println("| 15 Admission date     : " + admDate);
            System.out.println("| 16 Study year         : " + year);
            System.out.println("| 17 Guardian phone     : " + guardPhone);
            System.out.println("| 18 Scholarship        : " + (scholarship ? "YES" : "NO"));
            System.out.println("+------------------------------------------------------------+");
            System.out.println("| [1] Save student  [2] Edit (not fully interactive)  [0] Cancel |");
            System.out.println("+------------------------------------------------------------+");
            String conf = readLine("Select option: ");
            if ("1".equals(conf)) {
                students.add(s);
                System.out.println("+ Student " + stuNum + " registered successfully.");
            } else {
                System.out.println("Cancelled. No record saved.");
            }
        } catch (ValidationException e) {
            System.out.println("! Validation failed: " + e.getMessage());
        }
    }

    private static Department selectOrCreateDepartment() {
        while (true) {
            if (departments.isEmpty()) {
                System.out.println("No Department is registered.");
                System.out.println("1. Register Department now   0. Cancel");
                String c = readLine("Select: ");
                if ("1".equals(c)) {
                    registerDepartment();
                    if (departments.isEmpty()) return null;
                } else {
                    return null;
                }
            }
            System.out.println("+----------------- CHOOSE DEPARTMENT ------------------------+");
            for (int i = 0; i < departments.size(); i++) {
                System.out.println("| " + (i + 1) + "  " + departments.get(i).getRegistrationSummary());
            }
            System.out.println("| " + (departments.size() + 1) + "  [+] Register a new Department");
            System.out.println("| 0  Cancel");
            System.out.println("+------------------------------------------------------------+");
            int sel = readInt("Select option: ", 0, departments.size() + 1);
            if (sel == 0 || sel == -999) return null;
            if (sel == departments.size() + 1) {
                registerDepartment();
                continue;
            }
            return departments.get(sel - 1);
        }
    }

    // ==================== 3. TEACHER ====================
    private static void registerTeacher() {
        System.out.println("\n------------- REGISTER TEACHER -------------");
        if (departments.isEmpty()) {
            System.out.println("No Department exists. Register a Department first?");
            System.out.println("1. Register Department now   0. Cancel");
            if (!"1".equals(readLine("Select: "))) return;
            registerDepartment();
            if (departments.isEmpty()) return;
        }

        String personId = readRequired("Person ID [unique]: ");
        if (personId == null || idExists(personId)) { System.out.println("! Invalid or duplicate ID."); return; }
        String first = readRequired("First name: ");
        if (first == null) return;
        String last = readRequired("Last name: ");
        if (last == null) return;
        String email = readRequired("Email: ");
        if (email == null) return;
        String phone = readRequired("Phone: ");
        if (phone == null) return;
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");
        if (dob == null) return;
        String mem = readRequired("Member number: ");
        if (mem == null) return;
        Department dept = selectOrCreateDepartment();
        if (dept == null) return;
        LocalDate join = readDate("Join date (YYYY-MM-DD): ");
        if (join == null) return;
        String status = "ACTIVE";
        String instEmail = readRequired("Institutional email: ");
        if (instEmail == null) return;
        String advisor = readRequired("Advisor name: ");
        if (advisor == null) return;
        String empNum = readRequired("Employee number [unique]: ");
        if (empNum == null || idExists(empNum)) { System.out.println("! Duplicate employee number."); return; }
        String spec = readRequired("Specialization: ");
        if (spec == null) return;
        LocalDate hire = readDate("Hire date (YYYY-MM-DD): ");
        if (hire == null) return;
        BigDecimal salary = readMoney("Salary (positive): ");
        if (salary == null) return;
        String office = readRequired("Office number: ");
        if (office == null) return;
        String empType = readRequired("Employment type (FULL_TIME/PART_TIME): ");
        if (empType == null) return;

        Teacher t = new Teacher(personId, first, last, email, phone, dob,
                mem, dept, join, status, instEmail, advisor,
                empNum, spec, hire, salary, office, empType);
        try {
            t.validate();
            t.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                teachers.add(t);
                System.out.println("+ Teacher " + empNum + " registered successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 4. ADMINISTRATOR ====================
    private static void registerAdministrator() {
        System.out.println("\n------------- REGISTER ADMINISTRATOR -------------");
        String personId = readRequired("Person ID [unique]: ");
        if (personId == null || idExists(personId)) { System.out.println("! Invalid/duplicate."); return; }
        String first = readRequired("First name: ");
        if (first == null) return;
        String last = readRequired("Last name: ");
        if (last == null) return;
        String email = readRequired("Email: ");
        if (email == null) return;
        String phone = readRequired("Phone: ");
        if (phone == null) return;
        LocalDate dob = readDate("Date of birth (YYYY-MM-DD): ");
        if (dob == null) return;
        String staff = readRequired("Staff number [unique]: ");
        if (staff == null || idExists(staff)) { System.out.println("! Duplicate."); return; }
        String title = readRequired("Job title: ");
        if (title == null) return;
        String office = readRequired("Assigned office: ");
        if (office == null) return;
        LocalDate empDate = readDate("Employment date (YYYY-MM-DD): ");
        if (empDate == null) return;
        int level = readInt("Access level (1-10): ", 1, 10);
        if (level == -999) return;
        System.out.println("Active? [1 YES / 2 NO]");
        int act = readInt("Select: ", 1, 2);
        if (act == -999) return;

        Administrator a = new Administrator(personId, first, last, email, phone, dob,
                staff, title, office, empDate, level, act == 1);
        try {
            a.validate();
            a.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                administrators.add(a);
                System.out.println("+ Administrator " + staff + " registered successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 5. COURSE ====================
    private static void registerCourse() {
        System.out.println("\n------------- REGISTER COURSE -------------");
        if (departments.isEmpty() || teachers.isEmpty()) {
            System.out.println("Course requires Department and Teacher.");
            if (departments.isEmpty()) {
                System.out.println("No Department. 1. Register now  0. Cancel");
                if (!"1".equals(readLine("Select: "))) return;
                registerDepartment();
            }
            if (teachers.isEmpty()) {
                System.out.println("No Teacher. 1. Register now  0. Cancel");
                if (!"1".equals(readLine("Select: "))) return;
                registerTeacher();
            }
            if (departments.isEmpty() || teachers.isEmpty()) return;
        }

        String id = readRequired("Course ID [unique]: ");
        if (id == null || idExists(id)) { System.out.println("! Invalid/duplicate."); return; }
        String code = readRequired("Course Code [unique]: ");
        if (code == null || idExists(code)) { System.out.println("! Duplicate code."); return; }
        String title = readRequired("Course Title: ");
        if (title == null) return;
        int credits = readInt("Credit hours (1-6): ", 1, 6);
        if (credits == -999) return;

        System.out.println("Select Department:");
        for (int i = 0; i < departments.size(); i++)
            System.out.println("  " + (i + 1) + ". " + departments.get(i).getRegistrationSummary());
        int dsel = readInt("Select: ", 1, departments.size());
        if (dsel == -999) return;
        Department dept = departments.get(dsel - 1);

        System.out.println("Select Teacher:");
        for (int i = 0; i < teachers.size(); i++)
            System.out.println("  " + (i + 1) + ". " + teachers.get(i).getRegistrationSummary());
        int tsel = readInt("Select: ", 1, teachers.size());
        if (tsel == -999) return;
        Teacher tch = teachers.get(tsel - 1);

        Course c = new Course(id, code, title, credits, dept, tch);
        try {
            c.validate();
            c.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                courses.add(c);
                System.out.println("+ Course " + code + " registered successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 6. CLASSROOM ====================
    private static void registerClassroom() {
        System.out.println("\n------------- REGISTER CLASSROOM -------------");
        String id = readRequired("Classroom ID [unique]: ");
        if (id == null || idExists(id)) { System.out.println("! Invalid/duplicate."); return; }
        String bldg = readRequired("Building name: ");
        if (bldg == null) return;
        String room = readRequired("Room number: ");
        if (room == null) return;
        int cap = readInt("Capacity (>0): ", 1, 500);
        if (cap == -999) return;
        int floor = readInt("Floor number (>=0): ", 0, 50);
        if (floor == -999) return;
        System.out.println("Available? [1 YES / 2 NO]");
        int av = readInt("Select: ", 1, 2);
        if (av == -999) return;

        Classroom cl = new Classroom(id, bldg, room, cap, floor, av == 1);
        try {
            cl.validate();
            cl.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                classrooms.add(cl);
                System.out.println("+ Classroom " + id + " registered successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 7. ENROLL STUDENT ====================
    private static void enrollStudent() {
        System.out.println("\n-------------- ENROLL STUDENT --------------");
        if (students.isEmpty()) {
            System.out.println("No Student is registered.");
            System.out.println("1. Register Student now   0. Cancel");
            if (!"1".equals(readLine("Select: "))) return;
            registerStudent();
            if (students.isEmpty()) return;
        }
        if (courses.isEmpty()) {
            System.out.println("No Course is registered.");
            System.out.println("1. Register Course now   0. Cancel enrollment");
            if (!"1".equals(readLine("Select: "))) return;
            registerCourse();
            if (courses.isEmpty()) return;
            System.out.println("Course saved. Returning to Enrollment...");
        }

        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i + 1) + ". " + students.get(i).getRegistrationSummary());
        int ssel = readInt("Select: ", 1, students.size());
        if (ssel == -999) return;
        Student stu = students.get(ssel - 1);

        System.out.println("Select Course:");
        for (int i = 0; i < courses.size(); i++)
            System.out.println("  " + (i + 1) + ". " + courses.get(i).getRegistrationSummary());
        int csel = readInt("Select: ", 1, courses.size());
        if (csel == -999) return;
        Course crs = courses.get(csel - 1);

        String semester = readRequired("Semester (e.g. 2026-S1): ");
        if (semester == null) return;

        // Duplicate check
        for (Enrollment e : enrollments) {
            if (e.getStudent() == stu && e.getCourse() == crs &&
                e.getSemester().equalsIgnoreCase(semester) && e.isActive()) {
                System.out.println("! Duplicate active enrollment for same student, course and semester.");
                return;
            }
        }

        String enrId = "ENR" + (enrollments.size() + 1);
        LocalDate enrDate = LocalDate.now();
        Enrollment enr = new Enrollment(enrId, stu, crs, enrDate, semester, "ACTIVE");
        try {
            enr.validate();
            enr.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                enrollments.add(enr);
                System.out.println("+ Enrollment " + enrId + " recorded successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 8. ASSESSMENT ====================
    private static void recordAssessment() {
        System.out.println("\n------------- RECORD ASSESSMENT -------------");
        if (enrollments.isEmpty()) {
            System.out.println("No Enrollment exists. Register enrollment first.");
            return;
        }
        List<Enrollment> active = new ArrayList<>();
        for (Enrollment e : enrollments) if (e.isActive()) active.add(e);
        if (active.isEmpty()) {
            System.out.println("No active enrollment found.");
            return;
        }

        System.out.println("Select active Enrollment:");
        for (int i = 0; i < active.size(); i++)
            System.out.println("  " + (i + 1) + ". " + active.get(i).getRegistrationSummary());
        int sel = readInt("Select: ", 1, active.size());
        if (sel == -999) return;
        Enrollment enr = active.get(sel - 1);

        String type = readRequired("Assessment type (QUIZ/MIDTERM/FINAL/ASSIGNMENT): ");
        if (type == null) return;
        double max = 0;
        while (true) {
            String v = readLine("Maximum score (>0): ");
            try {
                max = Double.parseDouble(v);
                if (max > 0) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("! Enter a positive number.");
        }
        double obtained = -1;
        while (true) {
            String v = readLine("Obtained score (0-" + max + "): ");
            try {
                obtained = Double.parseDouble(v);
                if (obtained >= 0 && obtained <= max) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("! Score must be between 0 and " + max);
        }
        LocalDate adate = readDate("Assessment date (YYYY-MM-DD): ");
        if (adate == null) return;

        String aid = "ASM" + (assessments.size() + 1);
        Assessment a = new Assessment(aid, enr, type, max, obtained, adate);
        try {
            a.validate();
            a.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                assessments.add(a);
                System.out.println("+ Assessment " + aid + " recorded successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 9. FEE PAYMENT ====================
    private static void recordFeePayment() {
        System.out.println("\n------------- RECORD FEE PAYMENT -------------");
        if (students.isEmpty()) {
            System.out.println("No Student exists.");
            return;
        }
        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i + 1) + ". " + students.get(i).getRegistrationSummary());
        int sel = readInt("Select: ", 1, students.size());
        if (sel == -999) return;
        Student stu = students.get(sel - 1);

        BigDecimal amount = readMoney("Amount (positive): ");
        if (amount == null) return;
        LocalDate pdate = readDate("Payment date (YYYY-MM-DD): ");
        if (pdate == null) return;
        String method = readRequired("Payment method (CASH/BANK/MOBILE): ");
        if (method == null) return;
        String receipt = readRequired("Receipt number [unique]: ");
        if (receipt == null) return;
        for (FeePayment f : feePayments) {
            if (f.getReceiptNumber().equalsIgnoreCase(receipt)) {
                System.out.println("! Duplicate receipt number.");
                return;
            }
        }

        String pid = "PAY" + (feePayments.size() + 1);
        FeePayment fp = new FeePayment(pid, stu, amount, pdate, method, receipt);
        try {
            fp.validate();
            fp.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                feePayments.add(fp);
                System.out.println("+ Payment " + receipt + " recorded successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 10. LIBRARY BOOK ====================
    private static void registerLibraryBook() {
        System.out.println("\n------------- REGISTER LIBRARY BOOK -------------");
        String id = readRequired("Book ID [unique]: ");
        if (id == null || idExists(id)) { System.out.println("! Invalid/duplicate."); return; }
        String isbn = readRequired("ISBN: ");
        if (isbn == null) return;
        String title = readRequired("Title: ");
        if (title == null) return;
        String author = readRequired("Author: ");
        if (author == null) return;
        int total = readInt("Total copies (>=0): ", 0, 1000);
        if (total == -999) return;
        int avail = readInt("Available copies (0-" + total + "): ", 0, total);
        if (avail == -999) return;

        LibraryBook b = new LibraryBook(id, isbn, title, author, total, avail);
        try {
            b.validate();
            b.printDetails();
            if ("1".equals(readLine("[1] Save  [0] Cancel: "))) {
                libraryBooks.add(b);
                System.out.println("+ Book " + id + " registered successfully.");
            }
        } catch (ValidationException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 11. BORROW BOOK ====================
    private static void borrowBook() {
        System.out.println("\n------------- BORROW LIBRARY BOOK -------------");
        if (students.isEmpty() || libraryBooks.isEmpty()) {
            System.out.println("Student and LibraryBook must exist.");
            return;
        }
        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i + 1) + ". " + students.get(i).getRegistrationSummary());
        int ssel = readInt("Select: ", 1, students.size());
        if (ssel == -999) return;
        Student stu = students.get(ssel - 1);

        System.out.println("Select Book (available only):");
        List<LibraryBook> avail = new ArrayList<>();
        for (LibraryBook b : libraryBooks) {
            if (b.getAvailableCopies() > 0) avail.add(b);
        }
        if (avail.isEmpty()) {
            System.out.println("! No books available for borrowing.");
            return;
        }
        for (int i = 0; i < avail.size(); i++)
            System.out.println("  " + (i + 1) + ". " + avail.get(i).getRegistrationSummary());
        int bsel = readInt("Select: ", 1, avail.size());
        if (bsel == -999) return;
        LibraryBook book = avail.get(bsel - 1);

        LocalDate borrowed = LocalDate.now();
        LocalDate due = borrowed.plusDays(14);
        String loanId = "LOAN" + (bookLoans.size() + 1);

        BookLoan loan = new BookLoan(loanId, stu, book, borrowed, due, null);
        try {
            loan.validate();
            if (book.getAvailableCopies() <= 0) {
                throw new BookUnavailableException("No copies available for this book.");
            }
            loan.printDetails();
            if ("1".equals(readLine("[1] Confirm Borrow  [0] Cancel: "))) {
                book.decrementAvailable();
                bookLoans.add(loan);
                System.out.println("+ Book borrowed successfully. Loan ID: " + loanId);
                System.out.println("  Available copies now: " + book.getAvailableCopies());
            }
        } catch (ValidationException | BookUnavailableException e) {
            System.out.println("! " + e.getMessage());
        }
    }

    // ==================== 12. RETURN BOOK ====================
    private static void returnBook() {
        System.out.println("\n------------- RETURN LIBRARY BOOK -------------");
        List<BookLoan> active = new ArrayList<>();
        for (BookLoan l : bookLoans) if (l.isActive()) active.add(l);
        if (active.isEmpty()) {
            System.out.println("No active loans found.");
            return;
        }
        System.out.println("Select Loan to return:");
        for (int i = 0; i < active.size(); i++)
            System.out.println("  " + (i + 1) + ". " + active.get(i).getRegistrationSummary());
        int sel = readInt("Select: ", 1, active.size());
        if (sel == -999) return;
        BookLoan loan = active.get(sel - 1);

        LocalDate retDate = LocalDate.now();
        loan.setReturnedDate(retDate);
        loan.getBook().incrementAvailable();
        System.out.println("+ Book returned successfully.");
        System.out.println("  Available copies now: " + loan.getBook().getAvailableCopies());
        loan.printDetails();
    }

    // ==================== 13. VIEW ALL ====================
    private static void viewAllRecords() {
        System.out.println("\n============= ALL RECORDS =============");
        printList("DEPARTMENTS", departments);
        printList("STUDENTS", students);
        printList("TEACHERS", teachers);
        printList("ADMINISTRATORS", administrators);
        printList("COURSES", courses);
        printList("CLASSROOMS", classrooms);
        printList("ENROLLMENTS", enrollments);
        printList("ASSESSMENTS", assessments);
        printList("FEE PAYMENTS", feePayments);
        printList("LIBRARY BOOKS", libraryBooks);
        printList("BOOK LOANS", bookLoans);
    }

    private static void printList(String title, List<?> list) {
        System.out.println("\n--- " + title + " (" + list.size() + ") ---");
        if (list.isEmpty()) {
            System.out.println("  (none)");
            return;
        }
        for (Object o : list) {
            if (o instanceof Identifiable) {
                System.out.println("  " + ((Identifiable) o).getDisplayName() + "  |  " + o.toString());
            } else {
                System.out.println("  " + o);
            }
        }
    }

    // ==================== 14. SEARCH ====================
    private static void searchById() {
        String id = readRequired("Enter ID to search: ");
        if (id == null) return;
        Identifiable found = findById(id);
        if (found == null) {
            System.out.println("! Record not found for ID: " + id);
        } else {
            System.out.println("+ Found:");
            if (found instanceof Registrable) {
                ((Registrable) found).printDetails();
            } else {
                System.out.println(found.getDisplayName());
            }
        }
    }

    private static Identifiable findById(String id) {
        for (Department d : departments) if (d.getId().equalsIgnoreCase(id)) return d;
        for (Student s : students) if (s.getId().equalsIgnoreCase(id) || s.getStudentNumber().equalsIgnoreCase(id)) return s;
        for (Teacher t : teachers) if (t.getId().equalsIgnoreCase(id) || t.getEmployeeNumber().equalsIgnoreCase(id)) return t;
        for (Administrator a : administrators) if (a.getId().equalsIgnoreCase(id) || a.getStaffNumber().equalsIgnoreCase(id)) return a;
        for (Course c : courses) if (c.getId().equalsIgnoreCase(id) || c.getCourseCode().equalsIgnoreCase(id)) return c;
        for (Classroom cl : classrooms) if (cl.getId().equalsIgnoreCase(id)) return cl;
        for (Enrollment e : enrollments) if (e.getId().equalsIgnoreCase(id)) return e;
        for (Assessment a : assessments) if (a.getId().equalsIgnoreCase(id)) return a;
        for (FeePayment f : feePayments) if (f.getId().equalsIgnoreCase(id) || f.getReceiptNumber().equalsIgnoreCase(id)) return f;
        for (LibraryBook b : libraryBooks) if (b.getId().equalsIgnoreCase(id)) return b;
        for (BookLoan l : bookLoans) if (l.getId().equalsIgnoreCase(id)) return l;
        return null;
    }

    // ==================== 15. UPDATE (simplified) ====================
    private static void updateRecord() {
        System.out.println("\n------------- UPDATE RECORD -------------");
        System.out.println("1. Update Student Phone");
        System.out.println("2. Update Teacher Salary");
        System.out.println("3. Update Classroom Availability");
        System.out.println("0. Cancel");
        String c = readLine("Select: ");
        switch (c) {
            case "1" -> {
                if (students.isEmpty()) { System.out.println("No students."); return; }
                for (int i = 0; i < students.size(); i++)
                    System.out.println("  " + (i + 1) + ". " + students.get(i).getRegistrationSummary());
                int s = readInt("Select student: ", 1, students.size());
                if (s == -999) return;
                String phone = readRequired("New phone: ");
                if (phone != null) {
                    students.get(s - 1).setPhoneNumber(phone);
                    System.out.println("+ Phone updated.");
                }
            }
            case "2" -> {
                if (teachers.isEmpty()) { System.out.println("No teachers."); return; }
                for (int i = 0; i < teachers.size(); i++)
                    System.out.println("  " + (i + 1) + ". " + teachers.get(i).getRegistrationSummary());
                int t = readInt("Select teacher: ", 1, teachers.size());
                if (t == -999) return;
                BigDecimal sal = readMoney("New salary: ");
                if (sal != null) {
                    teachers.get(t - 1).setSalary(sal);
                    System.out.println("+ Salary updated.");
                }
            }
            case "3" -> {
                if (classrooms.isEmpty()) { System.out.println("No classrooms."); return; }
                for (int i = 0; i < classrooms.size(); i++)
                    System.out.println("  " + (i + 1) + ". " + classrooms.get(i).getRegistrationSummary());
                int cl = readInt("Select classroom: ", 1, classrooms.size());
                if (cl == -999) return;
                System.out.println("Available? [1 YES / 2 NO]");
                int av = readInt("Select: ", 1, 2);
                if (av != -999) {
                    classrooms.get(cl - 1).setAvailable(av == 1);
                    System.out.println("+ Availability updated.");
                }
            }
            default -> System.out.println("Cancelled.");
        }
    }

    // ==================== 16. STUDENT ACADEMIC SUMMARY ====================
    private static void showStudentAcademicSummary() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i + 1) + ". " + students.get(i).getRegistrationSummary());
        int sel = readInt("Select: ", 1, students.size());
        if (sel == -999) return;
        Student s = students.get(sel - 1);

        System.out.println("\n===== ACADEMIC SUMMARY: " + s.getDisplayName() + " =====");
        s.printDetails();
        System.out.println("\n--- Enrollments ---");
        boolean any = false;
        for (Enrollment e : enrollments) {
            if (e.getStudent() == s) {
                System.out.println("  " + e.getRegistrationSummary());
                any = true;
            }
        }
        if (!any) System.out.println("  (none)");
        System.out.println("\n--- Assessments ---");
        any = false;
        for (Assessment a : assessments) {
            if (a.getEnrollment() != null && a.getEnrollment().getStudent() == s) {
                System.out.println("  " + a.getRegistrationSummary());
                any = true;
            }
        }
        if (!any) System.out.println("  (none)");
    }

    // ==================== 17. PAYMENT HISTORY ====================
    private static void showPaymentHistory() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("Select Student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i + 1) + ". " + students.get(i).getRegistrationSummary());
        int sel = readInt("Select: ", 1, students.size());
        if (sel == -999) return;
        Student s = students.get(sel - 1);

        System.out.println("\n===== PAYMENT HISTORY: " + s.getDisplayName() + " =====");
        boolean any = false;
        BigDecimal total = BigDecimal.ZERO;
        for (FeePayment f : feePayments) {
            if (f.getStudent() == s) {
                System.out.println("  " + f.getRegistrationSummary());
                total = total.add(f.getAmount());
                any = true;
            }
        }
        if (!any) System.out.println("  (no payments)");
        else System.out.println("  TOTAL PAID: " + total);
    }

    // ==================== 18. AVAILABLE BOOKS ====================
    private static void showAvailableBooks() {
        System.out.println("\n===== AVAILABLE LIBRARY BOOKS =====");
        boolean any = false;
        for (LibraryBook b : libraryBooks) {
            if (b.getAvailableCopies() > 0) {
                System.out.println("  " + b.getRegistrationSummary());
                any = true;
            }
        }
        if (!any) System.out.println("  (no books currently available)");
    }

    // ==================== 19. POLYMORPHISM DEMO ====================
    private static void demonstratePolymorphism() {
        System.out.println("\n============= POLYMORPHISM DEMONSTRATION =============");
        System.out.println("1. Runtime polymorphism via List<Person>");
        System.out.println("2. Interface polymorphism via List<Identifiable>");
        System.out.println("3. Why Person & AcademicMember cannot be instantiated");
        System.out.println();

        // Build sample data if empty
        if (students.isEmpty() && teachers.isEmpty() && administrators.isEmpty()) {
            System.out.println("(Using temporary demo objects - register real ones for fuller demo)");
            // Minimal demo objects would require departments etc.; we just explain + use existing
        }

        List<Person> people = new ArrayList<>();
        people.addAll(students);
        people.addAll(teachers);
        people.addAll(administrators);

        if (people.isEmpty()) {
            System.out.println("No Person-derived records yet. Register Student/Teacher/Administrator first.");
        } else {
            System.out.println("--- List<Person> (runtime polymorphism) ---");
            for (Person p : people) {
                System.out.println("  ID: " + p.getId() +
                        " | Name: " + p.getDisplayName() +
                        " | Role: " + p.getRoleDescription() +
                        " | Active: " + p.isActive());
                if (p instanceof AcademicMember) {
                    System.out.println("      Academic Category: " + ((AcademicMember) p).getAcademicCategory());
                }
            }
        }

        System.out.println("\n--- List<Identifiable> (interface polymorphism) ---");
        List<Identifiable> all = new ArrayList<>();
        all.addAll(departments);
        all.addAll(students);
        all.addAll(teachers);
        all.addAll(administrators);
        all.addAll(courses);
        all.addAll(classrooms);
        all.addAll(enrollments);
        all.addAll(assessments);
        all.addAll(feePayments);
        all.addAll(libraryBooks);
        all.addAll(bookLoans);

        if (all.isEmpty()) {
            System.out.println("  (no records yet)");
        } else {
            for (Identifiable i : all) {
                System.out.println("  [" + i.getClass().getSimpleName() + "] " +
                        i.getId() + " -> " + i.getDisplayName() + " (active=" + i.isActive() + ")");
            }
        }

        System.out.println("\n--- Explanation ---");
        System.out.println("Person and AcademicMember are declared abstract.");
        System.out.println("Therefore the compiler forbids: new Person(...) or new AcademicMember(...).");
        System.out.println("They exist only as base types for inheritance and polymorphic references.");
        System.out.println("Student, Teacher and Administrator provide the concrete implementations");
        System.out.println("of the abstract methods getRoleDescription() and getAcademicCategory().");
        System.out.println("======================================================");
    }
}
