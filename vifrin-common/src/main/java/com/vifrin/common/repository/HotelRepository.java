package com.vifrin.common.repository;

import com.vifrin.common.entity.Comment;
import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByNameContainingIgnoreCase(String key);

    List<Hotel> findByDestinationId(Long id);
}
