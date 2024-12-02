/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.NewUserDto;
import org.uv.dswebpractica05.models.User1;

/**
 *
 * @author David
 */
@Component
public class NewUserConverter implements NewConverter <User1, NewUserDto>{

    @Override
    public User1 dtotoEntity(NewUserDto dto) {
        User1 user = new User1();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public List<User1> dtoListtoEntityList(List<NewUserDto> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
