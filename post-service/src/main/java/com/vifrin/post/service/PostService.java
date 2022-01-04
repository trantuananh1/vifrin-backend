package com.vifrin.post.service;

import com.vifrin.common.constant.OperationConstant;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.Media;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import com.vifrin.common.dto.PostDto;
import com.vifrin.common.payload.PostRequest;
import com.vifrin.common.repository.DestinationRepository;
import com.vifrin.common.repository.MediaRepository;
import com.vifrin.common.repository.PostRepository;
import com.vifrin.common.repository.UserRepository;
import com.vifrin.common.util.RedisUtil;
import com.vifrin.post.exception.NotAllowedException;
import com.vifrin.post.exception.ResourceNotFoundException;
import com.vifrin.post.mapper.PostMapper;
import com.vifrin.post.messaging.PostEventSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@Service
@Slf4j
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    private PostEventSender postEventSender;
    @Autowired
    PostMapper postMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    DestinationRepository destinationRepository;

    public PostDto createPost(PostRequest postRequest, String username) {
        log.info("creating post image urls {}", postRequest.getMediaIds());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(""));
        List<Long> mediaIds = postRequest.getMediaIds();
        List<Media> medias = mediaRepository.findAllById(mediaIds);
        Destination destination = null;
        if (postRequest.getDestinationId() != null && postRequest.getDestinationId() != 0) {
            destination = destinationRepository.findById(postRequest.getDestinationId()).get();
        }
        Post post = new Post(postRequest.getContent(), medias, postRequest.getConfig(), user, destination);
        post = postRepository.save(post);

        //update postId of media
        for (Media media : medias) {
            media.setPost(post);
        }
        mediaRepository.saveAll(medias);

        log.info("post {} is saved successfully for user {}",
                post.getId(), post.getUser().getUsername());


        user.getActivity().setPostsCount(user.getPosts().size());
        userRepository.save(user);

        if (destination!=null) {
            destination.setCheckInsCount(destination.getCheckInsCount() + 1);
            destinationRepository.save(destination);
        }

        postEventSender.sendPostCreated(post);
        return postMapper.postToPostDto(post, RedisUtil.getInstance().getValue(username));
    }

    public PostDto getPost(Long postId, String username) {
        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> {
                    log.warn("post not found id {}", postId);
                    return new ResourceNotFoundException(String.valueOf(postId));
                });
        return postMapper.postToPostDto(post, RedisUtil.getInstance().getValue(username));
    }

    public PostDto updatePost(PostRequest postRequest, Long postId, String username) {
        log.info("updating post {}", postId);
        postRepository
                .findById(postId)
                .map(post -> {
                    if (!post.getUser().getUsername().equals(username)) {
                        throw new NotAllowedException(username, String.valueOf(postId), OperationConstant.UPDATE);
                    }
                    post.setContent(postRequest.getContent());
                    post.setConfig(postRequest.getConfig());
                    //update media
                    List<Media> oldMedias = post.getMedias();
                    for (Media media : oldMedias) {
                        media.setPost(null);
                    }
                    mediaRepository.saveAll(oldMedias);
                    List<Long> mediaIds = postRequest.getMediaIds();
                    List<Media> medias = mediaRepository.findAllById(mediaIds);
                    post.setMedias(medias);
                    for (Media media : medias) {
                        media.setPost(post);
                    }
                    mediaRepository.saveAll(medias);
                    //update destination
                    if  (postRequest.getDestinationId()!=null && !Objects.equals(postRequest.getDestinationId(), post.getDestination().getId())){
                        Destination destination = destinationRepository.findById(postRequest.getDestinationId())
                                        .orElseThrow(() -> new ResourceNotFoundException(postRequest.getDestinationId()));
                        post.setDestination(destination);
                        destination.setCheckInsCount(destination.getCheckInsCount() + 1);
                        destinationRepository.save(destination);
                    }
                    postRepository.save(post);
                    return post;
                })
                .orElseThrow(() -> {
                    log.warn("post not found id {}", postId);
                    return new ResourceNotFoundException(String.valueOf(postId));
                });
        Post post = postRepository.findById(postId).get();
        return postMapper.postToPostDto(post, RedisUtil.getInstance().getValue(username));
    }

    public void deletePost(Long postId, String username) {
        log.info("deleting post {}", postId);
        postRepository
                .findById(postId)
                .map(post -> {
                    if (!post.getUser().getUsername().equals(username)) {
                        log.warn("user {} is not allowed to delete post id {}", username, postId);
                        throw new NotAllowedException(username, String.valueOf(postId), OperationConstant.DELETE);
                    }
                    postRepository.delete(post);
                    postEventSender.sendPostDeleted(post);
                    return post;
                })
                .orElseThrow(() -> {
                    log.warn("post not found id {}", postId);
                    return new ResourceNotFoundException(String.valueOf(postId));
                });
    }

    public List<PostDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(username));
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        String token = RedisUtil.getInstance().getValue(username);
        return postMapper.postsToPostDtos(posts, token);
    }

    public List<Post> postsByIdIn(List<String> ids) {
//        return postRepository.findByIdInOrderByCreatedAtDesc(ids);
        return null;
    }
}
