package com.alkafol.srmmicroservice.dto.clientdto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyRequestDto {
    private long phoneNumber;

    @Min(1)
    private double money;
}
