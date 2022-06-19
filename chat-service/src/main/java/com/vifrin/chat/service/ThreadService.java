package com.vifrin.chat.service;

import com.vifrin.chat.dto.ThreadDto;
import com.vifrin.chat.exception.ThreadExistException;
import com.vifrin.chat.mapper.ThreadMapper;
import com.vifrin.common.entity.User;
import com.vifrin.common.repository.ThreadRepository;
import com.vifrin.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: tranmanhhung
 * @since: Tue, 03/05/2022
 **/

@Service
public class ThreadService {
    @Autowired
    ThreadRepository threadRepository;
    @Autowired
    ThreadMapper threadMapper;
    @Autowired
    UserRepository userRepository;

    public ThreadDto createThread(ThreadDto threadDto) {
        User userOne = userRepository.findByUsername(threadDto.getUserOneFullName()).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        User userTwo = userRepository.findByUsername(threadDto.getUserTwoFullName()).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        if (threadRepository.getThread(userOne.getId(), userTwo.getId())!=null){
            throw new ThreadExistException();
        }
        return threadMapper.threadToThreadDto(threadRepository.save(threadMapper.mapToThread(userOne, userTwo)));
    }

    public ThreadDto getThread(long userIdOne, long userIdTwo){
        return threadMapper.threadToThreadDto(threadRepository.getThread(userIdOne, userIdTwo));
    }
}
