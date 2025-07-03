package com.bonbravo.store.controllers;


import com.bonbravo.store.dto.AddItemToCartRequest;
import com.bonbravo.store.dto.CartDto;
import com.bonbravo.store.dto.CartItemDto;
import com.bonbravo.store.dto.UpdateCartItemRequest;
import com.bonbravo.store.exceptions.CartNotFoundException;
import com.bonbravo.store.exceptions.ProductNotFoundException;
import com.bonbravo.store.services.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.annotation.Target;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "cart")
public class CartController {

    private final CartService cartService;


    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){
        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @RequestBody AddItemToCartRequest request
    ){
      var cartItemdto = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemdto);
    }


    @GetMapping("/{cartId}")
    public CartDto getCartItems(@PathVariable UUID cartId){
        return cartService.getCartItem(cartId);
    }


    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItems(
            @PathVariable(name = "productId") Long productId,
            @PathVariable(name = "cartId") UUID cartId,
            @RequestBody @Valid UpdateCartItemRequest request
    ){

        return cartService.updateItem(cartId, productId, request.getQuantity());
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> removeItems(
            @PathVariable("cartId") UUID cartId,
            @PathVariable("productId") Long productId
    ){
        cartService.removeCartItem(cartId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(
            @PathVariable UUID cartId
    ) {
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Product not found in the cart"));
    }


}
