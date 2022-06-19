package com.vifrin.chat.mapper;

import com.vifrin.chat.dto.MessageDto;
import com.vifrin.common.entity.Message;
import com.vifrin.common.entity.User;
import com.vifrin.common.entity.Thread;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Tue, 26/04/2022
 **/
@Mapper(componentModel = "spring")
public abstract class MessageMapper {
    @Mapping(target = "id", source = "message.id")
    @Mapping(target = "content", source = "message.content")
    @Mapping(target = "createdAt", source = "message.createdAt")
    @Mapping(target = "updatedAt", source = "message.updatedAt")
    @Mapping(target = "authorId", source = "message.author.id")
    @Mapping(target = "threadId", source = "message.thread.id")
    public abstract MessageDto messageToMessageDto(Message message);

    public abstract List<MessageDto> messageToMessageDtos(List<Message> messages);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "message.content")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "thread", source = "thread")
    public abstract Message mapToMessage(MessageDto message, User author, Thread thread);
}
