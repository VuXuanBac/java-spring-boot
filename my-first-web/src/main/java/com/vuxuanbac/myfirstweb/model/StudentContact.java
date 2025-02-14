package com.vuxuanbac.myfirstweb.model;

import lombok.*;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor

// This class's fields are used as additional columns in the container class's Table (Student)
// In container class (Student), use annotation @Embedded for the field of this Type
@Embeddable // Dont create a Table for this class,
public class StudentContact {

    // private String email; // Embedded Class must not have a field with same name as one field in the Container Class
    private String phone;
    @Column(
            nullable = false
    )
    private String guardianName;
    private String guardianPhone;
    @Column(
            nullable = false
    )
    private String address;
    @Column(
            nullable = false
    )
    private Boolean isLodging;
}
