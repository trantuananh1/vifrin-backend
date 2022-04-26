package com.vifrin.chat.mapper;

import com.vifrin.chat.domain.ChatMessage;
import com.vifrin.chat.dto.MessageDto;
import com.vifrin.chat.entity.Message;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.entity.Comment;
import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: trantuananh1
 * @since: Tue, 26/04/2022
 **/

@Mapper(componentModel = "spring")
public abstract class MessageMapper {
    @Mapping(target = "id", source = "message.id")
    @Mapping(target = "content", source = "message.content")
    @Mapping(target = "createdAt", source = "message.createdAt")
    @Mapping(target = "updatedAt", source = "message.updatedAt")
    @Mapping(target = "username", source = "message.username")
    public abstract MessageDto messageToMessageDto(Message message);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "message.content")
    @Mapping(target = "createdAt",expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "username", source = "message.username")
    public abstract Message mapToMessage(ChatMessage message, String username);
}
