package com.auca.course;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.department.Department;
import com.auca.teacher.Teacher;
import com.auca.exceptions.ValidationException;

/**
 * Course entity.
 */
public class Course implements Identifiable, Registrable {
    private String courseId;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course(String courseId, String courseCode, String courseTitle,
                  int creditHours, Department department, Teacher assignedTeacher) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.department = department;
        this.assignedTeacher = assignedTeacher;
    }

    @Override
    public String getId() { return courseId; }

    @Override
    public String getDisplayName() {
        return courseCode + " - " + courseTitle;
    }

    @Override
    public boolean isActive() { return true; }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Teacher getAssignedTeacher() { return assignedTeacher; }
    public void setAssignedTeacher(Teacher assignedTeacher) { this.assignedTeacher = assignedTeacher; }

    @Override
    public void validate() throws ValidationException {
        if (courseId == null || courseId.trim().isEmpty()) {
            throw new ValidationException("Course ID cannot be blank.");
        }
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new ValidationException("Course code is required.");
        }
        if (courseTitle == null || courseTitle.trim().length() < MIN_NAME_LENGTH) {
            throw new ValidationException("Course title is required.");
        }
        if (creditHours < 1 || creditHours > 6) {
            throw new ValidationException("Credit hours must be between 1 and 6.");
        }
        if (department == null) {
            throw new ValidationException("Department is required.");
        }
        if (assignedTeacher == null) {
            throw new ValidationException("Assigned teacher is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== COURSE DETAILS =====");
        System.out.println("Course ID        : " + courseId);
        System.out.println("Code             : " + courseCode);
        System.out.println("Title            : " + courseTitle);
        System.out.println("Credit Hours     : " + creditHours);
        System.out.println("Department       : " + (department != null ? department.getDisplayName() : "N/A"));
        System.out.println("Assigned Teacher : " + (assignedTeacher != null ? assignedTeacher.getDisplayName() : "N/A"));
    }

    @Override
    public String getRegistrationSummary() {
        return "[CRS] " + courseCode + " - " + courseTitle + " (" + creditHours + " cr)";
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
