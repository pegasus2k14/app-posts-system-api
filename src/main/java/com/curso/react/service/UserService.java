package com.curso.react.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.curso.react.shared.dto.PostDto;
import com.curso.react.shared.dto.UserDto;

public interface UserService extends UserDetailsService{
    public UserDto createUser(UserDto userDto);

    public UserDto getUser(String email);

    public List<PostDto> getUserPosts(String email);
}
