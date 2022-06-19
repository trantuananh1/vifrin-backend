package com.vifrin.hotel.service;

import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.dto.HotelDto;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.Hotel;
import com.vifrin.common.entity.Media;
import com.vifrin.common.payload.DestinationRequest;
import com.vifrin.common.payload.HotelRequest;
import com.vifrin.common.repository.DestinationRepository;
import com.vifrin.common.repository.HotelRepository;
import com.vifrin.common.repository.MediaRepository;
//import com.vifrin.destination.mapper.HotelMapper;
import com.vifrin.hotel.exception.ResourceNotFoundException;
import com.vifrin.hotel.mapper.HotelMapper;
import com.vifrin.hotel.messaging.CommentEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

@Service
@Slf4j
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelMapper hotelMapper;
    @Autowired
    MediaRepository mediaRepository;

    public HotelDto createHotel(HotelRequest hotelRequest){
        Hotel hotel = hotelMapper.hotelDtoToHotel(hotelRequest);
        hotel = hotelRepository.save(hotel);
        List<Media> medias = hotel.getMedias();
        for (Media media : medias){
            media.setHotel(hotel);
        }
        mediaRepository.saveAll(medias);
        return hotelMapper.hotelToHotelDto(hotel);
    }

    public List<HotelDto> getAllHotel(){
        List<Hotel> hotels = hotelRepository.findAll();
        return hotelMapper.hotelsToHotelsDto(hotels);
    }

    public HotelDto deleteHotel(Long id){
        return hotelMapper.hotelToHotelDto(hotelRepository
            .findById(id)
            .map(comment -> {
                hotelRepository.delete(comment);
                return comment;
            })
            .orElseThrow(() -> {
                log.warn("comment not found id {}", id);
                return new ResourceNotFoundException(id);
            })) ;
    }

    public void deleteAllHotels(){
        hotelRepository.deleteAll();
    }

    public List<HotelDto> search(String key){
        List<Hotel> hotels = hotelRepository.findByNameContainingIgnoreCase(key);
        return hotelMapper.hotelsToHotelsDto(hotels);
    }

    public List<HotelDto> findByDestinationId(Long id){
        List<Hotel> hotels = hotelRepository.findByDestinationId(id);
        return hotelMapper.hotelsToHotelsDto(hotels);
    }
}
