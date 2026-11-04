package io.github.laxlite.UGCExample.service;

import io.github.laxlite.UGCExample.dto.CommentDTO;
import io.github.laxlite.UGCExample.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

// TODO: change popularComment logic (if commentsPerGroups * groupsCount <= count on SQL result, then change return). For authors too
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    // groupNumber - номер пачки
    // groupsCount - общее количество показываемых групп
    // hours - за какое время находятся комментарии
    public List<CommentDTO> getPopularCommentsByRating(int groupsCount, int commentsPerGroup, int groupNumber, int hours){
        if (groupsCount <= 0 || commentsPerGroup <= 0 || groupNumber < 0 || hours <= 0) {
            throw new IllegalArgumentException(
                    "groupsCount, commentsPerGroup, hours must be > 0; groupNumber must be >= 0");
        }
        PageRequest pageRequest = PageRequest.of(groupNumber, commentsPerGroup);
        Instant threshold = Instant.now().minus(Duration.ofHours(hours));

        return commentRepository.findByCreatedAtAfterOrderByRating(threshold, pageRequest)
                .stream()
                .map(comment -> new CommentDTO(
                    comment.getId(), comment.getContent(), comment.getRating()
                ))
                .toList();
    }

    // TODO: можно добавить другие стратегии определения популярного комментария

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }
}
