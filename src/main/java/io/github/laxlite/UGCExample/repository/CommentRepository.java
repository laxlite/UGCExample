package io.github.laxlite.UGCExample.repository;

import io.github.laxlite.UGCExample.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByCreatedAtAfterOrderByRating(Instant threshold, Pageable pageable);
}
