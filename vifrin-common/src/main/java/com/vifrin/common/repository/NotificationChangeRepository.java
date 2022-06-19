package com.vifrin.common.repository;

import com.vifrin.common.entity.NotificationChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: tranmanhhung
 * @since: Thu, 23/12/2021
 **/

@Repository
public interface NotificationChangeRepository extends JpaRepository<NotificationChange, Long> {
}
