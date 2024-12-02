/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.RegisteredUserDto;
import org.uv.dswebpractica05.models.User1;

/**
 *
 * @author David
 */
@Component
public class RegisteredUserConverter implements RegisteredConverter <User1, RegisteredUserDto>{

    @Override
    public RegisteredUserDto entitytoDTO(User1 entity) {
        RegisteredUserDto user = new RegisteredUserDto();
        user.setUserName(entity.getUserName());
        user.setId(entity.getId());
        return user;
    }

    @Override
    public List<RegisteredUserDto> entityListtoDTOList(List<User1> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
