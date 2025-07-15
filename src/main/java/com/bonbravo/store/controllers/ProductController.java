package com.bonbravo.store.controllers;

import com.bonbravo.store.dto.ProductDto;
import com.bonbravo.store.mappers.ProductMapper;
import com.bonbravo.store.models.Product;
import com.bonbravo.store.repositories.CategoryRepository;
import com.bonbravo.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id){
        return productMapper.toDto(productRepository.findById(id).orElse(null));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            UriComponentsBuilder contentsBuilder,
            @RequestBody ProductDto request){

        var product = productMapper.toEntity(request);
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        product.setCategory(category);
        productRepository.save(product);
        var uri = contentsBuilder.path("/product/{id}").buildAndExpand(request.getId()).toUri();
        var productDto = productMapper.toDto(product);

        return ResponseEntity.created(uri).body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
        @RequestBody ProductDto request,
        @PathVariable(name = "id") Long id
    ){
        var category = categoryRepository.findById(request.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(id).orElse(null);
        if(product == null)
            return ResponseEntity.notFound().build();
        productMapper.updateEntity(request, product);
        product.setCategory(category);
        productRepository.save(product);

        request.setId(product.getId());

        return ResponseEntity.ok(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteUser(@PathVariable(name = "id") Long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null)
            return ResponseEntity.notFound().build();
        productRepository.delete(product);
        return ResponseEntity.noContent().build();
    }


}
























//
//        else {
//            products = productRepository.findAll(Sort.by(sort));
//        }
//        if (sort.contains("name")) {
//
//        }