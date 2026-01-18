package com.curso.react.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.react.model.request.UserDetailRequestModel;
import com.curso.react.model.response.PostRest;
import com.curso.react.model.response.UserRest;
import com.curso.react.service.UserService;
import com.curso.react.shared.dto.PostDto;
import com.curso.react.shared.dto.UserDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(){
        //obtenemos la autenticacion del usuario
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Recuperamos el correo del usuario autenticado
        String email = authentication.getPrincipal().toString();
        UserDto userDto = userService.getUser(email);
        UserRest userToReturn = new UserRest();
        
        userToReturn = mapper.map(userDto, UserRest.class);

        return userToReturn; 
    }

    @PostMapping()
    public UserRest createUser(@RequestBody UserDetailRequestModel userDetail) {
      UserRest userToResponse = new UserRest();
      UserDto userDto = new UserDto();
      //Copiamos las propiedades de userDetal en userDto
      BeanUtils.copyProperties(userDetail, userDto);
      UserDto createdUser = userService.createUser(userDto);
      //copiamos las propiedades createdUser en userToResponse
      BeanUtils.copyProperties(createdUser, userToResponse);
      return  userToResponse;
    }

    @GetMapping("/posts")
    public List<PostRest> getPosts() {
        //Recuperamos el Email del usuario en sesion 
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        List<PostDto> posts = userService.getUserPosts(email);
        List<PostRest> postRests = new ArrayList<>();
       for (PostDto postDto : posts) {
          PostRest postRest = mapper.map(postDto, PostRest.class);
          if(postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis()))< 0){
            postRest.setExpired(true);
          }
          postRests.add(postRest);
       }
        return postRests;
    }
    
    
}
