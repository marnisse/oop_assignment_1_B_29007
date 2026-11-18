package com.auca.assessment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;


public class Assessment implements Identifiable, Registrable {
    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment, String assessmentType,
                      double maximumScore, double obtainedScore, LocalDate assessmentDate) {
        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.maximumScore = maximumScore;
        this.obtainedScore = obtainedScore;
        this.assessmentDate = assessmentDate;
    }

    @Override
    public String getId() { return assessmentId; }

    @Override
    public String getDisplayName() {
        return assessmentType + " for " + (enrollment != null ? enrollment.getDisplayName() : "N/A");
    }

    @Override
    public boolean isActive() { return true; }

    public String getAssessmentId() { return assessmentId; }
    public void setAssessmentId(String assessmentId) { this.assessmentId = assessmentId; }

    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment enrollment) { this.enrollment = enrollment; }

    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }

    public double getMaximumScore() { return maximumScore; }
    public void setMaximumScore(double maximumScore) { this.maximumScore = maximumScore; }

    public double getObtainedScore() { return obtainedScore; }
    public void setObtainedScore(double obtainedScore) { this.obtainedScore = obtainedScore; }

    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate assessmentDate) { this.assessmentDate = assessmentDate; }

    @Override
    public void validate() throws ValidationException {
        if (assessmentId == null || assessmentId.trim().isEmpty()) {
            throw new ValidationException("Assessment ID cannot be blank.");
        }
        if (enrollment == null) {
            throw new ValidationException("Enrollment is required.");
        }
        if (!enrollment.isActive()) {
            throw new ValidationException("Only active enrollments can be assessed.");
        }
        if (assessmentType == null || assessmentType.trim().isEmpty()) {
            throw new ValidationException("Assessment type is required.");
        }
        if (maximumScore <= 0) {
            throw new ValidationException("Maximum score must be greater than zero.");
        }
        if (obtainedScore < 0 || obtainedScore > maximumScore) {
            throw new ValidationException("Obtained score must be between 0 and maximum score.");
        }
        if (assessmentDate == null) {
            throw new ValidationException("Assessment date is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== ASSESSMENT DETAILS =====");
        System.out.println("Assessment ID    : " + assessmentId);
        System.out.println("Enrollment       : " + (enrollment != null ? enrollment.getDisplayName() : "N/A"));
        System.out.println("Type             : " + assessmentType);
        System.out.println("Maximum Score    : " + maximumScore);
        System.out.println("Obtained Score   : " + obtainedScore);
        System.out.println("Assessment Date  : " + assessmentDate);
        System.out.println("Percentage       : " + String.format("%.1f%%", (obtainedScore / maximumScore) * 100));
    }

    @Override
    public String getRegistrationSummary() {
        return "[ASM] " + assessmentId + " - " + assessmentType + " | " +
               String.format("%.1f/%.1f", obtainedScore, maximumScore);
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
