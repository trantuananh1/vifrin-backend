package com.vifrin.comment.service;

import com.vifrin.comment.mapper.CommentMapper;
import com.vifrin.comment.messaging.CommentEventSender;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.Comment;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.CommentRepository;
import com.vifrin.common.repository.DestinationRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.common.util.RedisUtil;
import com.vifrin.feign.client.UserFeignClient;
import com.vifrin.post.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author: tranmanhhung
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
    DestinationRepository destinationRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    CommentEventSender commentEventSender;

    public CommentDto addComment(CommentDto commentDto, String username) {
        User user = userRepository.findByUsername(username).get();
        Comment comment = null;
        if (commentDto.getPostId() != null) {
            long postId = commentDto.getPostId();
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException(postId));
            comment = commentMapper.commentDtoToComment(commentDto, post, user);
            post.getActivity().setCommentsCount(post.getActivity().getCommentsCount() + 1);
            postRepository.save(post);
        } else {
            long destinationId = commentDto.getDestinationId();
            Destination destination = destinationRepository.findById(destinationId)
                    .orElseThrow(() -> new ResourceNotFoundException(destinationId));
            comment = commentMapper.commentDtoToComment(commentDto, destination, user);
            destination.getActivity().setCommentsCount(destination.getActivity().getCommentsCount() + 1);
            destinationRepository.save(destination);
        }
        comment = commentRepository.save(comment);
        commentEventSender.sendCommentCreated(comment);
        return commentMapper.commentToCommentDto(comment, RedisUtil.getInstance().getValue(username));
    }

    public CommentDto getComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(commentId));
        return commentMapper.commentToCommentDto(comment, RedisUtil.getInstance().getValue(username));
    }

    public List<CommentDto> getCommentsByPost(Long postId, String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = commentRepository.findAllByPostId(postId, pageable);
        return commentMapper.commentsToCommentDtos(comments, RedisUtil.getInstance().getValue(username));
    }

    public List<CommentDto> getCommentsByDestination(Long destinationId, String username, int page, int size, Optional<Integer> star) {
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = star.isPresent() ?
                commentRepository.findByDestinationIdAndStar(destinationId, star.get(), pageable) :
                commentRepository.findByDestinationId(destinationId, pageable);
        return commentMapper.commentsToCommentDtos(comments, RedisUtil.getInstance().getValue(username));
    }

    public void deleteComment(Long commentId) {
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
