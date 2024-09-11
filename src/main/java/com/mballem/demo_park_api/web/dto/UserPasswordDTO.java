package com.mballem.demo_park_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordDTO {

    @Size(min = 6,max = 6)
    @NotBlank
    private String active;
    @NotBlank
    @Size(min = 6,max = 6)
    private String newP;
    @NotBlank
    @Size(min = 6,max = 6)
    private String confirm;
}
