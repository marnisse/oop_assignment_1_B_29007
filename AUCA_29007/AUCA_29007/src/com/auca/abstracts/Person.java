package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;


public abstract class Person implements Identifiable, Registrable {
    protected String personId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName,
                  String email, String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    // Abstract method required by assignment
    public abstract String getRoleDescription();

    @Override
    public String getId() {
        return personId;
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isActive() {
        return true; // overridden in concrete classes where needed
    }

    public String getPersonId() { return personId; }
    public void setPersonId(String personId) { this.personId = personId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    @Override
    public void validate() throws ValidationException {
        if (personId == null || personId.trim().isEmpty()) {
            throw new ValidationException("Person ID cannot be blank.");
        }
        if (personId.length() < MIN_ID_LENGTH || personId.length() > MAX_ID_LENGTH) {
            throw new ValidationException("Person ID must be " + MIN_ID_LENGTH + "-" + MAX_ID_LENGTH + " characters.");
        }
        if (firstName == null || firstName.trim().length() < MIN_NAME_LENGTH || firstName.trim().length() > MAX_NAME_LENGTH) {
            throw new ValidationException("First name must be " + MIN_NAME_LENGTH + "-" + MAX_NAME_LENGTH + " characters.");
        }
        if (lastName == null || lastName.trim().length() < MIN_NAME_LENGTH || lastName.trim().length() > MAX_NAME_LENGTH) {
            throw new ValidationException("Last name must be " + MIN_NAME_LENGTH + "-" + MAX_NAME_LENGTH + " characters.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || email.length() > MAX_EMAIL_LENGTH) {
            throw new ValidationException("Invalid email address.");
        }
        if (phoneNumber == null || !phoneNumber.replaceAll("[^0-9]", "").matches("\\d{" + MIN_PHONE_LENGTH + "," + MAX_PHONE_LENGTH + "}")) {
            throw new ValidationException("Phone must contain " + MIN_PHONE_LENGTH + "-" + MAX_PHONE_LENGTH + " digits.");
        }
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            throw new ValidationException("Date of birth must be in the past.");
        }
    }

    @Override
    public String toString() {
        return getDisplayName() + " [" + personId + "]";
    }
}
