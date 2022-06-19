package com.vifrin.chat.service;

import com.vifrin.chat.dto.MessageDto;
import com.vifrin.chat.exception.ThreadNotFoundException;
import com.vifrin.chat.mapper.MessageMapper;
import com.vifrin.common.entity.User;
import com.vifrin.common.entity.Thread;
import com.vifrin.common.repository.MessageRepository;
import com.vifrin.common.repository.ThreadRepository;
import com.vifrin.common.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Mon, 25/04/2022
 **/

@Service
@Slf4j
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ThreadRepository threadRepository;

    public MessageDto addNewMessage(MessageDto message, String username) {
        User author = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("username not found"));
        Thread thread = threadRepository.findById(message.getThreadId()).orElseThrow(()-> new ThreadNotFoundException());
        return messageMapper.messageToMessageDto(messageRepository.save(messageMapper.mapToMessage(message, author, thread)));
    }

    public List<MessageDto> getMessages(long threadId){
        return  messageMapper.messageToMessageDtos(messageRepository.findAllByThreadId(threadId));
    }
}
