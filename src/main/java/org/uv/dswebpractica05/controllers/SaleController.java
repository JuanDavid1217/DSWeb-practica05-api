/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.controllers;

import java.io.File;
import java.net.URI;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import org.uv.dswebpractica05.dtos.NewSaleDto;
import org.uv.dswebpractica05.dtos.RegisteredSaleDto;
import org.uv.dswebpractica05.services.SaleService;

/**
 *
 * @author David
 */
@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;
    
    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }
    
    @PostMapping()
    public ResponseEntity<RegisteredSaleDto> createSale(@RequestBody NewSaleDto newSaleDto){
        RegisteredSaleDto newSale = saleService.createSale(newSaleDto);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newSale.getId()).toUri();
        return ResponseEntity.created(ubication).body(newSale);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RegisteredSaleDto> updateSale(@PathVariable("id") long id, @RequestBody NewSaleDto newSaleDto){
        RegisteredSaleDto updatedSale = saleService.updateSale(id, newSaleDto);
        if (updatedSale == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSale);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable("id") long id){
        boolean response = saleService.deleteSale(id);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RegisteredSaleDto> findById(@PathVariable("id") long id){
        RegisteredSaleDto sale = saleService.findById(id);
        if (sale == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sale);
    }
    
    @GetMapping()
    public ResponseEntity<List<RegisteredSaleDto>> findAll(){
        return ResponseEntity.ok(saleService.findAll());
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") long id) {
        Resource resource = saleService.createReport(id);
        if (resource == null){
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
        return ResponseEntity.ok().headers(headers).body(resource);
    }
    
    @GetMapping("/sendSale/{id}")
    public ResponseEntity<Void> sendSale(@PathVariable("id") long id){
        boolean response = saleService.sendReport(id);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
