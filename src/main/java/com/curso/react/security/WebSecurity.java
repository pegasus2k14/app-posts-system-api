package com.curso.react.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import com.curso.react.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity{

    private final  UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserService userService, BCryptPasswordEncoder encoder){
       this.userService = userService;
       this.bCryptPasswordEncoder =  encoder;
    }
    
  
     /**
    * indicamos que el endpoint "/users" es de tipo POST todos los demas seran privados es
      decir que requieren autenticacion. 
    */

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{
       //http.cors(cors -> cors.disable());
      /* http.cors(cors -> cors.configurationSource(request -> {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedMethods(List.of("POST"));
        return config;
    }));  */
        http.cors();
        http.csrf(cr -> cr.disable());  
        
        http.
           authorizeHttpRequests((auth)->auth
             .requestMatchers(HttpMethod.POST, "/users")
             .permitAll()
             .requestMatchers(HttpMethod.GET, "/posts/last")
             .permitAll()
             .requestMatchers(HttpMethod.GET,"/posts/{id}")
             .permitAll()
             .anyRequest()
             .authenticated()
             );   
             
            AuthtenticationFilter authtenticationFilter  = new AuthtenticationFilter(authenticationManager);
            authtenticationFilter.setFilterProcessesUrl("/users/login"); //Custom URL for Login
            http.addFilter(authtenticationFilter);
            http.addFilter(new AuthorizationFilter(authenticationManager));
            http.sessionManagement(sessionManagement->{
               sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            });
        return http.build();     
    }



     /*
       Aqui creamos un AuthenticationManager que es el encargado de autenticar a los usuarios 
     * indicamos el userService (servicio para manejar usuarios) que usaremos para nuestra aplicacion e indicamos el algoritmo de
     * codificacion que estaremos implementando para las contraseñas
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
       AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
       authBuilder.userDetailsService(this.userService).passwordEncoder(this.bCryptPasswordEncoder);
       return authBuilder.build();

    }
}
 