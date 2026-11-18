package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Concrete Teacher class - multilevel inheritance: Person -> AcademicMember -> Teacher
 */
public class Teacher extends AcademicMember {
    private String employeeNumber;
    private String specialization;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String officeNumber;
    private String employmentType;

    public Teacher(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department, LocalDate joinDate,
                   String academicStatus, String institutionalEmail, String advisorName,
                   String employeeNumber, String specialization, LocalDate hireDate,
                   BigDecimal salary, String officeNumber, String employmentType) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth,
              memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName);
        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
        this.officeNumber = officeNumber;
        this.employmentType = employmentType;
    }

    @Override
    public String getRoleDescription() {
        return "Teacher specializing in " + specialization + " (" + employmentType + ")";
    }

    @Override
    public String getAcademicCategory() {
        return "Academic Staff - Teacher";
    }

    public String getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public String getOfficeNumber() { return officeNumber; }
    public void setOfficeNumber(String officeNumber) { this.officeNumber = officeNumber; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (employeeNumber == null || employeeNumber.trim().isEmpty()) {
            throw new ValidationException("Employee number cannot be blank.");
        }
        if (specialization == null || specialization.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Specialization is required.");
        }
        if (hireDate == null) {
            throw new ValidationException("Hire date is required.");
        }
        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Salary must be strictly positive.");
        }
        if (officeNumber == null || officeNumber.trim().isEmpty()) {
            throw new ValidationException("Office number is required.");
        }
        if (employmentType == null || employmentType.trim().isEmpty()) {
            throw new ValidationException("Employment type is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== TEACHER DETAILS =====");
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
        System.out.println("Employee Number  : " + employeeNumber);
        System.out.println("Specialization   : " + specialization);
        System.out.println("Hire Date        : " + hireDate);
        System.out.println("Salary           : " + salary);
        System.out.println("Office Number    : " + officeNumber);
        System.out.println("Employment Type  : " + employmentType);
        System.out.println("Role             : " + getRoleDescription());
        System.out.println("Category         : " + getAcademicCategory());
    }

    @Override
    public String getRegistrationSummary() {
        return "[TCH] " + employeeNumber + " - " + getDisplayName() + " | " + specialization;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
