/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.dswebpractica05.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.uv.dswebpractica05.models.User1;
import org.uv.dswebpractica05.repositories.UserRepository;

/**
 *
 * @author juan
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    private final JWTUtils jwtUtils;
    private final UserRepository userRepository;
    
    public JWTAuthenticationFilter(JWTUtils jwtUtils, UserRepository userRepository){
        this.jwtUtils=jwtUtils;
        this.userRepository = userRepository;
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {
        User1 u=null;
        String user;
        String password;
        try{
            u = new ObjectMapper().readValue(request.getInputStream(), User1.class);
            user=u.getUserName();
            password=u.getPassword();
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user, password);
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, 
                                            HttpServletResponse response, 
                                            FilterChain chain, 
                                            Authentication authResult) 
                                            throws IOException, ServletException {
        
        User u=(User)authResult.getPrincipal();
        Optional<User1> usuario = userRepository.findByUserName(u.getUsername());
        String [] parts = usuario.get().getClass().getName().split("[.]");
        String token=jwtUtils.generateAccesToken(u.getUsername(), usuario.get().getId(), parts[parts.length-1]);
        response.addHeader("Authorization", token);
        Map<String, String> httpResponse = new HashMap<>();
        httpResponse.put("id", String.valueOf(usuario.get().getId()));
        httpResponse.put("type", parts[parts.length-1]);
        httpResponse.put("token", token);
        httpResponse.put("message", "Correct Authentication");
        httpResponse.put("Username", u.getUsername());
        
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        response.getWriter().flush();
        
        super.successfulAuthentication(request, response, chain, authResult); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
}
