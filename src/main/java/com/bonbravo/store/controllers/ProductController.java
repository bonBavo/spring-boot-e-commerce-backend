package com.bonbravo.store.controllers;

import com.bonbravo.store.dtos.ProductDto;
import com.bonbravo.store.mappers.ProductMapper;
import com.bonbravo.store.models.Product;
import com.bonbravo.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping("{id}")
    public ProductDto getProduct(@PathVariable Long id){
        return productMapper.toDto(productRepository.findById(id).orElse(null));
    }

    @GetMapping
    public Iterable<ProductDto> getProducts(@RequestParam(required = false, defaultValue = "") String sort,
                                            @RequestParam(required = false, defaultValue = "", name = "categoryId") Byte categoryId

    ){
        List<Product> products;
        if(!Set.of("name", "category_id").contains(sort))
            sort = "name";

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        }else {
            products = productRepository.findAllWithCategory();

        }


        return products.stream()
                .map(productMapper::toDto)
                .toList();

    }
}
























//
//        else {
//            products = productRepository.findAll(Sort.by(sort));
//        }
//        if (sort.contains("name")) {
//
//        }