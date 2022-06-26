package com.vifrin.common.repository;

import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByNameContainingIgnoreCase(String key);
    @Query(value = "SELECT * FROM destinations ORDER BY average_score DESC", nativeQuery = true)
    List<Destination> getTopDestinations(Pageable pageable);
}
