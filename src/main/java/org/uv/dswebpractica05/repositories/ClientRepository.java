/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.dswebpractica05.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dswebpractica05.models.Client;

/**
 *
 * @author David
 */
public interface ClientRepository extends JpaRepository<Client, Long>{
    public Optional<Client> findByEmail(String email);
}
