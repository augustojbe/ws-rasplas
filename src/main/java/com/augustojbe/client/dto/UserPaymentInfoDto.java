package com.augustojbe.client.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPaymentInfoDto {


    private Long id;

    @Size(min = 16, max = 16, message = "deve conter 16 caracteres")
    private String cardNumber;

    @Min(value = 1)
    @Max(value = 12)
    private Long cardExpirationMonth;

    private Long cardExpirationYear;

    @Size(min = 3, max = 3, message = "deve conter 3 caractere")
    private String cardSecurityCode;

    private BigDecimal price;

    private LocalDate dtPayment;

    private Long installments;

    @NotNull(message = "deve ser informado")
    private Long userId;
}
