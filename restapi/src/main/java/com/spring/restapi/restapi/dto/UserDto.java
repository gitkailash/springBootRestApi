package com.spring.restapi.restapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int userId;
    @NotNull(message = "Username Can't Be Null")
    private String name;

    @Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @Pattern(regexp = "^\\d{10}$", message = "Invalid Mobile Number")
    private String mobile;

    private String gender;

    @Min(18)
    @Max(64)
    private int age;

    @NotBlank(message = "Can not Be Blank")
    private String nationality;

}
