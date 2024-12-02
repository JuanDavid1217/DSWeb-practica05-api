/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author David
 */
public class NewSaleDto extends SaleDto{
    private long client_id;
    private List<NewDetailDto> details;

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }
    
    public List<NewDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<NewDetailDto> details) {
        this.details = details;
    }

}
