package org.doc.com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.doc.com.enums.Department;
import org.doc.com.enums.Status;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private Long id;

    private String serialNumber;

    private String title;

    private Status status;

    private Department department;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String text;

    private Long userId;
}