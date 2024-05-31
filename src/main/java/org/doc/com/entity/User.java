package org.doc.com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.doc.com.enums.Department;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "This field must be filled")
    @Length(max = 40, min = 2,
            message = "Name mustn't be longer than 40 and shorter than 2 characters")
    private String name;

    @NotBlank(message = "This field must be filled")
    @Length(max = 40, min = 2,
            message = "Surname mustn't be longer than 40 and shorter than 2 characters")
    private String surname;

    @NotBlank(message = "This field must be filled")
    @Length(max = 40, min = 3,
            message = "Position mustn't be longer than 40 and shorter than 3 characters")
    private String position;

    @Enumerated(EnumType.STRING)
    private Department department;

    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Document> documents;
}