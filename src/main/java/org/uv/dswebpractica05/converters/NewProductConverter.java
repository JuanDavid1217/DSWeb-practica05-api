/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.NewProductDto;
import org.uv.dswebpractica05.models.Product;

/**
 *
 * @author David
 */
@Component
public class NewProductConverter implements NewConverter <Product, NewProductDto> {

    @Override
    public Product dtotoEntity(NewProductDto dto) {
        Product product = new Product();
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }

    @Override
    public List<Product> dtoListtoEntityList(List<NewProductDto> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
