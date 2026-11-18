package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

/**
 * Abstract class for academic staff/students.
 * Extends Person - multilevel inheritance.
 */
public abstract class AcademicMember extends Person {
    protected String memberNumber;
    protected Department department;
    protected LocalDate joinDate;
    protected String academicStatus;
    protected String institutionalEmail;
    protected String advisorName;

    public AcademicMember(String personId, String firstName, String lastName,
                          String email, String phoneNumber, LocalDate dateOfBirth,
                          String memberNumber, Department department, LocalDate joinDate,
                          String academicStatus, String institutionalEmail, String advisorName) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    // Abstract method required by assignment
    public abstract String getAcademicCategory();

    public String getMemberNumber() { return memberNumber; }
    public void setMemberNumber(String memberNumber) { this.memberNumber = memberNumber; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public String getAcademicStatus() { return academicStatus; }
    public void setAcademicStatus(String academicStatus) { this.academicStatus = academicStatus; }

    public String getInstitutionalEmail() { return institutionalEmail; }
    public void setInstitutionalEmail(String institutionalEmail) { this.institutionalEmail = institutionalEmail; }

    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String advisorName) { this.advisorName = advisorName; }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(academicStatus);
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (memberNumber == null || memberNumber.trim().isEmpty()) {
            throw new ValidationException("Member number cannot be blank.");
        }
        if (memberNumber.length() < MIN_ID_LENGTH || memberNumber.length() > MAX_ID_LENGTH) {
            throw new ValidationException("Member number must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " characters.");
        }
        if (department == null) {
            throw new ValidationException("Department is required.");
        }
        if (joinDate == null) {
            throw new ValidationException("Join date is required.");
        }
        if (academicStatus == null || (!academicStatus.equalsIgnoreCase("ACTIVE") && !academicStatus.equalsIgnoreCase("INACTIVE"))) {
            throw new ValidationException("Academic status must be ACTIVE or INACTIVE.");
        }
        if (institutionalEmail == null || !institutionalEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidationException("Invalid institutional email.");
        }
        if (advisorName == null || advisorName.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Advisor name is required (min " + MIN_NAME_LENGTH + " chars).");
        }
    }
}
