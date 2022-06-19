package com.vifrin.common.repository;

import com.vifrin.common.entity.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 23/12/2021
 **/

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> getByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
