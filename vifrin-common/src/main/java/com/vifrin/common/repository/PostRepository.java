package com.vifrin.common.repository;

import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author: tranmanhhung
 * @since: Mon, 06/12/2021
 **/

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    List<Post> findByUser(User user, Sort sort);
//    List<Post> findByUserOrderByCreatedAtDesc(User user);
//    List<Post> findByUsernameOrderByCreatedAtDesc(String username);
//    List<Post> findByIdInOrderByCreatedAtDesc(List<String> ids);
    List<Post> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query(value = "select * from posts p order by p.comments_count DESC LIMIT ?1", nativeQuery = true)
    List<Post> getPostOrderByCommentsCountDesc(int size);


}
