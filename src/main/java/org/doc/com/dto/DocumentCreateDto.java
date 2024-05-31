package org.doc.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.doc.com.enums.Department;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentCreateDto {

    @NotBlank
    private String serialNumber;

    @NotBlank
    private String title;

    @NotNull
    private Department department;

    @NotBlank
    @Size(max = 2000)
    private String text;
}