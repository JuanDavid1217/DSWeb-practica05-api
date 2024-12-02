/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.controllers;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.uv.dswebpractica05.dtos.NewUserDto;
import org.uv.dswebpractica05.dtos.RegisteredUserDto;
import org.uv.dswebpractica05.exceptions.Exceptions;
import org.uv.dswebpractica05.services.UserService;

/**
 *
 * @author David
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService){
        this.userService = userService;
    }
    
    @PostMapping("/createAccount")
    public ResponseEntity<RegisteredUserDto> createAccount(@RequestBody NewUserDto newUserDto){
        RegisteredUserDto newUser = userService.createUser(newUserDto);
        if (newUser == null){
            throw new Exceptions("El nombre de usuario "+newUserDto.getUserName()+" ya se encuentra registrado", HttpStatus.CONFLICT);
        }else{
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        
            return ResponseEntity.created(ubication).body(newUser);
        }
    }
    
    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<RegisteredUserDto> deleteAccount(@PathVariable("id") long id){
        boolean response = userService.deleteUser(id);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
}
