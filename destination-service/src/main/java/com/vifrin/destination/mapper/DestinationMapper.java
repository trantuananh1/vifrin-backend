package com.vifrin.destination.mapper;

import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.entity.Comment;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: trantuananh1
 * @since: Thu, 16/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class DestinationMapper {

    @Mapping(target = "id", source = "destination.id")
    @Mapping(target = "name", source = "destination.name")
    @Mapping(target = "description", source = "destination.description")
    @Mapping(target = "averageScore", source = "destination.averageScore")
    @Mapping(target = "createdAt", source = "destination.createdAt")
    @Mapping(target = "updatedAt", source = "destination.updatedAt")
    public abstract DestinationDto destinationToDestinationDto(Destination destination);

    public abstract List<DestinationDto> destinationsToDestinationDtos(List<Destination> destinations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "destinationDto.name")
    @Mapping(target = "description", source = "destinationDto.description")
    @Mapping(target = "longitude", source = "destinationDto.longitude")
    @Mapping(target = "latitude", source = "destinationDto.latitude")
    @Mapping(target = "createdAt",expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    public abstract Destination destinationDtoToDestination(DestinationDto destinationDto);

    public abstract List<Destination> destinationDtosToDestination(List<DestinationDto> destinationDtos);
}
