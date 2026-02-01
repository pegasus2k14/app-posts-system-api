package com.curso.react.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.react.model.request.PostCreateRequestModel;
import com.curso.react.model.response.OperationStatusModel;
import com.curso.react.model.response.PostRest;
import com.curso.react.service.PostService;
import com.curso.react.service.UserService;
import com.curso.react.shared.PostCreationDto;
import com.curso.react.shared.dto.PostDto;
import com.curso.react.shared.dto.UserDto;
import com.curso.react.util.Exposures;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/posts")
public class PostController {
  @Autowired
  private PostService postService;
  @Autowired
  private ModelMapper mapper;
  @Autowired
  private UserService userService;
    
    @PostMapping()
    public PostRest createPost(@RequestBody @Valid PostCreateRequestModel  createRequestModel){
      //Recuperamos el usuario que se encuentra en sesion
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String mail = authentication.getPrincipal().toString();
      //Mapeamos el objeto recibido en un DTO
      PostCreationDto postCreationDto = mapper.map(createRequestModel, PostCreationDto.class);
      postCreationDto.setUserEmail(mail);
      //Creamos el Post
      PostDto postDto = postService.createPost(postCreationDto); 
      
      PostRest postToReturn =  mapper.map(postDto, PostRest.class);
      //Si la fecha de expiracion es menor que la actual
      if(postToReturn.getExpiresAt().compareTo(new Date(System.currentTimeMillis()))< 0){
          postToReturn.setExpired(true);  //el post esta expirado  
      }
      
      return postToReturn;
    } 

    //Endpoint para obtener los ultimos  20 post
    @GetMapping("/last")
    public List<PostRest> lastPosts(){
      List<PostDto> posts = postService.getLastPosts();
      System.out.println(posts.toString());
      List<PostRest> postRests = new ArrayList<>();
      for(PostDto post:posts){
        PostRest postRest = mapper.map(post, PostRest.class);
        postRests.add(postRest);
      }
      return postRests;
    } 

    @GetMapping("/{id}")
    public PostRest getPost(@PathVariable String id){
      PostDto post =  postService.getPost(id);
      PostRest postRest = mapper.map(post, PostRest.class);
      //Comprobamos si el Post ya expiro
      //Si la fecha de expiracion es menor que la actual
      if(postRest.getExpiresAt().compareTo(new Date(System.currentTimeMillis()))< 0){
        postRest.setExpired(true);  //el post esta expirado  
      }
      //Comprobamos si el Post es privado (1 es privado,) o si el post expiro
      if(postRest.getExposure().getId()==Exposures.PRIVATE || postRest.getExpired()){
          //Recuperamos el authentication
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          //recuperamos el email del usuario en sesion
          String email = authentication.getPrincipal().toString();
          //Recuperamos el usuario
          UserDto userDto = userService.getUser(email);
          //Comprobamos si el Id del usuario es igual al userId del Post
          if(userDto.getUserId()!=post.getUser().getUserId()){
             throw new RuntimeException("No tienes permisos para realizar esta accion");
          }

      }
      return postRest;
    }
    
    //Recibe como parametro el Id del Post a eliminar
    @DeleteMapping("/{id}")
    public OperationStatusModel deletePost(@PathVariable String id){
      //Recuperamos el Authentication a partir del contexto
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      //Recuperamos el usuario en sesion a partir de su correo
      UserDto user = userService.getUser(authentication.getPrincipal().toString());
      postService.deletePost(id, user.getId()); //pasamos el id del Post y del user
      
      OperationStatusModel operationStatusModel = new OperationStatusModel("DELETE", "SUCCESS");
      return operationStatusModel;
    }

       //Recibe como parametro el Id del Post a eliminar
       @PutMapping("/{id}")
       public PostRest updatePost(@RequestBody @Valid PostCreateRequestModel requestModel , @PathVariable String id){
         //Recuperamos el Authentication a partir del contexto
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         //Recuperamos el usuario en sesion a partir de su correo
         UserDto user = userService.getUser(authentication.getPrincipal().toString());
         
         PostCreationDto postUpdateDto = mapper.map(requestModel, PostCreationDto.class);
         //modificamos el post (id del post, id del user, objeto Post con los cambios)
         PostDto updatedPostDto = postService.updatePost(id, user.getId(), postUpdateDto);
         PostRest updatedPost = mapper.map(updatedPostDto, PostRest.class);

         return updatedPost;
       }
}
