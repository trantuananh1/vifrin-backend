package com.vifrin.hotel.mapper;

import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.dto.HotelDto;
import com.vifrin.common.dto.HotelDto;
import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.entity.*;
import com.vifrin.common.payload.HotelRequest;
import com.vifrin.common.repository.DestinationRepository;
import com.vifrin.common.repository.MediaRepository;
import com.vifrin.hotel.exception.ResourceNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

@Mapper(componentModel = "spring")
public abstract class HotelMapper {
    @Autowired
    MediaRepository mediaRepository;
    @Autowired
    MediaMapper mediaMapper;

    @Mapping(target = "id", source = "hotel.id")
    @Mapping(target = "name", source = "hotel.name")
    @Mapping(target = "description", source = "hotel.description")
    @Mapping(target = "averageScore", source = "hotel.averageScore")
    @Mapping(target = "longitude", source = "hotel.longitude")
    @Mapping(target = "latitude", source = "hotel.latitude")
    @Mapping(target = "price", source = "hotel.price")
    @Mapping(target = "phone", source = "hotel.phone")
    @Mapping(target = "createdAt", source = "hotel.createdAt")
    @Mapping(target = "updatedAt", source = "hotel.updatedAt")
    @Mapping(target = "medias", expression = "java(getMediaDto(hotel))")
    @Mapping(target = "destinationId", source = "hotel.destination.id")
    public abstract HotelDto hotelToHotelDto(Hotel hotel);

    public abstract List<HotelDto> hotelsToHotelsDto(List<Hotel> hotels);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "hotelRequest.name")
    @Mapping(target = "description", source = "hotelRequest.description")
    @Mapping(target = "longitude", source = "hotelRequest.longitude")
    @Mapping(target = "latitude", source = "hotelRequest.latitude")
    @Mapping(target = "price", source = "hotelRequest.price")
    @Mapping(target = "phone", source = "hotelRequest.phone")
    @Mapping(target = "createdAt",expression = "java(java.time.Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "medias", expression = "java(getMedias(hotelRequest))")
    public abstract Hotel hotelDtoToHotel(HotelRequest hotelRequest);

    public abstract List<Hotel> hotelsDtoToHotel(List<HotelDto> hotelsDto);

    public List<Media> getMedias(HotelRequest hotelRequest){
        List<Long> mediaIds = hotelRequest.getMediaIds();
        return mediaRepository.findAllById(mediaIds);
    }

    List<MediaDto> getMediaDto(Hotel hotel){
        List<Media> medias = hotel.getMedias();
        return mediaMapper.mediasToMediaDtos(medias);
    }
}
