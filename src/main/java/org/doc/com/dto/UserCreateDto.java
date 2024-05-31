package org.doc.com.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class UserCreateDto {

    @NotBlank
    @Size(max = 40, min = 2,
            message = "Name mustn't be longer than 40 and shorter than 2 characters")
    private String name;

    @NotBlank
    @Size(max = 40, min = 2,
            message = "Surname mustn't be longer than 40 and shorter than 2 characters")
    private String surname;

    @NotBlank
    @Size(max = 40, min = 3,
            message = "Position mustn't be longer than 40 and shorter than 2 characters")
    private String position;

    @NotNull
    private Department department;

    @NotBlank
    @Size(min = 10, max = 40, message = "Password must be more than 9 and less then 41 characters")
    private String password;
}