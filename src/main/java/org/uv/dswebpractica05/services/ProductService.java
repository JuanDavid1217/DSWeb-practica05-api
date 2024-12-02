/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.dswebpractica05.converters.NewProductConverter;
import org.uv.dswebpractica05.converters.RegisteredProductConverter;
import org.uv.dswebpractica05.dtos.NewProductDto;
import org.uv.dswebpractica05.dtos.RegisteredProductDto;
import org.uv.dswebpractica05.exceptions.Exceptions;
import org.uv.dswebpractica05.models.Product;
import org.uv.dswebpractica05.repositories.ProductRepository;

/**
 *
 * @author David
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final NewProductConverter newProductConverter;
    private final RegisteredProductConverter registeredProductConverter;
    
    public ProductService(ProductRepository productRepository, NewProductConverter newProductConverter, RegisteredProductConverter registeredProductConverter){
        this.productRepository = productRepository;
        this.newProductConverter = newProductConverter;
        this.registeredProductConverter = registeredProductConverter;
    }
    
    public RegisteredProductDto createProduct(NewProductDto newProductDto){
        Product product = newProductConverter.dtotoEntity(newProductDto);
        product = productRepository.save(product);
        return registeredProductConverter.entitytoDTO(product);
    }
    
    public RegisteredProductDto updateProduct(long id, NewProductDto newProductDto){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            return null;
        }else{
            Product newProduct = newProductConverter.dtotoEntity(newProductDto);
            newProduct.setId(optionalProduct.get().getId());
            newProduct = productRepository.save(newProduct);
            return registeredProductConverter.entitytoDTO(newProduct);
        }
    }
    
    public boolean deleteProduct(long id){
        if (productRepository.findById(id).isEmpty()){
            return false;
        }else{
            try{
                productRepository.deleteById(id);
                return true;
            }catch(Exception e){
                throw new Exceptions("El producto con id: "+id+" no puede ser eliminado.", HttpStatus.CONFLICT);
            }
        }
    }
    
    public RegisteredProductDto findById(long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()){
            return null;
        }else{
            return registeredProductConverter.entitytoDTO(optionalProduct.get());
        }
    }
    
    public List<RegisteredProductDto> findAll(){
        return registeredProductConverter.entityListtoDTOList(productRepository.findAll());
    }
    
}
