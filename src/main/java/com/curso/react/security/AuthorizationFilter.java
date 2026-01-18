package com.curso.react.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter{

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
       //Recuperamos el header que viene en la request del cliente         
       String header = request.getHeader(SecurityConstants.HEADER_STRING);
       //Si el headder es nulo  o no inicia con Bearer
       if(header== null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
         //pasamos la request al siguiente filtro
         chain.doFilter(request, response);
         return;
       } 
       UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
       //Seteamos el token al contexto de la aplicacion
       SecurityContextHolder.getContext().setAuthentication(authenticationToken);
       chain.doFilter(request, response);
    }  

    private UsernamePasswordAuthenticationToken  getAuthentication(HttpServletRequest request){
      //Extraemos el token que viene con la request
      String token = request.getHeader(SecurityConstants.HEADER_STRING);
      //Si eltoken existe
      if(token!=null){
        //Quitamos la sub cadena 'Bearer ' del token
        token = token.replace(SecurityConstants.TOKEN_PREFIX, ""); 
        String user = Jwts.parser()
           .verifyWith(Keys.hmacShaKeyFor(SecurityConstants.getTokenSecret().getBytes()))
           .build()
           .parseSignedClaims(token)
           .getPayload()
           .getSubject();  //obtenemos el email del usuario
         System.out.println("USER EMAIL -->"+ user);
         if(user!=null){
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
         }   
         return null;
      }
      return null;
    }
}
