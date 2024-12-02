/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.dswebpractica05.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.dswebpractica05.models.User1;

/**
 *
 * @author David
 */
public interface UserRepository extends JpaRepository<User1, Long>{
    public Optional<User1> findByUserName(String userName);
}
