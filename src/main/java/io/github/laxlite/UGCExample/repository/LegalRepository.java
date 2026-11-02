package io.github.laxlite.UGCExample.repository;

import io.github.laxlite.UGCExample.model.LegalDocument;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LegalRepository extends JpaRepository<LegalDocument, Long> {
    Optional<LegalDocument> findBySlug(String slug);
}
