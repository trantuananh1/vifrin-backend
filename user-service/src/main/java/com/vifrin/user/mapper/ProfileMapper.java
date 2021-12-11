package com.vifrin.user.mapper;

import com.vifrin.common.dto.ProfileDto;
import com.vifrin.common.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author: trantuananh1
 * @since: Wed, 08/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class ProfileMapper {
    @Mapping(target = "userId", source = "profile.userId")
    @Mapping(target = "email", source = "profile.email")
    @Mapping(target = "phoneNumber", source = "profile.phoneNumber")
    @Mapping(target = "bio", source = "profile.bio")
    @Mapping(target = "gender", source = "profile.gender")
    @Mapping(target = "fullName", source = "profile.fullName")
    @Mapping(target = "dateOfBirth", source = "profile.dateOfBirth")
    @Mapping(target = "country", source = "profile.country")
    public abstract ProfileDto profileToProfileDto(Profile profile);

    @Mapping(target = "userId", source = "profile.userId")
    @Mapping(target = "email", source = "profile.email")
    @Mapping(target = "phoneNumber", source = "profile.phoneNumber")
    @Mapping(target = "bio", source = "profile.bio")
    @Mapping(target = "gender", source = "profile.gender")
    @Mapping(target = "fullName", source = "profile.fullName")
    @Mapping(target = "dateOfBirth", source = "profile.dateOfBirth")
    @Mapping(target = "country", source = "profile.country")
    public abstract Profile profileDtoToProfile(ProfileDto profile);
}