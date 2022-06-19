package com.vifrin.hotel.mapper;

import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.entity.Activity;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.Media;
import com.vifrin.common.payload.DestinationRequest;
import com.vifrin.common.repository.MediaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class DestinationMapper {
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    MediaMapper mediaMapper;

    @Mapping(target = "id", source = "destination.id")
    @Mapping(target = "name", source = "destination.name")
    @Mapping(target = "description", source = "destination.description")
    @Mapping(target = "averageScore", source = "destination.averageScore")
    @Mapping(target = "createdAt", source = "destination.createdAt")
    @Mapping(target = "updatedAt", source = "destination.updatedAt")
    @Mapping(target = "checkInsCount", source = "destination.checkInsCount")
    @Mapping(target = "medias", expression = "java(getMediaDtos(destination))")
    public abstract DestinationDto destinationToDestinationDto(Destination destination);

    public abstract List<DestinationDto> destinationsToDestinationDtos(List<Destination> destinations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "destinationRequest.name")
    @Mapping(target = "description", source = "destinationRequest.description")
    @Mapping(target = "longitude", source = "destinationRequest.longitude")
    @Mapping(target = "latitude", source = "destinationRequest.latitude")
    @Mapping(target = "createdAt",expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "medias", expression = "java(getMedias(destinationRequest))")
    @Mapping(target = "activity", expression = "java(initActivity())")
    public abstract Destination destinationDtoToDestination(DestinationRequest destinationRequest);

    public abstract List<Destination> destinationDtosToDestination(List<DestinationDto> destinationDtos);

    public Activity initActivity(){
        return new Activity();
    }

    public List<Media> getMedias(DestinationRequest destinationRequest){
        List<Long> mediaIds = destinationRequest.getMediaIds();
        return mediaRepository.findAllById(mediaIds);
    }

    List<MediaDto> getMediaDtos(Destination destination){
        List<Media> medias = destination.getMedias();
        return mediaMapper.mediasToMediaDtos(medias);
    }
}
