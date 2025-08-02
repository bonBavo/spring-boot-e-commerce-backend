package com.bonbravo.store.services;

import com.bonbravo.store.dto.CartDto;
import com.bonbravo.store.dto.CartItemDto;
import com.bonbravo.store.exceptions.CartNotFoundException;
import com.bonbravo.store.exceptions.ProductNotFoundException;
import com.bonbravo.store.mappers.CartMapper;
import com.bonbravo.store.entities.Cart;
import com.bonbravo.store.repositories.CartRepository;
import com.bonbravo.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {

    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();

        var product = productRepository.findById(productId).orElse(null);
        if (product == null)
            throw new ProductNotFoundException();

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }
    /*
    public CartItemDto addToCart(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }

        var cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }
    * */

    public CartDto getCartItem(UUID id){
        var cart = cartRepository.getCartWithItems(id).orElse(null);

        if (cart == null)
            throw new CartNotFoundException();

        return cartMapper.toDto(cart);
    }


    public CartItemDto updateItem( UUID cartId, Long productId, Integer quantity){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new  CartNotFoundException();

        var cartItem = cart.getCartItem(productId);

        if (cartItem == null) {
            throw new  ProductNotFoundException();
        }

        cartItem.setQuantity(quantity);
        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void removeCartItem(UUID cartId, Long productId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
           throw new  CartNotFoundException();

        cart.removeItem(productId);

        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new  CartNotFoundException();
        cart.clearCart();
        cartRepository.save(cart);
    }

}
