package com.vifrin.like.service;

import com.vifrin.common.dto.LikeDto;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.LikeRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.post.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: trantuananh1
 * @since: Mon, 13/12/2021
 **/

public class LikeService {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    public void addLike(LikeDto likeDto, String username){
        long postId = likeDto.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(postId));
        User user = userRepository.findByUsername(username).get();

    }
}
