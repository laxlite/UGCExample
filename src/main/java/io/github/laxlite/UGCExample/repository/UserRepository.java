package io.github.laxlite.UGCExample.repository;

import io.github.laxlite.UGCExample.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByCreatedAtAfterOrderByRating(Instant threshold, Pageable pageable);
}
