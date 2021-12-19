package com.vifrin.common.repository;

import com.vifrin.common.entity.Destination;
import com.vifrin.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Thu, 16/12/2021
 **/

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    @Query(value = "SELECT * FROM destinations d WHERE d.name LIKE %?1%", nativeQuery = true)
    List<Destination> search(String key);
}
