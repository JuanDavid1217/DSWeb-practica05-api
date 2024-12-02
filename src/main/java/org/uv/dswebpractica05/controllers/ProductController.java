/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.uv.dswebpractica05.dtos.NewProductDto;
import org.uv.dswebpractica05.dtos.RegisteredProductDto;
import org.uv.dswebpractica05.services.ProductService;

/**
 *
 * @author David
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    
    @PostMapping()
    public ResponseEntity<RegisteredProductDto> createProduct(@RequestBody NewProductDto newProductDto){
        RegisteredProductDto newProduct = productService.createProduct(newProductDto);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newProduct.getId()).toUri();
        return ResponseEntity.created(ubication).body(newProduct);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RegisteredProductDto> updateProduct(@PathVariable("id") long id, @RequestBody NewProductDto newProductDto){
        RegisteredProductDto updatedProduct = productService.updateProduct(id, newProductDto);
        if (updatedProduct==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    } 
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id){
        boolean response = productService.deleteProduct(id);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RegisteredProductDto> findById(@PathVariable("id") long id){
        RegisteredProductDto product = productService.findById(id);
        if (product == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(product);
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<RegisteredProductDto>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
}
