package com.bonbravo.store.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class CheckoutRequest {
    @NotNull(message = "CartId id required")
    private UUID cartId;

}
