package com.vifrin.comment.service;

import com.vifrin.comment.mapper.CommentMapper;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.Comment;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.CommentRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.post.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@Service
@Slf4j
public class CommentService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserFeignClient userFeignClient;

    public CommentDto addComment(CommentDto commentDto, String username){
        long postId = commentDto.getPostId();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(postId));
        User user = userRepository.findByUsername(username).get();
        Comment comment = commentMapper.commentDtoToComment(commentDto, post, user);
        comment = commentRepository.save(comment);
        post.getActivity().setCommentsCount(post.getComments().size());
        return commentMapper.commentToCommentDto(comment);
    }

//    public boolean updateComment(){
//
//    }

    public CommentDto getComment(Long commentId, String username){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(commentId));
        return commentMapper.commentToCommentDto(comment);
    }

    public List<CommentDto> getComments(Long postId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentRepository.findAllByPostId(postId, pageable);
        return commentMapper.commentsToCommentDtos(comments);
    }

    public void deleteComment(Long commentId){
        commentRepository
                .findById(commentId)
                .map(comment -> {
                    commentRepository.delete(comment);
//                    postEventSender.sendPostDeleted(post);
                    return comment;
                })
                .orElseThrow(() -> {
                    log.warn("comment not found id {}", commentId);
                    return new ResourceNotFoundException(commentId);
                });
    }
}
