package com.bonbravo.store.controllers;

import com.bonbravo.store.dto.CheckoutRequest;
import com.bonbravo.store.dto.CheckoutResponse;
import com.bonbravo.store.dto.ErrorDto;
import com.bonbravo.store.exceptions.CartEmptyException;
import com.bonbravo.store.exceptions.CartNotFoundException;
import com.bonbravo.store.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout(
            @Valid @RequestBody CheckoutRequest checkoutRequest){

        return checkoutService.checkout(checkoutRequest);
    }

    @ExceptionHandler({CartEmptyException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
