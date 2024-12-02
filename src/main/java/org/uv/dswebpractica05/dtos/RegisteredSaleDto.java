/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.dtos;

import java.util.List;

/**
 *
 * @author David
 */
public class RegisteredSaleDto extends SaleDto{
    private long id;
    private String user;
    private String client;
    private List<RegisteredDetailDto> details;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    
    public List<RegisteredDetailDto> getDetails() {
        return details;
    }

    public void setDetails(List<RegisteredDetailDto> details) {
        this.details = details;
    }
    
}
