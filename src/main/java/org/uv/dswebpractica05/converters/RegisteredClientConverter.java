/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.RegisteredClientDto;
import org.uv.dswebpractica05.models.Client;

/**
 *
 * @author David
 */
@Component
public class RegisteredClientConverter implements RegisteredConverter <Client, RegisteredClientDto>{

    @Override
    public RegisteredClientDto entitytoDTO(Client entity) {
        RegisteredClientDto client = new RegisteredClientDto();
        client.setAddress(entity.getAddress());
        client.setEmail(entity.getEmail());
        client.setId(entity.getId());
        client.setLastName(entity.getLastName());
        client.setMaternalSurname(entity.getMaternalSurname());
        client.setName(entity.getName());
        client.setPhone(entity.getPhone());
        return client;
    }

    @Override
    public List<RegisteredClientDto> entityListtoDTOList(List<Client> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
