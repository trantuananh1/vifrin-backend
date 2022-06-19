package com.vifrin.like.service;

import com.vifrin.common.dto.LikeDto;
import com.vifrin.common.entity.Like;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.LikeRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.common.util.RedisUtil;
import com.vifrin.like.exception.ResourceNotFoundException;
import com.vifrin.like.mapper.LikeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: tranmanhhung
 * @since: Mon, 13/12/2021
 **/

@Service
@Slf4j
public class LikeService {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LikeMapper likeMapper;

    public boolean addLike(LikeDto likeDto, String username){
        try {
            long postId = likeDto.getPostId();
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException(postId));
            User user = userRepository.findByUsername(username).get();

            Optional<Like> likeOptional = likeRepository.findByUserIdAndPostId(user.getId(), postId);
            if (likeOptional.isPresent()){
                likeRepository.delete(likeOptional.get());
            } else {
                likeRepository.save(likeMapper.likeDtoToLike(likeDto, post, user));
            }

            post.getActivity().setLikesCount(post.getLikes().size());
            postRepository.save(post);
            user.getActivity().setLikesCount(user.getLikes().size());
            userRepository.save(user);
            return true;
        } catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    public List<LikeDto> getLikes(Long postId, String username, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Like> likes = likeRepository.getLikesByPostId(postId, pageable);
        String token = RedisUtil.getInstance().getValue(username);
        return likeMapper.likesToLikeDtos(likes, token);
    }
}
