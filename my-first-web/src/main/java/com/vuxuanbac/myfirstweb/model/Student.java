/***
 * An example of JPA Mapping:
 * - Table and Column config
 * - Basic Type Mapping (primitives, primitives wrapper, String, BigInteger,...)
 * - DateTime Mapping (LocalDate, LocalTime, LocalDateTime and @Temporal for others)
 * - Enum Mapping (@Enumerated and @Converter)
 * - Embedded (@Embeddable , @Embedded , @AttributeOverride)
 * - Transient
 * Classes: Student, StudentContact
 */
package com.vuxuanbac.myfirstweb.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@Data
// An Entity will be mapped to a DB Table with specific name:
// Name priority: @Table -> @Entity -> Java Class Name
@Entity
@Table(
        uniqueConstraints = {
                // Set a Unique constraint for "email" field in Table scope instead of column scope
                @UniqueConstraint(name = "student_email_unique", columnNames = "email")
        }
)
public class Student {

    @Id // An entity require an identifier (a field to be a Key in DB Table)
    @SequenceGenerator( //  auto increment
            name = "student_sequence",
            sequenceName = "student_generator",
            allocationSize = 1 // start from 1
    )
    @GeneratedValue( // generated value use specific strategy
            strategy = GenerationType.SEQUENCE,
            generator = "student_generator"
    )
    @Column(updatable = false) // can not change
    private Long id;
    @Column(
            name = "first_name" // used for column name
            ,nullable = false // NOT NULL
            ,columnDefinition = "TINYTEXT"
    )
    private String firstName;
    @Column(
            name = "last_name" // used for column name
            ,nullable = false // NOT NULL
            ,columnDefinition = "TINYTEXT"
    )
    private String lastName;
    @Column(
            nullable = false // NOT NULL
            // ,unique = true // unset if use Table constraint
            // ,columnDefinition = "TINYTEXT"
    )
    private String email;

    // If use java.util.Date, must specify the part (Date, or Time, or Date and Time) of the object want to store
    // @Temporal(TemporalType.DATE)
    // Instead, use LocalDate, LocalTime or LocalDateTime
    @Column(nullable = false)
    private LocalDate dob;

    public enum Gender{
        MALE,
        FEMALE,
        OTHER
    }
    // With enum, there are 2 strategy for storing value: By Enum name (STRING) or [default] By Enum value (ORDINAL - Integer)
    // Storing value has no meaning when reading database directly or reusing in another app.
    // ---------------
    // Or using @Converter to convert between property type in class and field type in database
    // - First, create a class implement AttributeConverter<Class_Property_Type,  Field_Type_In_DB>
    // - Use annotation @Converter(converter = [converter_class_name].class)
    //   or set @Converter(autoApply = true) on Converter class to default set this converter for all properties of "Class_Property_Type" type
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded // Use StudentContact properties as additional columns on the Table created by Student class
    // Can override the name of the property in StudentContact by using @AttributeOverride. Ex
    // @AttributeOverride(name = "contact_phone", column = @Column(name = "phone"))
    private StudentContact contact;


    @Transient // Does not cover this field as a table's column
    private Integer age;
    public Integer getAge(){
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public Student(String firstName, String lastName, String email, LocalDate dob, Gender gender, StudentContact contact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.contact = contact;
    }
}
