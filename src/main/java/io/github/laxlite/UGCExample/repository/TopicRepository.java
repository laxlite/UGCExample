package io.github.laxlite.UGCExample.repository;

import io.github.laxlite.UGCExample.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
