package com.sharing.doc_sharing.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ChangePasswordDto {

    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String newPassword;

    @NotBlank
    private String confirmNewPassword;

    // Getters and Setters
}
