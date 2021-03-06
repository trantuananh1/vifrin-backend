package com.vifrin.destination.service;

import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.Media;
import com.vifrin.common.payload.DestinationRequest;
import com.vifrin.common.repository.DestinationRepository;
import com.vifrin.common.repository.MediaRepository;
import com.vifrin.destination.mapper.DestinationMapper;
import com.vifrin.destination.exception.ResourceNotFoundException;
import com.vifrin.destination.messaging.CommentEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

@Service
@Slf4j
public class DestinationService {
    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    DestinationMapper destinationMapper;
    @Autowired
    MediaRepository mediaRepository;

    public DestinationDto createDestination(DestinationRequest destinationRequest){
        Destination destination = destinationMapper.destinationDtoToDestination(destinationRequest);
        destination = destinationRepository.save(destination);
        List<Media> medias = destination.getMedias();
        for (Media media : medias){
            media.setDestination(destination);
        }
        mediaRepository.saveAll(medias);
        return destinationMapper.destinationToDestinationDto(destination);
    }

    public DestinationDto updateDestination(Long destinationId, DestinationRequest destinationRequest){
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new ResourceNotFoundException(destinationId));
        destination.setName(destinationRequest.getName());
        destination.setDescription(destinationRequest.getDescription());
        destination.setLongitude(destinationRequest.getLongitude());
        destination.setLatitude(destinationRequest.getLatitude());
        destination.setUpdatedAt(Instant.now());
        //update media
        List<Long> oldMediaIds = destination.getMedias().stream().map(Media::getId).collect(Collectors.toList());
        List<Long> newMediaIds = destinationRequest.getMediaIds();
        List<Long> deletedMediaIds = new ArrayList<>(oldMediaIds);
        deletedMediaIds.removeAll(newMediaIds);
        for (Long mediaId : deletedMediaIds){
            mediaRepository.deleteById(mediaId);
        }
        List<Long> addedMediaIds = new ArrayList<>(newMediaIds);
        addedMediaIds.removeAll(oldMediaIds);
        List<Media> addedMedia = mediaRepository.findAllById(addedMediaIds);
        destination.getMedias().addAll(addedMedia);
        for (Media media : addedMedia){
            media.setDestination(destination);
        }
        mediaRepository.saveAll(addedMedia);
        return destinationMapper.destinationToDestinationDto(destinationRepository.save(destination));
    }

    public DestinationDto getDestination(long destinationId){
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(()->new ResourceNotFoundException(destinationId));
        return destinationMapper.destinationToDestinationDto(destinationRepository.save(destination));
    }


    public void deleteDestination(Long destinationId){
        destinationRepository
                .findById(destinationId)
                .map(comment -> {
                    destinationRepository.delete(comment);
                    return comment;
                })
                .orElseThrow(() -> {
                    log.warn("comment not found id {}", destinationId);
                    return new ResourceNotFoundException(destinationId);
                });
    }

    public List<DestinationDto> getAllDestination(){
        List<Destination> destinations = destinationRepository.findAll();
        return destinationMapper.destinationsToDestinationDtos(destinations);
    }

    public List<DestinationDto> getTopDestinations(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Destination> destinations = destinationRepository.getTopDestinations(pageable);
        return destinationMapper.destinationsToDestinationDtos(destinations.stream().filter(destination -> !destination.getMedias().isEmpty()).collect(Collectors.toList()));
    }

    public List<DestinationDto> getRandomDestinations(int page, int size){
        List<DestinationDto> destinationDtos = getTopDestinations(page, size);
        Collections.shuffle(destinationDtos, new Random((long)(Math.random() * destinationDtos.size())));
        return destinationDtos;
    }

    public List<DestinationDto> search(String key){
        List<Destination> destinations = destinationRepository.findByNameContainingIgnoreCase(key);
        return destinationMapper.destinationsToDestinationDtos(destinations);
    }

    public void updateAverageScore(CommentEventPayload payload){
        long destinationId = payload.getTargetId();
        Destination destination = destinationRepository.findById(destinationId).get();
        float currentScore = destination.getAverageScore();
        long commentsCount = destination.getActivity().getCommentsCount();
        float newScore = ((commentsCount - 1) *  currentScore + payload.getStar())/commentsCount;
        newScore = (float) (Math.ceil(newScore * 10) / 10);
        destination.setAverageScore(newScore);
        destinationRepository.save(destination);
    }

    public void removeWrongDestination() {
        List<Destination> destinations = destinationRepository.findAll();
        List<Destination> deletedDestinations = new ArrayList<>();
        for (Destination destination : destinations){
            try {
                Double number = Double.valueOf(destination.getAverageScore());
                if (number.isInfinite() || number.isNaN()){
                    deletedDestinations.add(destination);
                }
            }catch (Exception e){
                deletedDestinations.add(destination);
            }
        }
        for (Destination destination : deletedDestinations){
            this.deleteDestination(destination.getId());
        }
    }
}
