package com.ruralhealthcare.RHCW.model;

import jakarta.persistence.*;
<<<<<<< HEAD
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
=======
>>>>>>> 247573fd9cc57ab1709113e5f68b6635015c134f
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
<<<<<<< HEAD

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Min(0) @Max(150)
    private Integer age;

    @NotBlank
    private String gender;

=======
    private String name;
    private String email;
    private Integer age;
    private String gender;
>>>>>>> 247573fd9cc57ab1709113e5f68b6635015c134f
    private String address;
    private String language;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
<<<<<<< HEAD
        if (createdAt == null) {
=======
        if(createdAt == null) {
>>>>>>> 247573fd9cc57ab1709113e5f68b6635015c134f
            createdAt = LocalDateTime.now();
        }
    }
}
