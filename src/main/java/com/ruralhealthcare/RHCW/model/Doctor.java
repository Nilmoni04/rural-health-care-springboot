package com.ruralhealthcare.RHCW.model;

import jakarta.persistence.*;
<<<<<<< HEAD
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
=======
>>>>>>> 247573fd9cc57ab1709113e5f68b6635015c134f
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @NotBlank
    private String name;

    @NotBlank
    private String specialization;

    private String phone;

    @Email
    private String email;

=======
    private String name;
    private String specialization;
    private String phone;
    private String email;
>>>>>>> 247573fd9cc57ab1709113e5f68b6635015c134f
    private String location;
}
