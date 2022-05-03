package com.vifrin.chat.mapper;

import com.vifrin.chat.dto.ThreadDto;
import com.vifrin.common.entity.Thread;
import com.vifrin.common.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author: trantuananh1
 * @since: Tue, 03/05/2022
 **/
@Mapper(componentModel = "spring")
public abstract class ThreadMapper {
    @Mapping(target = "id", source = "thread.id")
    @Mapping(target = "userOneFullName", source = "thread.userOne.fullName")
    @Mapping(target = "userTwoFullName", source = "thread.userTwo.fullName")
    public abstract ThreadDto threadToThreadDto(Thread thread);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userOne", source = "userOne")
    @Mapping(target = "userTwo", source = "userTwo")
    public abstract Thread mapToThread(User userOne, User userTwo);
}
