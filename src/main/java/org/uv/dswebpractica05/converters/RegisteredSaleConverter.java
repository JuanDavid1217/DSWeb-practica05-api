/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.RegisteredSaleDto;
import org.uv.dswebpractica05.models.Sale;
import static org.uv.dswebpractica05.validations.Validation.datetoSring;

/**
 *
 * @author David
 */
@Component
public class RegisteredSaleConverter implements RegisteredConverter <Sale, RegisteredSaleDto>{
    private final RegisteredDetailConverter registeredDetailConverter;
    
    public RegisteredSaleConverter(RegisteredDetailConverter registeredDetailConverter){
        this.registeredDetailConverter = registeredDetailConverter;
    }
    
    @Override
    public RegisteredSaleDto entitytoDTO(Sale entity) {
        RegisteredSaleDto sale = new RegisteredSaleDto();
        sale.setId(entity.getId());
        
        sale.setClient(entity.getClient().getName()+" "+entity.getClient().getLastName());
        
        //You must review this convertion (SQL DATE to STRING)
        sale.setDate(datetoSring(entity.getDate()));
        
        
        sale.setDetails(registeredDetailConverter.entityListtoDTOList(entity.getDetails()));
        
        sale.setTotal(entity.getTotal());
        sale.setUser(entity.getUser().getUserName());
        return sale;
    }

    @Override
    public List<RegisteredSaleDto> entityListtoDTOList(List<Sale> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
