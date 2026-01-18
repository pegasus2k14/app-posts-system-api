package com.curso.react.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.react.entity.ExposureEntity;
import com.curso.react.entity.PostEntity;
import com.curso.react.entity.UserEntity;
import com.curso.react.repository.ExposureRepository;
import com.curso.react.repository.PostRepository;
import com.curso.react.repository.UserRepository;
import com.curso.react.shared.PostCreationDto;
import com.curso.react.shared.dto.PostDto;

@Service
public class PostServiceImpl implements PostService{
    
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ExposureRepository exposureRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public PostDto createPost(PostCreationDto post) {
        //Recuperamos el usuario
        UserEntity userEntity = userRepository.findByEmail(post.getUserEmail());
        //Recuperamos el Exposure
        ExposureEntity exposureEntity = exposureRepository.findById(post.getExposureId()).orElse(null);
        
        PostEntity postEntity = new PostEntity();
        postEntity.setUser(userEntity);
        postEntity.setExposure(exposureEntity);
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setPostId(UUID.randomUUID().toString());
        postEntity.setExpiresAt(new Date(System.currentTimeMillis() + (post.getExpirationTime() * 60000)));
        PostEntity createdPost = postRepository.save(postEntity);
        PostDto postToReturn =  mapper.map(createdPost, PostDto.class);
        return postToReturn;
    }

    @Override
    public List<PostDto> getLastPosts() {
       Long exposureId=2L;
       System.out.println(">> "+new Date(System.currentTimeMillis()));
       List<PostEntity> postEntities = postRepository.getLastPublicPost(exposureId, new Date(System.currentTimeMillis()));
       List<PostDto> postDtos = new ArrayList<>();
       for (PostEntity postEntity : postEntities) {
        PostDto postDto = mapper.map(postEntity, PostDto.class);
        postDtos.add(postDto);
       }
        return postDtos;
    }

    @Override
    public PostDto getPost(String postId) {
        PostEntity postEntity = postRepository.findByPostId(postId);
        PostDto postDto = mapper.map(postEntity, PostDto.class);
        return postDto;
    }

    @Override
    public void deletePost(String postId, Long userId) {
       //Recuperamos el post en base a su id
       PostEntity post = postRepository.findByPostId(postId);
       //Comprobamos si el post pertenece al usuario
       if(post.getUser().getId()!=userId){
        throw new RuntimeException("No se puede realizar esta acción");
       }
       //eliminamos el post
       postRepository.delete(post);
    }

    @Override
    public PostDto updatePost(String postId, Long userId, PostCreationDto postCreationDto) {
        
        //Recuperamos el post en base a su id
        PostEntity post = postRepository.findByPostId(postId);
        //Comprobamos si el post pertenece al usuario
        if(post.getUser().getId()!=userId){
         throw new RuntimeException("No se puede realizar esta acción");
        }
        //Recuperamos el PostExposure desde la BD
        ExposureEntity exposureEntity = exposureRepository.findById(postCreationDto.getExposureId()).get();
        //Seteamos los nuevos valores
        post.setExposure(exposureEntity);
        post.setTitle(postCreationDto.getTitle());
        post.setContent(postCreationDto.getContent());
        post.setExpiresAt(new Date(System.currentTimeMillis() + (postCreationDto.getExpirationTime() * 60000)));
        //Persistimos los cambios
        PostEntity updatedPost = postRepository.save(post);
        PostDto postDtoUpdated = mapper.map(updatedPost, PostDto.class);

        return postDtoUpdated;
    }    
    
}
