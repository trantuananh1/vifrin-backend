package com.vifrin.common.repository;

import com.vifrin.common.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Tue, 03/05/2022
 **/

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByThreadId(long threadId);
}
