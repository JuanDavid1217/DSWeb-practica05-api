/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.uv.dswebpractica05.models.User1;
import org.uv.dswebpractica05.repositories.UserRepository;

/**
 *
 * @author juan
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService{
    private final UserRepository repository;
    
    public UserDetailsServiceImp(UserRepository repository){
        this.repository=repository;
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User1> userDetails= repository.findByUserName(userName);
        if(userDetails.isEmpty()){
            throw new UsernameNotFoundException("El usuario "+userName+" no exite");
        }else{
            GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_USER");
            List<GrantedAuthority> list=new ArrayList<>();
            list.add(authority);

            Collection<? extends GrantedAuthority> authorities=list;

            return new User(userName, userDetails.get().getPassword(), true,
            true, true, true, authorities);
            
        }
        
        
        
    }
    
}
