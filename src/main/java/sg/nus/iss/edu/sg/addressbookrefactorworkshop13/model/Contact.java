package sg.nus.iss.edu.sg.addressbookrefactorworkshop13.model;

import java.io.Serializable;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact implements Serializable {
    
    @NotNull(message="Name cannot be null")
    @Size(min = 3, max = 64, message = "Name must be between 3 and 64 characters")
    private String name;

    @Email(message = "Must be a valid email")
    private String email;


    @NotNull(message="Phone number cannot be null")
    @Size(min = 7, message = "Must contain at least 7 digits")
    private String phoneNumber;

    @Past(message = "Must be born before today")
    @NotNull(message = "Date of Birth cannot be null")
    @DateTimeFormat(pattern="MM-dd-yyyy")
    private LocalDate dateOfBirth;

    @Min(value = 10, message = "Cannot be younger than 10 years old")
    @Max(value = 101, message = "Cannot be older than 100 years old")
    private int age;


    private String id;


    public Contact() {
        this.id = generateId(8);
    }

    public Contact(String id, String name, String email, String phoneNumber, 
                                LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Contact(String name, String email, String phoneNumber, 
                                        LocalDate dateOfBirth){
        this.id = generateId(8);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    private synchronized String generateId(int numOfChar){
        SecureRandom sr = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while(sb.length() < numOfChar) {
            sb.append(Integer.toHexString(sr.nextInt()));
        }
        
        return sb.toString().substring(0, numOfChar);
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}


    public LocalDate getDateOfBirth() {return dateOfBirth;}

    public void setDateOfBirth(LocalDate dateOfBirth) {
        int calculateAge = 0;
        if (dateOfBirth != null){
            calculateAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        this.dateOfBirth = dateOfBirth;
        this.age = calculateAge;
    
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}


    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

}
