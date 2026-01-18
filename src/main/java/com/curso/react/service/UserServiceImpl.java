package com.curso.react.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.curso.react.entity.PostEntity;
import com.curso.react.entity.UserEntity;
import com.curso.react.exceptions.EmailExistException;
import com.curso.react.repository.PostRepository;
import com.curso.react.repository.UserRepository;
import com.curso.react.shared.dto.PostDto;
import com.curso.react.shared.dto.UserDto;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
   @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
       if(userRepository.findByEmail(userDto.getEmail())!=null){
          throw new EmailExistException("El Usuario ya existe");
          //throw new RuntimeException("El Usuario ya existe");
       }


        System.out.println(userDto.toString());
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        //Generamos un userId aleatorio
        UUID userId = UUID.randomUUID();
        userEntity.setUserId(userId.toString());

        userEntity.setEncryptedPassword(encoder.encode(userDto.getPassword()));
        UserEntity savedUser = userRepository.save(userEntity);
        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(savedUser, userToReturn);
        return userToReturn;
    }

    //*Este metodo es utilizado para el inicio de sesion */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity==null){
            throw new UsernameNotFoundException(email);
        }
        //Retornamos el usuario de Spring que recibe como parametros el email del usuario
        //recuperado desde base de datos, el password encryptado y en este caso un arreglo
        //vacio
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity==null){
          throw new UsernameNotFoundException(email);
        } 
        UserDto userToReturn = new UserDto();
        BeanUtils.copyProperties(userEntity, userToReturn);
        return userToReturn;
    }

    @Override
    public List<PostDto> getUserPosts(String email) {
        //Recuperamos el usuario en sesion
        UserEntity userEntity = userRepository.findByEmail(email);
        List<PostEntity> posts = postRepository.getByUserIdOrderByCreatedAtDesc(userEntity.getId());
        
        List<PostDto> postDtos = new ArrayList<>();

        for (PostEntity post : posts) {
            PostDto postDto = new PostDto();
            postDto = mapper.map(post, PostDto.class);
            postDtos.add(postDto);
        }
        return postDtos;
    }
    
}
