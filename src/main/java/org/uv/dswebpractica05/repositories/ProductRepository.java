/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.dswebpractica05.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dswebpractica05.models.Product;

/**
 *
 * @author David
 */
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
