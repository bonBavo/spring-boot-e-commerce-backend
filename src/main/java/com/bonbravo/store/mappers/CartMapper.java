package com.bonbravo.store.mappers;


import com.bonbravo.store.dto.CartDto;
import com.bonbravo.store.dto.CartItemDto;
import com.bonbravo.store.models.Cart;
import com.bonbravo.store.models.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "items", source = "items")
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
