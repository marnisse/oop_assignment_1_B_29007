package com.auca.department;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;
import com.auca.exceptions.ValidationException;

/**
 * Department entity - implements both interfaces directly.
 */
public class Department implements Identifiable, Registrable {
    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher; // optional at creation

    public Department(String departmentId, String departmentName, String departmentCode,
                      String officeLocation, String contactEmail, Teacher headTeacher) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    @Override
    public String getId() {
        return departmentId;
    }

    @Override
    public String getDisplayName() {
        return departmentName + " [" + departmentCode + "]";
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String departmentCode) { this.departmentCode = departmentCode; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public Teacher getHeadTeacher() { return headTeacher; }
    public void setHeadTeacher(Teacher headTeacher) { this.headTeacher = headTeacher; }

    @Override
    public void validate() throws ValidationException {
        if (departmentId == null || departmentId.trim().isEmpty()) {
            throw new ValidationException("Department ID cannot be blank.");
        }
        if (departmentName == null || departmentName.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Department name is required.");
        }
        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            throw new ValidationException("Department code is required.");
        }
        if (officeLocation == null || officeLocation.trim().isEmpty()) {
            throw new ValidationException("Office location is required.");
        }
        if (contactEmail == null || !contactEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ValidationException("Invalid contact email.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== DEPARTMENT DETAILS =====");
        System.out.println("Department ID    : " + departmentId);
        System.out.println("Name             : " + departmentName);
        System.out.println("Code             : " + departmentCode);
        System.out.println("Office Location  : " + officeLocation);
        System.out.println("Contact Email    : " + contactEmail);
        System.out.println("Head Teacher     : " + (headTeacher != null ? headTeacher.getDisplayName() : "Not assigned"));
    }

    @Override
    public String getRegistrationSummary() {
        return "[DEP] " + departmentCode + " - " + departmentName;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
