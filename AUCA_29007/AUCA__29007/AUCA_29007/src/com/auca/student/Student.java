package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

/**
 * Concrete Student class - multilevel inheritance: Person -> AcademicMember -> Student
 */
public class Student extends AcademicMember {
    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department, LocalDate joinDate,
                   String academicStatus, String institutionalEmail, String advisorName,
                   String studentNumber, String programName, LocalDate admissionDate,
                   int studyYear, String guardianPhone, boolean scholarshipStatus) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth,
              memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName);
        this.studentNumber = studentNumber;
        this.programName = programName;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarshipStatus;
    }

    @Override
    public String getRoleDescription() {
        return "Student enrolled in " + programName + " (Year " + studyYear + ")";
    }

    @Override
    public String getAcademicCategory() {
        return "Undergraduate Student";
    }

    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    public int getStudyYear() { return studyYear; }
    public void setStudyYear(int studyYear) { this.studyYear = studyYear; }

    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }

    public boolean isScholarshipStatus() { return scholarshipStatus; }
    public void setScholarshipStatus(boolean scholarshipStatus) { this.scholarshipStatus = scholarshipStatus; }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (studentNumber == null || studentNumber.trim().isEmpty()) {
            throw new ValidationException("Student number cannot be blank.");
        }
        if (programName == null || programName.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Program name is required.");
        }
        if (admissionDate == null) {
            throw new ValidationException("Admission date is required.");
        }
        if (studyYear < 1 || studyYear > 6) {
            throw new ValidationException("Study year must be between 1 and 6.");
        }
        if (guardianPhone == null || !guardianPhone.replaceAll("[^0-9]", "").matches("\\d{10,15}")) {
            throw new ValidationException("Guardian phone must contain 10-15 digits.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== STUDENT DETAILS =====");
        System.out.println("Person ID        : " + personId);
        System.out.println("Name             : " + getDisplayName());
        System.out.println("Email            : " + email);
        System.out.println("Phone            : " + phoneNumber);
        System.out.println("Date of Birth    : " + dateOfBirth);
        System.out.println("Member Number    : " + memberNumber);
        System.out.println("Department       : " + (department != null ? department.getDisplayName() : "N/A"));
        System.out.println("Join Date        : " + joinDate);
        System.out.println("Academic Status  : " + academicStatus);
        System.out.println("Inst. Email      : " + institutionalEmail);
        System.out.println("Advisor          : " + advisorName);
        System.out.println("Student Number   : " + studentNumber);
        System.out.println("Program          : " + programName);
        System.out.println("Admission Date   : " + admissionDate);
        System.out.println("Study Year       : " + studyYear);
        System.out.println("Guardian Phone   : " + guardianPhone);
        System.out.println("Scholarship      : " + (scholarshipStatus ? "YES" : "NO"));
        System.out.println("Role             : " + getRoleDescription());
        System.out.println("Category         : " + getAcademicCategory());
    }

    @Override
    public String getRegistrationSummary() {
        return "[STU] " + studentNumber + " - " + getDisplayName() + " | " + programName + " (Y" + studyYear + ")";
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
