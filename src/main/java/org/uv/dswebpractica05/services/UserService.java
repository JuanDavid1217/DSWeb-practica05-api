/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.services;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.uv.dswebpractica05.converters.NewUserConverter;
import org.uv.dswebpractica05.converters.RegisteredUserConverter;
import org.uv.dswebpractica05.dtos.NewUserDto;
import org.uv.dswebpractica05.dtos.RegisteredUserDto;
import org.uv.dswebpractica05.exceptions.Exceptions;
import org.uv.dswebpractica05.models.User1;
import org.uv.dswebpractica05.repositories.UserRepository;

/**
 *
 * @author David
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final NewUserConverter newUserConverter;
    private final RegisteredUserConverter registeredUserConverter;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, NewUserConverter newUserConverter,
            RegisteredUserConverter registeredUserConverter, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.newUserConverter = newUserConverter;
        this.registeredUserConverter = registeredUserConverter;
        this.passwordEncoder = passwordEncoder;
    }
    
    public RegisteredUserDto createUser(NewUserDto newUserDto){
        if (userRepository.findByUserName(newUserDto.getUserName()).isEmpty()){
            newUserDto.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
            User1 user = newUserConverter.dtotoEntity(newUserDto);
            user = userRepository.save(user);
            return registeredUserConverter.entitytoDTO(user);
        }else{
            return null;
        }
    }
    
    public boolean deleteUser(long id){
        if (userRepository.findById(id).isEmpty()){
            return false;
        }else{
            try{
                userRepository.deleteById(id);
                return true;
            }catch(Exception e){
                throw new Exceptions("El usuario con id: "+id+" no puede ser eliminado.", HttpStatus.CONFLICT);
            }
        }
        
    }
    
}
