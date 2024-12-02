/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.NewDetailDto;
import org.uv.dswebpractica05.models.Detail;
import org.uv.dswebpractica05.models.Product;
import org.uv.dswebpractica05.repositories.ProductRepository;

/**
 *
 * @author David
 */
@Component
public class NewDetailConverter implements NewConverter <Detail, NewDetailDto>{
    private final ProductRepository productRepository;
    
    public NewDetailConverter(ProductRepository productRepository){
        this.productRepository = productRepository;
    }
    
    @Override
    public Detail dtotoEntity(NewDetailDto dto) {
        Detail detail = new Detail();
        Product product = productRepository.getById(dto.getProductId());
        detail.setProduct(product);
        detail.setQuantity(dto.getQuantity());
        return detail;
    }

    @Override
    public List<Detail> dtoListtoEntityList(List<NewDetailDto> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
