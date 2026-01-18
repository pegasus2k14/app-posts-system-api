package com.curso.react.service;

import java.util.List;

import com.curso.react.shared.PostCreationDto;
import com.curso.react.shared.dto.PostDto;

public interface PostService {
    public PostDto createPost(PostCreationDto post);

    public List<PostDto> getLastPosts();

    public PostDto getPost(String id);

    public void deletePost(String postId, Long userId);

    public PostDto updatePost(String postId, Long userId, PostCreationDto postCreationDto);
}
