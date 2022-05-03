package com.vifrin.common.repository;

import com.vifrin.common.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author: trantuananh1
 * @since: Tue, 03/05/2022
 **/

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
    @Query(value = "SELECT * FROM threads WHERE (user_id_one=?1 AND user_id_two=?2) OR (user_id_one=?2 AND user_id_two=?1)", nativeQuery = true)
    Thread getThread(long userIdOne, long userIdTwo);
}
