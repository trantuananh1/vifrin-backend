package com.vifrin.user.mapper;

import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.entity.User;
import com.vifrin.common.payload.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "isEnabled", source = "user.enabled")
    @Mapping(target = "createdAt", source = "user.createdAt")
    @Mapping(target = "updatedAt", source = "user.updatedAt")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "avatarUrl", source = "user.avatarUrl")
    @Mapping(target = "bio", source = "user.profile.bio")
    @Mapping(target = "postsCount", source = "user.activity.postsCount")
    @Mapping(target = "followersCount", source = "user.activity.followersCount")
    @Mapping(target = "followingsCount", source = "user.activity.followingsCount")
    public abstract UserDto userToUserDto(User user);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "avatarUrl", source = "user.avatarUrl")
    @Mapping(target = "fullName", expression = "java(getFullName(user))")
    @Mapping(target = "follow", expression = "java(isFollowing(user, iUser))")
    public abstract FollowDto userToFollowDto(User user, User iUser);

    public List<FollowDto> userListToFollowDtoList(List<User> users, User iUser) {
        List<FollowDto> followDtos = new ArrayList<>();
        for (User user : users){
            FollowDto followDto = userToFollowDto(user, iUser);
            followDtos.add(followDto);
        }
        return followDtos;
    }

    String getFullName(User user){
        return user.getProfile().getFullName();
    }

    boolean isFollowing(User user, User iUser){
        return iUser.getFollowings().contains(user);
    }
}
