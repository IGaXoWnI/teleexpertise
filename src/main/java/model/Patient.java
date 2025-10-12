package model;

import jakarta.persistence.*;

import java.util.Date;

import static jakarta.persistence.GenerationType.*;

@Entity(name = "Patient")
@Table(name = "patient" ,
        uniqueConstraints = {@UniqueConstraint(name = "phoneNumberConstraint" , columnNames ="phoneNumber" )}
)
public class Patient {
    @Id
    @SequenceGenerator(
            sequenceName = "PatientSequence",
            name = "PatientSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "PatientSequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id ;


    @Column(
            name = "first_name",
            nullable = false,
            columnDefinition = "TEXT"

    )
    private String firstName ;
    @Column(
            name = "last_name",
            nullable = false,
            columnDefinition = "TEXT"

    )
    private String lastName ;

    @Column(
            name = "birth_date" ,
            nullable = false,
            columnDefinition = "DATE"
    )
    private Date birthDate ;

    @Column(
            name = "phone_number",
            nullable = false,
            columnDefinition = "TEXT"

    )
    private String phoneNumber ;
    @Column(
            name = "address",
            columnDefinition = "TEXT"

    )
    private String address ;


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAdress() {
        return address;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", adress='" + address + '\'' +
                '}';
    }
}
