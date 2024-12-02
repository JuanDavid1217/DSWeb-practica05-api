/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.dswebpractica05.converters.NewClientConverter;
import org.uv.dswebpractica05.converters.RegisteredClientConverter;
import org.uv.dswebpractica05.dtos.NewClientDto;
import org.uv.dswebpractica05.dtos.RegisteredClientDto;
import org.uv.dswebpractica05.exceptions.Exceptions;
import org.uv.dswebpractica05.models.Client;
import org.uv.dswebpractica05.repositories.ClientRepository;

/**
 *
 * @author David
 */
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final NewClientConverter newClientConverter;
    private final RegisteredClientConverter registeredClientConverter;
    
    public ClientService(ClientRepository clientRepository, NewClientConverter newClientConverter, RegisteredClientConverter registeredClientConverter){
        this.clientRepository = clientRepository;
        this.newClientConverter = newClientConverter;
        this.registeredClientConverter = registeredClientConverter;
    }
    
    public RegisteredClientDto createClient(NewClientDto newClientDto){
        if(clientRepository.findByEmail(newClientDto.getEmail()).isEmpty()){
            Client client = newClientConverter.dtotoEntity(newClientDto);
            client = clientRepository.save(client);
            return registeredClientConverter.entitytoDTO(client);
        }else{
            throw new Exceptions("Email: "+newClientDto.getEmail()+" previamente registrado.", HttpStatus.CONFLICT);
        }
    }
    
    public RegisteredClientDto updateClient(long id, NewClientDto newClientDto){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()){
            return null;
        }else{
            if(clientRepository.findByEmail(newClientDto.getEmail()).isEmpty() || clientRepository.findByEmail(newClientDto.getEmail()).get().getId()==id){
                Client newClient = newClientConverter.dtotoEntity(newClientDto);
                newClient.setId(optionalClient.get().getId());
                newClient.setSales(optionalClient.get().getSales());
                newClient = clientRepository.save(newClient);
                return registeredClientConverter.entitytoDTO(newClient);
            }else{
                throw new Exceptions("Email: "+newClientDto.getEmail()+" previamente registrado.", HttpStatus.CONFLICT);
            }
        }
    }
    
    public boolean deleteClient(long id){
        if (clientRepository.findById(id).isEmpty()){
            return false;
        }else{
            clientRepository.deleteById(id);
            return true;
        }
    }
    
    public RegisteredClientDto findById(long id){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()){
            return null;
        }else{
            return registeredClientConverter.entitytoDTO(optionalClient.get());
        }
    }
    
    public List<RegisteredClientDto> findAll(){
        return registeredClientConverter.entityListtoDTOList(clientRepository.findAll());
    }
}
