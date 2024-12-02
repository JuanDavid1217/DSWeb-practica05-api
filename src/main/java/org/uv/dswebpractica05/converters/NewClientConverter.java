/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.NewClientDto;
import org.uv.dswebpractica05.models.Client;

/**
 *
 * @author David
 */
@Component
public class NewClientConverter implements NewConverter <Client, NewClientDto> {

    @Override
    public Client dtotoEntity(NewClientDto dto) {
        Client client = new Client();
        client.setAddress(dto.getAddress());
        client.setEmail(dto.getEmail());
        client.setLastName(dto.getLastName());
        client.setMaternalSurname(dto.getMaternalSurname());
        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        return client;
    }

    @Override
    public List<Client> dtoListtoEntityList(List<NewClientDto> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
