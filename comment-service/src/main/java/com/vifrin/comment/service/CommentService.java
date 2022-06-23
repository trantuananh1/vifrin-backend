package com.vifrin.comment.service;

import com.vifrin.comment.mapper.CommentMapper;
import com.vifrin.comment.messaging.CommentEventSender;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.StatisticRatingDto;
import com.vifrin.common.entity.*;
import com.vifrin.common.repository.*;
import com.vifrin.common.util.RedisUtil;
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
    HotelRepository hotelRepository;
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
        } else if (commentDto.getDestinationId() != null) {
            long destinationId = commentDto.getDestinationId();
            Destination destination = destinationRepository.findById(destinationId)
                    .orElseThrow(() -> new ResourceNotFoundException(destinationId));
            comment = commentMapper.commentDtoToComment(commentDto, destination, user);
            destination.getActivity().setCommentsCount(destination.getActivity().getCommentsCount() + 1);
            destinationRepository.save(destination);
        } else {
            long hotelId = commentDto.getHotelId();
            Hotel hotel = hotelRepository.findById(hotelId)
                    .orElseThrow(() -> new ResourceNotFoundException(hotelId));
            comment = commentMapper.commentDtoToComment(commentDto, hotel, user);
            hotel.getActivity().setCommentsCount(hotel.getActivity().getCommentsCount() + 1);
            hotelRepository.save(hotel);
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

    public List<CommentDto> getCommentsByHotel(Long hotelId, String username, int page, int size, Optional<Integer> star) {
        Pageable pageable = PageRequest.of(page, size);
        List<Comment> comments = star.isPresent() ?
                commentRepository.findByHotelIdAndStar(hotelId, star.get(), pageable) :
                commentRepository.findByHotelId(hotelId, pageable);
        return commentMapper.commentsToCommentDtos(comments, RedisUtil.getInstance().getValue(username));
    }

    public StatisticRatingDto getStatRating(long hotelId) {
        StatisticRatingDto statisticRatingDto = new StatisticRatingDto();
        statisticRatingDto.setOneStar(commentRepository.findByHotelIdAndStar(hotelId, 1, null).size());
        statisticRatingDto.setTwoStar(commentRepository.findByHotelIdAndStar(hotelId, 2, null).size());
        statisticRatingDto.setThreeStar(commentRepository.findByHotelIdAndStar(hotelId, 3, null).size());
        statisticRatingDto.setFourStar(commentRepository.findByHotelIdAndStar(hotelId, 4, null).size());
        statisticRatingDto.setFiveStar(commentRepository.findByHotelIdAndStar(hotelId, 5, null).size());
        return statisticRatingDto;
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
