package com.bonbravo.store.mappers;

import com.bonbravo.store.dtos.ProductDto;
import com.bonbravo.store.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
}
