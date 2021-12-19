package com.vifrin.destination.service;

import com.vifrin.common.dto.DestinationDto;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.repository.DestinationRepository;
import com.vifrin.destination.mapper.DestinationMapper;
import com.vifrin.destination.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Thu, 16/12/2021
 **/

@Service
@Slf4j
public class DestinationService {
    @Autowired
    DestinationRepository destinationRepository;
    @Autowired
    DestinationMapper destinationMapper;

    public DestinationDto createDestination(DestinationDto destinationDto){
        Destination destination = destinationMapper.destinationDtoToDestination(destinationDto);
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

    public List<DestinationDto> search(String key){
        List<Destination> destinations = destinationRepository.search(key);
        return destinationMapper.destinationsToDestinationDtos(destinations);
    }
}
