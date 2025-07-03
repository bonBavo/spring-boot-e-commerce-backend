package com.bonbravo.store.mappers;

import com.bonbravo.store.dto.ProductDto;
import com.bonbravo.store.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    Product toEntity(ProductDto dto);

    @Mapping(target = "id",  ignore = true)
    void updateEntity(ProductDto dto, @MappingTarget Product product);

}
