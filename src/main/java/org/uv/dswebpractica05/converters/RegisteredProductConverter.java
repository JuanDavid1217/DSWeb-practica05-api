/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.RegisteredProductDto;
import org.uv.dswebpractica05.models.Product;

/**
 *
 * @author David
 */
@Component
public class RegisteredProductConverter implements RegisteredConverter <Product, RegisteredProductDto>{

    @Override
    public RegisteredProductDto entitytoDTO(Product entity) {
        RegisteredProductDto product = new RegisteredProductDto();
        product.setDescription(entity.getDescription());
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setPrice(entity.getPrice());
        product.setStock(entity.getStock());
        return product;
    }

    @Override
    public List<RegisteredProductDto> entityListtoDTOList(List<Product> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
