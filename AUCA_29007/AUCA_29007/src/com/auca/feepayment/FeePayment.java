package com.auca.feepayment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.exceptions.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * FeePayment entity.
 */
public class FeePayment implements Identifiable, Registrable {
    private String paymentId;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String receiptNumber;

    public FeePayment(String paymentId, Student student, BigDecimal amount,
                      LocalDate paymentDate, String paymentMethod, String receiptNumber) {
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String getId() { return paymentId; }

    @Override
    public String getDisplayName() {
        return "Payment " + receiptNumber + " by " + (student != null ? student.getDisplayName() : "N/A");
    }

    @Override
    public boolean isActive() { return true; }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String receiptNumber) { this.receiptNumber = receiptNumber; }

    @Override
    public void validate() throws ValidationException {
        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new ValidationException("Payment ID cannot be blank.");
        }
        if (student == null) {
            throw new ValidationException("Student is required.");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Amount must be strictly positive.");
        }
        if (paymentDate == null) {
            throw new ValidationException("Payment date is required.");
        }
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new ValidationException("Payment method is required.");
        }
        if (receiptNumber == null || receiptNumber.trim().isEmpty()) {
            throw new ValidationException("Receipt number is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("===== FEE PAYMENT DETAILS =====");
        System.out.println("Payment ID       : " + paymentId);
        System.out.println("Student          : " + (student != null ? student.getDisplayName() : "N/A"));
        System.out.println("Amount           : " + amount);
        System.out.println("Payment Date     : " + paymentDate);
        System.out.println("Payment Method   : " + paymentMethod);
        System.out.println("Receipt Number   : " + receiptNumber);
    }

    @Override
    public String getRegistrationSummary() {
        return "[FEE] " + receiptNumber + " - " + amount + " | " +
               (student != null ? student.getDisplayName() : "N/A");
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
