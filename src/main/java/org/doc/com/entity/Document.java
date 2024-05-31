package org.doc.com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.doc.com.enums.Status;
import org.doc.com.enums.Department;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Serial number please")
    @Pattern(regexp = "^[A-Z]{2}\\d{5}-((EC)|(HR)|(DE)|(MA))$",
            message = "Invalid serial number")
    private String serialNumber;

    @NotBlank(message = "Title can not be empty")
    @Length(max = 40, message = "This field mustn't be longer than 40 characters")
    private String title;

    @Enumerated(EnumType.STRING)
    private Status status = Status.DRAFT;

    @Enumerated(EnumType.STRING)
    private Department department;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotBlank(message = "Document text is required")
    @Length(max = 2000, message = "Document text mustn't be longer than 2000 characters")
    private String text;

    // TODO: Attachment implementation

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Document(String serialNumber, String title, Department department, String text) {
        this.serialNumber = serialNumber;
        this.title = title;
        this.status = Status.DRAFT;
        this.department = department;
        this.text = text;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}