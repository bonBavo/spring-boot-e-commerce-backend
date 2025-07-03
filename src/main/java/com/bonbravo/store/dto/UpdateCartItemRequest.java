package com.bonbravo.store.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {

    @NotNull(message = "Quantity must be provided")
    @Min(value = 1, message = "quantinty cant be less than 1")
    @Max(value = 1000, message = "Quantity mus not exceed 1000")
    private Integer quantity;

}
