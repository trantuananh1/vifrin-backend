package com.vifrin.chat.service;

import com.vifrin.chat.domain.ChatMessage;
import com.vifrin.chat.dto.MessageDto;
import com.vifrin.chat.mapper.MessageMapper;
import com.vifrin.chat.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: trantuananh1
 * @since: Mon, 25/04/2022
 **/

@Service
@Slf4j
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;

    public MessageDto addNewMessage(ChatMessage message, String username){
        return messageMapper.messageToMessageDto(messageRepository.save(messageMapper.mapToMessage(message, username)));
    }
}
