package com.mballem.demo_park_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreaterDTO {

    @NotBlank
    @Email(message = "Invalid format")
    private String username;
    @NotBlank
    @Size(min = 6,max = 6)
    private String password;

}
