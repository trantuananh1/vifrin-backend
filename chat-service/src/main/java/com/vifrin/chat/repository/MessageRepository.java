package com.vifrin.chat.repository;

import com.vifrin.chat.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: trantuananh1
 * @since: Mon, 25/04/2022
 **/

public interface MessageRepository extends JpaRepository<Message, Long> {
}
