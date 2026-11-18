# AUCA_ID – School Management System

**Practical OOP Assignment**  
Console-based Java application demonstrating encapsulation, multilevel & hierarchical inheritance, interfaces, abstract classes, polymorphism, object relationships, validation and exception handling.

---

## Project Structure

```
AUCA_ID/
└── src/
    └── com/
        └── auca/
            ├── interfaces/
            │   ├── Identifiable.java
            │   └── Registrable.java
            ├── abstracts/
            │   ├── Person.java
            │   └── AcademicMember.java
            ├── student/Student.java
            ├── teacher/Teacher.java
            ├── administrator/Administrator.java
            ├── department/Department.java
            ├── course/Course.java
            ├── classroom/Classroom.java
            ├── enrollment/Enrollment.java
            ├── assessment/Assessment.java
            ├── feepayment/FeePayment.java
            ├── librarybook/LibraryBook.java
            ├── bookloan/BookLoan.java
            ├── exceptions/
            │   ├── ValidationException.java
            │   ├── DuplicateRecordException.java
            │   ├── RecordNotFoundException.java
            │   ├── PrerequisiteMissingException.java
            │   └── BookUnavailableException.java
            └── main/Main.java
```

---

## How to Compile & Run

### Option 1 – Command line (recommended for demonstration)

```bash
# Navigate into the project
cd AUCA_ID

# Compile everything
javac -d bin $(find src -name "*.java")

# Run
java -cp bin com.auca.main.Main
```

### Option 2 – From IntelliJ / Eclipse / VS Code

1. Open the `AUCA_ID` folder as a project (or import the `src` folder).
2. Set the source root to `src`.
3. Run the class `com.auca.main.Main`.

---

## Main Menu

```
============== AUCA_ID SCHOOL MANAGEMENT ==============
 1. Register Department       11. Borrow Library Book
 2. Register Student          12. Return Library Book
 3. Register Teacher          13. View All Records
 4. Register Administrator    14. Search Record by ID
 5. Register Course           15. Update Existing Record
 6. Register Classroom        16. Student Academic Summary
 7. Enroll Student            17. Student Payment History
 8. Record Assessment         18. View Available Books
 9. Record Fee Payment        19. Demonstrate Polymorphism
10. Register Library Book      0. Exit
```

---

## Recommended Demonstration Flow

1. **Register Department** (option 1) – create at least one department.
2. **Register Teacher** (option 3) – department is required; system will guide you if missing.
3. **Register Student** (option 2) – multi-step smart form (18 fields, review screen).
4. **Register Course** (option 5) – needs Department + Teacher.
5. **Enroll Student** (option 7) – prevents duplicate active enrollments.
6. **Record Assessment** (option 8) – only on active enrollments.
7. **Record Fee Payment** (option 9).
8. **Register Library Book** (option 10).
9. **Borrow Book** (option 11) → **Return Book** (option 12) – stock is updated correctly.
10. **View All Records** (13), **Search by ID** (14), **Update** (15).
11. **Student Academic Summary** (16) and **Payment History** (17).
12. **Demonstrate Polymorphism** (19) – shows `List<Person>` and `List<Identifiable>`.

---

## Key OOP Features Implemented

| Requirement                      | Implementation                                      |
|----------------------------------|-----------------------------------------------------|
| 2 Interfaces                     | `Identifiable`, `Registrable`                       |
| 2 Abstract classes               | `Person`, `AcademicMember`                          |
| 11 Concrete classes              | Student, Teacher, Administrator, Department, …      |
| Multilevel inheritance           | Person → AcademicMember → Student / Teacher         |
| Hierarchical inheritance         | Person → Administrator                              |
| Polymorphism                     | `List<Person>` + `List<Identifiable>` demo          |
| Custom exceptions                | 5 exception classes in `com.auca.exceptions`        |
| Validation                       | Length, uniqueness, dates, money, ranges, email…    |
| Object relationships             | Department↔Teacher, Course↔Teacher, Enrollment…     |
| Smart registration forms         | Guided multi-step forms with prerequisite handling  |
| Single shared Scanner            | All input via one `Scanner` on `System.in`          |

---

## Notes for the Examiner

- Person and AcademicMember **cannot** be instantiated (they are abstract).
- All public business operations are declared in the interfaces (or as abstract methods on the permitted abstract classes).
- Nested registration of missing prerequisites is supported (Department, Course, etc.).
- Book stock (`availableCopies`) is decremented on borrow and incremented on return.
- Invalid input is rejected with clear messages; the application never crashes on bad data.
- Cancel at any prompt abandons the current form without partial saves.

---

**Prepared for AUCA OOP Practical Assignment**  
Project name: `AUCA_ID`
