package com.vifrin.common.repository;

import com.vifrin.common.entity.NotificationObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@Repository
public interface NotificationObjectRepository extends JpaRepository<NotificationObject, Long> {
}
