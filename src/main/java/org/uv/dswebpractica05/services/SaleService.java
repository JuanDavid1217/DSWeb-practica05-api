/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.services;

import java.io.File;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.dswebpractica05.converters.NewSaleConverter;
import org.uv.dswebpractica05.converters.RegisteredSaleConverter;
import org.uv.dswebpractica05.dtos.NewDetailDto;
import org.uv.dswebpractica05.dtos.NewSaleDto;
import org.uv.dswebpractica05.dtos.RegisteredSaleDto;
import org.uv.dswebpractica05.exceptions.Exceptions;
import org.uv.dswebpractica05.models.Detail;
import org.uv.dswebpractica05.models.Product;
import org.uv.dswebpractica05.models.Sale;
import org.uv.dswebpractica05.models.User1;
import org.uv.dswebpractica05.reports.Email;
import org.uv.dswebpractica05.reports.Report;
import org.uv.dswebpractica05.repositories.ClientRepository;
import org.uv.dswebpractica05.repositories.ProductRepository;
import org.uv.dswebpractica05.repositories.SaleRepository;
import org.uv.dswebpractica05.repositories.UserRepository;
import static org.uv.dswebpractica05.validations.Validation.dateValidation;

/**
 *
 * @author David
 */
@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final NewSaleConverter newSaleConverter;
    private final RegisteredSaleConverter registeredSaleConverter;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Email email;
    
    public SaleService(SaleRepository saleRepository, NewSaleConverter newSaleConverter,
            RegisteredSaleConverter registeredSaleConverter, ClientRepository clientRepository,
            UserRepository userRepository, ProductRepository productRepository, Email email){
        this.saleRepository = saleRepository;
        this.newSaleConverter = newSaleConverter;
        this.registeredSaleConverter = registeredSaleConverter;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.email = email;
    }
    
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${jasperreport.path}")
    private String reportSchema;
    @Value("${reports.path}")
    private String finalReportPath;
    
    public void checkClient(long id){
        if (clientRepository.findById(id).isEmpty()){
            throw new Exceptions("Cliente No Registrado", HttpStatus.CONFLICT);
        }
    }
    
    public NewSaleDto checkDate(NewSaleDto newSaleDto){
        if (dateValidation(newSaleDto.getDate()).equals("Invalid Date.")){
            throw new Exceptions("Fecha Invalida", HttpStatus.CONFLICT);
        }else{
            newSaleDto.setDate(dateValidation(newSaleDto.getDate()));
        }
        return newSaleDto;
    }
    
    public List<NewDetailDto> confirmStock(List<NewDetailDto> details){
        details.removeIf(detail->detail.getQuantity()==0);
        for(NewDetailDto detail:details){
            Optional<Product> optionalProduct = productRepository.findById(detail.getProductId());
            if (optionalProduct.isEmpty()){
                throw new Exceptions("Producto No Encontrado", HttpStatus.CONFLICT);
            }else{
                Product product = optionalProduct.get();
                if (product.getStock()>detail.getQuantity()){
                    product.setStock(product.getStock()-detail.getQuantity());
                    productRepository.save(product);
                }else{
                    throw new Exceptions("Cantidad en existencia Insuficiente del producto: "+product.getName(), HttpStatus.CONFLICT);
                }
            }
        }
        return details;
    }
    
    public void cancelStock(List<Detail> details){
        for(Detail detail:details){
            Optional<Product> optionalProduct = productRepository.findById(detail.getProduct().getId());
            if (optionalProduct.isEmpty()){
                throw new Exceptions("Producto No Encontrado", HttpStatus.CONFLICT);
            }else{
                Product product = optionalProduct.get();
                product.setStock(product.getStock()+detail.getQuantity());
                productRepository.save(product);
            }
        }
    }
    
    @Transactional
    public RegisteredSaleDto createSale(NewSaleDto newSaleDto){
        String userName=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User1> optionalUser = userRepository.findByUserName(userName);
        checkClient(newSaleDto.getClient_id());
        newSaleDto = checkDate(newSaleDto);
        newSaleDto.setDetails(confirmStock(newSaleDto.getDetails()));
        if (newSaleDto.getDetails().size()>0){
            try{
                Sale sale = newSaleConverter.dtotoEntity(newSaleDto);
                sale.setUser(optionalUser.get());
                List<Detail> details = sale.getDetails();
                sale = saleRepository.save(sale);
                for (int i = 0; i < details.size(); i++){
                    Detail before = details.get(i);
                    before.setSale(sale);
                    details.set(i, before);
                }
                sale.setDetails(details);
                sale = saleRepository.save(sale);
                return registeredSaleConverter.entitytoDTO(sale);
            }catch(Exception e){
                throw new Exceptions("Ha ocurrido un error", HttpStatus.CONFLICT);
            }
        }else{
            throw new Exceptions("Ingresa por lo menos 1 producto", HttpStatus.CONFLICT);
        }
    }
    
    @Transactional
    public RegisteredSaleDto updateSale(long id, NewSaleDto newSaleDto){
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isEmpty()){
            return null;
        }else{
            String userName=SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User1> optionalUser = userRepository.findByUserName(userName);
            newSaleDto = checkDate(newSaleDto);
            cancelStock(optionalSale.get().getDetails());
            newSaleDto.setDetails(confirmStock(newSaleDto.getDetails()));
            if (newSaleDto.getDetails().size()>0){
                try{
                    Sale newSale = newSaleConverter.dtotoEntity(newSaleDto);
                    newSale.setUser(optionalUser.get());
                    newSale.setId(optionalSale.get().getId());
                    List<Detail> details = newSale.getDetails();
                    for (int i = 0; i < details.size(); i++){
                        Detail before = details.get(i);
                        before.setSale(newSale);
                        try{
                            before.setId(optionalSale.get().getDetails().get(i).getId());
                        }catch(Exception e){}
                        details.set(i, before);
                    }
                    newSale.setDetails(details);
                    newSale = saleRepository.save(newSale);
                    return registeredSaleConverter.entitytoDTO(newSale);
                }catch(Exception e){
                    throw new Exceptions("Ha ocurrido un error", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Ingresa por lo menos 1 producto", HttpStatus.CONFLICT);
            }
        }
    }
    
    public boolean deleteSale(long id){
        if (saleRepository.findById(id).isEmpty()){
            return false;
        }else{
            saleRepository.deleteById(id);
            return true;
        }
    }
    
    public RegisteredSaleDto findById(long id){
        Optional<Sale> optionalSale = saleRepository.findById(id);
        if (optionalSale.isEmpty()){
            return null;
        }else{
            return registeredSaleConverter.entitytoDTO(optionalSale.get());
        }
    }
    
    public List<RegisteredSaleDto> findAll(){
        return registeredSaleConverter.entityListtoDTOList(saleRepository.findAll());
    }
    
    public Resource createReport(long saleId){
        if (saleRepository.findById(saleId).isEmpty()){
            return null;
        }
        String path = Report.crateReport(url, user, password, reportSchema, finalReportPath, saleId);
        if (path==null || path.equals("")){
            throw new Exceptions("Ha ocurrido un error al crear el reporte", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try{
            File file = new File(path);
            Resource resource =  new FileSystemResource(file);
            return resource;
        }catch(Exception e){
            throw new Exceptions("Ha ocurrido un error al crear el reporte", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public boolean sendReport(long saleId){
        Optional<Sale> optionalSale = saleRepository.findById(saleId);
        if (optionalSale.isEmpty()){
            return false;
        }
        String path = Report.crateReport(url, user, password, reportSchema, finalReportPath, saleId);
        if (path==null || path.equals("")){
            throw new Exceptions("Ha ocurrido un error al crear el reporte", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try{
            File file = new File(path);
            email.sendEmailWithFile(optionalSale.get().getClient().getEmail(), "Reporte de Venta", "Crazy Dave Solutions le hace entrega de su reporte", file);
            file.delete();
            return true;
        }catch(Exception e){
            throw new Exceptions("Reporte no enviado, ha ocurrido un error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
