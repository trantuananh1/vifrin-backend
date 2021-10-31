package com.vifrin.auth.mapper;

import com.vifrin.common.entity.Role;
import com.vifrin.common.entity.User;
import com.vifrin.common.payload.UserDto;
import com.vifrin.common.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "isEnabled", source = "user.enabled")
    @Mapping(target = "createdAt", source = "user.createdAt")
    @Mapping(target = "updatedAt", source = "user.updatedAt")
    @Mapping(target = "role", source = "user.role")
    public abstract UserDto userToUserDto(User user);
}
