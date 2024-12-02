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
import org.uv.dswebpractica05.dtos.NewClientDto;
import org.uv.dswebpractica05.dtos.RegisteredClientDto;
import org.uv.dswebpractica05.services.ClientService;

/**
 *
 * @author David
 */
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;
    
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    
    @PostMapping()
    public ResponseEntity<RegisteredClientDto> createClient(@RequestBody NewClientDto newClientDto){
        RegisteredClientDto newClient  = clientService.createClient(newClientDto);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newClient.getId()).toUri();
        return ResponseEntity.created(ubication).body(newClient);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RegisteredClientDto> updateClient(@PathVariable("id") long id, @RequestBody NewClientDto newClientDto){
        RegisteredClientDto updatedClient = clientService.updateClient(id, newClientDto);
        if (updatedClient==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedClient);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") long id){
        boolean response = clientService.deleteClient(id);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RegisteredClientDto> findById(@PathVariable("id") long id){
        RegisteredClientDto client = clientService.findById(id);
        if (client==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }
    
    @GetMapping()
    public ResponseEntity<List<RegisteredClientDto>> findAll(){
        return ResponseEntity.ok(clientService.findAll());
    }
}
