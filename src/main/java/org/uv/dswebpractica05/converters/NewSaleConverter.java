/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.dswebpractica05.dtos.NewSaleDto;
import org.uv.dswebpractica05.models.Client;
import org.uv.dswebpractica05.models.Sale;
import org.uv.dswebpractica05.models.User1;
import static org.uv.dswebpractica05.validations.Validation.stringtoDate;

/**
 *
 * @author David
 */
@Component
public class NewSaleConverter implements NewConverter <Sale, NewSaleDto> {
    private final NewDetailConverter newDetailConverter;
    
    public NewSaleConverter(NewDetailConverter newDetailConverter){
        this.newDetailConverter = newDetailConverter;
    }
    
    @Override
    public Sale dtotoEntity(NewSaleDto dto) {
        Sale sale = new Sale();
        Client client = new Client();
        client.setId(dto.getClient_id());
        sale.setClient(client);
        
        sale.setDate(new java.sql.Date(stringtoDate(dto.getDate()).getTime()));
        
        sale.setDetails(newDetailConverter.dtoListtoEntityList(dto.getDetails()));
        sale.setTotal(dto.getTotal());
        
        return sale;
    }

    @Override
    public List<Sale> dtoListtoEntityList(List<NewSaleDto> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
