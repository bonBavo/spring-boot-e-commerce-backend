package com.bonbravo.store.mappers;

import com.bonbravo.store.dto.OrderDto;
import com.bonbravo.store.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);
}
