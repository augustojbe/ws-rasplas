package com.augustojbe.client.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "Atributo obrigatório")
    private String username;

    @NotBlank(message = "Atributo obrigatório")
    private String password;


}
