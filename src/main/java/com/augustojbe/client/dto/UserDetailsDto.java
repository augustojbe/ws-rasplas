package com.augustojbe.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsDto {

    @Email(message = "email inválido")
    private String email;

    @NotBlank(message = "atributo inválido")
    private String password;

    private String recoveryCode;
}
