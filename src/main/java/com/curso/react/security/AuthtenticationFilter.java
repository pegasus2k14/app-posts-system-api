package com.curso.react.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.curso.react.context.SpringApplicationContext;
import com.curso.react.model.request.UserLoginRequestModel;
import com.curso.react.service.UserService;
import com.curso.react.shared.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthtenticationFilter extends UsernamePasswordAuthenticationFilter{
    
    private  AuthenticationManager authenticationManager;
    private SecretKey secret_key;

    //Constructor
    public AuthtenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        this.secret_key = Keys.hmacShaKeyFor(SecurityConstants.getTokenSecret().getBytes(StandardCharsets.UTF_8));
        
    }
    
    //metodo sobrescrito de UsernamePasswordAuthenticationFilter
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
      try{
        //Leemos todo lo que viene en el request y lo pegamos en nuestro objeto DTO UserLoginRequestModel (mail y password)
          UserLoginRequestModel userModel = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);
          //Mediante el metodo authenticate nos identificamos mediante el email y password que viene en la request
          return authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(
                   userModel.getEmail(), 
                   userModel.getPassword(), 
                   new ArrayList<>()));
      }catch(IOException e){
         throw new RuntimeException(e);   
      }
        
    }

    //este metodo se ejecuta en el caso que la autenticacion sea exitosa para generar el Token
    @Override 
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
    throws IOException, ServletException{
       String username = ((User) authentication.getPrincipal()).getUsername(); //recuperamos el email
       String token = Jwts.builder()
       .subject(username)
       .expiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_DATE))
       .signWith(secret_key)
       .compact();
       //recuperamos el Bean UserService del contexto de Spring
       UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
       UserDto userDto = userService.getUser(username);
       response.addHeader("Access-Control-Expose-Headers", "Authorization, UserId");
       response.addHeader("UserId", userDto.getUserId());
       response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    } 
}
