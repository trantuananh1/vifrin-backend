package com.vifrin.user.mapper;

import com.vifrin.common.dto.FollowDto;
import com.vifrin.common.dto.UserSummary;
import com.vifrin.common.entity.User;
import com.vifrin.common.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "fullName", source = "user.profile.fullName")
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
    @Mapping(target = "isFollowing", expression = "java(isFollowing(user,me))")
    public abstract UserDto userToUserDto(User user, User me);

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
    @Mapping(target = "isFollowing", ignore = true)
    public abstract UserDto userToUserDto(User user);

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "avatarUrl", source = "user.avatarUrl")
    @Mapping(target = "fullName", source = "user.profile.fullName")
    @Mapping(target = "following", expression = "java(isFollowing(user, me))")
    @Mapping(target = "follower", expression = "java(isFollower(user, me))")
    public abstract UserSummary userToUserSummary(User user, User me);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "avatarUrl", source = "user.avatarUrl")
    @Mapping(target = "fullName", expression = "java(getFullName(user))")
    @Mapping(target = "following", expression = "java(isFollowing(user, me))")
    @Mapping(target = "follower", expression = "java(isFollower(user, me))")
    public abstract FollowDto userToFollowDto(User user, User me);

    public List<FollowDto> userListToFollowDtoList(List<User> users, User me) {
        List<FollowDto> followDtos = new ArrayList<>();
        for (User user : users){
            FollowDto followDto = userToFollowDto(user, me);
            followDtos.add(followDto);
        }
        return followDtos;
    }

    String getFullName(User user){
        return user.getProfile().getFullName();
    }

    boolean isFollowing(User user, User me){
        return me.getFollowings().contains(user);
    }
    boolean isFollower(User user, User me){
        return me.getFollowers().contains(user);
    }
}
