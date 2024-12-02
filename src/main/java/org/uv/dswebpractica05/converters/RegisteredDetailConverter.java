/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.RegisteredDetailDto;
import org.uv.dswebpractica05.models.Detail;

/**
 *
 * @author David
 */
@Component
public class RegisteredDetailConverter implements RegisteredConverter <Detail, RegisteredDetailDto>{

    @Override
    public RegisteredDetailDto entitytoDTO(Detail entity) {
        RegisteredDetailDto detail = new RegisteredDetailDto();
        detail.setDescription(entity.getDescription());
        detail.setId(entity.getId());
        detail.setName(entity.getName());
        detail.setPrice(entity.getPrice());
        detail.setQuantity(entity.getQuantity());
        detail.setProductId(entity.getProduct().getId());
        return detail;
    }

    @Override
    public List<RegisteredDetailDto> entityListtoDTOList(List<Detail> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
