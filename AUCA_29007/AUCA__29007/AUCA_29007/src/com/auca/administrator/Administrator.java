package com.auca.administrator;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

/**
 * Concrete Administrator - hierarchical inheritance: Person -> Administrator
 */
public class Administrator extends Person {
    private String staffNumber;
    private String jobTitle;
    private String assignedOffice;
    private LocalDate employmentDate;
    private int accessLevel;
    private boolean active;

    public Administrator(String personId, String firstName, String lastName,
                         String email, String phoneNumber, LocalDate dateOfBirth,
                         String staffNumber, String jobTitle, String assignedOffice,
                         LocalDate employmentDate, int accessLevel, boolean active) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.staffNumber = staffNumber;
        this.jobTitle = jobTitle;
        this.assignedOffice = assignedOffice;
        this.employmentDate = employmentDate;
        this.accessLevel = accessLevel;
        this.active = active;
    }

    @Override
    public String getRoleDescription() {
        return "Administrator - " + jobTitle + " (Access Level " + accessLevel + ")";
    }

    public String getStaffNumber() { return staffNumber; }
    public void setStaffNumber(String staffNumber) { this.staffNumber = staffNumber; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getAssignedOffice() { return assignedOffice; }
    public void setAssignedOffice(String assignedOffice) { this.assignedOffice = assignedOffice; }

    public LocalDate getEmploymentDate() { return employmentDate; }
    public void setEmploymentDate(LocalDate employmentDate) { this.employmentDate = employmentDate; }

    public int getAccessLevel() { return accessLevel; }
    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }

    public boolean isActiveFlag() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (staffNumber == null || staffNumber.trim().isEmpty()) {
            throw new ValidationException("Staff number cannot be blank.");
        }
        if (jobTitle == null || jobTitle.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Job title is required.");
        }
        if (assignedOffice == null || assignedOffice.trim().isEmpty()) {
            throw new ValidationException("Assigned office is required.");
        }
        if (employmentDate == null) {
            throw new ValidationException("Employment date is required.");
        }
        if (accessLevel < 1 || accessLevel > 10) {
            throw new ValidationException("Access level must be between 1 and 10.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== ADMINISTRATOR DETAILS =====");
        System.out.println("Person ID        : " + personId);
        System.out.println("Name             : " + getDisplayName());
        System.out.println("Email            : " + email);
        System.out.println("Phone            : " + phoneNumber);
        System.out.println("Date of Birth    : " + dateOfBirth);
        System.out.println("Staff Number     : " + staffNumber);
        System.out.println("Job Title        : " + jobTitle);
        System.out.println("Assigned Office  : " + assignedOffice);
        System.out.println("Employment Date  : " + employmentDate);
        System.out.println("Access Level     : " + accessLevel);
        System.out.println("Active           : " + (active ? "YES" : "NO"));
        System.out.println("Role             : " + getRoleDescription());
    }

    @Override
    public String getRegistrationSummary() {
        return "[ADM] " + staffNumber + " - " + getDisplayName() + " | " + jobTitle;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
